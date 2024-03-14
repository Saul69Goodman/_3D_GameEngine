package toolbox;

import entities.Camera;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Maths {

    public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale){
        // creating transformation matrix:
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity(); // sets to identity matrix - meaning basically resets matrix
        Matrix4f.translate(translation, matrix, matrix);

        // Rotations:
        Matrix4f.rotate((float)Math.toRadians(rx), new Vector3f(1, 0, 0), matrix, matrix); // rotate matrix by rx in radians around Vector specified as 1,0,0 and store back in matrix again
        Matrix4f.rotate((float)Math.toRadians(ry), new Vector3f(0, 1, 0), matrix, matrix);
        Matrix4f.rotate((float)Math.toRadians(rz), new Vector3f(0, 0, 1), matrix, matrix);

        // Scaling:
        Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);

        return matrix;
    }

    public static Matrix4f createViewMatrix(Camera camera) {

        Matrix4f viewMatrix = new Matrix4f(); // creating new matrix view - this will be crucial for the realistic camera movement
        viewMatrix.setIdentity(); // resetting it

        //Rotating matrix:
        Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix, viewMatrix); //pitch rotation
        Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix); // yaw rotation
        Matrix4f.rotate((float) Math.toRadians(camera.getRoll()), new Vector3f(0, 0, 1), viewMatrix, viewMatrix); // roll rotation

        // Translating matrix:
        Vector3f cameraPos = camera.getPosition(); // get position of camera
        // Negative because we want to move the world into the opposite direction of the camera
        // So it looks like the camera is moving but in reality the world is moving
        Vector3f negativeCameraPos = new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z); // save negative position to variable

        // Using negative camer position translate the viewmatrix -> the world view
        Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
        return viewMatrix;
    }
}
