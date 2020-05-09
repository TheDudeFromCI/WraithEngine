package net.whg.we.net.server;

import java.io.IOException;
import java.net.Socket;
import net.whg.we.net.ConnectionData;
import net.whg.we.net.IDataHandler;
import net.whg.we.net.IPacket;

/**
 * A basic implementation of the connection client interface.
 */
public class ServerClient implements IConnectedClient
{
    private final Socket socket;
    private final ConnectionData connection;
    private final IDataHandler dataHandler;

    /**
     * Creates a new server client wrapper.
     * 
     * @param socket
     *     - The socket this client is wrapping.
     * @param dataHandler
     *     - The data handler for parsing byte data.
     */
    public ServerClient(Socket socket, IDataHandler dataHandler)
    {
        this.socket = socket;
        this.dataHandler = dataHandler;

        var ip = socket.getInetAddress();
        var port = socket.getPort();
        connection = new ConnectionData(ip.toString(), port);
    }

    @Override
    public ConnectionData getConnection()
    {
        return connection;
    }

    @Override
    public void kick() throws IOException
    {
        socket.close();
    }

    @Override
    public IPacket readPacket() throws IOException
    {
        return dataHandler.readPacket(socket.getInputStream());
    }

    @Override
    public void writePacket(IPacket packet) throws IOException
    {
        dataHandler.writePacket(socket.getOutputStream(), packet);
    }
}
