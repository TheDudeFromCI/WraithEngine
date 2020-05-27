package net.whg.we.main;

/**
 * The time supplier is a class which severs the purpose of retrieving the
 * system time in nanoseconds. Used for time-based utilities.
 */
public interface ITimeSupplier
{
    /**
     * Gets the current system time in nano seconds.
     * 
     * @return The time in nano seconds.
     */
    long nanoTime();

    /**
     * Causes the thread to sleep for a given period of time.
     * 
     * @param ms
     *     - The number of milliseconds to sleep.
     * @param ns
     *     - The number of nanoseconds to sleep.
     */
    void sleep(long ms, int ns);
}
