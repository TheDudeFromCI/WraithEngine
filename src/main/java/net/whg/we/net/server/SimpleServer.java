package net.whg.we.net.server;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.whg.we.net.IDataHandler;
import net.whg.we.net.IPacket;
import net.whg.we.net.IServerSocket;

/**
 * A standard implementation for a TCP, multithreaded server.
 */
public class SimpleServer implements IServer
{
    private static final Logger logger = LoggerFactory.getLogger(SimpleServer.class);

    private final List<IConnectedClient> connectedClients = Collections.synchronizedList(new ArrayList<>());
    private final BlockingQueue<IPacket> packets = new ArrayBlockingQueue<>(256);

    private IClientHandler clientHandler;
    private IDataHandler dataHandler;
    private IServerSocket socket;

    private volatile boolean activeServerThread;

    @Override
    public void setClientHandler(IClientHandler handler)
    {
        if (isRunning())
            throw new IllegalStateException("Server is running!");

        clientHandler = handler;
    }

    @Override
    public void setDataHandler(IDataHandler handler)
    {
        if (isRunning())
            throw new IllegalStateException("Server is running!");

        dataHandler = handler;
    }

    @Override
    public void start(IServerSocket socket, int port) throws IOException
    {
        if (isRunning())
            throw new IllegalStateException("Server is already running!");

        if (!socket.isClosed())
            throw new IllegalArgumentException("Server socket already open!");

        this.socket = socket;
        startServerSocket(port);
    }

    private void startServerSocket(int port) throws IOException
    {
        socket.start(port);

        logger.info("Started server socket on port {}.", socket.getLocalPort());

        activeServerThread = true;
        var thread = new Thread(new ListenerThread());
        thread.setName("server-thread");
        thread.setDaemon(false);
        thread.start();
    }

    @Override
    public void stop()
    {
        if (!isRunning())
            return;

        activeServerThread = false;
        kickAllClients();
        tryCloseSocket();
        packets.clear();
    }

    private void tryCloseSocket()
    {
        try
        {
            socket.close();
        }
        catch (IOException e)
        {
            logger.error("Failed to close server socket!", e);
        }
    }

    /**
     * Kicks all clients currently connected.
     */
    public void kickAllClients()
    {
        while (!connectedClients.isEmpty())
        {
            var client = connectedClients.get(0);

            try
            {
                client.kick();
            }
            catch (IOException e)
            {
                var ip = client.getConnection()
                               .getIP();

                logger.error("Failed to kick client {}!", e, ip);
            }
        }
    }

    @Override
    public boolean isRunning()
    {
        return socket != null && !socket.isClosed();
    }

    @Override
    public List<IConnectedClient> getConnectedClients()
    {
        return Collections.unmodifiableList(connectedClients);
    }

    @Override
    public void handlePackets()
    {
        if (!isRunning())
            return;

        int count = packets.size();
        for (int i = 0; i < count; i++)
        {
            var packet = packets.poll();
            packet.handle();
        }
    }

    /**
     * The thread in charge of listening for incoming client connections.
     */
    private class ListenerThread implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
                listenForClients();
            }
            catch (SocketException e)
            {
                logger.info("Closed server socket.");
            }
            catch (Exception e)
            {
                logger.error("Failed to accept client socket!", e);
            }
            finally
            {
                tryCloseSocket();
            }
        }

        private void listenForClients() throws IOException
        {
            while (activeServerThread)
            {
                var s = socket.waitForClient();
                var client = new ServerClient(s, dataHandler);

                var ip = s.getIP();
                logger.info("Client '{}' connected.", ip);

                var thread = new Thread(new ClientThread(client));
                thread.setName("client-" + ip);
                thread.setDaemon(true);
                thread.start();
            }
        }
    }

    /**
     * The thread in charge of receiving incoming data from clients.
     */
    private class ClientThread implements Runnable
    {
        private final IConnectedClient client;

        public ClientThread(IConnectedClient client)
        {
            this.client = client;
        }

        @Override
        public void run()
        {
            connectedClients.add(client);

            try
            {
                clientHandler.onClientConnected(client);

                while (activeServerThread)
                    packets.put(client.readPacket());
            }
            catch (SocketException e)
            {
                // Client disconnected
            }
            catch (Exception e)
            {
                logger.error("Failed to read incoming packet!", e);
            }
            finally
            {
                try
                {
                    client.kick();
                }
                catch (IOException e)
                {
                    logger.error("Failed to close server socket!", e);
                }

                clientHandler.onClientDisconnected(client);
                connectedClients.remove(client);
            }
        }
    }
}
