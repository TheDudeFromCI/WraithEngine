package net.whg.we.net.server;

import java.io.IOException;
import net.whg.we.net.IDataHandler;

/**
 * A standard implementation for a TCP, multithreaded server.
 */
public class ServerSocket implements IServer
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
    public void start(int port) throws IOException
    {
        if (isRunning())
            throw new IllegalStateException("Server is already running!");

        socketListener = new SocketListener(port, clientHandler, dataHandler);
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
}
