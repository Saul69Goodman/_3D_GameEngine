package tester;

import Models.TexturedModel;
import Textures.ModelTexture;
import engine.DisplayManager;
import engine.Loader;
import Models.Model;
import engine.Renderer;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import shaders.staticShader;

// main game looping until the application is closed
public class GameLoop {

    public static void main(String[] args) throws LWJGLException {

        DisplayManager.createDisplay();

        // initializes all needed classes
        Loader loader = new Loader();
        Renderer renderer = new Renderer();

        staticShader shader = new staticShader();

        // OpenGL expects vertices to be defined counter clockwise
        // source: https://learnopengl.com/Getting-started/Hello-Triangle

        float[] vertices = {
                // triangle's 3 Vertices
                -0.5f, -0.5f, 0.0f, // Punkt1
                0.5f, -0.5f, 0.0f, // Punkt2
                0.0f,  0.5f, 0.0f  // Punkt3
        };

        int[] indices = {
                0, 1, 2
        };

        // OpenGL need to know texture coordinates corresponding to vertices. Texture coordinate system -> top left is 0,0
        float[] textureCoords = {
            0,1, //Punkt1
            1,1, //Punkt2
            0.5f,0 //Punkt3
        };

        Model myFirstTriangle = loader.loadToVAO(vertices, textureCoords, indices); // creates model
        ModelTexture texture = new ModelTexture(loader.loadTexture("image")); // in res folder
        TexturedModel texturedModel = new TexturedModel(myFirstTriangle, texture); // creates textured model from raw model and texture

        // game loop; logic, calculations and gameplay -> CPU ; rendering -> GPU
        while (!Display.isCloseRequested()) {
            // game logic
            renderer.prepare(); // prepare renderer every single frame
            shader.startProgram();
            renderer.render(texturedModel);
            shader.stopProgram();
            DisplayManager.updateDisplay(); // 1 frame

        }
        // cleanup after finished
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
