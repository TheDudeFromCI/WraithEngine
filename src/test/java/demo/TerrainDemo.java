package demo;

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

public class TerrainDemo extends DemoBase
{
    private static final int RESOLUTION = 64;
    private static final float SCALE = 30f;

    public TerrainDemo()
    {
        super("Terrain Demo");
        loadTerrain();
        // buildCube();

        OrbitCamera orbit = new OrbitCamera(getCamera(), getInput());
        orbit.setDistance(SCALE / 1.414f);
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

    private float sampleHeight(TextureData heightmap, float x, float y)
    {
        int a = Math.round(x * (heightmap.getWidth() - 1));
        int b = Math.round(y * (heightmap.getHeight() - 1));
        int rgba = heightmap.getPixel(a, b);

        return (rgba & 0xFF) / 255f;
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
                vertices[i + 4] = 1;
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

        return new VertexData(vertices, triangles, attributes);
    }

    public static void main(String[] args)
    {
        new TerrainDemo().run();
    }
}
