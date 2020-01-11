package net.whg.we.main;

import net.whg.we.rendering.IMesh;

/**
 * This behavior is used as a method for rendering a mesh to the scene. When
 * attached to a game object, this behavior ties into the scene renderer the
 * game object is currently sitting in and allows for the game object to have a
 * renderable mesh attached to it.
 */
public class RenderBehavior extends AbstractBehavior
{
    private IMesh mesh;

    void render()
    {
        if (getGameObject() == null)
            return;

        mesh.render();
    }

    @Override
    protected void onInit()
    {
        Scene scene = getGameObject().getScene();
        if (scene != null)
            scene.getRenderer()
                 .addRenderedObject(this);
    }

    @Override
    protected void onDispose()
    {
        mesh.dispose();
        mesh = null;

        GameObject go = getGameObject();
        if (go != null)
        {
            Scene scene = go.getScene();
            if (scene != null)
                scene.getRenderer()
                     .addRenderedObject(this);
        }
    }

    @Override
    protected void onSceneChange(Scene oldScene, Scene newScene)
    {
        oldScene.getRenderer()
                .removeRenderedObject(this);
        newScene.getRenderer()
                .addRenderedObject(this);
    }
}
