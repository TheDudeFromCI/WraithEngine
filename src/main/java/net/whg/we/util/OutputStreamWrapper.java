package net.whg.we.util;

import java.io.IOException;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final Logger logger = LoggerFactory.getLogger(OutputStreamWrapper.class);
    private final StringBuilder buffer = new StringBuilder();
    private final LogLevel logLevel;

    /**
     * Creates a new output stream wrapper object.
     * 
     * @param logLevel
     *     - The log level this wrapper uses when writing.
     */
    public OutputStreamWrapper(LogLevel logLevel)
    {
        this.logLevel = logLevel;
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
                logger.trace("{}", buffer);
                break;

            case DEBUG:
                logger.debug("{}", buffer);
                break;

            case INFO:
                logger.info("{}", buffer);
                break;

            case WARN:
                logger.warn("{}", buffer);
                break;

            case ERROR:
                logger.error("{}", buffer);
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
