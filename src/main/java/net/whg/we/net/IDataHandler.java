package net.whg.we.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
    IPacket readPacket(InputStream stream) throws IOException;

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
    void writePacket(OutputStream stream, IPacket packet) throws IOException;
}
