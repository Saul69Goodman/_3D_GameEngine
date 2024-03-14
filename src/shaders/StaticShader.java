package shaders;

import entities.Camera;
import org.lwjgl.util.vector.Matrix4f;
import toolbox.Maths;

public class StaticShader extends ShaderReader { // child class static shader -> to create all future static models implementation of shader

    private static final String vertexFile = "src/shaders/vertexShader.txt";
    private static final String fragmentFile = "src/shaders/fragmentShader.txt";

    private int location_transformationMatrix; // location saver
    private int location_projectionMatrix; // saves locations

    private int location_viewMatrix; // saves locations of view matrix

    public StaticShader() {
        super(vertexFile, fragmentFile);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix"); // get location of uniform variable, we only have one
        location_projectionMatrix = super.getUniformLocation("projectionMatrix"); // get location of projection variable
        location_viewMatrix = super.getUniformLocation("viewMatrix"); // get location of view matrix
    }

    @Override
    protected void bindProgramAttributes() {
        super.bindAttribute(0, "position"); // reference back to VAO being created in Loader -> attribute index 0
        super.bindAttribute(1, "textureCoords"); // bind attribute list 1 to var textureCoords
    }

    public void loadTransformationMatrix(Matrix4f matrix){ // method that can load up transformation matrix to the uniform variable in the shader code
        super.loadMatrix(location_transformationMatrix, matrix); // takes location and matrix
    }
    // projection matrix is another transformation matrix needed to create a feeling of 3D depth

    public void loadProjectionMatrix(Matrix4f projection){ // method that can load up similar to load transformationmatrix
        super.loadMatrix(location_projectionMatrix, projection);
    }

    public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Maths.createViewMatrix(camera); // create view matrix with camera
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }
}
