package ca.workpatrickwalker.engine;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Creates, manages and displays the game window.
 * 
 * @since 0.1.2
 * @author WorkPatrickWalker
 */
public class Window 
{
    private static final float A = 1.0f;
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
    private float r, g, b;
    
    /**
     * The default singleton constructor.
     *
     * @param width The window's width in pixels.
     * @param height The window's height in pixels.
     * @param title The window's title.
     * @since 0.1.2
     * @author WorkPatrickWalker
     */
    private Window(int width, int height, String title)
    {
        this.width = width;
        this.height = height;
        this.title = title;
        this.r = 1.0f;
        this.g = 1.0f;
        this.b = 1.0f;
    }

    /**
     * Gets the window instance singleton.
     *
     * @return The window singleton instance.
     * @since 0.1.2
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
     * @since 0.1.2
     * @author WorkPatrickWalker
     */
    public static void setWindow(int width, int height, String title)
    {
        if (instance == null) instance = new Window(width, height, title);
    }

    /**
     * Sets the r-value of the game window's background.
     * 
     * @param r The new r-value.
     * @since 0.2.1
     * @author WorkPatrickWalker
     */
    public static void setR(float r)
    {
        if (instance != null) instance.r = r;
    }

    /**
     * Sets the g-value of the game window's background.
     *
     * @param g The new g-value.
     * @since 0.2.1
     * @author WorkPatrickWalker
     */
    public static void setG(float g)
    {
        if (instance != null) instance.g = g;
    }

    /**
     * Sets the b-value of the game window's background.
     *
     * @param b The new b-value.
     * @since 0.2.1
     * @author WorkPatrickWalker
     */
    public static void setB(float b)
    {
        if (instance != null) instance.b = b;
    }

    /**
     * Initializes the game window and GLFW, and displays the game window to the monitor.
     * 
     * @param width The window's width in pixels.
     * @param height The window's height in pixels.
     * @param title The window's title.
     * @throws IllegalStateException If GLFW fails to initialize itself or the game window.
     * @since 0.1.2
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
        
        glfwSetCursorPosCallback(glfwWindowRef, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindowRef, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindowRef, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindowRef, KeyListener::keyCallback);

        glfwMakeContextCurrent(glfwWindowRef);

        glfwSwapInterval(VSYNC_ENABLED);

        glfwShowWindow(glfwWindowRef);

        GL.createCapabilities();
    }

    /**
     * Runs and handles the game loop.
     * 
     * @since 0.1.2
     * @author WorkPatrickWalker
     */
    public void runLoop()
    {
        while (!glfwWindowShouldClose(glfwWindowRef))
        {
            glfwPollEvents();
            glClearColor(this.r, this.g, this.b, A);
            glClear(GL_COLOR_BUFFER_BIT);
            glfwSwapBuffers(glfwWindowRef);
        }
    }

    /**
     * Frees the memory that GLFW allocates independent of Java's garbage collection.
     * 
     * @since 0.1.3
     * @author WorkPatricWalker
     */
    public void freeGLFWMem()
    {
        glfwFreeCallbacks(glfwWindowRef);
        glfwDestroyWindow(glfwWindowRef);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
