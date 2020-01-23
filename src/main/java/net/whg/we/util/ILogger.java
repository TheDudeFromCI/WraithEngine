package net.whg.we.util;

/**
 * This interface seeks to add a layer of abstraction between the output stream
 * logic and the logger, to make it more reusable and more testable.
 */
public interface ILogger
{
    /**
     * Logs a message using the trace logging level.
     * 
     * @param msg
     *     - The message to log.
     */
    void trace(String msg);

    /**
     * Logs a message using the debug logging level.
     * 
     * @param msg
     *     - The message to log.
     */
    void debug(String msg);

    /**
     * Logs a message using the info logging level.
     * 
     * @param msg
     *     - The message to log.
     */
    void info(String msg);

    /**
     * Logs a message using the warn logging level.
     * 
     * @param msg
     *     - The message to log.
     */
    void warn(String msg);

    /**
     * Logs a message using the error logging level.
     * 
     * @param msg
     *     - The message to log.
     */
    void error(String msg);
}
