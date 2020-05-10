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

public class SocketListener
{
    private static final Logger logger = LoggerFactory.getLogger(SocketListener.class);

    private final List<IConnectedClient> connectedClients = Collections.synchronizedList(new ArrayList<>());
    private final BlockingQueue<IPacket> packets = new ArrayBlockingQueue<>(256);
    private final IServerSocket socket;
    private final IClientHandler clientHandler;
    private final IDataHandler dataHandler;

    /**
     * Creates a new socket listener.
     * 
     * @param socket
     *     - The server socket backing this listener.
     * @param clientHandler
     *     - The client handler object.
     * @param dataHandler
     *     - The data handler object.
     * @throws IOException
     *     If there is an error opening the server socket.
     */
    public SocketListener(IServerSocket socket, IClientHandler clientHandler, IDataHandler dataHandler)
            throws IOException
    {
        this.clientHandler = clientHandler;
        this.dataHandler = dataHandler;
        this.socket = socket;

        var thread = new Thread(new ListenerThread());
        thread.setName("server-thread");
        thread.setDaemon(false);
        thread.start();
    }

    /**
     * Returns a read-only list of all connected clients. This method allocates
     * memory, so it is recommended to store the list for reuse if frequent access
     * is required.
     * 
     * @return The list of connected clients.
     */
    public List<IConnectedClient> getConnectedClients()
    {
        return Collections.unmodifiableList(connectedClients);
    }

    /**
     * Closes the active server socket and kicks all connected clients.
     */
    public void stop()
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
     * Checks whether or not the server socket is closed.
     */
    public boolean isClosed()
    {
        return socket.isClosed();
    }

    /**
     * Processes all queued packets. <br>
     * <br>
     * Note: Additional packets received while handling are not processed until the
     * next frame to avoid basic packet overloading, stalling frames for extended
     * periods of time.
     */
    public void handlePackets()
    {
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
                logger.info("Started server socket on port {}.", socket.getLocalPort());

                while (true)
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
            catch (SocketException e)
            {
                // Socket closed normally.
                logger.info("Closed server socket.");
            }
            catch (Exception e)
            {
                logger.error("Failed to accept client socket!", e);
            }
            finally
            {
                try
                {
                    if (!socket.isClosed())
                        socket.close();
                }
                catch (IOException e)
                {
                    logger.error("Failed to close server socket!", e);
                }
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

                while (true)
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
