package net.whg.we.main;

/**
 * A timer action is a game loop action which updates a reliable timer.
 * 
 * @see {@link Timer}
 */
public class TimerAction implements ILoopAction
{
    private final Timer timer;

    /**
     * Creates a new timer action.
     * 
     * @param timer
     *     - The timer to update each frame.
     */
    public TimerAction(Timer timer)
    {
        this.timer = timer;
    }

    @Override
    public void run()
    {
        timer.beginFrame();
    }

    @Override
    public int getPriority()
    {
        return -1000000;
    }
}
