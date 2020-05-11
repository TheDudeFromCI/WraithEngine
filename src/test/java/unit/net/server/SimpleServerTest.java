package unit.net.server;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.inOrder;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import net.whg.we.net.IDataHandler;
import net.whg.we.net.IPacket;
import net.whg.we.net.IServerSocket;
import net.whg.we.net.ISocket;
import net.whg.we.net.server.IClientHandler;
import net.whg.we.net.server.IConnectedClient;
import net.whg.we.net.server.SimpleServer;
import net.whg.we.util.Tuple;
import net.whg.we.util.Tuple.Tuple2;

public class SimpleServerTest
{
    private final int PORT = 1234;
    private IServerSocket serverSocket;
    private SimpleServer server;
    private IClientHandler clientHandler;
    private IDataHandler dataHandler;

    private boolean serverClosed = true;

    // Cheap and easy "Socket Closed" events.
    private IPacket killPacket;
    private ISocket killSocket;

    private BlockingQueue<ISocket> toLogin = new ArrayBlockingQueue<>(10);
    private BlockingQueue<IPacket> receivedPackets = new ArrayBlockingQueue<>(10);

    @Rule
    public Timeout globalTimeout = Timeout.seconds(6);

    @Before
    public void init() throws IOException
    {
        serverSocket = mock(IServerSocket.class);
        when(serverSocket.isClosed()).thenAnswer(a -> serverClosed);

        doAnswer(a -> serverClosed = false).when(serverSocket)
                                           .start(PORT);
        doAnswer(a ->
        {
            serverClosed = true;
            toLogin.put(killSocket);

            return null;
        }).when(serverSocket)
          .close();

        killPacket = mock(IPacket.class);
        killSocket = mock(ISocket.class);

        when(serverSocket.getLocalPort()).thenReturn(PORT);
        when(serverSocket.waitForClient()).thenAnswer(a ->
        {
            var socket = toLogin.take();
            if (socket == killSocket)
                throw new SocketException();

            return socket;
        });

        clientHandler = mock(IClientHandler.class);
        dataHandler = mock(IDataHandler.class);

        when(dataHandler.readPacket(any())).thenAnswer(a ->
        {
            var packet = receivedPackets.take();
            if (packet == killPacket)
                throw new SocketException();

            return packet;
        });

        server = new SimpleServer();
        server.setClientHandler(clientHandler);
        server.setDataHandler(dataHandler);
    }

    private Tuple2<ISocket, IConnectedClient> login() throws IOException, InterruptedException
    {
        var inputStream = mock(InputStream.class);
        var outputStream = mock(OutputStream.class);

        var socket = mock(ISocket.class);
        when(socket.getIP()).thenReturn("localhost");
        when(socket.getPort()).thenReturn(5678);
        when(socket.getInputStream()).thenReturn(inputStream);
        when(socket.getOutputStream()).thenReturn(outputStream);

        when(socket.isClosed()).thenReturn(false);

        int oldClients = server.getConnectedClients()
                               .size();
        toLogin.put(socket);

        var clientList = server.getConnectedClients();
        await().forever()
               .until(() -> clientList.size() == oldClients + 1);

        var connectedClient = clientList.get(oldClients);

        return Tuple.of(socket, connectedClient);
    }

    private void logout(ISocket socket) throws InterruptedException
    {
        when(socket.isClosed()).thenReturn(true);
        receivedPackets.put(killPacket);
    }

    @Test
    public void startServer() throws IOException
    {
        server.start(serverSocket, PORT);
        assertTrue(server.isRunning());

        assertEquals(0, server.getConnectedClients()
                              .size());

        server.stop();
        assertFalse(server.isRunning());

        verify(clientHandler, never()).onClientConnected(any());
        verify(dataHandler, never()).readPacket(any());
    }

    @Test
    public void clientConnected() throws Exception
    {
        server.start(serverSocket, PORT);

        var client = login();

        verify(clientHandler).onClientConnected(client.b);
        verify(dataHandler).readPacket(any());

        logout(client.a);

        verify(clientHandler, timeout(1000)).onClientDisconnected(any());
        assertEquals(0, server.getConnectedClients()
                              .size());

        server.stop();
    }

    @Test
    public void receiveMultiplePackets() throws Exception
    {
        server.start(serverSocket, PORT);
        assertTrue(server.isRunning());

        var packet1 = mock(IPacket.class);
        var packet2 = mock(IPacket.class);
        var packet3 = mock(IPacket.class);
        receivedPackets.put(packet1);
        receivedPackets.put(packet2);
        receivedPackets.put(packet3);

        var client = login();
        logout(client.a);

        verify(clientHandler, timeout(1000)).onClientDisconnected(any());

        assertTrue(server.isRunning());
        server.handlePackets();

        var order = inOrder(packet1, packet2, packet3);
        order.verify(packet1)
             .handle();

        order.verify(packet2)
             .handle();

        order.verify(packet3)
             .handle();

        server.stop();
    }
}
