package unit.net.packets;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import java.io.DataInput;
import java.io.IOException;
import org.junit.Test;
import net.whg.we.net.packets.IBinaryPacket;
import net.whg.we.net.packets.IPacketInitializer;
import net.whg.we.net.packets.PacketFactory;

public class PacketFactoryTest
{
    @Test
    public void readPacket() throws IOException
    {
        var packetID = 1234L;
        var packet = mock(IBinaryPacket.class);
        var initializer = mock(IPacketInitializer.class);
        when(packet.getPacketID()).thenReturn(packetID);
        when(initializer.getPacketID()).thenReturn(packetID);
        when(initializer.loadPacket(any())).thenReturn(packet);

        var input = mock(DataInput.class);
        when(input.readLong()).thenReturn(packetID);

        var factory = new PacketFactory();
        factory.register(initializer);

        assertEquals(packet, factory.getPacket(input));
    }

    @Test(expected = IOException.class)
    public void readPacket_UnknownID_Error() throws IOException
    {
        var input = mock(DataInput.class);
        when(input.readLong()).thenReturn(17L);

        var factory = new PacketFactory();
        factory.getPacket(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void register_AlreadyRegistered_Error()
    {
        var init1 = mock(IPacketInitializer.class);
        var init2 = mock(IPacketInitializer.class);
        when(init1.getPacketID()).thenReturn(1L);
        when(init2.getPacketID()).thenReturn(1L);

        var factory = new PacketFactory();
        factory.register(init1);
        factory.register(init2);
    }

    @Test
    public void readPacket_UsesCorrectInitializer() throws IOException
    {
        var packet1 = mock(IBinaryPacket.class);
        var packet2 = mock(IBinaryPacket.class);
        var packet3 = mock(IBinaryPacket.class);
        var factory = new PacketFactory();

        var init1 = mock(IPacketInitializer.class);
        when(init1.getPacketID()).thenReturn(1L);
        when(init1.loadPacket(any())).thenReturn(packet1);
        factory.register(init1);

        var init2 = mock(IPacketInitializer.class);
        when(init2.getPacketID()).thenReturn(2L);
        when(init2.loadPacket(any())).thenReturn(packet2);
        factory.register(init2);

        var init3 = mock(IPacketInitializer.class);
        when(init3.getPacketID()).thenReturn(3L);
        when(init3.loadPacket(any())).thenReturn(packet3);
        factory.register(init3);

        var input = mock(DataInput.class);
        when(input.readLong()).thenReturn(2L);

        assertEquals(packet2, factory.getPacket(input));
    }
}
