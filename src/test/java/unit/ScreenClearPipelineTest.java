package unit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.junit.Test;
import net.whg.we.main.PipelineConstants;
import net.whg.we.rendering.IScreenClearHandler;
import net.whg.we.rendering.ScreenClearPipeline;

public class ScreenClearPipelineTest
{
    @Test
    public void defaultPriority()
    {
        assertEquals(PipelineConstants.CLEAR_SCREEN,
                new ScreenClearPipeline(mock(IScreenClearHandler.class)).getPriority());
    }

    @Test
    public void clearScreen()
    {
        IScreenClearHandler screenClear = mock(IScreenClearHandler.class);
        ScreenClearPipeline pipeline = new ScreenClearPipeline(screenClear);

        pipeline.run();

        verify(screenClear).clearScreen();
    }
}
