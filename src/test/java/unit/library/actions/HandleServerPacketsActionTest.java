package unit.library.actions;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.junit.Test;
import net.whg.lib.actions.HandleServerPacketsAction;
import net.whg.we.main.PipelineConstants;
import net.whg.we.net.server.IServer;

public class HandleServerPacketsActionTest
{
    @Test
    public void handlePackets()
    {
        var server = mock(IServer.class);
        var handler = new HandleServerPacketsAction(server);

        handler.run();

        verify(server).handlePackets();
    }

    @Test
    public void defaultPriority()
    {
        var server = mock(IServer.class);
        var handler = new HandleServerPacketsAction(server);

        assertTrue(handler.getPriority() < PipelineConstants.PHYSICS_UPDATES);
        assertTrue(handler.getPriority() > PipelineConstants.CALCULATE_TIMESTAMPS);
    }
}
