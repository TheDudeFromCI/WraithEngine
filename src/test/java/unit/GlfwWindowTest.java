package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Test;
import net.whg.we.rendering.IRenderingEngine;
import net.whg.we.window.IWindowListener;
import net.whg.we.window.WindowSettings;
import net.whg.we.window.glfw.GlfwWindow;
import net.whg.we.window.glfw.IGlfw;

public class GlfwWindowTest
{
    private void disposeWindow()
    {
        GlfwWindow window = GlfwWindow.getActiveWindow();

        if (window != null)
            window.dispose();
    }

    @Test
    public void createWindow()
    {
        disposeWindow();

        IGlfw glfw = mock(IGlfw.class);
        when(glfw.getScreenSize()).thenReturn(new int[] {1920, 1080});
        when(glfw.createWindow(anyInt(), anyInt(), anyString(), anyBoolean())).thenReturn(27L);

        IRenderingEngine renderingEngine = mock(IRenderingEngine.class);
        WindowSettings settings = new WindowSettings();
        GlfwWindow window = new GlfwWindow(glfw, renderingEngine, settings);

        verify(glfw).init();
        verify(glfw).createWindow(800, 600, "Untitled", false);
        verify(glfw).showWindow(anyLong());
        verify(glfw).setWindowPosition(27, 560, 240);

        verify(glfw).addMousePosListener(any(), any());
        verify(glfw).addMouseButtonListener(any(), any());
        verify(glfw).addMouseWheelListener(any(), any());
        verify(glfw).addKeyListener(any(), any());
        verify(glfw).addWindowResizeListener(any(), any());

        verify(renderingEngine).init();

        assertEquals(27, window.getWindowId());
    }

    @Test
    public void updateWindow_smallTweaks()
    {
        disposeWindow();

        IGlfw glfw = mock(IGlfw.class);
        when(glfw.getScreenSize()).thenReturn(new int[] {1024, 768});
        when(glfw.createWindow(anyInt(), anyInt(), anyString(), anyBoolean())).thenReturn(14L);

        IRenderingEngine renderingEngine = mock(IRenderingEngine.class);
        WindowSettings settings = new WindowSettings();
        GlfwWindow window = new GlfwWindow(glfw, renderingEngine, settings);

        IWindowListener windowListener = mock(IWindowListener.class);
        window.addWindowListener(windowListener);

        WindowSettings settings2 = new WindowSettings();
        settings2.setTitle("Blank Screen Simulator (4 fps)");
        settings2.setResizable(true);
        settings2.setWidth(320);
        settings2.setHeight(240);
        settings2.setVsync(true);

        window.setProperties(settings2);

        verify(glfw, times(1)).init();
        verify(glfw, times(1)).showWindow(14);
        verify(glfw, times(1)).setWindowSize(anyLong(), anyInt(), anyInt());
        verify(glfw, never()).destroyWindow(14);

        verify(glfw, times(1)).setWindowResizable(14, true);
        verify(glfw, times(1)).setWindowPosition(anyLong(), anyInt(), anyInt());
        verify(glfw, times(1)).setVSync(1);

        verify(windowListener).onWindowUpdated(window);
    }

    @Test
    public void updateWindow_fullRebuild_fullscreen()
    {
        disposeWindow();

        IGlfw glfw = mock(IGlfw.class);
        when(glfw.getScreenSize()).thenReturn(new int[] {400, 300});
        when(glfw.createWindow(anyInt(), anyInt(), anyString(), anyBoolean())).thenReturn(100L);

        IRenderingEngine renderingEngine = mock(IRenderingEngine.class);
        WindowSettings settings = new WindowSettings();
        GlfwWindow window = new GlfwWindow(glfw, renderingEngine, settings);

        IWindowListener windowListener = mock(IWindowListener.class);
        window.addWindowListener(windowListener);

        WindowSettings settings2 = new WindowSettings();
        settings2.setFullscreen(true);

        window.setProperties(settings2);

        verify(glfw, times(1)).init();
        verify(glfw, times(2)).showWindow(anyLong());
        verify(glfw, times(1)).destroyWindow(anyLong());

        verify(windowListener).onWindowUpdated(window);
    }

    @Test
    public void updateWindow_fullRebuild_sampleCount()
    {
        disposeWindow();

        IGlfw glfw = mock(IGlfw.class);
        when(glfw.getScreenSize()).thenReturn(new int[] {400, 300});
        when(glfw.createWindow(anyInt(), anyInt(), anyString(), anyBoolean())).thenReturn(100L);

        IRenderingEngine renderingEngine = mock(IRenderingEngine.class);
        WindowSettings settings = new WindowSettings();
        GlfwWindow window = new GlfwWindow(glfw, renderingEngine, settings);

        WindowSettings settings2 = new WindowSettings();
        settings2.setSamples(13);

        window.setProperties(settings2);

        verify(glfw, times(1)).init();
        verify(glfw, times(2)).showWindow(anyLong());
        verify(glfw, times(1)).destroyWindow(anyLong());
    }

    @Test
    public void createWindow_dontCaptureSettings()
    {
        disposeWindow();

        IGlfw glfw = mock(IGlfw.class);
        when(glfw.getScreenSize()).thenReturn(new int[] {400, 300});
        when(glfw.createWindow(anyInt(), anyInt(), anyString(), anyBoolean())).thenReturn(100L);

        IRenderingEngine renderingEngine = mock(IRenderingEngine.class);
        WindowSettings settings = new WindowSettings();
        GlfwWindow window = new GlfwWindow(glfw, renderingEngine, settings);

        settings.setFullscreen(true);

        assertFalse(window.getProperties()
                          .isFullscreen());
    }

    @Test
    public void disposeWindow_isDestroyed()
    {
        disposeWindow();

        IGlfw glfw = mock(IGlfw.class);
        when(glfw.getScreenSize()).thenReturn(new int[] {400, 300});
        when(glfw.createWindow(anyInt(), anyInt(), anyString(), anyBoolean())).thenReturn(68L);

        IRenderingEngine renderingEngine = mock(IRenderingEngine.class);
        WindowSettings settings = new WindowSettings();
        GlfwWindow window = new GlfwWindow(glfw, renderingEngine, settings);

        window.dispose();

        verify(glfw).destroyWindow(anyLong());
        verify(glfw).terminate();
        verify(renderingEngine).dispose();

        assertTrue(window.isDisposed());
        assertNull(GlfwWindow.getActiveWindow());
    }

    @Test
    public void pollEvents()
    {
        disposeWindow();

        IGlfw glfw = mock(IGlfw.class);
        when(glfw.getScreenSize()).thenReturn(new int[] {16, 9});
        when(glfw.createWindow(anyInt(), anyInt(), anyString(), anyBoolean())).thenReturn(68L);

        IRenderingEngine renderingEngine = mock(IRenderingEngine.class);
        WindowSettings settings = new WindowSettings();
        GlfwWindow window = new GlfwWindow(glfw, renderingEngine, settings);

        window.pollEvents();

        verify(glfw).pollEvents();
        verify(glfw).swapBuffers(anyLong());
        verify(glfw).addMousePosListener(any(), any());
    }

    @Test
    public void pollEvents_windowRequestClose()
    {
        disposeWindow();

        IGlfw glfw = mock(IGlfw.class);
        when(glfw.getScreenSize()).thenReturn(new int[] {16, 9});
        when(glfw.createWindow(anyInt(), anyInt(), anyString(), anyBoolean())).thenReturn(68L);
        when(glfw.shouldWindowClose(anyLong())).thenReturn(true);

        IWindowListener windowListener = mock(IWindowListener.class);

        IRenderingEngine renderingEngine = mock(IRenderingEngine.class);
        WindowSettings settings = new WindowSettings();
        GlfwWindow window = new GlfwWindow(glfw, renderingEngine, settings);

        window.addWindowListener(windowListener);
        window.pollEvents();

        verify(windowListener).onWindowRequestClose(window);
    }
}
