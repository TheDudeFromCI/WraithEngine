package net.whg.we.main;

/**
 * The time class is used to contain information about the current time states
 * of the game. This includes time steps, running time, physics frame rate, and
 * frame delta times.
 */
public class Timer
{
    private static final String TIMER_NOT_STARTED = "Timer not started!";

    private final ITimeSupplier timeSupplier;
    private boolean running;
    private long startTime;
    private long elapsedTime;
    private long lastFrame;
    private float deltaTime;

    /**
     * Creates a new timer object.
     * 
     * @param timeSupplier
     *     - The object in charge of providing the system time.
     */
    public Timer(ITimeSupplier timeSupplier)
    {
        this.timeSupplier = timeSupplier;
    }

    /**
     * Starts, (resetting all values if necessary), the timer. This will record the
     * current time in nano seconds and preform all time-based operations based on
     * this value. If attached to a game loop, this should be called right as the
     * game loop is started. If this timer is already running, it is simply reset
     * and allowed to continue running.
     */
    public void startTimer()
    {
        running = true;
        startTime = timeSupplier.nanoTime();

        elapsedTime = 0;
        deltaTime = 0;
        lastFrame = startTime;
    }

    /**
     * Stops the timer, allowing target framerate modifications to be made on this
     * timer.
     */
    public void stopTimer()
    {
        running = false;
    }

    /**
     * Gets whether or not this timer is currently running.
     * 
     * @return True if this timer is currently running. False otherwise.
     */
    public boolean isRunning()
    {
        return running;
    }

    /**
     * Gets the amount of time which has passed since the timer was started.
     * 
     * @return The number of seconds this timer has been running.
     */
    public double getElapsedTime()
    {
        return elapsedTime / 1.0e9;
    }

    /**
     * Gets the time which has passed since the previous render frame.
     * 
     * @return The time in seconds.
     */
    public float getDeltaTime()
    {
        return deltaTime;
    }

    /**
     * Gets the current frames per second. This method only calculates the Fps based
     * on the current frame delta, and applies no smoothing.
     * 
     * @return The current fps.
     */
    public float getFps()
    {
        return 1f / deltaTime;
    }

    /**
     * Called at the beginning of each frame to calculate time updates such as delta
     * time, elapsed time, etc.
     * 
     * @throws IllegalStateException
     *     If the timer has not been started.
     */
    public void beginFrame()
    {
        if (!running)
            throw new IllegalStateException(TIMER_NOT_STARTED);

        long time = timeSupplier.nanoTime();
        elapsedTime = time - startTime;
        deltaTime = (float) ((time - lastFrame) / 1.0e9);

        lastFrame = time;
    }

    /**
     * Causes the current thread to sleep for the given number of seconds.
     * 
     * @param seconds
     *     - The number of seconds to sleep.
     */
    public void sleep(float seconds)
    {
        if (seconds <= 0)
            return;

        long ms = (long) (seconds * 1000);
        int ns = (int) ((seconds % 0.001) * 1.0e+9);

        timeSupplier.sleep(ms, ns);
    }
}
