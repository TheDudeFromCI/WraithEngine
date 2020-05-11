package net.whg.we.net.packets;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.whg.we.net.IDataHandler;
import net.whg.we.net.IPacket;

/**
 * A simple data handler for reading and writing binary packets.
 */
public class PacketDataHandler implements IDataHandler
{
    private final PacketFactory factory;

    /**
     * Creates a new packet data handler.
     * 
     * @param factory
     *     - The packet factory to use when receiving new packets.
     */
    public PacketDataHandler(PacketFactory factory)
    {
        this.factory = factory;
    }

    @Override
    public IBinaryPacket readPacket(DataInput stream) throws IOException
    {
        return factory.getPacket(stream);
    }

    @Override
    public void writePacket(DataOutput stream, IPacket packet) throws IOException
    {
        var binary = (IBinaryPacket) packet;
        binary.write(stream);
    }
}
