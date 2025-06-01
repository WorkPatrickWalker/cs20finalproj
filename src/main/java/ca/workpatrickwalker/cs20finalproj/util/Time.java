package ca.workpatrickwalker.cs20finalproj.util;

/**
 * Manages time calculation.
 */
public class Time 
{
    public static float atRun = System.nanoTime();

    /**
     * Return the time elapsed since the program was run in seconds.
     * 
     * @return The time elapsed since the program was run in seconds.
     */
    public static float elapsed()
    {
        return (float) ((System.nanoTime() - Time.atRun) * 1E-9);
    }
}
