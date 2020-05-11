package net.whg.we.net.server;

/**
 * Used as a bridge for handling network IO from the server side.
 */
public interface IClientHandler
{
    /**
     * Called when a client connects to the server.
     * 
     * @param client
     *     - The client.
     */
    void onClientConnected(IConnectedClient client);

    /**
     * Called when a client disconnects from the server.
     * 
     * @param client
     *     - The client.
     */
    void onClientDisconnected(IConnectedClient client);

    /**
     * Read incoming data from the given client, triggering events as necessary.
     */
    void readData(IConnectedClient client);
}
