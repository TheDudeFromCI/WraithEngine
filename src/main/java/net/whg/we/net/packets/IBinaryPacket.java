package net.whg.we.net.packets;

import java.io.DataOutput;
import java.io.IOException;
import net.whg.we.net.IPacket;

/**
 * A binary encoded version of a packet.
 */
public interface IBinaryPacket extends IPacket
{
    /**
     * Gets the unique packet type ID for this packet. All packets of this type
     * should return this same value.
     * 
     * @return The packet type ID.
     */
    long getPacketID();

    /**
     * Writes this packet's contents to the data output.
     * 
     * @param out
     *     - The data output to write to.
     * @throws IOException
     *     If an error occurs while writing to the data output.
     */
    void write(DataOutput out) throws IOException;
}
