package net.whg.we.net.packets;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Used to build packets based on a given input stream using a set of registered
 * packet initializers.
 */
public class PacketFactory
{
    private final List<IPacketInitializer<?>> initializers = Collections.synchronizedList(new ArrayList<>());

    /**
     * Initializes a new packet initializer.
     * 
     * @param initializer
     *     - The initializer to register.
     * @throws IllegalArgumentException
     *     If an initializer for the given packet ID is already registered.
     */
    public void register(IPacketInitializer<?> initializer)
    {
        long id = initializer.getPacketID();
        if (getInitializer(id) != null)
            throw new IllegalArgumentException("An initializer for the given packet ID is already registered!");

        initializers.add(initializer);
    }

    /**
     * @param input
     *     - The data input to read from.
     * @return The newly loaded packet.
     * @throws IOException
     *     If an error occurs while reading from the input stream.
     */
    public IBinaryPacket getPacket(DataInput input) throws IOException
    {
        var id = input.readLong();
        var initializer = getInitializer(id);

        if (initializer == null)
            throw new IOException("Unknown packet ID '" + id + "'!");

        return initializer.loadPacket(input);
    }

    /**
     * Returns the given packet initializer for the given packet ID.
     * 
     * @param id
     *     - The packet ID.
     * @return The packet initializer, or null if there is no initializer for the
     *     given packet ID.
     */
    private IPacketInitializer<?> getInitializer(long id)
    {
        for (var initializer : initializers)
            if (initializer.getPacketID() == id)
                return initializer;

        return null;
    }
}
