package net.whg.we.net;

/**
 * A data object which was received over a network socket to be handled.
 */
public interface IPacket
{
    /**
     * Called from the main thread to handle this packet.
     */
    void handle();

    /**
     * Gets the socket which sent this socket. public
     * 
     * @return The packet sender, or null if this packet does not have a sender.
     *     (I.e. Generated to be sent.)
     */
    IPacketSender getSender();
}
