package net.whg.we.util;

/**
 * The color object is used to store an RGBA color in a float-based data
 * storage. All values are stored on a scale of 0 to 1.
 */
public class Color
{
    private float red;
    private float green;
    private float blue;
    private float alpha;

    /**
     * Creates a new color object, with the alpha set to 1.
     * 
     * @param red
     *     - The red component.
     * @param green
     *     - The green component.
     * @param blue
     *     - The blue component.
     * @throws IllegalArgumentException
     *     If any of the components are out of the range 0-1, inclusive.
     */
    public Color(float red, float green, float blue)
    {
        this(red, green, blue, 1f);
    }

    /**
     * Creates a new color object.
     * 
     * @param red
     *     - The red component.
     * @param green
     *     - The green component.
     * @param blue
     *     - The blue component.
     * @param alpha
     *     - The alpha component.
     * @throws IllegalArgumentException
     *     If any of the components are out of the range 0-1, inclusive.
     */
    public Color(float red, float green, float blue, float alpha)
    {
        if (red < 0 || red > 1 || green < 0 || green > 1 || blue < 0 || blue > 1 || alpha < 0 || alpha > 1)
            throw new IllegalArgumentException("Components must be between 0 and 1!");

        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    /**
     * Gets the red component of this color.
     * 
     * @return A value between 0 and 1, representing the red channel.
     */
    public float getRed()
    {
        return red;
    }

    /**
     * Gets the green component of this color.
     * 
     * @return A value between 0 and 1, representing the green channel.
     */
    public float getGreen()
    {
        return green;
    }

    /**
     * Gets the blue component of this color.
     * 
     * @return A value between 0 and 1, representing the blue channel.
     */
    public float getBlue()
    {
        return blue;
    }

    /**
     * Gets the alpha component of this color.
     * 
     * @return A value between 0 and 1, representing the alpha channel.
     */
    public float getAlpha()
    {
        return alpha;
    }
}
