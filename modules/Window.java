package HIOF.GameEnigne2D.modules;

import HIOF.GameEnigne2D.utils.Time;
import HIOF.GameEnigne2D.worlds.world1;
import HIOF.GameEnigne2D.worlds.world2;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    protected int width;
    protected int height;
    protected String title;
    protected double fps;
    protected long glfwWindow;
    protected float r, g, b, alpha;
    protected static Window window = null;
    protected static Room currentRoom;

    protected Window() {
        this.r = 0;
        this.g = 0;
        this.b = 0;
        this.alpha = 1;
    }

    protected Window(int width, int height, String title, double fps) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.fps = fps;
        this.r = 0;
        this.g = 0;
        this.b = 0;
        this.alpha = 1;
    }

    public static Window get() {
        return Window.window;
    }

    public static Window create() {
        if (Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }

    public static Window create(int width, int height, String title, double fps) {
        if (Window.window == null) {
            Window.window = new Window(width, height, title, fps);
        }
        return Window.window;
    }

    public void changeRoom(int newRoom) {
        switch (newRoom) {
            case 0:
                currentRoom = new world1();
                //currentRoom.init();
                break;
            case 1:
                currentRoom = new world2();
                break;
            default:
                assert false : "Room not found '" + newRoom + "'";
                break;
        }
    }

    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public float getR() {
        return r;
    }
    public void setR(float r) {
        this.r = r;
    }
    public void changeR(float value) {
        this.r += value;
    }

    public float getG() {
        return g;
    }
    public void setG(float g) {
        this.g = g;
    }
    public void changeG(float value) {
        this.g += value;
    }

    public float getB() {
        return b;
    }
    public void setB(float b) {
        this.b = b;
    }
    public void changeB(float value) {
        this.b += value;
    }

    public float getAlpha() {
        return alpha;
    }
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
    public void changeAlpha(float value) {
        this.alpha += value;
    }

    public void setColor(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void run() {
        System.out.println("Starting using LWJGL" + Version.getVersion());

        init();
        loop();

        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        glfwMakeContextCurrent(glfwWindow);
        glfwSwapInterval(1);

        glfwShowWindow(glfwWindow);

        GL.createCapabilities();
    }

    public void setup() {

    }

    public void update(float dT) {

    }

    public void loop() {
        setup();
        double lastTime = glfwGetTime();
        float beginTime = Time.getTime();
        float endTime;
        float dT = -1.0f;

        while (!glfwWindowShouldClose(glfwWindow)) {
            glfwPollEvents();

            glClearColor(r, g, b, alpha);
            glClear(GL_COLOR_BUFFER_BIT);

            update(dT);

            glfwSwapBuffers(glfwWindow);

            endTime = Time.getTime();
            dT = endTime - beginTime;
            beginTime = endTime;
            while (glfwGetTime() < lastTime  + 1.0/fps) {

            }
            lastTime += 1.0/fps;
        }
    }
}
