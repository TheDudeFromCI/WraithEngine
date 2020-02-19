package demo;

import org.joml.Vector3f;
import net.whg.we.main.GameObject;
import net.whg.we.rendering.IMesh;
import net.whg.we.rendering.IShader;
import net.whg.we.rendering.ITexture;
import net.whg.we.rendering.Material;
import net.whg.we.rendering.RenderBehavior;
import net.whg.we.rendering.ShaderAttributes;
import net.whg.we.rendering.TextureData;
import net.whg.we.rendering.VertexData;
import net.whg.we.rendering.TextureData.SampleMode;
import net.whg.we.util.OrbitCamera;

public class TerrainDemo extends DemoBase
{
    private static final int RESOLUTION = 127;
    private static final float SCALE = 30f;

    public TerrainDemo()
    {
        super("Terrain Demo");
        loadTerrain();

        OrbitCamera orbit = new OrbitCamera(getCamera(), getInput());
        orbit.setDistance(SCALE / 1.414f);
        orbit.setRotation(0f, -1f);
        GameObject go = new GameObject();
        go.setName("Orbit Camera");
        go.addBehavior(orbit);
        getScene().addGameObject(go);
    }

    private void loadTerrain()
    {
        VertexData vertexData = generatePlane(RESOLUTION, SCALE, SCALE / 8f);

        IMesh mesh = getRenderingEngine().createMesh();
        mesh.update(vertexData);

        GameObject gameObject = new GameObject();
        gameObject.setName("Terrain");

        RenderBehavior renderBehavior = new RenderBehavior();
        renderBehavior.setMesh(mesh);
        renderBehavior.setMaterial(loadGrassMaterial());
        gameObject.addBehavior(renderBehavior);

        gameObject.getTransform()
                  .setPosition(-SCALE / 2f, 0f, -SCALE / 2f);

        getScene().addGameObject(gameObject);
    }

    private Material loadGrassMaterial()
    {
        IShader shader = loadShader("diffuse");

        TextureData grassData = loadTextureData("grass.png");
        grassData.setSampleMode(SampleMode.NEAREST);
        ITexture grass = getRenderingEngine().createTexture();
        grass.update(grassData);

        Material grassMaterial = new Material(shader);
        grassMaterial.setTextures(new ITexture[] {grass}, new String[] {"diffuse"});

        return grassMaterial;
    }

    private float clamp(float v, float min, float max)
    {
        return Math.min(max, Math.max(v, min));
    }

    private float sampleHeight(TextureData heightmap, float x, float y)
    {
        x = clamp(x, 0f, 1f);
        y = clamp(y, 0f, 1f);

        int a = Math.round(x * (heightmap.getWidth() - 1));
        int b = Math.round(y * (heightmap.getHeight() - 1));
        int rgba = heightmap.getPixel(a, b);

        return (rgba & 0xFF) / 255f;
    }

    private void calculateNormals(float[] vertices, short[] triangles, int vertexSize, int posOffset, int normalOffset)
    {
        Vector3f p1 = new Vector3f();
        Vector3f p2 = new Vector3f();
        Vector3f p3 = new Vector3f();

        Vector3f v = new Vector3f();
        Vector3f w = new Vector3f();
        Vector3f n = new Vector3f();

        for (int t = 0; t < triangles.length; t += 3)
        {
            int index;

            index = triangles[t + 0] * vertexSize + posOffset;
            p1.set(vertices[index + 0], vertices[index + 1], vertices[index + 2]);

            index = triangles[t + 1] * vertexSize + posOffset;
            p2.set(vertices[index + 0], vertices[index + 1], vertices[index + 2]);

            index = triangles[t + 2] * vertexSize + posOffset;
            p3.set(vertices[index + 0], vertices[index + 1], vertices[index + 2]);

            p2.sub(p1, v);
            p3.sub(p1, w);
            v.cross(w, n);
            n.normalize();

            vertices[triangles[t + 0] * vertexSize + normalOffset + 0] += n.x;
            vertices[triangles[t + 0] * vertexSize + normalOffset + 1] += n.y;
            vertices[triangles[t + 0] * vertexSize + normalOffset + 2] += n.z;

            vertices[triangles[t + 1] * vertexSize + normalOffset + 0] += n.x;
            vertices[triangles[t + 1] * vertexSize + normalOffset + 1] += n.y;
            vertices[triangles[t + 1] * vertexSize + normalOffset + 2] += n.z;

            vertices[triangles[t + 2] * vertexSize + normalOffset + 0] += n.x;
            vertices[triangles[t + 2] * vertexSize + normalOffset + 1] += n.y;
            vertices[triangles[t + 2] * vertexSize + normalOffset + 2] += n.z;
        }

        for (int i = normalOffset; i < vertices.length; i += vertexSize)
        {
            n.x = vertices[i + 0];
            n.y = vertices[i + 1];
            n.z = vertices[i + 2];

            n.normalize();

            vertices[i + 0] = n.x;
            vertices[i + 1] = n.y;
            vertices[i + 2] = n.z;
        }
    }

    private VertexData generatePlane(int verts, float scale, float amplitude)
    {
        TextureData heightmap = loadTextureData("terrain.png");

        ShaderAttributes attributes = new ShaderAttributes();
        attributes.addAttribute(ShaderAttributes.ATTRIB_POSITION, 3);
        attributes.addAttribute(ShaderAttributes.ATTRIB_NORMAL, 3);
        attributes.addAttribute(ShaderAttributes.ATTRIB_TANGENT, 3);
        attributes.addAttribute(ShaderAttributes.ATTRIB_BITANGENT, 3);
        attributes.addAttribute(ShaderAttributes.ATTRIB_UV, 2);

        int vertexSize = attributes.getVertexSize();

        float[] vertices = new float[(verts + 1) * (verts + 1) * vertexSize];
        short[] triangles = new short[verts * verts * 6];

        for (int y = 0, i = 0; y <= verts; y++)
        {
            for (int x = 0; x <= verts; x++, i += vertexSize)
            {
                float dx = (float) x / verts;
                float dy = (float) y / verts;

                vertices[i + 0] = dx * scale;
                vertices[i + 1] = sampleHeight(heightmap, dx, dy) * amplitude;
                vertices[i + 2] = dy * scale;

                vertices[i + 3] = 0;
                vertices[i + 4] = 0;
                vertices[i + 5] = 0;

                vertices[i + 6] = 0;
                vertices[i + 7] = 0;
                vertices[i + 8] = 0;

                vertices[i + 9] = 0;
                vertices[i + 10] = 0;
                vertices[i + 11] = 0;

                vertices[i + 12] = dx * scale;
                vertices[i + 13] = dy * scale;
            }
        }

        for (int ti = 0, vi = 0, y = 0; y < verts; y++, vi++)
        {
            for (int x = 0; x < verts; x++, ti += 6, vi++)
            {
                triangles[ti + 0] = (short) vi;
                triangles[ti + 1] = (short) (vi + verts + 1);
                triangles[ti + 2] = (short) (vi + 1);
                triangles[ti + 3] = (short) (vi + 1);
                triangles[ti + 4] = (short) (vi + verts + 1);
                triangles[ti + 5] = (short) (vi + verts + 2);
            }
        }

        calculateNormals(vertices, triangles, vertexSize, 0, 3);

        return new VertexData(vertices, triangles, attributes);
    }

    public static void main(String[] args)
    {
        new TerrainDemo().run();
    }
}
