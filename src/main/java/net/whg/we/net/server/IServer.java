package net.whg.we.net.server;

import java.io.IOException;
import java.util.List;
import net.whg.we.net.IDataHandler;
import net.whg.we.net.IServerSocket;

/**
 * Used as a bridge for handling network IO from the server side.
 */
public interface IServer
{
    /**
     * Starts the server.
     * 
     * @param socket
     *     - The server socket to open this start with.
     * @param port
     *     - The port to open the socket on or 0 to let the system choose.
     * @throws IOException
     *     If the server could not be started.
     * @throws IllegalArgumentException
     *     If the socket is already open.
     * @throws IllegalStateException
     *     If this server is already running.
     */
    void start(IServerSocket socket, int port) throws IOException;

    /**
     * Stops the server if it is currently running. If the server is not running, no
     * action is preformed.
     */
    void stop();

    /**
     * Checks whether or not the server is currently running.
     * 
     * @return True if the server is running. False otherwise.
     */
    boolean isRunning();

    /**
     * Assigns the client handler to use for this server.
     * 
     * @param handler
     *     - The handler.
     * @throws IllegalStateException
     *     If the server is running.
     */
    void setClientHandler(IClientHandler handler);

    /**
     * Assigns the data handler to use for this server.
     * 
     * @param handler
     *     - The handler.
     * @throws IllegalStateException
     *     If the server is running.
     */
    void setDataHandler(IDataHandler handler);

    /**
     * Returns a read-only list of all connected clients. This method allocates
     * memory, so it is recommended to store the list for reuse if frequent access
     * is required.
     * 
     * @return The list of connected clients.
     */
    List<IConnectedClient> getConnectedClients();

    /**
     * Handles and disposes all currently queued packets.
     */
    void handlePackets();
}
