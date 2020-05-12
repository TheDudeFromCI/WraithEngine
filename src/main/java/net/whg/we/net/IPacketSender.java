package net.whg.we.net;

import java.io.IOException;

/**
 * Represents a connection wrapper which has sent a packet to this library.
 */
public interface IPacketSender
{
    /**
     * Sends a packet to this packet sender.
     * 
     * @param packet
     *     - The packet to send.
     */
    void sendPacket(IPacket packet) throws IOException;
}
