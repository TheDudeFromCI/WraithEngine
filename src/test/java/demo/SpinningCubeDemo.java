package demo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.joml.Quaternionf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.whg.we.external.AssimpAPI;
import net.whg.we.external.GlfwApi;
import net.whg.we.external.OpenGLApi;
import net.whg.we.external.TimeSupplierApi;
import net.whg.we.main.AbstractBehavior;
import net.whg.we.main.GameLoop;
import net.whg.we.main.GameObject;
import net.whg.we.main.ITimeSupplier;
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
import net.whg.we.rendering.opengl.IOpenGL;
import net.whg.we.rendering.opengl.OpenGLRenderingEngine;
import net.whg.we.resource.ModelLoader;
import net.whg.we.resource.TextureLoader;
import net.whg.we.resource.assimp.IAssimp;
import net.whg.we.window.IWindow;
import net.whg.we.window.IWindowAdapter;
import net.whg.we.window.Screen;
import net.whg.we.window.WindowSettings;
import net.whg.we.window.glfw.GlfwWindow;
import net.whg.we.window.glfw.IGlfw;

public class SpinningCubeDemo
{
    private static final Logger logger = LoggerFactory.getLogger(SpinningCubeDemo.class);

    public static void main(String[] args) throws IOException
    {
        logger.info("Running Spinning Cube demo.");

        ITimeSupplier timeSupplier = new TimeSupplierApi();
        Timer timer = new Timer(timeSupplier);

        IOpenGL opengl = new OpenGLApi();
        IRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);

        WindowSettings windowSettings = new WindowSettings();
        windowSettings.setTitle("Spinning Cubes Demo");

        IGlfw glfw = new GlfwApi();
        IWindow window = new GlfwWindow(glfw, renderingEngine, windowSettings);

        IAssimp assimp = new AssimpAPI();

        Screen screen = new Screen(window);
        Camera camera = new Camera(screen);
        camera.getTransform()
              .setPosition(0f, 0f, 3f);

        RenderPipeline renderPipeline = new RenderPipeline();
        renderPipeline.setCamera(camera);

        IScreenClearHandler screenClear = renderingEngine.getScreenClearHandler();
        screenClear.setClearColor(new Color(0.2f, 0.2f, 0.5f));

        Scene scene = new Scene();
        scene.addPipelineAction(new UpdatePipeline());
        scene.addPipelineAction(new ScreenClearPipeline(screenClear));
        scene.addPipelineAction(renderPipeline);
        scene.addGameObject(buildCube(renderingEngine, assimp, timer));

        SceneGameLoop gameLoop = new SceneGameLoop();
        gameLoop.addScene(scene);
        gameLoop.addAction(new TimerAction(timer));
        gameLoop.addAction(new PollEventsPipeline(window));
        addCloseListener(window, gameLoop);

        timer.startTimer();
        gameLoop.loop();
        window.dispose();
    }

    private static void addCloseListener(IWindow window, GameLoop gameLoop)
    {
        window.addWindowListener(new IWindowAdapter()
        {
            @Override
            public void onWindowRequestClose(IWindow window)
            {
                gameLoop.stop();
            }
        });
    }

    private static String loadText(File file) throws IOException
    {
        return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())), StandardCharsets.UTF_8);
    }

    private static GameObject buildCube(IRenderingEngine renderingEngine, IAssimp assimp, Timer timer)
            throws IOException
    {
        File cubeFile = new File("src/test/res/cube.obj");
        File diffuseVertFile = new File("src/test/res/diffuse.vert");
        File diffuseFragFile = new File("src/test/res/diffuse.frag");
        File grassFile = new File("src/test/res/grass.png");

        IMesh mesh = renderingEngine.createMesh();
        mesh.update((VertexData) new ModelLoader(assimp).loadScene(cubeFile)
                                                        .get(0)
                                                        .getData());

        IShader shader = renderingEngine.createShader();
        shader.compile(new RawShaderCode(loadText(diffuseVertFile), loadText(diffuseFragFile)));

        ITexture texture = renderingEngine.createTexture();
        TextureData textureData = TextureLoader.loadTexture(grassFile);
        textureData.setSampleMode(SampleMode.NEAREST);
        texture.update(textureData);

        Material material = new Material(shader);
        material.setTextures(new ITexture[] {texture}, new String[] {"diffuse"});

        RenderBehavior renderBehavior = new RenderBehavior();
        renderBehavior.setMesh(mesh);
        renderBehavior.setMaterial(material);

        GameObject gameObject = new GameObject();
        gameObject.setName("Cube");
        gameObject.addBehavior(renderBehavior);
        gameObject.addBehavior(new SpinCube(timer));

        return gameObject;
    }

    private static class SpinCube extends AbstractBehavior implements IUpdateable
    {
        private final Timer timer;

        SpinCube(Timer timer)
        {
            this.timer = timer;
        }

        @Override
        public void update()
        {
            Quaternionf rot = getGameObject().getTransform()
                                             .getRotation();

            float t = (float) timer.getElapsedTime();
            rot.identity();
            rot.rotateLocalX(t * 2.2369f);
            rot.rotateLocalY(t * 1.4562f);
            rot.rotateLocalZ(t * 0.2123f);
        }
    }
}
