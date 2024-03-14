package shaders;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

public abstract class ShaderReader { //abstract because generic shader programm - every shader programm after that is based of the generic one

    private int programID;
    private int vertexShaderID; //for vertex shader txt
    private int fragmentShaderID;// for fragment shader txt

    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16); // Float buffer needed for Matrix 16 -> 4x4 size; place in memory needed for it

    public ShaderReader(String vertexFile, String fragmentFile){
        vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER); // gets vertex shader ID whilst loading
        fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER); // gets fragment shader ID whilst loading
        programID = GL20.glCreateProgram(); // creates shader program
        GL20.glAttachShader(programID, vertexShaderID); // attaches vertex shader to program
        GL20.glAttachShader(programID, fragmentShaderID);// attaches fragment shader to program
        bindProgramAttributes();
        GL20.glLinkProgram(programID); // links program, usable form, final step - OpenGL verifies all attached shaders are compatible
        GL20.glValidateProgram(programID); // validate that everything works fine with shader program and ready to use
        getAllUniformLocations(); // must be called after program created
    }

    // ----------Methods for loading in values--------------

    protected void loadMatrix(int location, Matrix4f matrix){ // Matrix4f is matrix based on homogenous coordinates -> 4x4
        matrix.store(matrixBuffer); // store matrix in buffer
        matrixBuffer.flip(); // ready to read
        GL20.glUniformMatrix4(location, false, matrixBuffer); // load it up into the location of the uniform variable
    }

    protected void loadBoolean(int location, boolean value){
        float toLoad = 0; // default is false
        if(value){
            toLoad = 1; // set to true if parameter value is true
        }
        GL20.glUniform1f(location, toLoad); // loads
    }

    protected void loadFloat(int location, float value){ // load float value - uniform float variable
        GL20.glUniform1f(location, value); // location is the location where it is saved in the shader code same for vector
    }

    protected void loadVector(int location, Vector3f vector){ // Vector3f ist a vector uniform variable in OpenGL
        GL20.glUniform3f(location, vector.x, vector.y, vector.z);
    }

    protected abstract void getAllUniformLocations();// all shaders program classes will have a method to get all uniform locations

    protected int getUniformLocation(String uniformName){ // gets uniform variable location - Uniform variable is needed for various transformations -> it is a variable that can be changed during rendering unlike static VBOs -> basically a matrix
        return GL20.glGetUniformLocation(programID, uniformName); // returns uniform variable location
    }

    /**
     * Start the program by using the specified program ID.
     */
    public void startProgram(){
        GL20.glUseProgram(programID);
    }

    /**
     * Stops the program.
     */
    public void stopProgram(){
        GL20.glUseProgram(0);
    }


    /**
     * Cleans up the resources used by the program.
     */
    public void cleanUp(){ // memory managment
        stopProgram(); // make sure no program is running
        GL20.glDetachShader(programID, vertexShaderID); // detaches vertex shader
        GL20.glDetachShader(programID, fragmentShaderID); // deletes fragment shader
        GL20.glDeleteShader(vertexShaderID); // deletes vertex shader
        GL20.glDeleteShader(fragmentShaderID); // deletes fragment shader
        GL20.glDeleteProgram(programID); // self delete
    }


    // abstract method - must be implemented in every child shader program
    protected abstract void bindProgramAttributes();

    /**
     * Binds the attribute of the VAO to the specified variable name.
     *
     * @param  attribute      the number of the attribute in the list of the VAO
     * @param  variableName   the name of the variable to bind
     */
    protected void bindAttribute(int attribute, String variableName){ // number of attribute list of VAO (the object to be rendered) from engine
        GL20.glBindAttribLocation(programID, attribute, variableName);
    } // can't be done from child class because it need program id

    /**
     * Loads and compiles a shader from a file.
     *
     * @param  file  the file path of the shader
     * @param  type  the type of the shader (vertex or fragment)
     * @return       the ID of the compiled shader
     */
    private static int loadShader(String file, int type){
        StringBuilder shaderSource = new StringBuilder(); // creates string builder

        // fills string builder with source code from vertex or fragment Shader txt
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine())!=null){
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }

        //creates shader -> attach source string to it -> compile it
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);

        // check for errors
        if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS )== GL11.GL_FALSE){
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader!"); // -> print errors if there are any
            System.exit(-1);
        }
        return shaderID; // returns id of shader
    }
}
