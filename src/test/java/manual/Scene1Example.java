package manual;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.joml.Quaternionf;
import net.whg.we.external.AssimpAPI;
import net.whg.we.external.GlfwApi;
import net.whg.we.external.OpenGLApi;
import net.whg.we.main.GameLoop;
import net.whg.we.main.GameObject;
import net.whg.we.main.Input;
import net.whg.we.main.Scene;
import net.whg.we.main.UserControlsUpdater;
import net.whg.we.rendering.RenderBehavior;
import net.whg.we.rendering.Camera;
import net.whg.we.rendering.Color;
import net.whg.we.rendering.CullingMode;
import net.whg.we.rendering.IMesh;
import net.whg.we.rendering.IRenderingEngine;
import net.whg.we.rendering.IScreenClearHandler;
import net.whg.we.rendering.IShader;
import net.whg.we.rendering.ITexture;
import net.whg.we.rendering.Material;
import net.whg.we.rendering.RawShaderCode;
import net.whg.we.rendering.TextureData;
import net.whg.we.rendering.VertexData;
import net.whg.we.rendering.TextureData.SampleMode;
import net.whg.we.rendering.opengl.IOpenGL;
import net.whg.we.rendering.opengl.OpenGLRenderingEngine;
import net.whg.we.resource.ModelLoader;
import net.whg.we.resource.Resource;
import net.whg.we.resource.TextureLoader;
import net.whg.we.resource.assimp.IAssimp;
import net.whg.we.window.IWindow;
import net.whg.we.window.IWindowAdapter;
import net.whg.we.window.WindowSettings;
import net.whg.we.window.glfw.GlfwWindow;
import net.whg.we.window.glfw.IGlfw;

public class Scene1Example
{
    public static void main(String[] args) throws IOException
    {
        IGlfw glfw = new GlfwApi();
        IOpenGL opengl = new OpenGLApi(true);
        IAssimp assimp = new AssimpAPI();

        IRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        WindowSettings windowSettings = new WindowSettings();
        IWindow window = new GlfwWindow(glfw, renderingEngine, windowSettings);

        UserControlsUpdater.bind(window);

        IScreenClearHandler screenClear = renderingEngine.getScreenClearHandler();
        screenClear.setClearColor(new Color(0.15f, 0.15f, 0.15f));

        ModelLoader modelLoader = new ModelLoader(assimp);

        List<Resource> resources = modelLoader.loadScene(new File("src/test/res/cube.obj"));
        VertexData cubeData = (VertexData) resources.get(0)
                                                    .getData();
        String vertShader =
                new String(Files.readAllBytes(Paths.get("src/test/res/normal_shader.vert")), StandardCharsets.UTF_8);
        String fragShader =
                new String(Files.readAllBytes(Paths.get("src/test/res/normal_shader.frag")), StandardCharsets.UTF_8);

        Camera camera = new Camera();
        camera.getTransform()
              .setPosition(0, 0, 5);

        IMesh mesh = renderingEngine.createMesh();
        mesh.update(cubeData);

        IShader shader = renderingEngine.createShader();
        shader.compile(new RawShaderCode(vertShader, fragShader));

        TextureData textureData = TextureLoader.loadTexture(new File("src/test/res/grass.png"));
        textureData.setSampleMode(SampleMode.NEAREST);
        textureData.setMipmap(true);
        ITexture texture = renderingEngine.createTexture();
        texture.update(textureData);

        renderingEngine.setCullingMode(CullingMode.NONE);

        Material material = new Material(shader);
        material.setTextures(new ITexture[] {texture}, new String[] {"diffuse"});

        GameObject cube = new GameObject();
        RenderBehavior renderer = new RenderBehavior();
        renderer.setMesh(mesh);
        renderer.setMaterial(material);
        cube.addBehavior(renderer);

        Scene scene = new Scene();
        scene.addGameObject(cube);

        cube.getTransform()
            .setRotation(new Quaternionf(0.5f, 0.5f, 0f, 1f));

        GameLoop gameLoop = new GameLoop();

        gameLoop.addAction(() ->
        {
            if (Input.isMouseButtonDown(0))
            {
                final float s = 0.01f;
                float dx = Input.getMouseDeltaX() * s;
                float dy = Input.getMouseDeltaY() * s;

                cube.getTransform()
                    .getRotation()
                    .rotateX(dy)
                    .rotateY(dx);
            }

            if (Input.getScrollWheelDelta() != 0)
            {
                cube.getTransform()
                    .setSize(cube.getTransform()
                                 .getSize().x
                            * (float) Math.pow(1.1f, -Input.getScrollWheelDelta()));
            }
        });
        gameLoop.addAction(() -> screenClear.clearScreen());
        // gameLoop.addAction(() -> scene.getRenderer()
        // .render(camera));
        gameLoop.addAction(() -> Input.endFrame());
        gameLoop.addAction(() -> window.pollEvents());

        window.addWindowListener(new IWindowAdapter()
        {
            @Override
            public void onWindowRequestClose(IWindow window)
            {
                gameLoop.stop();
            }
        });

        gameLoop.loop();

        mesh.dispose();
        shader.dispose();
        texture.dispose();

        window.dispose();
    }
}
