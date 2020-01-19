package net.whg.we.rendering;

import net.whg.we.util.Color;

/**
 * A screen clear handler is a rendering-specific operation which allows for the
 * screen to be cleared using a specific set of parameters and configurations.
 */
public interface IScreenClearHandler
{
    /**
     * Clears the screen for rendering. This is usually called at the begining of
     * the frame. This function uses the given configuration for the color and depth
     * clearing options.
     */
    void clearScreen();

    /**
     * Sets what color should be used to clear the screen with.
     * <p>
     * Default value is the color black.
     * 
     * @param color
     *     - The color to clear the screen with, or null to disable clearing the
     *     color channel of the screen.
     */
    void setClearColor(Color color);

    /**
     * Sets whether or not depth should be cleared.
     * <p>
     * Default value is true.
     * 
     * @param clearDepth
     *     - Whether or not depth should be cleared.
     */
    void setClearDepth(boolean clearDepth);

    /**
     * Gets the current color used for clearing the screen.
     * 
     * @return The screen clear color, or null if clearing the color channel is
     *     disabled.
     */
    Color getClearColor();

    /**
     * Gets whether or not clearing the depth channel is enabled or not.
     * 
     * @return True if the screen currently clears depth, false otherwise.
     */
    boolean getClearDepth();
}
