package net.whg.we.main;

import net.whg.we.window.IWindow;

/**
 * The poll events action is used to trigger a window to poll events and swap
 * the render buffer, which needs to be done at the end of every frame in order
 * to render the game to the screen.
 */
public class PollEventsPipeline implements ILoopAction
{
    private final IWindow window;

    /**
     * Creates a new poll-events action.
     * 
     * @param window
     *     - The window to poll the events for each frame.
     */
    public PollEventsPipeline(IWindow window)
    {
        this.window = window;
    }

    @Override
    public void run()
    {
        window.pollEvents();
    }

    @Override
    public int getPriority()
    {
        return PipelineConstants.POLL_WINDOW_EVENTS;
    }
}
