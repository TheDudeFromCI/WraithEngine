package unit.engine.net.packets;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.junit.Test;
import net.whg.we.net.packets.IBinaryPacket;
import net.whg.we.net.packets.PacketDataHandler;
import net.whg.we.net.packets.PacketFactory;

public class PacketDataHandlerTest
{
    @Test
    public void readPacket() throws IOException
    {
        var packet = mock(IBinaryPacket.class);
        var factory = mock(PacketFactory.class);
        var stream = mock(DataInput.class);

        when(factory.getPacket(stream, null)).thenReturn(packet);

        var handler = new PacketDataHandler(factory);
        assertEquals(packet, handler.readPacket(stream, null));
    }

    @Test
    public void writePacket() throws IOException
    {
        var packetID = 120938L;
        var packet = mock(IBinaryPacket.class);
        when(packet.getPacketID()).thenReturn(packetID);

        var factory = mock(PacketFactory.class);
        var stream = mock(DataOutput.class);

        var handler = new PacketDataHandler(factory);
        handler.writePacket(stream, packet);

        verify(packet).write(stream);
        verify(stream).writeLong(packetID);
    }
}
