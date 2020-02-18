package demo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.whg.we.external.AssimpAPI;
import net.whg.we.external.GlfwApi;
import net.whg.we.external.OpenGLApi;
import net.whg.we.external.TimeSupplierApi;
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
import net.whg.we.rendering.RawShaderCode;
import net.whg.we.rendering.RenderPipeline;
import net.whg.we.rendering.ScreenClearPipeline;
import net.whg.we.rendering.TextureData;
import net.whg.we.rendering.VertexData;
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
 * This class is used to handle most of the low-level implmentation for several
 * of the more complex demos available to keep the code examples clean and to
 * the point. If you're looking where to get started, see the
 * {@link SpinningCubeDemo} demo for a more friendly introduction.
 * 
 * @author TheDudeFromCI
 */
public abstract class DemoBase
{
    private static final Logger logger = LoggerFactory.getLogger(DemoBase.class);

    private Scene scene;
    private Timer timer;
    private SceneGameLoop gameLoop;
    private IWindow window;
    private IRenderingEngine renderingEngine;
    private IScreenClearHandler screenClear;
    private Camera camera;

    /**
     * Initializes the demo with default settings. This will open the window but
     * does not start the game loop.
     * 
     * @param name
     *     - The name of the demo.
     */
    protected DemoBase(String name)
    {
        WindowSettings windowSettings = new WindowSettings();
        windowSettings.setTitle(name);

        renderingEngine = new OpenGLRenderingEngine(new OpenGLApi());
        window = new GlfwWindow(new GlfwApi(), renderingEngine, windowSettings);

        scene = new Scene();

        screenClear = renderingEngine.getScreenClearHandler();
        screenClear.setClearColor(new Color(0.2f, 0.4f, 0.8f));
        scene.addPipelineAction(new ScreenClearPipeline(screenClear));

        camera = new Camera(new Screen(window));
        scene.addPipelineAction(new RenderPipeline(camera));
        camera.getTransform()
              .setPosition(0f, 0f, 3f);

        gameLoop = new SceneGameLoop();
        gameLoop.addScene(scene);

        timer = new Timer(new TimeSupplierApi());
        gameLoop.addAction(new TimerAction(timer));

        scene.addPipelineAction(new UpdatePipeline(timer));

        gameLoop.addAction(new PollEventsPipeline(window));

        WindowCloseHandler.bindToWindow(window, gameLoop);
    }

    /**
     * Runs the game loop.
     */
    protected void run()
    {
        timer.startTimer();
        gameLoop.loop();

        window.dispose();
    }

    /**
     * Converts the given resource path into a file.
     * 
     * @param path
     *     - The path of the resource, relative to the test/res folder.
     * @return The file.
     */
    private File getFile(String path)
    {
        return new File("src/test/res/" + path);
    }

    /**
     * Loads a shader at the given path. The vertex and fragment shaders are
     * discovered by appending ".vert" and ".frag" to the path respectively.
     * 
     * @param path
     *     - The path to find the shader at.
     * @return The shader.
     */
    protected IShader loadShader(String path)
    {
        try
        {
            String vertFile = getFile(path + ".vert").getAbsolutePath();
            String fragFile = getFile(path + ".frag").getAbsolutePath();

            RawShaderCode shaderCode =
                    new RawShaderCode(new String(Files.readAllBytes(Paths.get(vertFile)), StandardCharsets.UTF_8),
                            new String(Files.readAllBytes(Paths.get(fragFile)), StandardCharsets.UTF_8));

            IShader shader = renderingEngine.createShader();
            shader.compile(shaderCode);
            return shader;
        }
        catch (IOException e)
        {
            logger.error("Failed to load shader: " + path, e);
            return null;
        }
    }

    /**
     * Loads the vertex data of aa mesh at the given path. If multiple meshes exist
     * within the file, only the first one is returned.
     * 
     * @param path
     *     - The path of the file to load.
     * @return The mesh.
     */
    protected VertexData loadMeshData(String path)
    {
        try
        {
            ModelLoader modelLoader = new ModelLoader(new AssimpAPI());

            List<Resource> resources = modelLoader.loadScene(getFile(path));
            return (VertexData) resources.get(0)
                                         .getData();
        }
        catch (IOException e)
        {
            logger.error("Failed to load mesh: " + path, e);
            return null;
        }
    }

    /**
     * Loads the mesh at the given file path. A new mesh instance will automatically
     * be created and the vertex data uploaded to it.
     * 
     * @param path
     *     - The path of the mesh file to load.
     * @return The mesh.
     */
    protected IMesh loadMesh(String path)
    {
        IMesh mesh = renderingEngine.createMesh();
        mesh.update(loadMeshData(path));

        return mesh;
    }

    /**
     * Loads the texture data at the given file path. This can be any common image
     * file format.
     * 
     * @param path
     *     - The path of the texture to load.
     * @return The texture data.
     */
    protected TextureData loadTextureData(String path)
    {
        try
        {
            return TextureLoader.loadTexture(getFile(path));
        }
        catch (IOException e)
        {
            logger.error("Failed to load texture: " + path, e);
            return null;
        }
    }

    /**
     * Loads the texture at the given file path. This can be any common image file
     * format. A new texture instance will automatically be created and the texture
     * data uploaded to it.
     * 
     * @param path
     *     - The path of the file to load.
     * @return The texture.
     */
    protected ITexture loadTexture(String path)
    {
        ITexture texture = renderingEngine.createTexture();
        texture.update(loadTextureData(path));

        return texture;
    }

    /**
     * Gets the screen clear handler.
     * 
     * @return The screen clear handler.
     */
    protected IScreenClearHandler getScreenClear()
    {
        return screenClear;
    }

    /**
     * Gets the main camera.
     * 
     * @return The camera.
     */
    protected Camera getCamera()
    {
        return camera;
    }

    /**
     * Gets the rendering engine.
     * 
     * @return The rendering engine.
     */
    protected IRenderingEngine getRenderingEngine()
    {
        return renderingEngine;
    }

    /**
     * Gets the active scene.
     * 
     * @return The scene.
     */
    protected Scene getScene()
    {
        return scene;
    }
}
