package manual;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.joml.Quaternionf;
import org.lwjgl.glfw.GLFW;
import net.whg.we.main.GameLoop;
import net.whg.we.main.GameObject;
import net.whg.we.main.RenderBehavior;
import net.whg.we.main.Scene;
import net.whg.we.main.UserControlsUpdater;
import net.whg.we.rendering.Camera;
import net.whg.we.rendering.IMesh;
import net.whg.we.rendering.IScreenClearHandler;
import net.whg.we.rendering.IShader;
import net.whg.we.rendering.Material;
import net.whg.we.rendering.VertexData;
import net.whg.we.resource.ModelLoader;
import net.whg.we.resource.Resource;
import net.whg.we.resource.assimp.AssimpAPI;
import net.whg.we.resource.assimp.IAssimp;
import net.whg.we.window.IWindow;
import net.whg.we.window.IWindowAdapter;
import net.whg.we.window.WindowSettings;
import net.whg.we.window.glfw.GlfwWindow;

public class Scene1Example
{
    private static boolean running = true;

    public static void main(String[] args) throws IOException
    {
        WindowSettings windowSettings = new WindowSettings();
        IWindow window = new GlfwWindow(windowSettings);

        window.addWindowListener(new IWindowAdapter()
        {
            @Override
            public void onKeyReleased(IWindow window, int keyCode)
            {
                if (keyCode == GLFW.GLFW_KEY_ESCAPE)
                    running = false;
            }

            @Override
            public void onWindowRequestClose(IWindow window)
            {
                running = false;
            }
        });

        UserControlsUpdater.bind(window);

        IScreenClearHandler screenClear = window.getRenderingEngine()
                                                .getScreenClearHandler();

        IAssimp assimp = new AssimpAPI();
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

        IMesh mesh = window.getRenderingEngine()
                           .createMesh();
        mesh.update(cubeData);

        IShader shader = window.getRenderingEngine()
                               .createShader();
        shader.compile(vertShader, null, fragShader);
        Material material = new Material(shader);

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
        gameLoop.addAction(() -> screenClear.clearScreen());
        gameLoop.addAction(() -> scene.getRenderer()
                                      .render(camera));
        gameLoop.addAction(() -> window.pollEvents());

        gameLoop.addAction(() ->
        {
            if (!running)
                gameLoop.stop();
        });

        gameLoop.loop();

        mesh.dispose();
        shader.dispose();

        window.dispose();
    }
}
