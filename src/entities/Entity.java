package entities;

import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

public class Entity {

    private TexturedModel model; // model
    private Vector3f position; // postions needed for entity is Vector3f class
    private float rotX, rotY, rotZ; // if need be to rotate
    private float scale; // if need be to scale

    public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }

    // this is all possible because we changed vertexShader so that everytime it is rendered we don't have to render the object again at a new position.
    // instead uniform values / transformation matrix is used and multiplied with the vector which represents the position

    public void increasePosition(float dx, float dy, float dz){ // function able to move entity in the world, takes xyz
        this.position.x += dx; // simply adds the dx value to the already position of x
        this.position.y += dy;
        this.position.z += dz;
    }

    public void increaseRotation(float dx, float dy, float dz) { // function able to rotate entity in the world
        this.rotX += dx; // simply adds the dx value to rotationX
        this.rotY += dy;
        this.rotZ += dz;
    }


    public void setModel(TexturedModel model) {
        this.model = model;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public TexturedModel getModel() {
        return model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getRotX() {
        return rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public float getScale() {
        return scale;
    }
}
