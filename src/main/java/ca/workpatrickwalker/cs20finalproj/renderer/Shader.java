package ca.workpatrickwalker.cs20finalproj.renderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Shader 
{
    private static final int COMPONENT1_BODY = 1;
    private static final int COMPONENT2_BODY = 2;
    private static final int TWO_COMPONENTS = 3;
    
    private final String filePath;
    
    private String fragmentSrc;
    private int programID;
    private String vertexSrc;
    
    
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

        System.out.println(vertexSrc);
        System.out.println(fragmentSrc);
    }

    public void compile()
    {
        
    }
    
    public void detach()
    {
        
    }
    
    public void use()
    {
        
    }
}
