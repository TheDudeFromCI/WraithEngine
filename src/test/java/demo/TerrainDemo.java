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
import net.whg.lib.behaviors.OrbitCamera;

/**
 * This demo shows an introduction to working with procedurally generated
 * models. In this case, working with reading texture data and using it to
 * generate a terrain mesh. This demo will assume you understand the basics of
 * working with WraithEngine.
 */
public class TerrainDemo extends DemoBase
{
    /**
     * When generating our terrain mesh, this is the number of quads to generate the
     * terrain with.
     */
    private static final int RESOLUTION = 127;

    /**
     * This is the size of the terrain we will be generating in meters.
     */
    private static final float SCALE = 30f;

    /**
     * The amplitude is the max height of the terrain in meters.
     */
    private static final float AMPLITUIDE = SCALE / 8f;

    /**
     * Starts the Terrain demo. Your usual Java stuff.
     */
    public static void main(String[] args)
    {
        // Alright, let's dive in. This creates and starts the terrain demo.
        new TerrainDemo().run();
    }

    /**
     * Creates the Terrain demo.
     */
    public TerrainDemo()
    {
        // This simply calls the demo base to initialize the common WraithEngine
        // pipeline. This will keep the code cleaner and on topic.
        super("Terrain Demo");

        // Now we'll load the terrain and add it to the scene.
        loadTerrain();

        // We want to be able to get a better look at the terrain, so let's setup a
        // quick orbit camera to rotate around the center of the map.
        setupOrbitCamera();
    }

    /**
     * Creates a new game object to manage the camera orbit behavior.
     */
    private void setupOrbitCamera()
    {
        // The orbit camera behavior is a simple controller for allowing the user to
        // pivot the camera around a point. This will allow us to get a much better look
        // at our newly generated mesh.
        OrbitCamera orbit = new OrbitCamera(getCamera(), getInput());

        // We want the camera to orbit around the center of the terrain, so we will
        // offset the orbit here to line it up.
        orbit.getOffset()
             .set(SCALE / 2f, AMPLITUIDE / 2f, SCALE / 2f);

        // Because the terrain is pretty big, we need to adjust the camera distance. I
        // think using the terrain scale * sqrt(2) is a good value for handing this
        // without too much empty space on the screen.
        orbit.setDistance(SCALE / 1.414f);

        // Lastly, let's just set the initial rotation of the camera to an arial view,
        // so we can see the whole thing at once. The orbit camera doesn't have
        // collision detection, so the default rotation (0, 0) might clip through the
        // ground here!
        orbit.setRotation(0f, -1f);

        // Awesome. Now that we have the orbit camera beavhior set up, we can attach it
        // to a new, empty game object and add it to our scene.
        GameObject go = new GameObject();
        go.setName("Orbit Camera");
        go.addBehavior(orbit);
        getScene().addGameObject(go);
    }

    /**
     * Loads the heightmap texture and generates a terrain mesh from the heightmap
     * data. The terrain will then be converted to a game object and added to the
     * scene.
     */
    private void loadTerrain()
    {
        // Okay, starting off pretty standard. Create a game object, name it, and add it
        // to the scene.
        GameObject gameObject = new GameObject();
        gameObject.setName("Terrain");
        getScene().addGameObject(gameObject);

        // We want to render the terrain of course, so let's attach a render behavior.
        RenderBehavior renderBehavior = new RenderBehavior();
        gameObject.addBehavior(renderBehavior);

        // We need to add a material to the behavior so it knows how to render the mesh.
        // Let's load a simple grass material for this.
        renderBehavior.setMaterial(loadGrassMaterial());

        // Now let's get to the mesh itself. We'll need to first generate the vertex
        // data for the terrain mesh.
        VertexData vertexData = generateMesh();

        // Now we can quickly upload that vertex data into a regular mesh and send that
        // to the render behavior. We should be able to actually see the terrain now! :D
        IMesh mesh = getRenderingEngine().createMesh();
        mesh.update(vertexData);
        renderBehavior.setMesh(mesh);
    }

    /**
     * Loads a grass material we will apply to the terrain.
     * 
     * @return Loads and returns a grass material.
     */
    private Material loadGrassMaterial()
    {
        // All materials start with a shader, so let's go ahead an load a standard
        // diffuse shader.
        IShader shader = loadShader("diffuse");

        // Let's load the grass texture and update the sampling mode. We want the
        // texture to use mipmaps so it looks good in the distance. Also, we want the
        // sampling mode to used nearest when close up to preserve the pixels, but to
        // blend together with mipmaps when far away to reduce noisy artfacts.
        TextureData grassData = loadTextureData("grass.png");
        grassData.setMipmap(true);
        grassData.setSampleMode(SampleMode.NEAREST_SMOOTHED);

        // Now we convert the texture data into a texture object on the GPU.
        ITexture grass = getRenderingEngine().createTexture();
        grass.update(grassData);

        // Lastly, let's combine the shader and texture in the usual way to finish up
        // our material.
        Material grassMaterial = new Material(shader);
        grassMaterial.setTextures(new ITexture[] {grass}, new String[] {"diffuse"});
        return grassMaterial;
    }

    /**
     * Samples the given heightmap texture at the given coordinates.
     * 
     * @param heightmap
     *     - The heightmap to sample from.
     * @param x
     *     - The normalized x coord to sample from.
     * @param y
     *     - The normalized y coord to sample from.
     * @return The normalized height at the given location.
     */
    private float sampleHeight(TextureData heightmap, float x, float y)
    {
        // To sample the height value of the heightmap, we need to convert the
        // normalized coordinates to the pixel coordinates of the texture. We can do
        // this by multiplying the x and y with the width and height of the texture,
        // then rounding to the nearest integer. We also want to subtract by one to stop
        // an out of bounds error from occur when sampling at the very edge of the
        // texture.
        int a = Math.round(x * (heightmap.getWidth() - 1));
        int b = Math.round(y * (heightmap.getHeight() - 1));

        // Now we can grab the pixel from the texture. While we can use
        // getPixelAsColor(int, int), that method is far too slow to rely on with how
        // often we'll be needing to sample the height map. It's far more effcient to
        // sample the compressed int value and do some basic binary operations to
        // extract what we need.
        int argb = heightmap.getPixel(a, b);

        // Since we just need the grayscale height value, we can simply use "& 0xFF" to
        // pull out the blue component. Divide it by 255 to take it back into the 0 to 1
        // range.
        return (argb & 0xFF) / 255f;
    }

    /**
     * This function calculates all of the smoothed normals for the terrain. This
     * works by calculating the normal for each triangle via cross-product of the
     * edges. That normal vector is then added to each of the 3 vertex normals for
     * the triangle. After all triangles have been processed, the normal vector for
     * each vertex is then normalized.
     * 
     * @param vertices
     *     - The list of vertices to process.
     * @param triangles
     *     - The triangles for the mesh.
     * @param vertexSize
     *     - The size of each vertex. I.e. the total number of float values required
     *     to represent a vertex.
     * @param posOffset
     *     - The offset of the position value within the vertex array.
     * @param normalOffset
     *     - The offset of the normal value within the vertex array.
     */
    private void recalculateNormals(float[] vertices, short[] triangles, int vertexSize, int posOffset,
            int normalOffset)
    {
        // This method is used to calculate the normals of the terrain mesh after the
        // shape has finished generating. While this chunk of code looks pretty complex,
        // it's actually quite simple once you break it down. You ready? Let's dive in.

        // First, let's just create some buffer objects to store our vectors in. Doing
        // this outside of the loop allows us to reuse them. Reusing objects is always
        // important to save preformance! First we'll need three vectors to store the
        // position of the three vertices of a triangle, p1, p2, and p3 respectively.
        Vector3f p1 = new Vector3f();
        Vector3f p2 = new Vector3f();
        Vector3f p3 = new Vector3f();

        // Next, we'll make some extra vectors, v and w will be used to represent two of
        // the side lengths of a triangle. This can be used to find the plane of the
        // triangle. n is the normal of the triangle perpendicular to the plane.
        Vector3f v = new Vector3f();
        Vector3f w = new Vector3f();
        Vector3f n = new Vector3f();

        // Starting off, we're going to want to quickly run through every vertex and set
        // its normal value to 0. This will ensure that we have a clean slate when we do
        // our math. Because vertices are stored as a single float array, we'll have to
        // pull out the normal positions manually and modify them. The vertexSize is the
        // number of floats which is used to make up a single vertex. So we can step
        // along this value to target each vertex one by one. Because normals are
        // somewhere in the middle of the vertex properties, we will offset i so that we
        // target the normal attribute of each vertex.
        for (int i = normalOffset; i < vertices.length; i += vertexSize)
        {
            // Nowe just reset the next 3 values to 0.
            vertices[i + 0] = 0;
            vertices[i + 1] = 0;
            vertices[i + 2] = 0;
        }

        // Next, we're going to step over each triangle in the mesh. A triangle is made
        // of 3 vertices, so we will increment by 3 each step.
        for (int t = 0; t < triangles.length; t += 3)
        {
            int index;

            // This next part looks a bit overwhelming. But really, all we're doing is just
            // reading the index values for each point on the triangle, and grabbing the
            // vertex position and storing it in p1, p2 and p3. Remember, with a triangle,
            // it points to the vertex id, number it's position within the array. So we'll
            // have to multiply it by the vertex position. Then offset that value to the
            // location of the vertex position attribute. Finally we can pull those float
            // values from the vertice array and plug them right into our vectors.
            index = triangles[t + 0] * vertexSize + posOffset;
            p1.set(vertices[index + 0], vertices[index + 1], vertices[index + 2]);

            index = triangles[t + 1] * vertexSize + posOffset;
            p2.set(vertices[index + 0], vertices[index + 1], vertices[index + 2]);

            index = triangles[t + 2] * vertexSize + posOffset;
            p3.set(vertices[index + 0], vertices[index + 1], vertices[index + 2]);

            // This tiny part right here is where we do the actual normal calculation. This
            // works by creating two directional vectors, for sides p2-p1 and p3-p1 of the
            // triangle. Then we take the cross product to find the vector perpendicular to
            // them. Or in other words, the normal. We just have to normalize the output
            // before we can use it. Ta da! Done.
            p2.sub(p1, v);
            p3.sub(p1, w);
            v.cross(w, n);
            n.normalize();

            // Alright, now that we have the triangle normal, we're just going to add that
            // to each of the three vertices which make up the triangle in the same manner
            // as above. As you probably learned back in grade school, you can find the
            // average of a list of numbers by adding them all together and dividing by tthe
            // number of items in that list. The exact same principal exists here, even in 3
            // dimensions! We want to generate smoothed vertex normals, which can be done by
            // setting the normal of the vertex to the average of all triangles which are
            // touching the vertex.
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

        // By this point, each vertex has a normal length each is equal to the sum of
        // the normals of all triangles which touch it. All you have to do is divide by
        // the number of triangles which use that vertex and poof, you have yourself a
        // smoothed normal. But how do we do that? Easy! The length of a normal is
        // always 1 unit, thus, the length of each normal is each to the number of
        // triangles which added to it! We can just do a simple normalize operation and
        // we're done.
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

    /**
     * This method is responsible for generating the terrain mesh.
     * 
     * @return The vertex data from the resulting terrain mesh.
     */
    private VertexData generateMesh()
    {
        // Let's jump into the meat of this demo. Generating the terrain. Alright,
        // first, we're going to want to load the heightmap texture. We'll just load it
        // as texture data, as we won't be sending it to the GPU.
        TextureData heightmap = loadTextureData("terrain.png");

        // You're also going to have to create a shader attributes object when
        // generating a mesh. Each shader has it's own layout of attributes for
        // rendering meshes. Since we're using the diffuse shader, this is it's intended
        // layout. This is also the default attribute layout when loading models through
        // the model loader.
        ShaderAttributes attributes = new ShaderAttributes();
        attributes.addAttribute(ShaderAttributes.ATTRIB_POSITION, 3);
        attributes.addAttribute(ShaderAttributes.ATTRIB_NORMAL, 3);
        attributes.addAttribute(ShaderAttributes.ATTRIB_TANGENT, 3);
        attributes.addAttribute(ShaderAttributes.ATTRIB_BITANGENT, 3);
        attributes.addAttribute(ShaderAttributes.ATTRIB_UV, 2);

        // The vertex size is the total number of floats which are required to make up a
        // vertex with the current attribute layout. In the Shader Attributes above, we
        // have position which is 3, normal which is 3, tangents, also 3, bi tangents,
        // 3, and lastly uvs, which are 2. Add all those together, and you get 14.
        int vertexSize = attributes.getVertexSize();

        // Using the resolution of the terrain, we can count how many vertices and
        // triangles we're going to need. As resolution is the number of quads along one
        // side of the terrain, so squaring that gives us the number of quads within the
        // entire terrain. Quads require 4 vertices to exist, but they also share
        // vertices with touching quads. So the number actually works out to be the
        // number of quads plus 1, squared. For triangles, it's simply the number of
        // quads times 2, as it takes two triangles to make a quad.
        int vertexCount = (RESOLUTION + 1) * (RESOLUTION + 1);
        int triangleCount = RESOLUTION * RESOLUTION * 2;

        // Using the vertex size, vertex count, and triangle count, we can build some
        // arrays which will hold the vertices and tringles. The vertex array is an
        // array of floats stored in a straight set of values based on the attribute
        // layout. Triangles are just pointers to vertex ids. Each set of 3 vertex
        // pointers, make up a single triangle. Also, keep in mind that triangles point
        // to the vertex id, ignoring vertex size completely.
        float[] vertices = new float[vertexCount * vertexSize];
        short[] triangles = new short[triangleCount * 3];

        // Now lets build our grid of vertices.
        for (int y = 0, i = 0; y <= RESOLUTION; y++)
        {
            for (int x = 0; x <= RESOLUTION; x++, i += vertexSize)
            {
                // The dx and dy, or delta x and delta y, represent the percent of the way (0-1
                // inclusive) we are across the grid. Quite useful for making it resolution and
                // scale independent.
                float dx = (float) x / RESOLUTION;
                float dy = (float) y / RESOLUTION;

                // Now we calculate the position of the vertex. When chosing the index of the
                // vertex within the array, it's vertex important to keep the attribute layout
                // in mind. Since the position is the first attribute in the layout, we can
                // start at offset 0. Attributes are continuous, so the next 3 elements are the
                // x, y, and z components of the vertex position. To calculate the position, we
                // can simply multiply the delta x and delta y with the scale to find the world
                // x and z positions. The y element can be found by sampling the heightmap at
                // the delta position and multiplying the output by our desired amplitude.
                vertices[i + 0] = dx * SCALE;
                vertices[i + 1] = sampleHeight(heightmap, dx, dy) * AMPLITUIDE;
                vertices[i + 2] = dy * SCALE;

                // We're going to ignore the normal, tangent, and bitangent for now. Let's skip
                // ahead to the uv. Given it's position within the attribute layout, it has an
                // offset of 12. Also, because it's 2 elements long, we can know the next two
                // elements are the u and v components. Here, we'll just use the same scaling as
                // the map itself. This will push from uv elements into valurs above 1, which is
                // okay because the grass texture is set to repeat wrap mode.
                vertices[i + 12] = dx * SCALE;
                vertices[i + 13] = dy * SCALE;
            }
        }

        // Now that we have the vertices generated, we just have to generate the
        // triangles. Triangles don't care much about the vertices themselves, just
        // their id. Because the terrain is basically a grid shape, we automatically
        // know the id of each vertex already and can calculate it based on the
        // triangle's index. Let's start by iterating over the grid of quads.
        for (int t = 0, v = 0, y = 0; y < RESOLUTION; y++, v++)
        {
            for (int x = 0; x < RESOLUTION; x++, v++)
            {
                // Each quad is made of two triangles and a triangle is made of three vertices.
                // So we just have to assign the 6 vertex ids which are used to make up this
                // quad. Knowing that the vertices are in a grid shape, we can cheat and
                // calculate the right vertex indices by adding simple offsets. Easy peasy.
                triangles[t++] = (short) v;
                triangles[t++] = (short) (v + RESOLUTION + 1);
                triangles[t++] = (short) (v + 1);
                triangles[t++] = (short) (v + 1);
                triangles[t++] = (short) (v + RESOLUTION + 1);
                triangles[t++] = (short) (v + RESOLUTION + 2);
            }
        }

        // Above, we skipped calculating the normals. Calculating the normals after the
        // all triangles and vertex positions have been calculated makes things much
        // easier, so we'll do that now. We'll still ignore the tangents and bitangents
        // as our grass texture doesn't have any normal mapping anyway.
        recalculateNormals(vertices, triangles, vertexSize, 0, 3);

        // Finally... we're done. We can drop all of this data into a new vertex data
        // object and return it to be compiled into a regular mesh. Awesome.
        return new VertexData(vertices, triangles, attributes);
    }
}
