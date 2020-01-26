package net.whg.we.main;

/**
 * A scene listener can be used to listen for common events which are triggered
 * by a scene.
 */
public interface ISceneListener
{
    /**
     * Called when a game object is added to a scene. This event is called after the
     * game object has been added.
     * 
     * @param go
     *     - The game object which was added.
     */
    void onGameObjectAdded(GameObject go);

    /**
     * Called when a game object has been removed from a scene. This event is called
     * after the game object has been removed.
     * 
     * @param go
     *     - The game object which was removed.
     */
    void onGameObjectRemoved(GameObject go);

    /**
     * Called when a pipeline action has been added to the scene. This event is
     * called after the pipeline has been added.
     * 
     * @param action
     *     - The pipeline action which was added.
     */
    void onPipelineAdded(IPipelineAction action);

    /**
     * Called when a pipeline action has been removed from the scene. This event is
     * called after the pipeline has been removed.
     * 
     * @param action
     *     - The pipeline action which was removed.
     */
    void onPipelineRemoved(IPipelineAction action);
}
