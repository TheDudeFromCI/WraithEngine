package net.whg.we.main;

/**
 * A loop action is used to determine how a game loop should operate. Loop
 * actions are attached to a game loop, and are called, in sequence, each frame
 * until the game loop is stopped.
 */
public interface ILoopAction
{
    /**
     * Runs this loop action. This is called once per frame.
     */
    void run();

    /**
     * Gets the priority level of this loop action. This value should not change.
     * When a loop action is first added to a game loop, all loop actions are sorted
     * based on their priority level. Actions with a higher priority level are
     * called after actions with a lower priority level.
     * <p>
     * The default priority level of an action is 0.
     * 
     * @return The priority level of this action.
     */
    default int getPriority()
    {
        return 0;
    }
}
