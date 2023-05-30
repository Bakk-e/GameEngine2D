package hiof.gameenigne2d.modules.window;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {
    private Matrix4f projectionMatrix, viewMatrix;
    private Vector2f position;

    /**
     * Creates a new camera/viewport as big as the Window
     * @param x x-axis position
     * @param y y-axis position
     */
    public Camera(int x, int y) {
        this.position = new Vector2f(x, y);
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        adjustProjection(Window.window.getWidth(), Window.window.getHeight());
    }


    /**
     * Creates a new camera/viewport
     * @param x x-axis position
     * @param y y-axis position
     * @param width in pixels
     * @param height in pixels
     */
    //Camera is given its projection and its view, here it is set so that it only does 2 dimensions. But can be
    // transfigured to do 3 dimensions with some work since its using OpenGL
    public Camera(int x, int y, int width , int height) {
        this.position = new Vector2f(x, y);
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        adjustProjection(width, height);
    }

    /**
     * Adjusts the projection matrix for the camera
     * @param width in pixels
     * @param height in pixels
     */
    public void adjustProjection(int width, int height) {
        projectionMatrix.identity();
        projectionMatrix.ortho(0.0f, (float)width, 0.0f, (float)height, 0.0f, 100.0f);
    }

    public Matrix4f getViewMatrix() {
        Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
        Vector3f cameraUp = new Vector3f(0.0f, 1.0f,0.0f);
        this.viewMatrix.identity();
        viewMatrix.lookAt(new Vector3f(position.x, position.y, 20.0f),
                cameraFront.add(position.x, position.y, 0.0f),
                cameraUp);

        return this.viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }

    /**
     * @param value what to change the x value by
     */
    public void changeX(int value) {
        position = new Vector2f(position.x + value, position.y);
    }

    /**
     * @param value what to change the x value by
     */
    public void changeY(int value) {
        position = new Vector2f(position.x, position.y + value);
    }

    public void setX(float x) {
        position = new Vector2f((int)x, position.y);
    }

    public void setY(float y) {
        position = new Vector2f(position.x, (int)y);
    }
}
