package tester;

import entities.Camera;
import entities.Entity;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import textures.ModelTexture;
import engine.DisplayManager;
import engine.Loader;
import models.Model;
import engine.Renderer;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import shaders.StaticShader;

/**
 * Main Game Loop Class until application is closed
 * <p>
 * Der Code basiert auf https://youtube.com/playlist?list=PLRIWtICgwaX0u7Rf9zkZhLoLuZVfUksDP&si=MrKzrECIsA1TJwy8
 * und ist auf Englisch kommentiert worden von Alper Ahmed
 *
 * @author Alper Ahmed
 */
public class GameLoop {

    public static void main(String[] args) throws LWJGLException {

        DisplayManager.createDisplay();

        // initializes all needed classes
        Loader loader = new Loader();

        StaticShader shader = new StaticShader(); // static shader first

        Renderer renderer = new Renderer(shader); // then renderer because needed as parameter

        // OpenGL expects vertices to be defined counter clockwise
        // Weitere Quellen: https://learnopengl.com/Getting-started/Hello-Triangle
        // https://www.lwjgl.org/guide
        // https://legacy.lwjgl.org/javadoc.html
        // https://javadoc.lwjgl.org/
        // https://coffeebeancode.gitbook.io/lwjgl-game-design/tutorials/chapter-1-drawing-your-first-triangle
        // https://redketchup.io/color-picker


        // Vertices: https://www.youtube.com/watch?v=EmHnBUG7yME&t=500s
        float[] vertices = {
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                0.0f, 0.5f, 0.0f,

                -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                0.0f, 0.5f, 0.0f,

                -0.5f, -0.5f, -0.5f,
                -0.5f, -0.5f, 0.5f,
                0.0f, 0.5f, 0.0f,

                0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, 0.5f,
                0.0f, 0.5f, 0.0f,

                0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, 0.5f,
                0.0f, 0.5f, 0.0f,

                -0.5f, -0.5f, 0.5f,
                -0.5f, -0.5f, -0.5f,
                0.0f, 0.5f, 0.0f,
        };

        // Indices
        int[] indices = {
                // Base triangle
                0, 1, 2,

                // Side triangle 1
                3, 4, 5,

                // Side triangle 2
                6, 7, 8,

                // Side triangle 3
                9, 10, 11
        };

        // Texture coordinates (flipped upside down)
        float[] textureCoords = {
                // Base triangle
                0.0f, 1.0f, // Bottom left
                1.0f, 1.0f, // Bottom right
                0.5f, 0.0f, // Top middle

                // Side triangles
                0.0f, 1.0f, // Bottom left
                1.0f, 1.0f, // Bottom right
                0.5f, 0.0f, // Top apex

                0.0f, 1.0f, // Bottom left
                1.0f, 1.0f, // Bottom right
                0.5f, 0.0f, // Top apex

                0.0f, 1.0f, // Bottom left
                1.0f, 1.0f, // Bottom right
                0.5f, 0.0f  // Top apex
        };



        // Creating of Models and Entities:
        Model myFirstTriangle = loader.loadToVAO(vertices, textureCoords, indices); // creates model
        ModelTexture texture = new ModelTexture(loader.loadTexture("image")); // in res folder
        TexturedModel texturedModel = new TexturedModel(myFirstTriangle, texture); // creates textured model from raw model and texture

        Entity entity = new Entity(texturedModel, new Vector3f(0,0,-1), 0, 0, 0, 1);

        // Camera:
        Camera camera = new Camera();

        // game loop; logic, calculations and gameplay -> CPU ; rendering -> GPU
        while (!Display.isCloseRequested()) {
            // game logic

            entity.increaseRotation(0,0.5f,0);

            camera.move();

            renderer.prepare(); // prepare renderer every single frame

            shader.startProgram();
            shader.loadViewMatrix(camera); // camera movement

            renderer.render(entity, shader);

            shader.stopProgram();

            DisplayManager.updateDisplay(); // 1 frame passing by

        }
        // cleanup after finished
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
