package demo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.joml.Quaternionf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.whg.we.external.AssimpAPI;
import net.whg.we.external.GlfwApi;
import net.whg.we.external.OpenGLApi;
import net.whg.we.external.TimeSupplierApi;
import net.whg.we.main.AbstractBehavior;
import net.whg.we.main.GameObject;
import net.whg.we.main.IUpdateable;
import net.whg.we.main.PollEventsPipeline;
import net.whg.we.main.Scene;
import net.whg.we.main.SceneGameLoop;
import net.whg.we.main.Timer;
import net.whg.we.main.TimerAction;
import net.whg.we.main.UpdatePipeline;
import net.whg.we.rendering.Camera;
import net.whg.we.rendering.Color;
import net.whg.we.rendering.IMesh;
import net.whg.we.rendering.IRenderingEngine;
import net.whg.we.rendering.IScreenClearHandler;
import net.whg.we.rendering.IShader;
import net.whg.we.rendering.ITexture;
import net.whg.we.rendering.Material;
import net.whg.we.rendering.RawShaderCode;
import net.whg.we.rendering.RenderBehavior;
import net.whg.we.rendering.RenderPipeline;
import net.whg.we.rendering.ScreenClearPipeline;
import net.whg.we.rendering.TextureData;
import net.whg.we.rendering.VertexData;
import net.whg.we.rendering.TextureData.SampleMode;
import net.whg.we.rendering.opengl.OpenGLRenderingEngine;
import net.whg.we.resource.ModelLoader;
import net.whg.we.resource.Resource;
import net.whg.we.resource.TextureLoader;
import net.whg.we.window.IWindow;
import net.whg.we.window.Screen;
import net.whg.we.window.WindowCloseHandler;
import net.whg.we.window.WindowSettings;
import net.whg.we.window.glfw.GlfwWindow;

/**
 * The spinning cube demo shows all of the core elements required to create a
 * simple scene set up. This includes loading a window, handling timing, working
 * with a pipeline, creating behaviors, updating a scene, loading resources, and
 * rendering a scene.
 * <p>
 * In this demo, you can see a small textured cube which was loaded from file,
 * placed into a scene and is set rotating endlessly. The purpose of this demo
 * is to show how a game would normally be set up to allow for elements to be
 * added or configured.
 * 
 * @author TheDudeFromCI
 */
public class SpinningCubeDemo
{
    private static final Logger logger = LoggerFactory.getLogger(SpinningCubeDemo.class);

    /**
     * Launches the spinning cube demo.
     * 
     * @param args
     *     - Does nothing
     * @throws IOException
     *     If the required resource files could not be found.
     */
    public static void main(String[] args) throws IOException
    {
        logger.info("Running Spinning Cube demo.");

        // The window settings object can be used to configure how the window should be
        // displayed. This includes things like title, size, fullscreen, vSync, etc.
        WindowSettings windowSettings = new WindowSettings();
        windowSettings.setTitle("Spinning Cube Demo");

        // The rendering engine is used to actually render the game. Here, we're loading
        // the OpenGL 3.3 rendering engine.
        IRenderingEngine renderingEngine = new OpenGLRenderingEngine(new OpenGLApi());

        // Creates and displays the window. This window uses the GLFW window handling
        // framework. The window will initialize the rendering engine for us here.
        IWindow window = new GlfwWindow(new GlfwApi(), renderingEngine, windowSettings);

        // Now we can start building our scene. A scene is simply a collection of game
        // objects which are active at a given time. This could mean they are being
        // rendered to the screen, or are simply running logic in the background. A
        // scene is needed to maintain what's active and what's not. Scenes operate on a
        // pipeline workflow, so we'll need to create the pipeline now.
        Scene scene = new Scene();

        // As we can to clear the screen each frame, to draw a new image, we'll add a
        // screen clear pipeline action to the scene. This will tell the scene to clear
        // the screen each frame at the correct time. We'll also confiugre the clear
        // color to be a dark blue color.
        IScreenClearHandler screenClear = renderingEngine.getScreenClearHandler();
        screenClear.setClearColor(new Color(0.2f, 0.2f, 0.5f));
        scene.addPipelineAction(new ScreenClearPipeline(screenClear));

        // Since we want to render our scene, we need to add a render pipeline action to
        // it. The render pipeline can be initialized with a camera, so we'll go ahead
        // and do that here. We'll also offset the position of the camera to see the
        // cube better.
        Camera camera = new Camera(new Screen(window));
        scene.addPipelineAction(new RenderPipeline(camera));
        camera.getTransform()
              .setPosition(0f, 0f, 3f);

        // A game loop is the more important element. This loop is in charge of handling
        // running the events each frame which are required to run the game. A
        // SceneGameLoop is an extention of the game loop which will automatically
        // handle scenes and pipelines as well.
        SceneGameLoop gameLoop = new SceneGameLoop();
        gameLoop.addScene(scene);

        // A lot of updates within a game tend to require a timer to work properly. In
        // our case, the time is needed each frame in order to update the rotation of
        // the cube. So we'll add that here. Notice this is added to the game loop
        // itself, not the scene.
        Timer timer = new Timer(new TimeSupplierApi());
        gameLoop.addAction(new TimerAction(timer));

        // In order to spin the cube, it will need to be updated every frame. To trigger
        // these updates, we'll need to add an update pipeline action to the scene. We
        // can use the timer object we created eariler to initialize the pipeline with.
        scene.addPipelineAction(new UpdatePipeline(timer));

        // When working with a window, it's extremely important to poll the window
        // events at the end of each frame in order to actually display anything on
        // screen or recieve any window events. We can add this helper here to do that
        // for us. Once again, this is added to the game loop and not the scene.
        gameLoop.addAction(new PollEventsPipeline(window));

        // When the user clicks the close button on the window, the window sends out a
        // window close request. This utility will trigger the game loop to stop once
        // the window close request is made.
        WindowCloseHandler.bindToWindow(window, gameLoop);

        // Finally, let's create the cube game object, and add it to the scene.
        scene.addGameObject(buildCube(renderingEngine));

        // Now that the scene is set up, we're ready to start the game loop. Since we're
        // working with a timer, we want to start the timer right before starting the
        // game loop to make sure all the timing is correct.
        timer.startTimer();
        gameLoop.loop();

        // At the game loop is stopped, we can dispose the window and let the program
        // exit peacefully.
        window.dispose();
    }

    /**
     * This function loads all the text from a file and returns it as a string.
     * 
     * @param file
     *     - The file to load.
     * @return The text within the file.
     * @throws IOException
     *     If there was an error while loading the file.
     */
    private static String loadText(File file) throws IOException
    {
        return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())), StandardCharsets.UTF_8);
    }

    /**
     * This function loads all resources required by the spinning cube, and returns
     * it as a game object to be added to the scene.
     * 
     * @param renderingEngine
     *     - The rendering engine, to allocate resources with.
     * @return The spinning cube game object.
     * @throws IOException
     *     If the resources could not be loaded.
     */
    private static GameObject buildCube(IRenderingEngine renderingEngine) throws IOException
    {
        // Just laying out the file paths to where each resource is located.
        File cubeFile = new File("src/test/res/cube.obj");
        File diffuseVertFile = new File("src/test/res/diffuse.vert");
        File diffuseFragFile = new File("src/test/res/diffuse.frag");
        File grassFile = new File("src/test/res/grass.png");

        // First, let's load the mesh. This one is a bit messy looking, so let's break
        // it down. The model loader is an object which can be used to load 3D model
        // files. We'll use this here to load the cube file.
        ModelLoader modelLoader = new ModelLoader(new AssimpAPI());

        // When the model loader loads a file, a list of resources are returned. These
        // are all the resources the model loader was able to pull from the file.
        // (I.e. mesh, skeleton, materials, etc) In our case, the file only contains
        // mesh data, so we can extract the first resource from the list and pull the
        // data as vertex data.
        List<Resource> resources = modelLoader.loadScene(cubeFile);
        VertexData vertexData = (VertexData) resources.get(0)
                                                      .getData();

        // Now that we have the vertex data, we can compile it into a mesh.
        IMesh mesh = renderingEngine.createMesh();
        mesh.update(vertexData);

        // To load the shader, we just need the vertex shader code and the fragment
        // shader code. We can load the text from those files, and place them into a raw
        // shader code object to pass to the shader.
        RawShaderCode shaderCode = new RawShaderCode(loadText(diffuseVertFile), loadText(diffuseFragFile));

        // Like the mesh, create the shader and compile it using the shader code.
        IShader shader = renderingEngine.createShader();
        shader.compile(shaderCode);

        // Now for the texture. We can load a texture in the same manner as loading a
        // model, using the TextureLoader object. We also want to change the sampling
        // mode to NEAREST, to keep that "pixel-y" look.
        TextureData textureData = TextureLoader.loadTexture(grassFile);
        textureData.setSampleMode(SampleMode.NEAREST);

        // Now we can compile the texture data into a texture.
        ITexture texture = renderingEngine.createTexture();
        texture.update(textureData);

        // After gathering both the shader and the textures, we can use those to create
        // a material which represents our grass. Here, "diffuse" is the name of the
        // texture within the shader code, and corresponds to the grass texture.
        Material material = new Material(shader);
        material.setTextures(new ITexture[] {texture}, new String[] {"diffuse"});

        // All the pieces are set up! Now lets create the game object which represents
        // the cube.
        GameObject gameObject = new GameObject();
        gameObject.setName("Cube");

        // A Behavior is a piece of logic which is attached to a game object to change
        // it's behavior. A render behavoir is an example of that which can be used to
        // get an object to render. You simplely need to provide it with a mesh and a
        // material to work with. Once added to the cube, the cube should be appear on
        // screen.
        RenderBehavior renderBehavior = new RenderBehavior();
        renderBehavior.setMesh(mesh);
        renderBehavior.setMaterial(material);
        gameObject.addBehavior(renderBehavior);

        // We also want to add another behavior for making our cube spin. The custom
        // class for that logic can be seen below. Here we just create a new instance
        // and add it to the cube.
        gameObject.addBehavior(new SpinCube());

        return gameObject;
    }

    /**
     * This class contains a simple set of logic which will rotate the cube forever,
     * updating the rotation each frame before rendering.
     */
    private static class SpinCube extends AbstractBehavior implements IUpdateable
    {
        @Override
        public void update(Timer timer)
        {
            // Get the rotation component of the game object's transform.
            Quaternionf rot = getGameObject().getTransform()
                                             .getRotation();

            // Get the amount of time, in seconds, that passed since the timer was started.
            // (At the beginning of the game loop.)
            float t = (float) timer.getElapsedTime();

            // Reset the rotation, and apply the new rotation values to it.
            rot.identity();
            rot.rotateLocalX(t * 2.2369f);
            rot.rotateLocalY(t * 1.4562f);
            rot.rotateLocalZ(t * 0.2123f);
        }
    }
}
