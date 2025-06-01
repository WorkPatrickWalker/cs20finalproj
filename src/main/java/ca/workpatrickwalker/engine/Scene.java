package ca.workpatrickwalker.engine;

/**
 * An abstraction for game scene classes that enforces timed scene updating.
 */
public abstract class Scene 
{
    /**
     * The default scene class constructor.
     */
    public Scene()
    {
        
    }

    /**
     * Handles frame transition events.
     * 
     * @param dT The frame's duration.
     */
    public abstract void update(float dT);
}
