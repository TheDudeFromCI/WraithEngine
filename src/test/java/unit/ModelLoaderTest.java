package unit;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.util.List;
import org.junit.Test;
import net.whg.we.rendering.VertexData;
import net.whg.we.resource.ModelLoader;
import net.whg.we.resource.Resource;

public class ModelLoaderTest
{
    @Test
    public void loadMesh()
    {
        File file = new File("src/test/res/cube.obj");
        List<Resource> resources = ModelLoader.loadScene(file);

        VertexData vertexData = (VertexData) resources.get(0)
                                                      .getData();

        assertEquals(24, vertexData.getVertexCount());
        assertEquals(14, vertexData.getVertexSize());
    }
}
