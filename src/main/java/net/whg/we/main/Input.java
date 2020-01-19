package net.whg.we.main;

import java.util.Arrays;

/**
 * This input class is a reference for the current key and mouse states for the
 * focused window. This can be used to check for mouse inputs and key presses as
 * they occur.
 */
public final class Input
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

    private static boolean[] keyStates = new boolean[MAX_INPUT_KEY_CODE + 1];
    private static boolean[] lastKeyStates = new boolean[MAX_INPUT_KEY_CODE + 1];
    private static boolean[] mouseButtons = new boolean[MAX_INPUT_MOUSE_BUTTON + 1];
    private static boolean[] lastMouseButtons = new boolean[MAX_INPUT_MOUSE_BUTTON + 1];
    private static float mouseX;
    private static float mouseY;
    private static float lastMouseX;
    private static float lastMouseY;
    private static float scrollWheelDelta;

    /**
     * Clear all data stored in this class, resetting it to default settings. Any
     * keys or buttons currently held are assumed unpressed. Mouse position is
     * assumed to be at 0, 0.
     */
    public static void clear()
    {
        Arrays.fill(keyStates, false);
        Arrays.fill(lastKeyStates, false);
        Arrays.fill(mouseButtons, false);
        Arrays.fill(lastMouseButtons, false);

        mouseX = 0f;
        mouseY = 0f;
        lastMouseX = 0f;
        lastMouseY = 0f;
        scrollWheelDelta = 0f;
    }

    /**
     * Assigns a new state to a given key.
     * 
     * @param key
     *     - The key in question.
     * @param pressed
     *     - True if the key was pressed. False if the key was released.
     */
    static void setKeyState(final int key, final boolean pressed)
    {
        keyStates[key] = pressed;
    }

    /**
     * Assigns a new set of coords to the mouse position.
     * 
     * @param mouseX
     *     - The current mouse x.
     * @param mouseY
     *     - The current mouse y.
     */
    static void setMousePos(final float mouseX, final float mouseY)
    {
        Input.mouseX = mouseX;
        Input.mouseY = mouseY;
    }

    /**
     * Assigns a new state to the given mouse button.
     * 
     * @param button
     *     - The mouse button in question.
     * @param pressed
     *     - True if the mouse button was pressed. False if the mouse button was
     *     released.
     */
    static void setMouseButtonState(final int button, final boolean pressed)
    {
        mouseButtons[button] = pressed;
    }

    /**
     * Assigns the amount the mouse wheel was scrolled this frame.
     * 
     * @param delta
     *     - The scroll delta.
     */
    static void setScrollWheelDelta(final float delta)
    {
        scrollWheelDelta = delta;
    }

    /**
     * This should be called at the end of the frame. When called, key and mouse
     * states are copied from the current frame buffer to the previous frame buffer,
     * to allow for delta functions to work as intended.
     */
    public static void endFrame()
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
    public static boolean isKeyDown(final int key)
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
    public static boolean isKeyJustDown(final int key)
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
    public static boolean isKeyJustUp(final int key)
    {
        return !keyStates[key] && lastKeyStates[key];
    }

    /**
     * Gets the current x position of the mouse.
     * 
     * @return The mouse x pos.
     */
    public static float getMouseX()
    {
        return mouseX;
    }

    /**
     * Gets the current y position of the mouse.
     * 
     * @return The mouse y pos.
     */
    public static float getMouseY()
    {
        return mouseY;
    }

    /**
     * Gets the x delta position of the mouse.
     * 
     * @return The mouse delta x.
     */
    public static float getMouseDeltaX()
    {
        return mouseX - lastMouseX;
    }

    /**
     * Gets the y delta position of the mouse.
     * 
     * @return The mouse delta y.
     */
    public static float getMouseDeltaY()
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
    public static boolean isMouseButtonDown(final int button)
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
    public static boolean isMouseButtonJustDown(final int button)
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
    public static boolean isMouseButtonJustUp(final int button)
    {
        return !mouseButtons[button] && lastMouseButtons[button];
    }

    /**
     * Gets the amount the scroll wheel was rotated this frame.
     * 
     * @return The scroll wheel delta.
     */
    public static float getScrollWheelDelta()
    {
        return scrollWheelDelta;
    }

    private Input()
    {}
}
