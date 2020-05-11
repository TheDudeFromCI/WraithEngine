package net.whg.we.net;

/**
 * Contains details for an option socket connection.
 */
public class ConnectionData
{
    private final String ip;
    private final int port;

    /**
     * Creates a new connection data object.
     * 
     * @param ip
     *     - The ip of the connected socket.
     * @param port
     *     - The port of the connected socket.
     */
    public ConnectionData(String ip, int port)
    {
        this.ip = ip;
        this.port = port;
    }

    /**
     * Gets the socket connection IP address.
     */
    public String getIP()
    {
        return ip;
    }

    /**
     * Gets the port of the connected socket.
     */
    public int getPort()
    {
        return port;
    }
}
