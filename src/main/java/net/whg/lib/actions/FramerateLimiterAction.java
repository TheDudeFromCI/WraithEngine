package net.whg.lib.actions;

import net.whg.we.main.ILoopAction;
import net.whg.we.main.Timer;
import net.whg.we.main.PipelineConstants;

/**
 * An action which can be used to limit the framerate of the application to
 * prevent it from exceeding a target framerate.
 */
public class FramerateLimiterAction implements ILoopAction
{
    private final Timer timer;
    private final double targetFPS;
    private double smoothing;

    /**
     * Creates a new framerate limit action.
     * 
     * @param timer
     *     - The timer backing this action.
     * @param targetFPS
     *     - The framerate cap.
     */
    public FramerateLimiterAction(Timer timer, float targetFPS)
    {
        this.timer = timer;
        this.targetFPS = targetFPS + 0.05;
    }

    @Override
    public void run()
    {
        double delta = timer.getDeltaTime();
        double toWait = (smoothing + delta) / 2;
        smoothing = delta;

        toWait = (2 / targetFPS) - toWait;

        if (toWait > 0)
        {
            long ms = (long) (toWait * 1000);
            int ns = (int) ((toWait % 0.001) * 1.0e+9);

            sleep(ms, ns);
        }
    }

    /**
     * Causes the thread to sleep for a given period of time, catching interrupted
     * exceptions if they occur.
     * 
     * @param ms
     *     - The number of milliseconds to sleep.
     * @param ns
     *     - The number of nanoseconds to sleep.
     */
    private void sleep(long ms, int ns)
    {
        try
        {
            Thread.sleep(ms, ns);
        }
        catch (InterruptedException e)
        {
            // TODO Find better approach for handling this exceptions.
        }
    }

    @Override
    public int getPriority()
    {
        return PipelineConstants.FRAMERATE_LIMITER;
    }
}
