package manual;

import org.lwjgl.glfw.GLFW;
import net.whg.we.window.IWindow;
import net.whg.we.window.IWindowAdapter;
import net.whg.we.window.WindowSettings;
import net.whg.we.window.glfw.GlfwWindow;

public class GLFWWindowTest
{
    private static boolean running = true;

    public static void main(String[] args)
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

        while (running)
            window.pollEvents();

        window.dispose();
    }
}
