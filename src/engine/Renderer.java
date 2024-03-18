package engine;

import entities.Entity;
import models.Model;
import models.TexturedModel;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;
import shaders.StaticShader;
import toolbox.Maths;

public class Renderer {

    //some variables needed for rendering
    private static final float FOV = 70;
    private static final float nearPlane = 0.1f;
    private static final float farPlane = 1000;

    private Matrix4f projectionMatrix; // matrix itself

    public Renderer(StaticShader shader){ // need shader because it has to be load it straight away to the shader
        createProjectionMatrix(); // only needs to be created once because it's a transformation matrix so it can be edited during rendering
        shader.startProgram(); // after matrix directly start and then ->
        shader.loadProjectionMatrix(projectionMatrix);// load shader - you only have to do it once
        shader.stopProgram(); // stop shader again -> we never have to load the projection matrix to the shader again
    }

    public void prepare (){ // prepare OPENGL to render the game

        GL11.glEnable(GL11.GL_DEPTH_TEST); // enables depth testing -> basically Hidden surface removal -> or better render in right order so the driangles on top come on top

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // clears the color buffer and depth buffer every frame
        GL11.glClearColor((float) 160 / 255 , (float) 32 / 255 , (float) 240 / 255, 1); // rgb values, opacity, sets the color to be used after the color buffer is cleared

    }

    /*Beginning: render raw model
    Edit 1: renders textured models
    Edit 2: renders entities*/

    public void render (Entity entity, StaticShader shader){ // static shader so that it uploads transformation of enrtity

        TexturedModel texturedModel = entity.getModel(); // gets the textured model from the entity
        Model model = texturedModel.getModel(); // gets the raw model from the TexturedModel for rendering

        GL30.glBindVertexArray(model.getVaoID()); // binds the vao, can't do anything unless it is bound

        GL20.glEnableVertexAttribArray(0); // activates the first attributelist where i stored the data
        GL20.glEnableVertexAttribArray(1); // activates the second attributelist

        // Transformations:
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale()); // creates transformation matrix from the entity class
        // loads it to shader
        shader.loadTransformationMatrix(transformationMatrix);

        GL13.glActiveTexture(GL13.GL_TEXTURE0); // activates the first texture bank where we can bind the texture
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID()); // binds the texture

        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);  // draws the model, parameters: what type of object we want - triangles, number of verteces to render, type of data = indeces buffer that contains int, offset - where in the buffer to start 0 means beginning

        GL20.glDisableVertexAttribArray(0); // deactivates the first attributelist
        GL20.glDisableVertexAttribArray(1); // deactivates the second attributelist

        GL30.glBindVertexArray(0); // unbinds the vao
    } // renders the model

    /**
     * Create the projection matrix based on the current display width, height, field of view,
     * near and far planes. Refer to https://www.songho.ca/opengl/gl_projectionmatrix.html for details.
     */
    private void createProjectionMatrix(){
        // Calculate aspect ratio based on display width and height
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();

        // Calculate scale factors for x and y axes
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;

        // Calculate frustum length
        float frustum_length = farPlane - nearPlane;

        // Create a new 4x4 matrix for the projection matrix
        projectionMatrix = new Matrix4f();

        // Fill the projection matrix elements
        projectionMatrix.m00 = x_scale; // Scaling factor for x axis
        projectionMatrix.m11 = y_scale; // Scaling factor for y axis
        projectionMatrix.m22 = -((farPlane + nearPlane) / frustum_length); // Perspective division
        projectionMatrix.m23 = -1; // Depth correction
        projectionMatrix.m32 = -((2 * nearPlane * farPlane) / frustum_length); // Depth correction
        projectionMatrix.m33 = 0; // Last element of a projection matrix
    }
}
