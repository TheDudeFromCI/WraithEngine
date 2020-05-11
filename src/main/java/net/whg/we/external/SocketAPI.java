package net.whg.we.external;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import net.whg.we.net.ISocket;

/**
 * An implementation of the ISocket API.
 */
public class SocketAPI implements ISocket
{
    private static final String SOCKET_NOT_OPEN_WARNING = "Socket not open!";

    private Socket socket;

    /**
     * Creates a new socket API.
     */
    public SocketAPI()
    {
        this(null);
    }

    /**
     * Creates a new socket API which wraps an existing Java socket.
     */
    public SocketAPI(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void close() throws IOException
    {
        if (!isClosed())
            socket.close();
    }

    @Override
    public void connect(String ip, int port) throws IOException
    {
        if (!isClosed())
            throw new IllegalStateException("Socket already open!");

        socket = new Socket(ip, port);
    }

    @Override
    public InputStream getInputStream() throws IOException
    {
        if (isClosed())
            throw new IOException(SOCKET_NOT_OPEN_WARNING);

        return socket.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException
    {
        if (isClosed())
            throw new IOException(SOCKET_NOT_OPEN_WARNING);

        return socket.getOutputStream();
    }

    @Override
    public boolean isClosed()
    {
        return socket == null || socket.isClosed();
    }

    @Override
    public String getIP()
    {
        if (isClosed())
            return "";

        return socket.getInetAddress()
                     .toString();
    }

    @Override
    public int getPort()
    {
        if (isClosed())
            return -1;

        return socket.getPort();
    }
}
