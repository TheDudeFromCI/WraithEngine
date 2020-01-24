package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import org.junit.Test;
import net.whg.we.main.GameLoop;
import net.whg.we.main.ILoopAction;

public class GameLoopTest
{
    @Test
    public void addAction()
    {
        ILoopAction action = mock(ILoopAction.class);

        GameLoop gameLoop = new GameLoop();
        gameLoop.addAction(action); // Original
        gameLoop.addAction(action); // Duplicate
        gameLoop.addAction(null); // Null

        assertEquals(1, gameLoop.loopActions()
                                .size());

        gameLoop.removeAction(null);
        assertEquals(1, gameLoop.loopActions()
                                .size());

        gameLoop.removeAction(action);
        assertEquals(0, gameLoop.loopActions()
                                .size());
    }

    @Test(timeout = 1000)
    public void loop_stop()
    {
        GameLoop gameLoop = new GameLoop();

        int[] i = new int[] {10};
        gameLoop.addAction(() ->
        {
            i[0]--;
            if (i[0] == 0)
                gameLoop.stop();
        });

        gameLoop.loop();
        assertTrue(i[0] == 0);
    }
}
