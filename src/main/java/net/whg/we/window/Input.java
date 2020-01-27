package net.whg.we.window;

/**
 * This input class is a reference for the current key and mouse states for the
 * focused window. This can be used to check for mouse inputs and key presses as
 * they occur.
 */
public class Input
{
    /**
     * The largest key code input provided by GLFW. Used for making sure enough
     * memory is allocated to store all key states.
     */
    private static final int MAX_INPUT_KEY_CODE = 348;

    /**
     * The largest mouse button input provided by GLFW. Used for making sure enough
     * memory is allocated to store all mouse button states.
     */
    private static final int MAX_INPUT_MOUSE_BUTTON = 7;

    /**
     * The input listener is a helper class which can be used to bind to a window to
     * listen for events it triggers.
     */
    private class InputListener extends IWindowAdapter
    {
        @Override
        public void onMouseMove(IWindow window, float newX, float newY)
        {
            Input.this.mouseX = newX;
            Input.this.mouseY = newY;
        }

        @Override
        public void onKeyPressed(IWindow window, int keyCode)
        {
            Input.this.keyStates[keyCode] = true;
        }

        @Override
        public void onKeyReleased(IWindow window, int keyCode)
        {
            Input.this.keyStates[keyCode] = false;
        }

        @Override
        public void onMouseWheel(IWindow window, float scrollX, float scrollY)
        {
            Input.this.scrollWheelDelta = scrollY;
        }

        @Override
        public void onMousePressed(IWindow window, int mouseButton)
        {
            Input.this.mouseButtons[mouseButton] = true;
        }

        @Override
        public void onMouseReleased(IWindow window, int mouseButton)
        {
            Input.this.mouseButtons[mouseButton] = false;
        }
    }

    private final boolean[] keyStates = new boolean[MAX_INPUT_KEY_CODE + 1];
    private final boolean[] lastKeyStates = new boolean[MAX_INPUT_KEY_CODE + 1];
    private final boolean[] mouseButtons = new boolean[MAX_INPUT_MOUSE_BUTTON + 1];
    private final boolean[] lastMouseButtons = new boolean[MAX_INPUT_MOUSE_BUTTON + 1];
    private float mouseX;
    private float mouseY;
    private float lastMouseX;
    private float lastMouseY;
    private float scrollWheelDelta;

    /**
     * Creates a new input object, and binds a listener to the given window.
     * 
     * @param window
     *     - The window to bind to.
     */
    public Input(IWindow window)
    {
        window.addWindowListener(new InputListener());
    }

    /**
     * This should be called at the end of the frame. When called, key and mouse
     * states are copied from the current frame buffer to the previous frame buffer,
     * to allow for delta functions to work as intended.
     */
    public void endFrame()
    {
        System.arraycopy(keyStates, 0, lastKeyStates, 0, keyStates.length);
        System.arraycopy(mouseButtons, 0, lastMouseButtons, 0, mouseButtons.length);

        lastMouseX = mouseX;
        lastMouseY = mouseY;
        scrollWheelDelta = 0f;
    }

    /**
     * Checks if the key is currently held down.
     * 
     * @param key
     *     - The key to check for.
     * @return True if the key is being pressed, false otherwise.
     */
    public boolean isKeyDown(final int key)
    {
        return keyStates[key];
    }

    /**
     * Checks if the key was just pressed down this frame.
     * 
     * @param key
     *     - The key to check for.
     * @return True if the key is being pressed and was not pressed on the previous
     *     frame. False otherwise.
     */
    public boolean isKeyJustDown(final int key)
    {
        return keyStates[key] && !lastKeyStates[key];
    }

    /**
     * Checks if the key was just released this frame.
     * 
     * @param key
     *     - The key to check for.
     * @return True if the key was being pressed last frame and is no longer being
     *     pressed. False otherwise.
     */
    public boolean isKeyJustUp(final int key)
    {
        return !keyStates[key] && lastKeyStates[key];
    }

    /**
     * Gets the current x position of the mouse.
     * 
     * @return The mouse x pos.
     */
    public float getMouseX()
    {
        return mouseX;
    }

    /**
     * Gets the current y position of the mouse.
     * 
     * @return The mouse y pos.
     */
    public float getMouseY()
    {
        return mouseY;
    }

    /**
     * Gets the x delta position of the mouse.
     * 
     * @return The mouse delta x.
     */
    public float getMouseDeltaX()
    {
        return mouseX - lastMouseX;
    }

    /**
     * Gets the y delta position of the mouse.
     * 
     * @return The mouse delta y.
     */
    public float getMouseDeltaY()
    {
        return mouseY - lastMouseY;
    }

    /**
     * Checks if the mouse button is currently held down.
     * 
     * @param button
     *     - The mouse button to check for.
     * @return True if the mouse button is being pressed, false otherwise.
     */
    public boolean isMouseButtonDown(final int button)
    {
        return mouseButtons[button];
    }

    /**
     * Checks if the mouse button was just pressed down this frame.
     * 
     * @param button
     *     - The button to check for.
     * @return True if the mouse button is being pressed and was not pressed on the
     *     previous frame. False otherwise.
     */
    public boolean isMouseButtonJustDown(final int button)
    {
        return mouseButtons[button] && !lastMouseButtons[button];
    }

    /**
     * Checks if the mouse button was just released this frame.
     * 
     * @param button
     *     - The button to check for.
     * @return True if the mouse button was being pressed last frame and is no
     *     longer being pressed. False otherwise.
     */
    public boolean isMouseButtonJustUp(final int button)
    {
        return !mouseButtons[button] && lastMouseButtons[button];
    }

    /**
     * Gets the amount the scroll wheel was rotated this frame.
     * 
     * @return The scroll wheel delta.
     */
    public float getScrollWheelDelta()
    {
        return scrollWheelDelta;
    }
}
