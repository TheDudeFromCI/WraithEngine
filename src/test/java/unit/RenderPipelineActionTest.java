package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import org.junit.Test;
import net.whg.we.main.GameObject;
import net.whg.we.main.PipelineConstants;
import net.whg.we.main.RenderBehavior;
import net.whg.we.main.RenderPipelineAction;
import net.whg.we.rendering.Camera;
import net.whg.we.rendering.IMesh;
import net.whg.we.rendering.Material;

public class RenderPipelineActionTest
{
    @Test
    public void addBehavior()
    {
        RenderBehavior behavior = new RenderBehavior();
        RenderPipelineAction action = new RenderPipelineAction();
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

        RenderPipelineAction action = new RenderPipelineAction();
        action.setCamera(new Camera());
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

        RenderPipelineAction action = new RenderPipelineAction();
        action.enableBehavior(behavior);

        action.run();

        verify(mesh, never()).render();
    }

    @Test
    public void ensureCorrectPriority()
    {
        assertEquals(PipelineConstants.RENDER_SOLIDS, new RenderPipelineAction().getPriority());
    }
}
