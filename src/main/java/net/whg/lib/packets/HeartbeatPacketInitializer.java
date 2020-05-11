package net.whg.lib.packets;

import java.io.DataInput;
import java.io.IOException;
import net.whg.we.net.IPacketSender;
import net.whg.we.net.packets.IPacketInitializer;

public class HeartbeatPacketInitializer implements IPacketInitializer<HeartbeatPacket>
{
    @Override
    public HeartbeatPacket loadPacket(DataInput input, IPacketSender sender) throws IOException
    {
        return new HeartbeatPacket(sender);
    }

    @Override
    public long getPacketID()
    {
        return HeartbeatPacket.PACKET_ID;
    }
}
