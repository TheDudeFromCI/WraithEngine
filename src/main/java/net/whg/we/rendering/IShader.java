package net.whg.we.rendering;

/**
 * A shader is used to determine how an object should be rendered to the screen.
 * It contains driver-specific code to be executed on the graphics card to
 * manipulate vertices into a renderable form. Shaders are also used to apply
 * effects to objects such as textures and lighting.
 */
public interface IShader
{
    /**
     * When called, this shader is bound to the graphics card, if not already bound,
     * so that future mesh renders will use this shader to render with.
     */
    void bind();
}
