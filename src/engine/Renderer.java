package engine;

import Models.Model;
import Models.TexturedModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Renderer {

    public void prepare (){ // prepare OPENGL to render the game

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT); // clears the color buffer
        GL11.glClearColor((float) 160 / 255 , (float) 32 / 255 , (float) 240 / 255, 1); // rgb values, opacity, sets the color to be used after the color buffer is cleared

    }

    public void render (TexturedModel texturedModel){
        Model model = texturedModel.getModel(); // gets the raw model from the TexturedModel for rendering

        GL30.glBindVertexArray(model.getVaoID()); // binds the vao, can't do anything unless it is bound

        GL20.glEnableVertexAttribArray(0); // activates the first attributelist where i stored the data
        GL20.glEnableVertexAttribArray(1); // activates the second attributelist

        GL13.glActiveTexture(GL13.GL_TEXTURE0); // activates the first texture bank where we can bind the texture
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID()); // binds the texture

        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);  // draws the model, parameters: what type of object we want - triangles, number of verteces to render, type of data = indeces buffer that contains int, offset - where in the buffer to start 0 means beginning

        GL20.glDisableVertexAttribArray(0); // deactivates the first attributelist
        GL20.glDisableVertexAttribArray(1); // deactivates the second attributelist

        GL30.glBindVertexArray(0); // unbinds the vao
    } // renders the model
}
