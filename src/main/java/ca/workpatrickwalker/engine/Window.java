package ca.workpatrickwalker.engine;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Creates, manages and displays the game window.
 * 
 * @since 1.0.0
 * @author WorkPatrickWalker
 */
public class Window 
{
    private static final long DEFAULT_MONITOR = 0L;
    private static final long GLFW_WINDOW_CREATION_FAILED = 0L;
    private static final long NO_SHARING = 0L;
    private static final int VSYNC_ENABLED = 1;
    
    public static final int HD_HEIGHT = 1080;
    public static final int HD_WIDTH = 1920;
    public static final String TESTING_TITLE_PLACEHOLDER = "cs20finalproj";

    private static long glfwWindowRef;
    private static Window instance = null;

    private int height;
    private String title;
    private int width;
    

    /**
     * The default singleton constructor.
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
    }

    /**
     * Gets the window instance singleton.
     *
     * @return The window singleton instance.
     * @since 1.0.0
     * @author WorkPatrickWalker
     */
    public static Window get()
    {
        return instance;
    }
    
    /**
     * Sets the window instance singleton if one does not already exist.
     *
     * @param width The window's width in pixels.
     * @param height The window's height in pixels.
     * @param title The window's title.
     * @since 1.0.0
     * @author WorkPatrickWalker
     */
    public static void set(int width, int height, String title)
    {
        if (instance == null) instance = new Window(width, height, title);
    }

    /**
     * Initializes the game window and GLFW, and displays the game window to the monitor.
     * 
     * @param width The window's width in pixels.
     * @param height The window's height in pixels.
     * @param title The window's title.
     * @throws IllegalStateException If GLFW fails to initialize itself or the game window.
     * @since 1.0.0
     * @author WorkPatrickWalker
     */
    public void init()
    {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW.");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        glfwWindowRef = glfwCreateWindow(this.width, this.height, this.title, DEFAULT_MONITOR, NO_SHARING);
        if (glfwWindowRef == GLFW_WINDOW_CREATION_FAILED) throw new IllegalStateException("GLFW failed to create the window.");

        glfwMakeContextCurrent(glfwWindowRef);

        glfwSwapInterval(VSYNC_ENABLED);

        glfwShowWindow(glfwWindowRef);

        GL.createCapabilities();
    }

    /**
     * Runs and handles the game loop.
     * 
     * @since 1.0.0
     * @author WorkPatrickWalker
     */
    public void runLoop()
    {
        while (!glfwWindowShouldClose(glfwWindowRef))
        {
            glfwPollEvents();
            glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);
            glfwSwapBuffers(glfwWindowRef);
        }
    }
}
