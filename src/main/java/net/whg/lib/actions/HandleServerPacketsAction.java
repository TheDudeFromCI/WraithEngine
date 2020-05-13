package net.whg.lib.actions;

import net.whg.we.main.ILoopAction;
import net.whg.we.main.PipelineConstants;
import net.whg.we.net.server.IServer;

/**
 * Calls the server to handle all currently received packets since the previous
 * frame.
 */
public class HandleServerPacketsAction implements ILoopAction
{
    private final IServer server;

    /**
     * Creates a new action for the given server.
     * 
     * @param server
     *     - The server to handle.
     */
    public HandleServerPacketsAction(IServer server)
    {
        this.server = server;
    }

    @Override
    public void run()
    {
        server.handlePackets();
    }

    @Override
    public int getPriority()
    {
        return PipelineConstants.PHYSICS_UPDATES - 1000;
    }
}
