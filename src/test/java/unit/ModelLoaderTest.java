package unit;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.junit.Test;
import net.whg.we.rendering.VertexData;
import net.whg.we.resource.ModelLoader;
import net.whg.we.resource.Resource;
import net.whg.we.resource.assimp.AssimpException;
import net.whg.we.resource.assimp.IAssimp;
import net.whg.we.resource.assimp.IAssimpMesh;
import net.whg.we.resource.assimp.IAssimpScene;

public class ModelLoaderTest
{
    private IAssimp buildAssimpMock_plainCube()
    {
        final float[][] vertices = {{1.0f, 1.0f, -1.0f}, {-1.0f, 1.0f, -1.0f}, {-1.0f, 1.0f, 1.0f}, {1.0f, 1.0f, 1.0f},
                {1.0f, -1.0f, 1.0f}, {1.0f, 1.0f, 1.0f}, {-1.0f, 1.0f, 1.0f}, {-1.0f, -1.0f, 1.0f},
                {-1.0f, -1.0f, 1.0f}, {-1.0f, 1.0f, 1.0f}, {-1.0f, 1.0f, -1.0f}, {-1.0f, -1.0f, -1.0f},
                {-1.0f, -1.0f, -1.0f}, {1.0f, -1.0f, -1.0f}, {1.0f, -1.0f, 1.0f}, {-1.0f, -1.0f, 1.0f},
                {1.0f, -1.0f, -1.0f}, {1.0f, 1.0f, -1.0f}, {1.0f, 1.0f, 1.0f}, {1.0f, -1.0f, 1.0f},
                {-1.0f, -1.0f, -1.0f}, {-1.0f, 1.0f, -1.0f}, {1.0f, 1.0f, -1.0f}, {1.0f, -1.0f, -1.0f}};

        final float[][] normals = {{0.0f, 1.0f, 0.0f}, {0.0f, 1.0f, 0.0f}, {0.0f, 1.0f, 0.0f}, {0.0f, 1.0f, 0.0f},
                {0.0f, 0.0f, 1.0f}, {0.0f, 0.0f, 1.0f}, {0.0f, 0.0f, 1.0f}, {0.0f, 0.0f, 1.0f}, {-1.0f, 0.0f, 0.0f},
                {-1.0f, 0.0f, 0.0f}, {-1.0f, 0.0f, 0.0f}, {-1.0f, 0.0f, 0.0f}, {0.0f, -1.0f, 0.0f}, {0.0f, -1.0f, 0.0f},
                {0.0f, -1.0f, 0.0f}, {0.0f, -1.0f, 0.0f}, {1.0f, 0.0f, 0.0f}, {1.0f, 0.0f, 0.0f}, {1.0f, 0.0f, 0.0f},
                {1.0f, 0.0f, 0.0f}, {0.0f, 0.0f, -1.0f}, {0.0f, 0.0f, -1.0f}, {0.0f, 0.0f, -1.0f}, {0.0f, 0.0f, -1.0f}};

        final float[][] tangents = {{-1.0f, 0.0f, 0.0f}, {-1.0f, 0.0f, 0.0f}, {-1.0f, 0.0f, 0.0f}, {-1.0f, 0.0f, 0.0f},
                {0.0f, 1.0f, 0.0f}, {0.0f, 1.0f, 0.0f}, {0.0f, 1.0f, 0.0f}, {0.0f, 1.0f, 0.0f}, {0.0f, 1.0f, 0.0f},
                {0.0f, 1.0f, 0.0f}, {0.0f, 1.0f, 0.0f}, {0.0f, 1.0f, 0.0f}, {1.0f, 0.0f, 0.0f}, {1.0f, 0.0f, 0.0f},
                {1.0f, 0.0f, 0.0f}, {1.0f, 0.0f, 0.0f}, {0.0f, 1.0f, 0.0f}, {0.0f, 1.0f, 0.0f}, {0.0f, 1.0f, 0.0f},
                {0.0f, 1.0f, 0.0f}, {0.0f, 1.0f, 0.0f}, {0.0f, 1.0f, 0.0f}, {0.0f, 1.0f, 0.0f}, {0.0f, 1.0f, 0.0f}};

        final float[][] bitangents = {{0.0f, 0.0f, 1.0f}, {0.0f, 0.0f, 1.0f}, {0.0f, 0.0f, 1.0f}, {0.0f, 0.0f, 1.0f},
                {-1.0f, 0.0f, 0.0f}, {-1.0f, 0.0f, 0.0f}, {-1.0f, 0.0f, 0.0f}, {-1.0f, 0.0f, 0.0f}, {0.0f, 0.0f, -1.0f},
                {0.0f, 0.0f, -1.0f}, {0.0f, 0.0f, -1.0f}, {0.0f, 0.0f, -1.0f}, {0.0f, 0.0f, 1.0f}, {0.0f, 0.0f, 1.0f},
                {0.0f, 0.0f, 1.0f}, {0.0f, 0.0f, 1.0f}, {0.0f, 0.0f, 1.0f}, {0.0f, 0.0f, 1.0f}, {0.0f, 0.0f, 1.0f},
                {0.0f, 0.0f, 1.0f}, {1.0f, 0.0f, 0.0f}, {1.0f, 0.0f, 0.0f}, {1.0f, 0.0f, 0.0f}, {1.0f, 0.0f, 0.0f}};

        final float[][][] uvs = {{{0.625f, 0.5f}, {0.875f, 0.5f}, {0.875f, 0.25f}, {0.625f, 0.25f}, {0.375f, 0.25f},
                {0.625f, 0.25f}, {0.625f, 0.0f}, {0.375f, 0.0f}, {0.375f, 1.0f}, {0.625f, 1.0f}, {0.625f, 0.75f},
                {0.375f, 0.75f}, {0.125f, 0.5f}, {0.375f, 0.5f}, {0.375f, 0.25f}, {0.125f, 0.25f}, {0.375f, 0.5f},
                {0.625f, 0.5f}, {0.625f, 0.25f}, {0.375f, 0.25f}, {0.375f, 0.75f}, {0.625f, 0.75f}, {0.625f, 0.5f},
                {0.375f, 0.5f}}};

        final short[][] triangles = {{0, 1, 2}, {0, 2, 3}, {4, 5, 6}, {4, 6, 7}, {8, 9, 10}, {8, 10, 11}, {12, 13, 14},
                {12, 14, 15}, {16, 17, 18}, {16, 18, 19}, {20, 21, 22}, {20, 22, 23}};

        return buildAssimpMock(vertices, normals, tangents, bitangents, uvs, triangles);
    }

    private IAssimp buildAssimpMock(float[][] vertices, float[][] normals, float[][] tangents, float[][] bitangents,
            float[][][] uvs, short[][] triangles)
    {
        IAssimp assimp = mock(IAssimp.class);
        IAssimpScene scene = mock(IAssimpScene.class);
        IAssimpMesh mesh = mock(IAssimpMesh.class);

        when(assimp.loadScene(any())).thenReturn(scene);

        when(scene.countMeshes()).thenReturn(1);
        when(scene.getMesh(anyInt())).thenReturn(mesh);

        when(mesh.countBones()).thenReturn(0);
        when(mesh.countVertices()).thenReturn(vertices.length);
        when(mesh.countTriangles()).thenReturn(triangles.length);
        when(mesh.countUVLayers()).thenReturn(uvs.length);

        doAnswer(a ->
        {
            float[] data = a.getArgument(0);
            int pos = a.getArgument(1);
            int index = a.getArgument(2);

            data[pos] = vertices[index][0];
            data[pos + 1] = vertices[index][1];
            data[pos + 2] = vertices[index][2];

            return null;
        }).when(mesh)
          .getVertexPosition(any(), anyInt(), anyInt());

        doAnswer(a ->
        {
            float[] data = a.getArgument(0);
            int pos = a.getArgument(1);
            int index = a.getArgument(2);

            data[pos] = normals[index][0];
            data[pos + 1] = normals[index][1];
            data[pos + 2] = normals[index][2];

            return null;
        }).when(mesh)
          .getVertexNormal(any(), anyInt(), anyInt());

        doAnswer(a ->
        {
            float[] data = a.getArgument(0);
            int pos = a.getArgument(1);
            int index = a.getArgument(2);

            data[pos] = tangents[index][0];
            data[pos + 1] = tangents[index][1];
            data[pos + 2] = tangents[index][2];

            return null;
        }).when(mesh)
          .getVertexTangent(any(), anyInt(), anyInt());

        doAnswer(a ->
        {
            float[] data = a.getArgument(0);
            int pos = a.getArgument(1);
            int index = a.getArgument(2);

            data[pos] = bitangents[index][0];
            data[pos + 1] = bitangents[index][1];
            data[pos + 2] = bitangents[index][2];

            return null;
        }).when(mesh)
          .getVertexBitangent(any(), anyInt(), anyInt());

        doAnswer(a ->
        {
            float[] data = a.getArgument(0);
            int pos = a.getArgument(1);
            int index = a.getArgument(2);
            int layer = a.getArgument(3);

            data[pos] = uvs[layer][index][0];
            data[pos + 1] = uvs[layer][index][1];

            return null;
        }).when(mesh)
          .getVertexUV(any(), anyInt(), anyInt(), anyInt());

        return assimp;
    }

    @Test
    public void loadSimpleModelCube() throws IOException
    {
        File file = mock(File.class);
        when(file.exists()).thenReturn(true);
        when(file.canRead()).thenReturn(true);

        IAssimp assimp = buildAssimpMock_plainCube();
        ModelLoader loader = new ModelLoader(assimp);

        List<Resource> resources = loader.loadScene(file);
        VertexData vertexData = (VertexData) resources.get(0)
                                                      .getData();

        assertEquals(24, vertexData.getVertexCount());
        assertEquals(12, vertexData.getTriangleCount());
        assertEquals(14, vertexData.getVertexSize());
    }

    @Test(expected = FileNotFoundException.class)
    public void loadScene_fileNotFound() throws IOException
    {
        File file = mock(File.class);
        when(file.exists()).thenReturn(false);
        when(file.canRead()).thenReturn(false);

        IAssimp assimp = buildAssimpMock_plainCube();
        ModelLoader loader = new ModelLoader(assimp);

        loader.loadScene(file);
    }

    @Test(expected = IOException.class)
    public void loadScene_fileCannotBeRead() throws IOException
    {
        File file = mock(File.class);
        when(file.exists()).thenReturn(true);
        when(file.canRead()).thenReturn(false);

        IAssimp assimp = buildAssimpMock_plainCube();
        ModelLoader loader = new ModelLoader(assimp);

        loader.loadScene(file);
    }

    @Test(expected = AssimpException.class)
    public void loadScene_failedToLoad() throws IOException
    {
        File file = mock(File.class);
        when(file.exists()).thenReturn(true);
        when(file.canRead()).thenReturn(true);

        IAssimp assimp = mock(IAssimp.class);
        when(assimp.loadScene(any())).thenReturn(null);

        new ModelLoader(assimp).loadScene(file);
    }
}
