package engine;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import classes.List;

public class Loader {

    // !! list was changed to abi-klassen list !!
    // keeps track of VBOs and VAOs
    private List<Integer> VAOs = new List<>();
    private List<Integer> VBOs = new List<>();

    public Model loadToVAO(float[] positions, int[] indices){ // returns model takes position
        int vaoID = createVAO(); // creates empty vao and assigns id
        bindIndicesBuffer(indices); // bind
        storeDataInAttributesList(0, positions); // loads the positions into first attributelist index 0

        unbindVAO(); // after vao is assigned id it unbind to prevent any changes/issues

        return new Model(vaoID, indices.length); // returns Model + directly creates it
    }

    /**
     * Clean up resources by deleting all vertex array objects and buffer objects.
     */
    public void cleanUp(){ // TODO: if issues -> change to arraylist
        VAOs.toFirst();

        while(!VAOs.hasAccess()){
            GL30.glDeleteVertexArrays(VAOs.getContent());
            VAOs.next();
        }

        VBOs.toFirst();

        while(!VBOs.hasAccess()){
            GL30.glDeleteVertexArrays(VBOs.getContent());
            VBOs.next();
        }

    }


    private int createVAO(){ // creates empty VAO
        int vaoID = GL30.glGenVertexArrays(); // creates empty vao with id
        VAOs.append(vaoID); // adds vao to list
        GL30.glBindVertexArray(vaoID); // binds vao to memory
        return vaoID; // returns
    }

    private void storeDataInAttributesList(int attributeNumber, float[] data) { // stores data in VAO
        // buffer is simply a block of memory that can be written to
        // we are practically binding the vbos to Buffers, blocks of memory so they are stored

        int vboID = GL15.glGenBuffers(); // creates empty vbo -> attribute list stored as vbo
        VBOs.append(vboID); // adds vbo to list
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID); // binds vbo, specifying type of vbo: GL_ARRAY_BUFFER

        FloatBuffer buffer = storeDataInFloatBuffer(data); // creates a FloatBuffer and stores data in it
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW); // stores data in vbo
        // last parameter is usage. GL_STATIC_DRAW means the data is read only and never will be edited

        GL20.glVertexAttribPointer(attributeNumber, 3, GL11.GL_FLOAT, false, 0, 0);
        // first parameter: attributeindex, second parameter: length of each data (we are using 3D vertex = 3), third parameter: type of data, fourth parameter: normalized: false raw integer values, fifth parameter: stride - is there any data inbetween verteces: no there isn't, sixth parameter: offset
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); // after use unbind the buffer to free up memory
    }

    private void unbindVAO(){ // unbinds VAO
        GL30.glBindVertexArray(0); // 0 unbinds
    }

    // indices Buffer is used instead of vertex buffer object because storing the verteces directly results in more memory usage because of repeating verteces. Index buffer ist better as Objects get bigger

    private void bindIndicesBuffer(int[] indices){
        int VBOID = GL15.glGenBuffers(); // generates vertex buffer object empty
        VBOs.append(VBOID); // adds Index Buffer to VBO list
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, VBOID); // this time binds different type of buffer -> element array buffer -> for indices
        IntBuffer buffer = storeDataInIntBuffer(indices); // stores indices in buffer calls storeDataInIntBuffer
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW); // stores data in buffer - first parameter is type of buffer, second is buffer, last parameter is usage - Static never to be changed
    }

    private IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length); // BufferUtils allocates memory for the intBuffer with the length of data
        buffer.put(data); // fills with data
        buffer.flip(); // finished writing and ready to read data
        return buffer;
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length); // BufferUtils allocates memory for the FloatBuffer
        buffer.put(data);
        buffer.flip(); // finished writing and ready to read data
        return buffer;
    }
}
