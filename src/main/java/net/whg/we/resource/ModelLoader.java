package net.whg.we.resource;

import java.io.File;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIPropertyStore;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIVector3D;
import org.lwjgl.assimp.Assimp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.whg.we.rendering.ShaderAttributes;
import net.whg.we.rendering.VertexData;

/**
 * The model loader is a utilty for loading 3D model file formats.
 */
public final class ModelLoader
{
    private static final Logger logger = LoggerFactory.getLogger(ModelLoader.class);

    private ModelLoader()
    {}

    public static List<Resource> loadScene(File file)
    {
        logger.debug("Loading model file '{}'", file);
        AIScene scene = loadAssimpScene(file);

        List<Resource> resources = new ArrayList<>();

        for (int i = 0; i < scene.mNumMeshes(); i++)
            resources.add(new Resource(loadMesh(AIMesh.create(scene.mMeshes()
                                                                   .get(i)))));

        Assimp.aiReleaseImport(scene);
        return resources;
    }

    private static AIScene loadAssimpScene(File file)
    {
        AIPropertyStore settings = Assimp.aiCreatePropertyStore();
        Assimp.aiSetImportPropertyInteger(settings, Assimp.AI_CONFIG_PP_SLM_VERTEX_LIMIT, 65535);

        AIScene scene = Assimp.aiImportFile(file.toString(),
                Assimp.aiProcess_Triangulate | Assimp.aiProcess_GenSmoothNormals | Assimp.aiProcess_FlipUVs
                        | Assimp.aiProcess_CalcTangentSpace | Assimp.aiProcess_LimitBoneWeights
                        | Assimp.aiProcess_SplitLargeMeshes | Assimp.aiProcess_OptimizeMeshes
                        | Assimp.aiProcess_JoinIdenticalVertices);

        Assimp.aiReleasePropertyStore(settings);

        if (scene == null)
            throw new IllegalStateException("Failed to load scene!");

        return scene;
    }

    private static VertexData loadMesh(AIMesh mesh)
    {
        // Count mesh information
        int boneCount = mesh.mNumBones();
        int vertexCount = mesh.mNumVertices();
        int triCount = mesh.mNumFaces();

        ShaderAttributes attributes = new ShaderAttributes();
        attributes.addAttribute(ShaderAttributes.ATTRIB_POSITION, 3);
        attributes.addAttribute(ShaderAttributes.ATTRIB_NORMAL, 3);

        if (mesh.mTangents() != null)
            attributes.addAttribute(ShaderAttributes.ATTRIB_TANGENT, 3);

        if (mesh.mBitangents() != null)
            attributes.addAttribute(ShaderAttributes.ATTRIB_BITANGENT, 3);

        int uvCount = 0;
        while (mesh.mTextureCoords(uvCount) != null)
        {
            uvCount++;
            attributes.addAttribute(ShaderAttributes.getIndexedAttribute(ShaderAttributes.ATTRIB_UV, uvCount), 2);
        }

        if (boneCount > 0)
        {
            attributes.addAttribute(ShaderAttributes.ATTRIB_BONE_INDICES, 4);
            attributes.addAttribute(ShaderAttributes.ATTRIB_BONE_WEIGHTS, 4);
        }

        // Build vertex data array
        int index = 0;
        float[] vertices = new float[vertexCount * attributes.getVertexSize()];
        for (int v = 0; v < vertexCount; v++)
        {
            // Get position data
            AIVector3D pos = mesh.mVertices()
                                 .get(v);
            vertices[index++] = pos.x();
            vertices[index++] = pos.y();
            vertices[index++] = pos.z();

            // Get normal data
            AIVector3D normal = mesh.mNormals()
                                    .get(v);
            vertices[index++] = normal.x();
            vertices[index++] = normal.y();
            vertices[index++] = normal.z();

            if (mesh.mTangents() != null)
            {
                AIVector3D tangent = mesh.mTangents()
                                         .get(v);
                vertices[index++] = tangent.x();
                vertices[index++] = tangent.y();
                vertices[index++] = tangent.z();
            }

            if (mesh.mBitangents() != null)
            {
                AIVector3D bitangent = mesh.mBitangents()
                                           .get(v);
                vertices[index++] = bitangent.x();
                vertices[index++] = bitangent.y();
                vertices[index++] = bitangent.z();
            }

            for (int texIndex = 0; texIndex < uvCount; texIndex++)
            {
                AIVector3D uv = mesh.mTextureCoords(texIndex)
                                    .get(v);
                vertices[index++] = uv.x();
                vertices[index++] = uv.y();
            }

            // Add bone weight buffer, if needed
            if (boneCount > 0)
                index += 8;
        }

        // Build triangle data array
        index = 0;
        short[] triangles = new short[triCount * 3];
        for (int f = 0; f < triCount; f++)
        {
            // Get vertex indices
            AIFace face = mesh.mFaces()
                              .get(f);
            IntBuffer indices = face.mIndices();
            triangles[index++] = (short) indices.get(0);
            triangles[index++] = (short) indices.get(1);
            triangles[index++] = (short) indices.get(2);
        }

        logger.trace("Loaded mesh with {} vertices, {} triangles, and {}", vertexCount, triCount, attributes);
        return new VertexData(vertices, triangles, attributes);
    }
}
