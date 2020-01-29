package net.whg.we.rendering;

import net.whg.we.main.IPipelineAction;
import net.whg.we.main.PipelineConstants;

/**
 * The screen clear pipeline simply clears the screen at the begining of the
 * render phase of a frame.
 */
public class ScreenClearPipeline implements IPipelineAction
{
    private final IScreenClearHandler screenClear;

    /**
     * Creates a new screen clear pipeline object.
     * 
     * @param screenClear
     *     - The screen clear handler to trigger each frame.
     */
    public ScreenClearPipeline(IScreenClearHandler screenClear)
    {
        this.screenClear = screenClear;
    }

    @Override
    public void run()
    {
        screenClear.clearScreen();
    }

    @Override
    public int getPriority()
    {
        return PipelineConstants.CLEAR_SCREEN;
    }
}
