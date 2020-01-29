package net.whg.we.window;

import net.whg.we.main.ILoopAction;
import net.whg.we.main.PipelineConstants;
import net.whg.we.window.Input;

/**
 * This action triggers the end-frame method for input objects near the end of
 * the loop to prepare the object for recieving input updates.
 */
public class InputEndFrameAction implements ILoopAction
{
    private final Input input;

    public InputEndFrameAction(Input input)
    {
        this.input = input;
    }

    @Override
    public void run()
    {
        input.endFrame();
    }

    @Override
    public int getPriority()
    {
        return PipelineConstants.ENDFRAME;
    }
}
