package net.whg.we.external;

import net.whg.we.main.ITimeSupplier;

/**
 * The implementation of the timer supplier interface.
 */
public class TimeSupplierAPI implements ITimeSupplier
{
    @Override
    public long nanoTime()
    {
        return System.nanoTime();
    }
}
