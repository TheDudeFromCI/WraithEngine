package net.whg.we.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.whg.we.util.ILogger;

/**
 * The implementation of the logger api.
 */
public class LoggerApi implements ILogger
{
    private static final Logger logger = LoggerFactory.getLogger(LoggerApi.class);

    @Override
    public void trace(String msg)
    {
        logger.trace(msg);
    }

    @Override
    public void debug(String msg)
    {
        logger.debug(msg);
    }

    @Override
    public void info(String msg)
    {
        logger.info(msg);
    }

    @Override
    public void warn(String msg)
    {
        logger.warn(msg);
    }

    @Override
    public void error(String msg)
    {
        logger.error(msg);
    }
}
