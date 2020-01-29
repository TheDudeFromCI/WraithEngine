package net.whg.we.main;

/**
 * This class contains a set of constants which are used to define the major
 * steps within the game loop pipeline. This can be used for determining proper
 * offsets for when events should occur.
 * <p>
 * When handling priority values for gameloop actions, direct values, such as
 * <code>23</code> should not be used. Instead, it is safer and future-proof to
 * use values such as <code>PipelineConstants.FRAME_UPDATES + 23</code>. This
 * has no effect on preformance and will not break if default values are
 * adjusted in the future.
 */
public final class PipelineConstants
{
    /**
     * The first call within a frame. Used to preform actions like calculating delta
     * time and required phyiscs updates.
     * <p>
     * Value is equal to {@value}.
     */
    public static final int CALCULATE_TIMESTAMPS = -1000000;

    /**
     * Called early within the frame to update all physics based or other
     * time-sensitive updates. This includes things such as player input or AI
     * updates. Physics are called on a reliable timer, and are executed multiple
     * times per frame, if needed, to catch up, or sometimes ignored completely in
     * some frames.s
     * <p>
     * Value is equal to {@value}.
     */
    public static final int PHYSICS_UPDATES = -10000;

    /**
     * Called to prepare the scene for rendering. One update is called each frame to
     * prepare the frame, and only updates events that change each frame. This
     * includes actions such as camera transformation, particle effects, or
     * interpolating physics updates. This update is also commonly used to indicate
     * whether an object will be rendered or not via frustrum culling and distance
     * from camera.
     * <p>
     * Value is equal to {@value}.
     */
    public static final int FRAME_UPDATES = 0;

    /**
     * Called after the update method in order to handle animation updates. This
     * usually includes updating skeletal animations and processing particle
     * effects.
     * <p>
     * Value is equal to {@value}.
     */
    public static final int ANIMATION_UPDATES = 10000;

    /**
     * Called after animation updates to post-process skeletal animations. This is
     * mainly targeted towards actions such as tweaking foot IK or handling head
     * look targets which are applied after the animation pose is determined.
     * <p>
     * Value is equal to {@value}.
     */
    public static final int IK_UPDATES = 12500;

    /**
     * Called after all updates are preformed on the scene to handle any last minute
     * tweaks which would occur before the scene is officially rendered. This is
     * most often used for post-update listeners to handle updates which are
     * effected by the previous states but effect no others. A common example of
     * this is a camera which follows a target, which would be updated in this step
     * to ensure the camera follows a smooth path and is called after all other
     * entities are in their final render location.
     * <p>
     * Value is equal to {@value}.
     */
    public static final int LATE_UPDATES = 20000;

    /**
     * This is called at the start of the rendering part of the pipeline to screen
     * the screen to render to. This is primarily used to clear the color and depth
     * buffer fields. If a skybox is being used, it is rendered during this step. If
     * post processing is being used, the buffer frame is prepared during this step.
     * <p>
     * Value is equal to {@value}.
     */
    public static final int CLEAR_SCREEN = 29000;

    /**
     * After clearing the screen, next the solid objects within the scene are
     * rendered. Objects are rendered directly to the output in order from closest
     * objects to furthest objects. In a forward rendering path, lighting is applied
     * to objects as they are rendered. In a deffered rendering path, objects are
     * rendered to the material buffer textures.
     * <p>
     * Value is equal to {@value}.
     */
    public static final int RENDER_SOLIDS = 30000;

    /**
     * After rendering solids to the screen, solid decals are rendered next. The
     * properties of rendering decals is nearly identical to rendering solids.
     * <p>
     * Value is equal to {@value}.
     */
    public static final int RENDER_DECALS = 32500;

    /**
     * In a deffered rendering pipeline, the lighting updates are applied during
     * this phase to draw all lights to the material buffer textures. This step is
     * ignored in a forward lighting pipeline.
     * <p>
     * Value is equal to {@value}.
     */
    public static final int DEFFERED_RENDERING = 35000;

    /**
     * Due to the nature of rendering transparent objects, all transparent objects
     * must be rendered together in a single pass. This includes all transparent
     * materials and particles. All transparent objects are rendered with a forward
     * render pass, regardless of render pipeline, in back-to-front order.
     * <p>
     * Value is equal to {@value}.
     */
    public static final int RENDER_TRANSPARENTS = 40000;

    /**
     * After the scene is rendered, post processing effects are applied. This
     * includes actions such as SSAO or depth blur. If post processing is not
     * enabled, nothing happens during this step.
     * <p>
     * Value is equal to {@value}.
     */
    public static final int POST_PROCESSING = 50000;

    /**
     * To prepare the frame to render UI, the depth buffer is cleared here.
     * <p>
     * Value is equal to {@value}.
     */
    public static final int CLEAR_DEPTH = 59000;

    /**
     * The UI rendering is all handled in a single pass, in back-to-front order.
     * <p>
     * Value is equal to {@value}.
     */
    public static final int RENDER_UI = 60000;

    /**
     * At the effective end of the frame, game objects which were marked for removal
     * during this frame are removed and disposed. This step is also used for
     * general cleanup throughout the scene including disposing unused resources and
     * resizing buffers or pools.
     * <p>
     * Value is equal to {@value}.
     */
    public static final int DISPOSE_GAMEOBJECTS = 70000;

    /**
     * The end frame step is used to trigger some events to mark the frame as
     * complete and prepare local buffers for the next frame. A common use-case of
     * this is the Input class, which swaps key binding buffers, marking currently
     * pressed keys as occuring on the previous frame and preparing to recieve new
     * input updates. This step marks the end of frame for all game logic.
     * <p>
     * Value is equal to {@value}.
     */
    public static final int ENDFRAME = 80000;

    /**
     * Called after all game-logic for a frame is completed, the window is polled
     * for new user input events as well as triggering the GPU to swap render
     * buffers, pushing the rendered image to the screen.
     * <p>
     * Value is equal to {@value}.
     */
    public static final int POLL_WINDOW_EVENTS = 90000;

    /**
     * If a framerate cap is enabled, this step serves to sleep the thread as needed
     * to enforce that the framerate cap is not exceeded.
     * <p>
     * Value is equal to {@value}.
     */
    public static final int FRAMERATE_LIMITER = 100000;

    private PipelineConstants()
    {}
}
