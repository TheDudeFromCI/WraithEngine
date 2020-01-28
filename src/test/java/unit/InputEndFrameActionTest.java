package unit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.junit.Test;
import net.whg.we.main.PipelineConstants;
import net.whg.we.window.Input;
import net.whg.we.window.InputEndFrameAction;

public class InputEndFrameActionTest
{
    @Test
    public void defaultPriority()
    {
        assertEquals(PipelineConstants.ENDFRAME, new InputEndFrameAction(mock(Input.class)).getPriority());
    }

    @Test
    public void endFrame()
    {
        Input input = mock(Input.class);
        InputEndFrameAction action = new InputEndFrameAction(input);

        action.run();

        verify(input).endFrame();
    }
}
