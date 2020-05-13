package net.whg.we.net.server;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import net.whg.we.net.ConnectionData;
import net.whg.we.net.IDataHandler;
import net.whg.we.net.IPacket;
import net.whg.we.net.ISocket;

/**
 * A basic implementation of the connection client interface.
 */
public class ServerClient implements IConnectedClient
{
    private final ISocket socket;
    private final ConnectionData connection;
    private final IDataHandler dataHandler;
    private final DataInput dataInput;
    private final DataOutput dataOutput;

    /**
     * Creates a new server client wrapper.
     * 
     * @param socket
     *     - The socket this client is wrapping.
     * @param dataHandler
     *     - The data handler for parsing byte data.
     * @throws IOException
     */
    public ServerClient(ISocket socket, IDataHandler dataHandler) throws IOException
    {
        this.socket = socket;
        this.dataHandler = dataHandler;

        this.dataInput = new DataInputStream(socket.getInputStream());
        this.dataOutput = new DataOutputStream(socket.getOutputStream());

        var ip = socket.getIP();
        var port = socket.getPort();
        connection = new ConnectionData(ip, port);
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
        return dataHandler.readPacket(dataInput, this);
    }

    @Override
    public void sendPacket(IPacket packet) throws IOException
    {
        dataHandler.writePacket(dataOutput, packet);
    }
}
