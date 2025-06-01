package ca.workpatrickwalker.cs20finalproj;

import ca.workpatrickwalker.cs20finalproj.engine.Window;

public class Main {
    
    public static void main(String[] args) 
    {
        Window.set(Window.HD_WIDTH, Window.HD_HEIGHT, Window.TESTING_TITLE_PLACEHOLDER);
        Window.get().init();
        Window.get().runLoop();
        Window.get().freeGLFWMem();
    }
}
