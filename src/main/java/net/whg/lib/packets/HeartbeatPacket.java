package net.whg.lib.packets;

import java.io.DataOutput;
import net.whg.we.net.IPacketSender;
import net.whg.we.net.packets.IBinaryPacket;

/**
 * Sent periodically over the network to indicate the connection is still
 * active.
 */
public class HeartbeatPacket implements IBinaryPacket
{
    public static final long PACKET_ID = 8716237733363712L;

    private final IPacketSender sender;

    /**
     * Creates a new heartbeat packet to be sent.
     */
    public HeartbeatPacket()
    {
        this(null);
    }

    /**
     * Creates a new received heartbeat packet from the given sender.
     * 
     * @param sender
     *     - The packet sender.
     */
    HeartbeatPacket(IPacketSender sender)
    {
        this.sender = sender;
    }

    @Override
    public void handle()
    {
        // TODO Mark sender as still active.
    }

    @Override
    public long getPacketID()
    {
        return PACKET_ID;
    }

    @Override
    public void write(DataOutput out)
    {
        // Contains no data.
    }

    @Override
    public IPacketSender getSender()
    {
        return sender;
    }
}
