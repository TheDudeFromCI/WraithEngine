package net.whg.we.util;

import java.io.File;
import java.nio.IntBuffer;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIPropertyStore;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIVector3D;
import org.lwjgl.assimp.Assimp;
import net.whg.we.rendering.ShaderAttributes;
import net.whg.we.rendering.VertexData;

public final class MeshLoader
{
    private MeshLoader()
    {}

    public static VertexData loadMesh(File file)
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

        AIMesh[] meshes = new AIMesh[scene.mNumMeshes()];
        for (int i = 0; i < meshes.length; i++)
            meshes[i] = AIMesh.create(scene.mMeshes()
                                           .get(i));

        VertexData data = loadMesh(meshes[0]);
        Assimp.aiReleaseImport(scene);
        return data;
    }

    public static VertexData loadMesh(AIMesh mesh)
    {
        // Count mesh information
        int boneCount = mesh.mNumBones();
        int vertexCount = mesh.mNumVertices();
        int triCount = mesh.mNumFaces();

        ShaderAttributes attributes = new ShaderAttributes();
        attributes.addAttribute("pos", 3);
        attributes.addAttribute("normal", 3);

        int uvCount = 0;
        while (mesh.mTextureCoords(uvCount) != null)
            uvCount++;

        if (mesh.mTangents() != null)
            attributes.addAttribute("tangent", 3);

        if (mesh.mBitangents() != null)
            attributes.addAttribute("bitangent", 3);

        if (uvCount > 0)
        {
            attributes.addAttribute("uv", 2);

            for (int i = 2; i <= uvCount; i++)
                attributes.addAttribute("uv" + i, 2);
        }

        if (boneCount > 0)
        {
            attributes.addAttribute("bone1", 4);
            attributes.addAttribute("bone2", 4);
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

        return new VertexData(vertices, triangles, attributes);
    }
}
