package ca.workpatrickwalker.engine;

/**
 * Listens for and handles mouse inputs.
 */
public class MouseListener 
{
    private static final int CENTER_MOUSE_BUTTON = 2;
    private static final int LEFT_MOUSE_BUTTON = 0;
    private static final int PRESSED = 1;
    private static final int RELEASED = 0;
    private static final int RIGHT_MOUSE_BUTTON = 1;
    private static final int STANDARD_MOUSE_BUTTONS = 3;

    private final boolean[] mouseButtonStates = new boolean[STANDARD_MOUSE_BUTTONS];
    
    private static MouseListener instance = null;
    
    private double x, y, lastX, lastY, scrollDX, scrollDY;
    private boolean dragging;

    /**
     * The default mouse listener singleton constructor.
     */
    private MouseListener()
    {
        this.x = 0.0;
        this.y = 0.0;
        this.lastX = 0.0;
        this.lastY = 0.0;
        this.scrollDX = 0.0;
        this.scrollDY = 0.0;
        this.dragging = false;
    }

    /**
     * Gets the mouse listener singleton instance and creates one if one does not already exist.
     * 
     * @return The mouse listener singleton instance.
     */
    public static MouseListener get()
    {
        if (instance == null) instance = new MouseListener();
        return instance;
    }

    /**
     * Gets the mouse's dragging state.
     *
     * @return If the mouse is dragging or not.
     */
    public static boolean isDragging()
    {
        return get().dragging;
    }

    /**
     * Updates a mouse button's state when an action involving it is detected.
     *
     * @param window The GLFW window.
     * @param button The button the action was performed on.
     * @param action The action performed.
     * @param mods Modifier keys that were being pressed down when the action was performed.
     */
    public static void mouseButtonCallback(@SuppressWarnings("unused") long window, int button, int action, int mods)
    {
        if (button < STANDARD_MOUSE_BUTTONS)
        {
            if (action == PRESSED)
            {
                get().mouseButtonStates[button] = true;
            }
            else if (action == RELEASED)
            {
                get().mouseButtonStates[button] = false;
                get().dragging = false;
            }
        }
    }

    /**
     * Checks if a specific button is being pressed or not.
     *
     * @param button The button being checked.
     * @return If the button is being pressed or not.
     */
    public static boolean mouseButtonsPressed(int button)
    {
        if (button < STANDARD_MOUSE_BUTTONS) return get().mouseButtonStates[button];
        return false;
    }

    /**
     * Updates the mouse's position variables when movement is detected.
     * 
     * @param window The GLFW window.
     * @param x The mouse's current x position.
     * @param y The mouse's current y position.
     */
    public static void mousePosCallback(@SuppressWarnings("unused") long window, double x, double y)
    {
        get().lastX = get().x;
        get().lastY = get().y;
        get().x = x;
        get().y = y;
        get().dragging = get().mouseButtonStates[LEFT_MOUSE_BUTTON] || get().mouseButtonStates[CENTER_MOUSE_BUTTON] || get().mouseButtonStates[RIGHT_MOUSE_BUTTON];
    }

    /**
     * Updates the mouse scroll wheel's displacement variables when scrolling is detected.
     * 
     * @param window The GLFW window.
     * @param dX The mouse scroll wheel's horizontal displacement.
     * @param dY The mouse scroll wheel's vertical displacement.
     */
    public static void mouseScrollCallback(@SuppressWarnings("unused") long window, double dX, double dY)
    {
        get().scrollDX = dX;
        get().scrollDY = dY;
    }

    /**
     * Zeroes displacement-related variables at the end of a frame.
     */
    public static void reset()
    {
        get().scrollDX = 0;
        get().scrollDY = 0;
        get().lastX = get().x;
        get().lastY = get().y;
    }

    /**
     * Gets the mouse's current x displacement.
     *
     * @return The mouse's current x displacement.
     */
    public static float getDX()
    {
        return (float) (get().x - get().lastX);
    }

    /**
     * Gets the mouse's current y displacement.
     *
     * @return The mouse's current y displacement.
     */
    public static float getDY()
    {
        return (float) (get().y - get().lastY);
    }

    /**
     * Gets the mouse scroll wheel's current x displacement.
     *
     * @return The mouse scroll wheel's current x displacement.
     */
    public static float getScrollDX()
    {
        return (float) get().scrollDX;
    }

    /**
     * Gets the mouse scroll wheel's current y displacement.
     *
     * @return The mouse scroll wheel's current y displacement.
     */
    public static float getScrollDY()
    {
        return (float) get().scrollDY;
    }

    /**
     * Gets the mouse's current x position.
     * 
     * @return The mouse's current x position.
     */
    public static float getX()
    {
        return (float) get().x;
    }

    /**
     * Gets the mouse's current y position.
     *
     * @return The mouse's current y position.
     */
    public static float getY()
    {
        return (float) get().y;
    }
}
