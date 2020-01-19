package net.whg.we.rendering;

/**
 * The mode to use when culling faces during rendering.
 */
public enum CullingMode
{
    /**
     * Never cull any faces.
     */
    NONE,

    /**
     * Only cull triangles facing towards the camera.
     */
    FRONT_FACING,

    /**
     * Only cull triangles facing away from the camera.
     */
    BACK_FACING
}
