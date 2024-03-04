package tester;

import engine.DisplayManager;
import engine.Loader;
import engine.Model;
import engine.Renderer;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

// main game looping until the application is closed
public class GameLoop {

    public static void main(String[] args) throws LWJGLException {

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();

        // OpenGL expects vertices to be defined counter clockwise
        // source: https://learnopengl.com/Getting-started/Hello-Triangle

        float vertices[] = { // how are their organized, which order
                // triangle's 3 Vertices
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.0f,  0.5f, 0.0f
        };

        Model myFirstTriangle = loader.loadToVAO(vertices); // creates model

        while (!Display.isCloseRequested()) {

            renderer.prepare(); // prepare renderer every single frame
            // game logic
            renderer.render(myFirstTriangle);
            // render
            DisplayManager.updateDisplay(); // 1 frame

        }
        // cleanup after finished
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
