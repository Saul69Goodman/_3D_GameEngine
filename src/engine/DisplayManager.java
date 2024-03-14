package engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

// Manages display
public class DisplayManager {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int FPS = 120;

    /**
     * Creates a display using LWJGL library for a 3D game engine.
     *
     * @throws LWJGLException  if there is an error creating the display
     */
    public static void createDisplay() throws LWJGLException {

        ContextAttribs attributes = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT)); // Sets the display
            Display.create(new PixelFormat(), attributes);// Creates the display pixel format???
            Display.setTitle("3D Game Engine");
        } catch (LWJGLException e) { // throws LWJGLException if there is an error creating the display
            e.printStackTrace();
        }

        GL11.glViewport(0, 0, WIDTH, HEIGHT); // Sets the viewport to fullscreen bottom left: 0,0 to WIDTH, HEIGHT

    }

    /**
     * Update the display every frame
     *
     */
    public static void updateDisplay() {

        Display.sync(FPS); // Limits the display to FPS
        Display.update(); // Updates the display
    }

    /**
     * Closes the display.
     *
     */
    public static void closeDisplay() {

        Display.destroy();

    }
}