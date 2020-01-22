package net.whg.we.rendering;

/**
 * A texture represents any image file which exists on the GPU and is used
 * within materials in order to render them.
 */
public interface ITexture extends IRenderResource
{
    /**
     * Binds this texture to the given texture slot.
     * 
     * @param slot
     *     - The texture slot to bind this texture to.
     * @throws InvalidArgumentException
     *     If the slot is less than 0 or more than 23.
     */
    void bind(int slot);

    /**
     * Creates or updates this texture to use the matching texture data.
     * 
     * @param textureData
     *     - The new texture data to replace this texture with.
     * @throws IllegalArgumentException
     *     If the texture data is null.
     */
    void update(TextureData textureData);
}
