package net.whg.we.external;

import java.io.File;
import java.nio.IntBuffer;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIPropertyStore;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIVector3D;
import org.lwjgl.assimp.Assimp;
import net.whg.we.resource.assimp.IAssimp;
import net.whg.we.resource.assimp.IAssimpMesh;
import net.whg.we.resource.assimp.IAssimpScene;

/**
 * An implementation of the Assimp loading API.
 */
public class AssimpAPI implements IAssimp
{
    @Override
    public IAssimpScene loadScene(File file)
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
            return null;

        return new AssimpScene(scene);
    }

    /**
     * Represents a scene which was loaded by assimp.
     */
    public class AssimpScene implements IAssimpScene
    {
        private final AIScene scene;
        private boolean disposed;

        private AssimpScene(AIScene scene)
        {
            this.scene = scene;
        }

        @Override
        public int countMeshes()
        {
            return scene.mNumMeshes();
        }

        @Override
        public void dispose()
        {
            if (isDisposed())
                return;

            disposed = true;
            Assimp.aiReleaseImport(scene);
        }

        @Override
        public boolean isDisposed()
        {
            return disposed;
        }

        @Override
        public IAssimpMesh getMesh(int index)
        {
            return new AssimpMesh(AIMesh.create(scene.mMeshes()
                                                     .get(index)));
        }
    }

    /**
     * Represents a mesh which was loaded by Assimp.
     */
    public class AssimpMesh implements IAssimpMesh
    {
        private final AIMesh mesh;

        private AssimpMesh(AIMesh mesh)
        {
            this.mesh = mesh;
        }

        @Override
        public int countBones()
        {
            return mesh.mNumBones();
        }

        @Override
        public int countVertices()
        {
            return mesh.mNumVertices();
        }

        @Override
        public int countTriangles()
        {
            return mesh.mNumFaces();
        }

        @Override
        public int countUVLayers()
        {
            int uvCount = 0;
            while (mesh.mTextureCoords(uvCount) != null)
                uvCount++;

            return uvCount;
        }

        @Override
        public void getVertexPosition(float[] data, int pos, int index)
        {
            AIVector3D vec = mesh.mVertices()
                                 .get(index);

            data[pos] = vec.x();
            data[pos + 1] = vec.y();
            data[pos + 2] = vec.z();
        }

        @Override
        public void getVertexNormal(float[] data, int pos, int index)
        {
            AIVector3D vec = mesh.mNormals()
                                 .get(index);

            data[pos] = vec.x();
            data[pos + 1] = vec.y();
            data[pos + 2] = vec.z();
        }

        @Override
        public void getVertexTangent(float[] data, int pos, int index)
        {
            AIVector3D vec = mesh.mTangents()
                                 .get(index);

            data[pos] = vec.x();
            data[pos + 1] = vec.y();
            data[pos + 2] = vec.z();
        }

        @Override
        public void getVertexBitangent(float[] data, int pos, int index)
        {
            AIVector3D vec = mesh.mBitangents()
                                 .get(index);

            data[pos] = vec.x();
            data[pos + 1] = vec.y();
            data[pos + 2] = vec.z();
        }

        @Override
        public void getVertexUV(float[] data, int pos, int index, int uvLayer)
        {
            AIVector3D vec = mesh.mTextureCoords(uvLayer)
                                 .get(index);

            data[pos] = vec.x();
            data[pos + 1] = vec.y();
        }

        @Override
        public void getTriangle(short[] data, int pos, int index)
        {
            AIFace face = mesh.mFaces()
                              .get(index);

            IntBuffer indices = face.mIndices();
            data[pos] = (short) indices.get(0);
            data[pos + 1] = (short) indices.get(1);
            data[pos + 2] = (short) indices.get(2);
        }
    }
}
