package ca.workpatrickwalker.cs20finalproj.engine;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Handles the level editor scene.
 */
public class LevelEditorScene extends Scene 
{
    private static final int ALL_ELEMENTS = 0;
    private static final int GL_FAILED = 0;
    private static final int COLOUR_ATTRIBS = 4;
    private static final int COLOUR_ATTRIBS_INDEX = 1;
    private static final int COLOUR_ATTRIBS_OFFSET = 12;
    private static final int POS_ATTRIBS = 3;
    private static final int POS_ATTRIBS_INDEX = 0;
    private static final int POS_ATTRIBS_OFFSET = 0;
    private static final int UNBIND = 0;
    private static final boolean UNNORMALIZE_VERTEX = false;
    private static final int VERTEX_SIZE = 28;
    
    private int eBOID;
    private int[] elements =
            {
                    0,  1,  3,
                    1,  2,  3
            };
    private int fragID;
    private String fragSrc =
            """
                    #version 330 core
            
                    in vec4 fragColour;
            
                    out vec4 colour;
            
                    void main()
                    {
                        colour = fragColour;
                    }
            """;
    private int shaderProg;
    private int vAOID;
    private int vBOID;
    private int vertexID;
    private String vertexSrc =
            """
                    #version 330 core
            
                    layout (location = 0) in vec3 pos;
                    layout (location = 1) in vec4 colour;
            
                    out vec4 fragColour;
            
                    void main()
                    {
                        fragColour = colour;
                        gl_Position = vec4(pos, 1.0);
                    }
            """;
    private float[] vertices =
            {
                    0.5f,   0.5f,   0.0f,       0.0f,   0.0f,   1.0f,   1.0f,
                    -0.5f,  0.5f,   0.0f,       0.0f,   1.0f,   0.0f,   1.0f,
                    -0.5f,  -0.5f,  0.0f,       1.0f,   1.0f,   0.0f,   1.0f,
                    0.5f,  -0.5f,  0.0f,       1.0f,   0.0f,   0.0f,   1.0f
            };
    
    /**
     * The default level editor scene constructor.
     */
    public LevelEditorScene()
    {
        
    }

    /**
     * Puts the vertices in use
     */
    public void bindVertices()
    {
        glUseProgram(shaderProg);
        glBindVertexArray(vAOID);
        glEnableVertexAttribArray(POS_ATTRIBS_INDEX);
        glEnableVertexAttribArray(COLOUR_ATTRIBS_INDEX);
    }

    /**
     * Compiles the level editor scene's fragment shader. Throws an assertion if GL fails.
     */
    public void compileFrag()
    {
        fragID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragID, fragSrc);
        glCompileShader(fragID);

        if (glGetShaderi(fragID, GL_COMPILE_STATUS) == GL_FAILED)
        {
            System.out.println("ERROR: 'default.glsl'\n\tFragment shader compilation failed.");
            // Since GL is written in C, we need to pass it the length of the info log as well
            System.out.println(glGetShaderInfoLog(fragID, glGetShaderi(fragID, GL_INFO_LOG_LENGTH)));
            assert false : "";
        }
    }

    /**
     * Compiles the level editor scene's vertex shader. Throws an assertion if GL fails.
     */
    public void compileVertex()
    {
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexID, vertexSrc);
        glCompileShader(vertexID);
        
        if (glGetShaderi(vertexID, GL_COMPILE_STATUS) == GL_FAILED)
        {
            System.out.println("ERROR: 'default.glsl'\n\tVertex shader compilation failed.");
            // Since GL is written in C, we need to pass it the length of the info log as well
            System.out.println(glGetShaderInfoLog(vertexID, glGetShaderi(vertexID, GL_INFO_LOG_LENGTH)));
            assert false : "";
        }
    }

    /**
     * Draws the in-use vertices to the game window.
     */
    public void draw()
    {
        glDrawElements(GL_TRIANGLES, elements.length, GL_UNSIGNED_INT, ALL_ELEMENTS);
    }

    /**
     * Generates EBOs from the defined VAOs.
     */
    public void genEBOs()
    {
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elements.length);
        elementBuffer.put(elements).flip();
        
        eBOID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eBOID);
        // The EBOs will not be being modified while they are in use so static draw is alright to use
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);
    }

    /**
     * Generates VAOs from the defined vertexes.
     */
    public void genVAOs()
    {
        vAOID = glGenVertexArrays();
        glBindVertexArray(vAOID);
    }

    /**
     * Generates VBOs from the defined VAOs.
     */
    public void genVBOs()
    {
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices).flip();
        
        vBOID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vBOID);
        // The VBOs will not be being modified while they are in use so static draw is alright to use
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
    }

    /**
     * Initializes the level editor scene.
     */
    @Override public void init()
    {
        compileVertex();
        compileFrag();
        linkShaders();
        genVAOs();
        genVBOs();
        genEBOs();
        setVertexAttribs();
    }

    /**
     * Links the level editor scene's shaders. Throws an assertion if GL fails.
     */
    public void linkShaders()
    {
        shaderProg = glCreateProgram();
        glAttachShader(shaderProg, vertexID);
        glAttachShader(shaderProg, fragID);
        glLinkProgram(shaderProg);
        
        if (glGetProgrami(shaderProg, GL_LINK_STATUS) == GL_FAILED)
        {
            System.out.println("ERROR: 'default.glsl'\n\tShader linking failed.");
            // Since GL is written in C, we need to pass it the length of the info log as well
            System.out.println(glGetProgramInfoLog(shaderProg, glGetProgrami(vertexID, GL_INFO_LOG_LENGTH)));
            assert false : "";
        }
    }

    /**
     * Gives GL information on the vertices
     */
    public void setVertexAttribs()
    {
        glVertexAttribPointer(POS_ATTRIBS_INDEX, POS_ATTRIBS, GL_FLOAT, UNNORMALIZE_VERTEX, VERTEX_SIZE, POS_ATTRIBS_OFFSET);
        glEnableVertexAttribArray(POS_ATTRIBS_INDEX);
        glVertexAttribPointer(COLOUR_ATTRIBS_INDEX, COLOUR_ATTRIBS, GL_FLOAT, UNNORMALIZE_VERTEX, VERTEX_SIZE, COLOUR_ATTRIBS_OFFSET);
        glEnableVertexAttribArray(COLOUR_ATTRIBS_INDEX);
    }

    /**
     * Takes the vertices out of use
     */
    public void unbindVertices()
    {
        glDisableVertexAttribArray(POS_ATTRIBS_INDEX);
        glDisableVertexAttribArray(COLOUR_ATTRIBS_INDEX);

        glBindVertexArray(UNBIND);
        glUseProgram(UNBIND);
    }

    /**
     * Handles frame transition events.
     *
     * @param dT The frame's duration.
     */
    @Override public void update(float dT)
    {
        bindVertices();
        draw();
        unbindVertices();
    }
}
