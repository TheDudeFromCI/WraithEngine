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
    private float physicsFrameRate = 30f;
    private boolean running;
    private long startTime;
    private long elapsedTime;
    private long idealPhysicsFrame;
    private long lastFrame;
    private float deltaTime;
    private long physicsFrame;
    private long renderFrame;

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
     * Starts, (resetting all values if nessicary), the timer. This will record the
     * current time in nano seconds and preform all time-based operations based on
     * this value. If attached to a game loop, this should be called right as the
     * game loop is started. If this timer is already running, it is simply reset
     * and allowed to continue running.
     */
    public void startTimer()
    {
        running = true;
        startTime = timeSupplier.nanoTime();

        physicsFrame = 0;
        renderFrame = 0;
        elapsedTime = 0;
        idealPhysicsFrame = 0;
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
     * Assigns the target framerate for physics to run at.
     * 
     * @param frameRate
     *     - The number of physics frames to run per second.
     * @throws IllegalStateException
     *     If the timer is currentlyrunning.
     * @throws IllegalArgumentException
     *     If the framerate is negative.
     */
    public void setPhysicsFrameRate(float frameRate)
    {
        if (running)
            throw new IllegalStateException("Cannot modify physics timestep while running!");

        if (frameRate < 0f)
            throw new IllegalArgumentException("Physics framerate cannot be negative!");

        physicsFrameRate = frameRate;
    }

    /**
     * Gets the target physics framerate for this timer.
     * 
     * @return The number of physics frames which should be run each second.
     */
    public float getPhysicsFrameRate()
    {
        return physicsFrameRate;
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
     * Gets the physics frame which should have been run, given the amount of time
     * which has passed since starting the timer.
     * <p>
     * When dealing with physics updates, which need to run on a relable timer, this
     * class takes care of that by calculating the ideal physics frame at the
     * begining of each frame. Physics can then be called a number of times to catch
     * up. The physics frame should be used as:
     * 
     * <pre>
     * timer.beginFrame();
     * while (timer.getPhysicsFrame() &lt; timer.getIdealPhysicsFrame)
     * {
     *     timer.incrementPhysicsFrame();
     *     doPhysicsUpdate();
     * }
     * doFrameUpdate();
     * </pre>
     * 
     * @return The physics frame that should have passed.
     * @see Timer#getPhysicsFrame()
     */
    public long getIdealPhysicsFrame()
    {
        return idealPhysicsFrame;
    }

    /**
     * Gets the physics frame which has currently been handled.
     * 
     * @return The physics frame id.
     * @see Timer#getIdealPhysicsFrame()
     */
    public long getPhysicsFrame()
    {
        return physicsFrame;
    }

    /**
     * Increments the current physics frame.
     * 
     * @throws IllegalStateException
     *     If the timer is not running.
     * @see Timer#getIdealPhysicsFrame()
     * @see Timer#getPhysicsFrame()
     */
    public void incrementPhysicsFrame()
    {
        if (!running)
            throw new IllegalStateException(TIMER_NOT_STARTED);

        physicsFrame++;
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
     * Gets the current render frame number.
     * 
     * @return The render frame number.
     */
    public long getRenderFrame()
    {
        return renderFrame;
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
     * Called at the begining of each frame to calculate time updates such as delta
     * time, elapsed time, etc.
     * 
     * @throws IllegalStateException
     *     If the timer has not been started.
     */
    public void beginFrame()
    {
        if (!running)
            throw new IllegalStateException(TIMER_NOT_STARTED);

        renderFrame++;

        long time = timeSupplier.nanoTime();
        elapsedTime = time - startTime;
        idealPhysicsFrame = (long) (getElapsedTime() * physicsFrameRate) + 1;
        deltaTime = (float) ((time - lastFrame) / 1.0e9);

        lastFrame = time;
    }
}
