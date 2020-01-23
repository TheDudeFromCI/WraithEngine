package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import org.junit.Test;
import net.whg.we.main.GameObject;
import net.whg.we.rendering.Material;
import net.whg.we.main.RenderBehavior;
import net.whg.we.main.RenderPipelineAction;
import net.whg.we.main.Scene;
import net.whg.we.rendering.Camera;
import net.whg.we.rendering.IMesh;
import net.whg.we.rendering.IShader;

public class RenderBehaviorTest
{
    @Test
    public void setMesh()
    {
        IMesh mesh = mock(IMesh.class);

        RenderBehavior behavior = new RenderBehavior();
        assertNull(behavior.getMesh());

        behavior.setMesh(mesh);
        assertEquals(mesh, behavior.getMesh());
    }

    @Test(expected = IllegalStateException.class)
    public void setMesh_alreadyDisposed()
    {
        RenderBehavior behavior = new RenderBehavior();
        behavior.dispose();

        behavior.setMesh(mock(IMesh.class));
    }

    @Test
    public void setMaterial()
    {
        Material material = new Material(mock(IShader.class));

        RenderBehavior behavior = new RenderBehavior();
        assertNull(behavior.getMaterial());

        behavior.setMaterial(material);
        assertEquals(material, behavior.getMaterial());
    }

    @Test(expected = IllegalStateException.class)
    public void setMaterial_alreadyDisposed()
    {
        RenderBehavior behavior = new RenderBehavior();
        behavior.dispose();

        behavior.setMaterial(mock(Material.class));
    }

    @Test
    public void render_normalConditions()
    {
        IMesh mesh = mock(IMesh.class);
        IShader shader = mock(IShader.class);
        Material material = new Material(shader);

        Scene scene = new Scene();
        GameObject go = new GameObject();
        RenderBehavior behavior = new RenderBehavior();
        behavior.setMesh(mesh);
        behavior.setMaterial(material);
        go.addBehavior(behavior);
        scene.addGameObject(go);

        RenderPipelineAction renderPipeline = new RenderPipelineAction();
        renderPipeline.setCamera(new Camera());
        renderPipeline.enableBehavior(behavior);
        renderPipeline.run();

        verify(shader).bind();
        verify(mesh).render();
    }

    @Test
    public void render_behaviorAddedLater()
    {
        IMesh mesh = mock(IMesh.class);
        IShader shader = mock(IShader.class);
        Material material = new Material(shader);

        Scene scene = new Scene();
        GameObject go = new GameObject();
        RenderBehavior behavior = new RenderBehavior();
        behavior.setMesh(mesh);
        behavior.setMaterial(material);
        scene.addGameObject(go);
        go.addBehavior(behavior);

        RenderPipelineAction renderPipeline = new RenderPipelineAction();
        renderPipeline.setCamera(new Camera());
        renderPipeline.enableBehavior(behavior);
        renderPipeline.run();

        verify(shader).bind();
        verify(mesh).render();
    }

    @Test
    public void render_noMesh_dontRender()
    {
        IShader shader = mock(IShader.class);
        Material material = new Material(shader);

        Scene scene = new Scene();
        GameObject go = new GameObject();
        RenderBehavior behavior = new RenderBehavior();
        behavior.setMaterial(material);
        scene.addGameObject(go);
        go.addBehavior(behavior);

        RenderPipelineAction renderPipeline = new RenderPipelineAction();
        renderPipeline.setCamera(new Camera());
        renderPipeline.enableBehavior(behavior);
        renderPipeline.run();

        verify(shader, never()).bind();
    }

    @Test
    public void render_noMaterial_dontRender()
    {
        IMesh mesh = mock(IMesh.class);

        Scene scene = new Scene();
        GameObject go = new GameObject();
        RenderBehavior behavior = new RenderBehavior();
        behavior.setMesh(mesh);
        scene.addGameObject(go);
        go.addBehavior(behavior);

        RenderPipelineAction renderPipeline = new RenderPipelineAction();
        renderPipeline.setCamera(new Camera());
        renderPipeline.enableBehavior(behavior);
        renderPipeline.run();

        verify(mesh, never()).render();
    }

    @Test
    public void dispose_onDisposeCalled()
    {
        IMesh mesh = mock(IMesh.class);
        IShader shader = mock(IShader.class);
        Material material = new Material(shader);

        RenderBehavior behavior = new RenderBehavior();
        behavior.setMesh(mesh);
        behavior.setMaterial(material);

        behavior.dispose();

        assertTrue(behavior.isDisposed());
        assertNull(behavior.getMesh());
        assertNull(behavior.getMaterial());
    }
}
