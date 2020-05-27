package net.whg.we.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.whg.we.main.ITimeSupplier;

/**
 * The implementation of the timer supplier interface.
 */
public class TimeSupplierAPI implements ITimeSupplier
{
    private static final Logger logger = LoggerFactory.getLogger(TimeSupplierAPI.class);

    @Override
    public long nanoTime()
    {
        return System.nanoTime();
    }

    @Override
    public void sleep(long ms, int ns)
    {
        try
        {
            Thread.sleep(ms, ns);
        }
        catch (InterruptedException e)
        {
            logger.error("Sleep operation interrupted!", e);

            Thread.currentThread()
                  .interrupt();
        }
    }
}
