package net.whg.we.net.server;

import java.io.IOException;
import net.whg.we.net.ConnectionData;
import net.whg.we.net.IPacket;
import net.whg.we.net.IPacketSender;

/**
 * Represents a connected client socket within a server.
 */
public interface IConnectedClient extends IPacketSender
{
    /**
     * Gets details about the client connection.
     * 
     * @return The connection details.
     */
    ConnectionData getConnection();

    /**
     * Kicks this client from the server.
     * 
     * @throws IOException
     *     If an exception occurs while preforming this action.
     */
    void kick() throws IOException;

    /**
     * Called from the client thread to retrieve the next packet which was received
     * by this client.
     * 
     * @return The next packet.
     * @throws IOException
     *     If an exception occurs while preforming this action.
     */
    IPacket readPacket() throws IOException;
}
