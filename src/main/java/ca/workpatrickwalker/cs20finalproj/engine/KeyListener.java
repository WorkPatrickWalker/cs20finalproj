package ca.workpatrickwalker.cs20finalproj.engine;

/**
 * Listens for and handles keyboard inputs.
 */
public class KeyListener 
{
    private static final int PRESSED = 1;
    private static final int RELEASED = 0;
    private static final int STANDARD_KEYCODES = 350;
    
    private final boolean[] keyStates = new boolean[STANDARD_KEYCODES];

    private static KeyListener instance = null;

    /**
     * The default key listener singleton constructor.
     */
    private KeyListener()
    {
        
    }

    /**
     * Gets the key listener singleton instance and creates one if one does not already exist.
     * 
     * @return The key listener singleton instance.
     */
    public static KeyListener get()
    {
        if (instance == null) instance = new KeyListener();
        return instance;
    }

    /**
     * Updates a key's state when an action involving it is detected.
     * 
     * @param window The GLFW window.
     * @param keyCode The key the action was performed on.
     * @param scancode The key's scancode.
     * @param action The action performed on the key.
     * @param mods Modifier keys that were pressed down when the action was performed.
     */
    public static void keyCallback(@SuppressWarnings("unused") long window, int keyCode, @SuppressWarnings("unused") int scancode, int action, int mods)
    {
        if (keyCode < STANDARD_KEYCODES)
        {
            if (action == PRESSED)
            {
                get().keyStates[keyCode] = true;
            }
            else if (action == RELEASED)
            {
                get().keyStates[keyCode] = false;
            }
        }
    }

    /**
     * Checks if a specific key is being pressed or not.
     * 
     * @param keyCode The key being checked.
     * @return If the key is being pressed or not.
     */
    public static boolean keyPressed(int keyCode)
    {
        if (keyCode < STANDARD_KEYCODES) return get().keyStates[keyCode];
        return false;
    }
}
