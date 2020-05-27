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

        var seconds = (float) ((2 / targetFPS) - toWait);
        timer.sleep(seconds);
    }

    @Override
    public int getPriority()
    {
        return PipelineConstants.FRAMERATE_LIMITER;
    }
}
