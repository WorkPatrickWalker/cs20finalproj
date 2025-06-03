package ca.workpatrickwalker.cs20finalproj.engine;

import org.joml.Matrix4f;
import org.joml.Vector2f;

/**
 * Handles the game camera.
 */
public class Camera 
{
    private static final float DEPTH_RENDER_MAX = 100.0f;
    private static final float DEPTH_RENDER_MIN = 0.0f;
    private static final float GRID_SQ_LENGTH = 32.0f;
    private static final float HOR_GRID_SQS = 40.0f;
    private static final float HOR_OFFSET = 0.0f;
    private static final float VERT_GRID_SQS = 21.0f;
    private static final float VERT_OFFSET = 0.0f;
    
    private Vector2f pos;
    private Matrix4f projectionMat;
    private Matrix4f viewMat;

    /**
     * Spawn in a game camera at a specified location in the world.
     * 
     * @param pos Where in the game world the camera should be spawned in.
     */
    public Camera(Vector2f pos)
    {
        this.pos = pos;
        this.projectionMat = new Matrix4f();
        this.viewMat = new Matrix4f();
    }

    /**
     * Adjusts the camera's projection if the screen size changes.
     */
    public void adjustProjection()
    {
        projectionMat.identity();
        projectionMat.ortho(HOR_OFFSET, HOR_GRID_SQS * GRID_SQ_LENGTH, VERT_OFFSET, VERT_GRID_SQS * GRID_SQ_LENGTH, DEPTH_RENDER_MIN, DEPTH_RENDER_MAX);
    }
}
