package net.whg.we.rendering.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.whg.we.rendering.ITexture;
import net.whg.we.rendering.TextureData;
import net.whg.we.rendering.TextureData.SampleMode;

/**
 * This class is the OpenGL implementation of the texture object.
 */
public class GLTexture implements ITexture
{
    private static final String TEXTURE_DISPOSED = "Texture has already been disposed!";
    private static final Logger logger = LoggerFactory.getLogger(GLTexture.class);

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
        ByteBuffer pixels = BufferUtils.createByteBuffer(pixelCount * 4);

        for (int y = 0; y < textureData.getHeight(); y++)
        {
            for (int x = 0; x < textureData.getWidth(); x++)
            {
                int color = textureData.getPixel(x, y);
                pixels.put((byte) ((color >> 16) & 0xFF));
                pixels.put((byte) ((color >> 8) & 0xFF));
                pixels.put((byte) (color & 0xFF));
                pixels.put((byte) ((color >> 24) & 0xFF));
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
        SampleMode sampleMode = textureData.getSampleMode();

        if (sampleMode == SampleMode.TRILINEAR && !textureData.hasMipmap())
        {
            sampleMode = SampleMode.BILINEAR;
            logger.warn("Texture uploaded with Sample mode TRILINEAR, but mipmaps "
                    + "disabled. Setting sampling mode to BILINEAR.");
        }

        switch (sampleMode)
        {
            case NEAREST:
                opengl.setTexture2DNearestInterpolation(textureData.hasMipmap());
                break;

            case BILINEAR:
                opengl.setTexture2DBilinearInterpolation(textureData.hasMipmap());
                break;

            case TRILINEAR:
                opengl.setTexture2DTrilinearpolation();
                break;

            default:
                throw new GLException("Unsupported sampling mode! Mode: " + textureData.getSampleMode());
        }
    }

    @Override
    public void update(TextureData data)
    {
        if (isDisposed())
            throw new IllegalStateException(TEXTURE_DISPOSED);

        if (!created)
            textureId = opengl.generateTexture();

        bindStates.bindTexture(textureId, 0);

        opengl.uploadTexture2DDataRGBA8(getPixelData(data), data.getWidth(), data.getHeight());

        updateWrapping(data);
        updateSampling(data);

        if (data.hasMipmap())
            opengl.generateTexture2DMipmaps();

        created = true;
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
