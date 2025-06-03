package ca.workpatrickwalker.cs20finalproj.renderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL20.*;

/**
 * Handles the creation, compilation and de/attachment of shaders.
 */
public class Shader 
{
    private static final int COMPONENT1_BODY = 1;
    private static final int COMPONENT2_BODY = 2;
    private static final int GL_FAILED = 0;
    private static final int TWO_COMPONENTS = 3;
    private static final int UNBIND = 0;
    
    public static final String DEFAULT = "default";
    
    private final String filePath;
    
    private int fragmentID;
    private String fragmentSrc;
    private int program;
    private int vertexID;
    private String vertexSrc;


    /**
     * Reads in the inputted .glsl file, searching for shader components (vertex, fragment) and recording them.
     * Kills on assertion if the .glsl file does not contain exactly one vertex component and one fragment component.
     * 
     * @param fileName The name of the target .glsl file
     */
    public Shader(String fileName)
    {
        this.filePath = "shaders/" + fileName + ".glsl";
        
        String src = "";
        try
        {
            src = new String(Files.readAllBytes(Paths.get(filePath)));
        }
        catch (IOException e)
        {
            e.printStackTrace(System.out);
            assert false : "Error: Could not open '" + filePath + "'.";
        }

        String[] splitFileSrc = src.split("(#type )([a-zA-Z]+)");
        assert splitFileSrc.length == TWO_COMPONENTS : "Error: '" + filePath + "' must have exactly two shaders.";

        int typeDescStartIndex = src.indexOf("#type ") + "#type ".length();
        int typeDescEndIndex = src.indexOf("\r\n", typeDescStartIndex);
        String component1 = src.substring(typeDescStartIndex, typeDescEndIndex);

        if (component1.equals("vertex")) vertexSrc = splitFileSrc[COMPONENT1_BODY];
        else if (component1.equals("fragment")) fragmentSrc = splitFileSrc[COMPONENT1_BODY];
        else assert false : "Error: Unknown shader type: '" + component1 + "'.";

        typeDescStartIndex = src.indexOf("#type ", typeDescEndIndex) + "#type ".length();
        typeDescEndIndex = src.indexOf("\r\n", typeDescStartIndex);
        String component2 = src.substring(typeDescStartIndex, typeDescEndIndex);

        if (component2.equals("vertex"))
        {
            if (!component1.equals("fragment")) assert false : "Error: '" + filePath + "' has no fragment shader.";
            else vertexSrc = splitFileSrc[COMPONENT2_BODY];
        }
        else if (component2.equals("fragment"))
        {
            if (!component1.equals("vertex")) assert false : "Error: '" + filePath + "' has no vertex shader.";
            else fragmentSrc = splitFileSrc[COMPONENT2_BODY];
        }
        else assert false : "Error: Unknown shader type: '" + component2 + "'.";
    }

    /**
     * Compiles the shader components. Kills on assertion if GL fails.
     */
    public void compile()
    {
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexID, vertexSrc);
        glCompileShader(vertexID);

        if (glGetShaderi(vertexID, GL_COMPILE_STATUS) == GL_FAILED)
        {
            System.out.println("ERROR: '" + filePath + "'\n\tVertex shader compilation failed.");
            // Since GL is written in C, we need to pass it the length of the info log as well
            System.out.println(glGetShaderInfoLog(vertexID, glGetShaderi(vertexID, GL_INFO_LOG_LENGTH)));
            assert false : "";
        }
        
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentID, fragmentSrc);
        glCompileShader(fragmentID);

        if (glGetShaderi(fragmentID, GL_COMPILE_STATUS) == GL_FAILED)
        {
            System.out.println("ERROR: '" + filePath + "'\n\tFragment shader compilation failed.");
            // Since GL is written in C, we need to pass it the length of the info log as well
            System.out.println(glGetShaderInfoLog(fragmentID, glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH)));
            assert false : "";
        }
        
        System.out.println(vertexSrc);
        System.out.println(fragmentSrc);
    }

    /**
     * Takes the shader out of use.
     */
    public void detach()
    {
        glUseProgram(UNBIND);
    }

    /**
     * Links the shader's components. Kills on assertion if GL fails.
     */
    public void link()
    {
        program = glCreateProgram();
        glAttachShader(program, vertexID);
        glAttachShader(program, fragmentID);
        glLinkProgram(program);

        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FAILED)
        {
            System.out.println("ERROR: '" + filePath + "'\n\tShader linking failed.");
            // Since GL is written in C, we need to pass it the length of the info log as well
            System.out.println(glGetProgramInfoLog(program, glGetProgrami(program, GL_INFO_LOG_LENGTH)));
            assert false : "";
        }
    }

    /**
     * Puts the shader into use.
     */
    public void use()
    {
        glUseProgram(program);
    }
}
