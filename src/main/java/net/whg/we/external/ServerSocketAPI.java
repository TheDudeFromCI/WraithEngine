package net.whg.we.external;

import java.io.IOException;
import java.net.ServerSocket;
import net.whg.we.net.IServerSocket;
import net.whg.we.net.ISocket;

public class ServerSocketAPI implements IServerSocket
{
    private ServerSocket socket;

    @Override
    public void close() throws IOException
    {
        if (!isClosed())
            socket.close();
    }

    @Override
    public void start(int port) throws IOException
    {
        if (isClosed())
            throw new IOException("Server socket not open!");

    }

    @Override
    public ISocket waitForClient() throws IOException
    {
        if (isClosed())
            throw new IOException("Server socket not open!");

        return new SocketAPI(socket.accept());
    }

    @Override
    public boolean isClosed()
    {
        return socket == null || socket.isClosed();
    }

    @Override
    public int getLocalPort()
    {
        if (isClosed())
            throw new IllegalStateException();

        return socket.getLocalPort();
    }
}
