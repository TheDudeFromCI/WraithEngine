package unit.engine.rendering;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import org.junit.Test;
import net.whg.we.main.GameObject;
import net.whg.we.main.PipelineConstants;
import net.whg.we.rendering.RenderBehavior;
import net.whg.we.rendering.RenderPipeline;
import net.whg.we.window.Screen;
import net.whg.we.rendering.Camera;
import net.whg.we.rendering.IMesh;
import net.whg.we.rendering.Material;

public class RenderPipelineActionTest
{
    @Test
    public void addBehavior()
    {
        RenderBehavior behavior = new RenderBehavior();
        RenderPipeline action = new RenderPipeline();
        action.enableBehavior(behavior);

        assertEquals(1, action.renderBehaviors()
                              .size());
        assertTrue(action.renderBehaviors()
                         .contains(behavior));
    }

    @Test
    public void renderElements()
    {
        IMesh mesh = mock(IMesh.class);
        Material material = mock(Material.class);

        GameObject go = new GameObject();

        RenderBehavior behavior = new RenderBehavior();
        behavior.setMesh(mesh);
        behavior.setMaterial(material);
        go.addBehavior(behavior);

        RenderPipeline action = new RenderPipeline();
        action.setCamera(new Camera(mock(Screen.class)));
        action.enableBehavior(behavior);

        action.run();

        verify(mesh).render();
    }

    @Test
    public void renderElements_noCamera()
    {
        IMesh mesh = mock(IMesh.class);
        Material material = mock(Material.class);

        GameObject go = new GameObject();

        RenderBehavior behavior = new RenderBehavior();
        behavior.setMesh(mesh);
        behavior.setMaterial(material);
        go.addBehavior(behavior);

        RenderPipeline action = new RenderPipeline();
        action.enableBehavior(behavior);

        action.run();

        verify(mesh, never()).render();
    }

    @Test
    public void ensureCorrectPriority()
    {
        assertEquals(PipelineConstants.RENDER_SOLIDS, new RenderPipeline().getPriority());
    }

    @Test
    public void initializeWithCamera()
    {
        Camera camera = mock(Camera.class);
        RenderPipeline pipeline = new RenderPipeline(camera);

        assertTrue(camera == pipeline.getCamera());
    }
}
