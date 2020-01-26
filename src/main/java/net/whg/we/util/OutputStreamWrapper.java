package net.whg.we.util;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A simple utility class for streaming to the logger, via an output stream.
 * Intended as a replacement for using System.out and System.err, to make the
 * system more compatible with a logger.
 */
public class OutputStreamWrapper extends OutputStream
{
    /**
     * A logging level is used to determine which channel to write the stream to.
     */
    public enum LogLevel
    {
        TRACE,
        DEBUG,
        INFO,
        WARN,
        ERROR
    }

    private final StringBuilder buffer = new StringBuilder();
    private final ILogger logger;
    private final LogLevel logLevel;

    /**
     * Creates a new output stream wrapper object.
     * 
     * @param logLevel
     *     - The log level this wrapper uses when writing.
     * @param logger
     *     - The logger to write messages to.
     */
    public OutputStreamWrapper(LogLevel logLevel, ILogger logger)
    {
        this.logLevel = logLevel;
        this.logger = logger;
    }

    @Override
    public void write(int b) throws IOException
    {
        char c = (char) (b & 0xFF);

        if (c == '\n')
            flush();
        else
            buffer.append(c);
    }

    @Override
    public void flush() throws IOException
    {
        switch (logLevel)
        {
            case TRACE:
                logger.trace(buffer.toString());
                break;

            case DEBUG:
                logger.debug(buffer.toString());
                break;

            case INFO:
                logger.info(buffer.toString());
                break;

            case WARN:
                logger.warn(buffer.toString());
                break;

            case ERROR:
                logger.error(buffer.toString());
                break;
        }

        buffer.delete(0, buffer.length());
    }

    @Override
    public void close() throws IOException
    {
        flush();
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException
    {
        String line = new String(b, off, len);

        int index;
        while ((index = line.indexOf('\n')) > -1)
        {
            buffer.append(line.substring(0, index));
            flush();

            line = line.substring(index + 1);
        }

        buffer.append(line);
    }
}
