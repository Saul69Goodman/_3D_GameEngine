package shaders;

public class staticShader extends shaderReader { // child class static shader -> to create all future static models implementation of shader

    private static final String vertexFile = "src/shaders/vertexShader.txt";
    private static final String fragmentFile = "src/shaders/fragmentShader.txt";

    public staticShader() {
        super(vertexFile, fragmentFile);
    }

    @Override
    protected void bindProgramAttributes() {
        super.bindAttribute(0, "position"); // reference back to VAO being created in Loader -> attribute index 0
        super.bindAttribute(1, "textureCoords"); // bind attribute list 1 to var textureCoords
    }
}
