package net.whg.we.net.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.whg.we.net.IDataHandler;
import net.whg.we.net.IServerSocket;

/**
 * A standard implementation for a TCP, multithreaded server.
 */
public class SimpleServer implements IServer
{
    private IClientHandler clientHandler;
    private IDataHandler dataHandler;
    private SocketListener socketListener;

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

        socket.start(port);
        socketListener = new SocketListener(socket, clientHandler, dataHandler);
    }

    @Override
    public void stop()
    {
        if (!isRunning())
            return;

        socketListener.stop();
        socketListener = null;
    }

    @Override
    public boolean isRunning()
    {
        return socketListener != null && !socketListener.isClosed();
    }

    @Override
    public List<IConnectedClient> getConnectedClients()
    {
        if (isRunning())
            return socketListener.getConnectedClients();

        return new ArrayList<>();
    }

    @Override
    public void handlePackets()
    {
        if (isRunning())
            socketListener.handlePackets();
    }
}
