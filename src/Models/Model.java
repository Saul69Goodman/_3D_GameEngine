package Models;

public class Model {

    private int vaoID;
    private int vertexCount;

    public Model(int vaoID, int vertexCount){
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }

    /**
     * Get the VAO ID.
     *
     * @return the VAO ID
     */
    public int getVaoID() {
        return vaoID;
    }

    /**
     * Get the vertex count.
     *
     * @return         	the vertex count
     */
    public int getVertexCount() {
        return vertexCount;
    }
}
