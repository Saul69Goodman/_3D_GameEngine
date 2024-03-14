package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    private Vector3f position = new Vector3f(0, 0, 0);
    // Nick, Gier und Roll
    private float pitch;
    private float yaw;

    public void move(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            position.z -= 0.02f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            position.z += 0.02f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            position.x -= 0.02f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            position.x += 0.02f;
        }
    }
    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

    private float roll;
}
