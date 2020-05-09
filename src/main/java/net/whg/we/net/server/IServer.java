package net.whg.we.net.server;

import java.io.IOException;
import net.whg.we.net.IDataHandler;

/**
 * Used as a bridge for handling network IO from the server side.
 */
public interface IServer
{
    /**
     * Starts the server.
     * 
     * @param port
     *     - The port to start the server on.
     * @throws IOException
     *     If the server could not be started.
     */
    void start(int port) throws IOException;

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
}
