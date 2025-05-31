package ca.workpatrickwalker.engine;

/**
 * Creates, manages and displays the game window.
 * 
 * @since 1.0.0
 * @author WorkPatrickWalker
 */
public class Window 
{
    private static final int HD_WIDTH = 1920;
    private static final int HD_HEIGHT = 1080;
    private static final String TESTING_TITLE = "cs20finalproj 1.0.0";

    private static Window instance = null;
    private final int width, height;
    private final String title;

    /**
     * Create the game window.
     * 
     * @param width The window's width in pixels.
     * @param height The window's height in pixels.
     * @param title The window's title.
     * @since 1.0.0
     * @author WorkPatrickWalker
     */
    private Window(int width, int height, String title)
    {
        this.width = width;
        this.height = height;
        this.title = title;
        Window.instance = this;
    }

    /**
     * Get the window singleton instance and create it if it doesn't already exist.
     * 
     * @return The window singleton instance.
     * @see Window#Window(int, int, String)  Window
     * @since 1.0.0
     * @author WorkPatrickWalker
     */
    public static Window get()
    {
        if (instance == null) new Window(HD_WIDTH, HD_HEIGHT, TESTING_TITLE);
        return instance;
    }
}
