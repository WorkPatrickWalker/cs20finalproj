package ca.workpatrickwalker.util;

/**
 * Manages frame time calculation.
 * 
 * @since 0.2.2
 * @author WorkPatrickWalker
 */
public class Time 
{
    public static float frameDT;
    public static float atFrameEnd;
    public static float atFrameStart;
    public static float atRun = System.nanoTime();

    /**
     * Return the time elapsed since the program was run in seconds.
     * 
     * @return The time elapsed since the program was run in seconds.
     * @since 0.2.2
     * @author WorkPatrickWalker
     */
    public static float elapsed()
    {
        return (float) ((System.nanoTime() - Time.atRun) * 1E-9);
    }
}
