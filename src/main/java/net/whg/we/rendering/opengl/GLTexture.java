package net.whg.we.rendering.opengl;

import java.nio.ByteBuffer;
import net.whg.we.rendering.ITexture;
import net.whg.we.rendering.TextureData;

/**
 * This class is the OpenGL implementation of the texture object.
 */
public class GLTexture implements ITexture
{
    private static final String TEXTURE_DISPOSED = "Texture has already been disposed!";

    private final IOpenGL opengl;
    private final BindStates bindStates;
    private boolean disposed;
    private boolean created;
    private int textureId;

    /**
     * Creates a new OpenGL texture object.
     * 
     * @param opengl
     *     - The OpenGL instance.
     * @param bindStates
     *     - The active OpenGL bind states.
     */
    GLTexture(IOpenGL opengl, BindStates bindStates)
    {
        this.opengl = opengl;
        this.bindStates = bindStates;
    }

    @Override
    public void bind(int slot)
    {
        if (isDisposed())
            throw new IllegalStateException(TEXTURE_DISPOSED);

        if (!created)
            throw new IllegalStateException("Texture not yet created!");

        bindStates.bindTexture(textureId, slot);
    }

    /**
     * Extracts pixel colors from the texture data object and formats them into an
     * RGBA8 byte buffer.
     * 
     * @param textureData
     *     - The texture data containing the color data.
     * @return A formatted byte buffer containing the pixel colors.
     */
    private ByteBuffer getPixelData(TextureData textureData)
    {
        int pixelCount = textureData.getWidth() * textureData.getHeight();
        ByteBuffer pixels = ByteBuffer.allocate(pixelCount * 4);

        byte[] pixelColor = new byte[4];
        for (int y = 0; y < textureData.getHeight(); y++)
        {
            for (int x = 0; x < textureData.getWidth(); x++)
            {
                int color = textureData.getPixel(x, y);
                pixelColor[0] = (byte) ((color >> 16) & 0xFF);
                pixelColor[1] = (byte) ((color >> 8) & 0xFF);
                pixelColor[2] = (byte) (color & 0xFF);
                pixelColor[3] = (byte) ((color >> 24) & 0xFF);
                pixels.put(pixelColor);
            }
        }

        pixels.flip();
        return pixels;
    }

    /**
     * Extracts and binds the wrapping mode expected by the given texture data.
     * 
     * @param textureData
     *     - The texture data.
     */
    private void updateWrapping(TextureData textureData)
    {
        switch (textureData.getWrapMode())
        {
            case CLAMP:
                opengl.setTexture2DClampWrapMode();
                break;

            case REPEAT:
                opengl.setTexture2DRepeatWrapMode();
                break;

            default:
                throw new GLException("Unsupported wrapping mode! Mode: " + textureData.getWrapMode());
        }
    }

    /**
     * Extracts and binds the sampling mode expected by the given texture data.
     * 
     * @param textureData
     *     - The texture data.
     */
    private void updateSampling(TextureData textureData)
    {
        switch (textureData.getSampleMode())
        {
            case NEAREST:
                opengl.setTexture2DNearestInterpolation();
                break;

            case BILINEAR:
                opengl.setTexture2DBilinearInterpolation();
                break;

            case TRILINEAR:
                opengl.setTexture2DTrilinearpolation();
                break;

            default:
                throw new GLException("Unsupported sampling mode! Mode: " + textureData.getSampleMode());
        }
    }

    @Override
    public void update(TextureData textureData)
    {
        if (isDisposed())
            throw new IllegalStateException(TEXTURE_DISPOSED);

        ByteBuffer pixels = getPixelData(textureData);

        if (!created)
            textureId = opengl.generateTexture();

        bindStates.bindTexture(textureId, 0);

        updateWrapping(textureData);
        opengl.uploadTexture2DDataRGBA8(pixels, textureData.getWidth(), textureData.getHeight());

        if (textureData.hasMipmap())
            opengl.generateTexture2DMipmaps();

        updateSampling(textureData);
    }

    @Override
    public void dispose()
    {
        if (isDisposed())
            return;

        disposed = true;
        unbind();
        opengl.deleteTexture(textureId);
    }

    /**
     * Unbinds this texture from all texture slots. Returns bounds texture slot back
     * after unbinding.
     */
    private void unbind()
    {
        int activeTextureSlot = bindStates.getBoundTextureSlot();

        for (int i = 0; i < 24; i++)
            if (bindStates.getBoundTexture(i) == textureId)
                bindStates.bindTexture(0, i);

        bindStates.bindTextureSlot(activeTextureSlot);
    }

    @Override
    public boolean isDisposed()
    {
        return disposed;
    }

    @Override
    public boolean isCreated()
    {
        return created;
    }
}
