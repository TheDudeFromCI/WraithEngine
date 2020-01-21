package net.whg.we.rendering;

import net.whg.we.util.Color;

/**
 * A texture data object is an uncompiled collection of data which is used to
 * make up a texture object. It contains information about how the texture
 * should be sampled as well as what color information the texture is comprised
 * of.
 * <p>
 * Internally, texture data objects store color data as an int array, allowing
 * for compact storage, and quick access. As such, it is much quicker to access
 * and modify data using this format. Pixels are stored in the format ARGB.
 */
public class TextureData
{
    /**
     * The sample mode of a texture is used to describe how the pixels within a
     * texture should be sampled.
     */
    public enum SampleMode
    {
        /**
         * In nearest sampling mode, the closest pixel to the given coordinates are
         * returned with no interpolation. This is the fastest operation, but the lowest
         * quality.
         */
        NEAREST,

        /**
         * In bilinear sampling mode, the closest 4 pixels to the given coordinates are
         * returned and blended together linearly. This is the default sampling value.
         */
        BILINEAR,

        /**
         * In trilinear sampling mode, the closest 4 pixels to the given coordinates,
         * for the current mipmap level, the level above, and the level below, are all
         * blended together linearly. This is the slowest operation, but the highest
         * quality.
         */
        TRILINEAR,
    }

    /**
     * If a texture represents a normal map, the normal mapping mode can be used to
     * determine how the normal information is intended to be read.
     */
    public enum NormalMapMode
    {
        /**
         * If this texture does not represent a normal map. This is the default value.
         */
        NON_NORMALMAP,

        /**
         * If this texture uses OpenGL-style normal mapping. This is the standard normal
         * mapping mode used for WraithEngine.
         */
        OPENGL_STYLE,

        /**
         * If this texture uses DirectX-style normal mapping. In this mode, the green
         * channel is inverted as it is being uploaded to the GPU to convert it to an
         * OpenGL style mapping. This conversion only exists during the upload process,
         * and does not effect the this texture data object in any way.
         */
        DIRECTX_STYLE,
    }

    /**
     * The wrapping mode is used to indicate how pixels should be sampled when out
     * of bounds.
     */
    public enum WrapMode
    {
        /**
         * In clamp wrapping mode, out of bounds pixels sample the pixel color along the
         * edge of the texture, closest to the requested pixel coordinate.
         */
        CLAMP,

        /**
         * In repeat wrapping mode, out of bound pixels are wrapped around the other
         * side of the texture, allowing for seamless textures to be cleanly repeated
         * infinitely. This is the default wrapping mode.
         */
        REPEAT,
    }

    private final int[] rgb;
    private final int width;
    private final int height;
    private boolean mipmap;
    private boolean sRGB = true;
    private SampleMode sampleMode = SampleMode.BILINEAR;
    private NormalMapMode normalMapMode = NormalMapMode.NON_NORMALMAP;
    private WrapMode wrapMode = WrapMode.REPEAT;
    private int anisotropicFiltering = 1;

    /**
     * Creates a new texture data object with the given size.
     * 
     * @param width
     *     - The width of this texture.
     * @param height
     *     - The height of this texture.
     */
    public TextureData(int width, int height)
    {
        if (width < 1 || height < 1)
            throw new IllegalArgumentException("Height and width must be positive integers!");

        this.width = width;
        this.height = height;
        rgb = new int[width * height];
    }

    /**
     * Gets the width of this texture.
     * 
     * @return The width in pixels.
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Gets the height of this texture.
     * 
     * @return The height in pixels.
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * Checks whether or not the given coordinates are within the bounds of this
     * texture. If they are, nothing happens.
     * 
     * @param x
     *     - The x coordinate.
     * @param y
     *     - The y coordinate.
     * @throws IllegalArgumentException
     *     If the given coordinate is outside of the texture bounds.
     */
    private void validateCoords(int x, int y)
    {
        if (x < 0 || x >= width || y < 0 || y >= height)
            throw new IllegalArgumentException("Pixel coords out of bounds!");
    }

    /**
     * Converts a set of coordinates to an index within the color data array.
     * 
     * @param x
     *     - The x coordinate.
     * @param y
     *     - The y coordinate.
     * @return The index for the given coords.
     */
    private int coordsToIndex(int x, int y)
    {
        return y * width + x;
    }

    /**
     * Gets the pixel at the given coordinates in the form of an ARGB int.
     * 
     * @param x
     *     - The x coordinate.
     * @param y
     *     - The y coordinate.
     * @return The pixel color in ARGB format.
     * @throws IllegalArgumentException
     *     if the given coordinates are outside of the texture bounds.
     */
    public int getPixel(int x, int y)
    {
        validateCoords(x, y);
        return rgb[coordsToIndex(x, y)];
    }

    /**
     * Sets the pixel at the given coordinates in the form of an ARGB int.
     * 
     * @param x
     *     - The x coordinate.
     * @param y
     *     - The y coordinate.
     * @param color
     *     - The new pixel color in ARGB format.
     * @throws IllegalArgumentException
     *     if the given coordinates are outside of the texture bounds.
     */
    public void setPixel(int x, int y, int color)
    {
        validateCoords(x, y);
        rgb[coordsToIndex(x, y)] = color;
    }

    /**
     * Gets the pixel at the given coordinates as a color object.
     * <p>
     * Note: This method creates a new color object with each call, and should be
     * used sparingly. It can be very slow for large accesses.
     * 
     * @param x
     *     - The x coordinate.
     * @param y
     *     - The y coordinate.
     * @return The pixel color.
     * @throws IllegalArgumentException
     *     if the given coordinates are outside of the texture bounds.
     */
    public Color getPixelAsColor(int x, int y)
    {
        int rgba = getPixel(x, y);

        float a = ((rgba >> 24) & 0xFF) / 255f;
        float r = ((rgba >> 16) & 0xFF) / 255f;
        float g = ((rgba >> 8) & 0xFF) / 255f;
        float b = (rgba & 0xFF) / 255f;

        return new Color(r, g, b, a);
    }

    /**
     * Sets the pixel at the given coordinates as a color object.
     * 
     * @param x
     *     - The x coordinate.
     * @param y
     *     - The y coordinate.
     * @param color
     *     - The color to set the pixel to.
     * @throws IllegalArgumentException
     *     if the given coordinates are outside of the texture bounds.
     * @throws IllegalArgumentException
     *     if the color object is null.
     */
    public void setPixelAsColor(int x, int y, Color color)
    {
        if (color == null)
            throw new IllegalArgumentException("Color cannot be null!");

        int r = Math.round(color.getRed() * 255);
        int g = Math.round(color.getGreen() * 255);
        int b = Math.round(color.getBlue() * 255);
        int a = Math.round(color.getAlpha() * 255);

        int rgba = 0;
        rgba |= (a & 0xFF) << 24;
        rgba |= (r & 0xFF) << 16;
        rgba |= (g & 0xFF) << 8;
        rgba |= b & 0xFF;

        setPixel(x, y, rgba);
    }

    /**
     * Checks whether or not this texture uses mipmaps.
     * 
     * @return True if this texture uses mipmaps. False otherwise.
     */
    public boolean hasMipmap()
    {
        return mipmap;
    }

    /**
     * Assigns whether or not this texture should use mipmaps.
     * 
     * @param mipmap
     *     - True if this texture should use mipmaps. False otherwise.
     */
    public void setMipmap(boolean mipmap)
    {
        this.mipmap = mipmap;
    }

    /**
     * Checks whether or not this texture uses the sRGB color format. This is
     * commonly true for textures which represent color data, and false for textures
     * which represent non-color data.
     * 
     * @return Whether or not this texture uses sRGB colors.
     */
    public boolean issRGB()
    {
        return sRGB;
    }

    /**
     * Sets whether or not this texture should use sRGB colors. This is commonly
     * true for textures which represent color data, and false for textures which
     * represent non-color data.
     * 
     * @param sRGB
     *     - Whether or not this texture uses sRGB colors.
     */
    public void setsRGB(boolean sRGB)
    {
        this.sRGB = sRGB;
    }

    /**
     * Gets the pixel sampling mode for this texture.
     * 
     * @return The sampling mode.
     * @see SampleMode
     */
    public SampleMode getSampleMode()
    {
        return sampleMode;
    }

    /**
     * Sets the pixel sampling mode for this texture.
     * 
     * @param sampleMode
     *     - The sampling mode.
     * @throws IllegalArgumentException
     *     If the sample mode is null.
     * @see SampleMode
     */
    public void setSampleMode(SampleMode sampleMode)
    {
        if (sampleMode == null)
            throw new IllegalArgumentException("Sampling mode cannot be null!");

        this.sampleMode = sampleMode;
    }

    /**
     * Gets the normal mapping mode for this texture.
     * 
     * @return The normal mapping mode.
     * @see NormalMapMode
     */
    public NormalMapMode getNormalMapMode()
    {
        return normalMapMode;
    }

    /**
     * Sets the normal mapping mode for this texture.
     * 
     * @param normalMapMode
     *     - The normal mapping mode.
     * @throws IllegalArgumentException
     *     If the normal map mode is null.
     * @see NormalMapMode
     */
    public void setNormalMapMode(NormalMapMode normalMapMode)
    {
        if (normalMapMode == null)
            throw new IllegalArgumentException("Normal mapping mode cannot be null!");

        this.normalMapMode = normalMapMode;
    }

    /**
     * Gets the wrapping mode for this texture.
     * 
     * @return The wrapping mode.
     * @see WrapMode
     */
    public WrapMode getWrapMode()
    {
        return wrapMode;
    }

    /**
     * Sets the wrapping mode for this texture.
     * 
     * @param wrapMode
     *     - The wrapping mode.
     * @throws IllegalArgumentException
     *     If the wrap mode is null.
     * @see WrapMode
     */
    public void setWrapMode(WrapMode wrapMode)
    {
        if (wrapMode == null)
            throw new IllegalArgumentException("Wrap mode cannot be null!");

        this.wrapMode = wrapMode;
    }

    /**
     * Gets the anisotropic filtering level for this texture. The default value is 1
     * which is no filtering.
     * 
     * @return The anisotropic filtering level.
     */
    public int getAnisotropicFiltering()
    {
        return anisotropicFiltering;
    }

    /**
     * Sets the anisotropic filtering level for this texture.
     * 
     * @param anisotropicFiltering
     *     - The anisotropic filtering level.
     * @throws IllegalArgumentException
     *     If the value is less than 1 or greater than 16.
     */
    public void setAnisotropicFiltering(int anisotropicFiltering)
    {
        if (anisotropicFiltering < 1 || anisotropicFiltering > 16)
            throw new IllegalArgumentException(
                    "Anisotropic Filter must be a value between 1 and 16! Recieved: " + anisotropicFiltering);

        this.anisotropicFiltering = anisotropicFiltering;
    }
}
