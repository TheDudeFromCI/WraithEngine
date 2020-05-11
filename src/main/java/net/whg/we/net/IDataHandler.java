package net.whg.we.net;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Used to buffer an incoming data stream to convert it to a usable packet.
 */
public interface IDataHandler
{
    /**
     * Reads the input stream and converts the incoming data as a packet.
     * 
     * @param stream
     *     - The input stream to read from.
     * @return The packet.
     * @throws IOException
     *     If an exception occurs while preforming this action.
     */
    IPacket readPacket(DataInput stream) throws IOException;

    /**
     * Writes a packet to the output stream.
     * 
     * @param stream
     *     - The output stream to write to.
     * @param packet
     *     - The packet to write.
     * @throws IOException
     *     If an exception occurs while preforming this action.
     */
    void writePacket(DataOutput stream, IPacket packet) throws IOException;
}
