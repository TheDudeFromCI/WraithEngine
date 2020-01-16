package net.whg.we.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.whg.we.rendering.ShaderAttributes;
import net.whg.we.rendering.VertexData;
import net.whg.we.resource.assimp.AssimpException;
import net.whg.we.resource.assimp.IAssimp;
import net.whg.we.resource.assimp.IAssimpMesh;
import net.whg.we.resource.assimp.IAssimpScene;

/**
 * The model loader is a utilty for loading 3D model file formats.
 */
public final class ModelLoader
{
    private static final Logger logger = LoggerFactory.getLogger(ModelLoader.class);

    private final IAssimp assimp;

    public ModelLoader(final IAssimp assimp)
    {
        this.assimp = assimp;
    }

    /**
     * Loads the scene within the given file as a list of resources.
     * 
     * @param file
     *     - The file to load.
     * @return A list of resources which were loaded from this file.
     * @throws AssimpException
     *     If the scene fails to load.
     * @throws FileNotFoundException
     *     If the file cannot be found.
     * @throws IOException
     *     If the file cannot be read.
     */
    public List<Resource> loadScene(final File file) throws IOException
    {
        logger.debug("Loading model file '{}'", file);

        if (!file.exists())
            throw new FileNotFoundException("Cannot find file: " + file);

        if (!file.canRead())
            throw new IOException("Cannot read file: " + file);

        final IAssimpScene scene = assimp.loadScene(file);

        if (scene == null)
            throw new AssimpException("Failed to load scene!");

        final List<Resource> resources = new ArrayList<>();

        final int meshCount = scene.countMeshes();
        for (int i = 0; i < meshCount; i++)
            resources.add(loadMesh(scene.getMesh(i)));

        scene.dispose();
        return resources;
    }

    /**
     * Loads the given Assimp mesh object.
     */
    private static Resource loadMesh(final IAssimpMesh mesh)
    {
        // Count mesh information
        final int boneCount = mesh.countBones();
        final int vertexCount = mesh.countVertices();
        final int triCount = mesh.countTriangles();

        final ShaderAttributes attributes = new ShaderAttributes();
        attributes.addAttribute(ShaderAttributes.ATTRIB_POSITION, 3);
        attributes.addAttribute(ShaderAttributes.ATTRIB_NORMAL, 3);
        attributes.addAttribute(ShaderAttributes.ATTRIB_TANGENT, 3);
        attributes.addAttribute(ShaderAttributes.ATTRIB_BITANGENT, 3);

        final int uvCount = mesh.countUVLayers();
        for (int i = 1; i <= uvCount; i++)
            attributes.addAttribute(ShaderAttributes.getIndexedAttribute(ShaderAttributes.ATTRIB_UV, i), 2);

        if (boneCount > 0)
        {
            attributes.addAttribute(ShaderAttributes.ATTRIB_BONE_INDICES, 4);
            attributes.addAttribute(ShaderAttributes.ATTRIB_BONE_WEIGHTS, 4);
        }

        int index = 0;
        final float[] vertices = new float[vertexCount * attributes.getVertexSize()];
        for (int v = 0; v < vertexCount; v++)
        {
            mesh.getVertexPosition(vertices, index, v);
            index += 3;

            mesh.getVertexNormal(vertices, index, v);
            index += 3;

            mesh.getVertexTangent(vertices, index, v);
            index += 3;

            mesh.getVertexBitangent(vertices, index, v);
            index += 3;

            for (int uv = 0; uv < uvCount; uv++)
            {
                mesh.getVertexUV(vertices, index, v, uv);
                index += 2;
            }

            if (boneCount > 0)
                index += 8;
        }

        final short[] triangles = new short[triCount * 3];

        index = 0;
        for (int f = 0; f < triCount; f++)
        {
            mesh.getTriangle(triangles, index, f);
            index += 3;
        }

        logger.trace("Loaded mesh with {} vertices, {} triangles, and {}", vertexCount, triCount, attributes);
        logger.trace("Vertices: {}", Arrays.toString(vertices));
        logger.trace("Triangles: {}", Arrays.toString(triangles));
        return new Resource(new VertexData(vertices, triangles, attributes));
    }
}
