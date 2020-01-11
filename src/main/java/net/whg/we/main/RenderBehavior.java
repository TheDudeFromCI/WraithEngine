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
    private Material material;

    /**
     * Sets the mesh to be rendered.
     * 
     * @param mesh
     *     - The mesh.
     */
    public void setMesh(IMesh mesh)
    {
        if (isDisposed())
            throw new IllegalStateException("Behavior already disposed!");

        this.mesh = mesh;
    }

    /**
     * Gets the mesh this render behavior is rendering.
     * 
     * @return The mesh.
     */
    public IMesh getMesh()
    {
        return mesh;
    }

    /**
     * Sets the material used to render the mesh with.
     * 
     * @param material
     *     - The material.
     */
    public void setMaterial(Material material)
    {
        if (isDisposed())
            throw new IllegalStateException("Behavior already disposed!");

        this.material = material;
    }

    /**
     * Gets the material used to render the mesh with.
     * 
     * @return The material.
     */
    public Material getMaterial()
    {
        return material;
    }

    /**
     * Checks if the mesh the material, and the game object have all been assigned.
     * 
     * @return True if all components have been assigned. False otherwise.
     */
    private boolean canRender()
    {
        return getGameObject() != null && mesh != null && material != null;
    }

    /**
     * Calls for this object to be rendered. This will bind the currently assigned
     * material and trigger a draw call for the mesh. This method does nothing if
     * this behavior has not yet been initialized, or if the mesh or material have
     * not yet been assigned.
     * 
     * @throws IllegalStateException
     *     If this behavior was already disposed.
     */
    void render()
    {
        if (isDisposed())
            throw new IllegalStateException("Behavior already disposed!");

        if (canRender())
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
        mesh = null;
        material = null;

        GameObject go = getGameObject();
        if (go != null)
        {
            Scene scene = go.getScene();
            if (scene != null)
                scene.getRenderer()
                     .removeRenderedObject(this);
        }
    }

    @Override
    protected void onSceneChange(Scene oldScene, Scene newScene)
    {
        if (isDisposed())
            return;

        oldScene.getRenderer()
                .removeRenderedObject(this);
        newScene.getRenderer()
                .addRenderedObject(this);
    }
}
