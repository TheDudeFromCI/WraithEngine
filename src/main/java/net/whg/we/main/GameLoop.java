package net.whg.we.main;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The game loop object is a container for handling a standard game loop. A game
 * loop being per-frame actions such as updating physics, handling player input,
 * and rendering the screen until the game loop is stopped.
 */
public class GameLoop
{
    private final List<ILoopAction> loopActions = new CopyOnWriteArrayList<>();
    private boolean running;

    /**
     * Adds a new loop action to this game loop. When a loop action is first added,
     * all actions in this game loop are sorted based on their priority, such that,
     * actions with a lower priority are executed first and actions with a higher
     * priority are executed last. The sorter used is a stable sorter, allowing
     * actions of the same priority to remain in the same order that they were added
     * in. This method does nothing if the loop action is null or is already in this
     * game loop.
     * <p>
     * If this game loop is currently running, modifying game loop actions applies
     * on the next frame.
     * 
     * @param loopAction
     *     - The action to add.
     */
    public void addAction(ILoopAction loopAction)
    {
        if (loopAction == null)
            return;

        if (loopActions.contains(loopAction))
            return;

        loopActions.add(loopAction);
        loopActions.sort((a, b) -> a.getPriority() - b.getPriority());
    }

    /**
     * Removes a loop action from this game loop. This method does nothing if the
     * loop action is null or is not in this game loop.
     * <p>
     * If this game loop is currently running, modifying game loop actions applies
     * on the next frame.
     * 
     * @param loopAction
     *     - The loop action to remove.
     */
    public void removeAction(ILoopAction loopAction)
    {
        if (loopAction == null)
            return;

        loopActions.remove(loopAction);
    }

    /**
     * Runs this game loop. This will start a loop which will run repeatedly until
     * the {@link #stop()} method is called.
     */
    public void loop()
    {
        running = true;
        while (running)
        {
            for (ILoopAction action : loopActions)
                action.run();
        }
    }

    /**
     * When this is called, the game loop will stop after the current frame. This
     * method does nothing if the game loop is not running.
     */
    public void stop()
    {
        running = false;
    }
}
