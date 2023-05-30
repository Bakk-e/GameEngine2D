package hiof.gameenigne2d.modules.window;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.openal.ALC10.*;
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
    protected long audioContext;
    protected long audioDevice;


    /**
     * This creates a basic window so that the user doesn't have to do it themselves. To actually
     * add code you should create a child object and override changeRoom, setup and update
     */
    protected Window() {
        this.r = 0;
        this.g = 0;
        this.b = 0;
        this.alpha = 1;
    }

    /**
     * This creates a basic window so that the user doesn't have to do it themselves. To actually
     * add code you should create a child object and override changeRoom, setup and update
     */
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

    /**
     * @return returns the current Window object
     */
    public static Window get() {
        return Window.window;
    }

    /**
     * @return returns the saved Window object, if one does not exist it creates a new one
     */
    public static Window create() {
        if (Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }

    /**
     * @return returns the saved Window object, if one does not exist it creates a new one
     */
    public static Window create(int width, int height, String title, double fps) {
        if (Window.window == null) {
            Window.window = new Window(width, height, title, fps);
        }
        return Window.window;
    }

    /**
     * @return returns the current room
     */
    public static Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Sets the current room to the parameter
     * @param room room you want to set as current
     */
    public static void setCurrentRoom(Room room) {
        Window.currentRoom = room;
        Window.currentRoom.start();
        Window.currentRoom.init();
    }

    /**
     * @return returns the width of the Window
     */
    public int getWidth() {
        return width;
    }
    /**
     * Sets the width of the Window
     * @param width in pixels
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return returns the height of the Window
     */
    public int getHeight() {
        return height;
    }
    /**
     * Sets the height of the Window
     * @param height in pixels
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return returns the title of the Window
     */
    public String getTitle() {
        return title;
    }
    /**
     * Sets the title of the Window
     * @param title string
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return returns the red value of the background color of the Window
     */
    public float getR() {
        return r;
    }
    /**
     * Sets the red value of the background color of the Window
     * @param r red value
     */
    public void setR(float r) {
        this.r = r;
    }
    /**
     * Changes the red value of the background color of the Window by using += value
     * @param value red value
     */
    public void changeR(float value) {
        this.r += value;
    }

    /**
     * @return returns the green value of the background color of the Window
     */
    public float getG() {
        return g;
    }
    /**
     * Sets the green value of the background color of the Window
     * @param g green value
     */
    public void setG(float g) {
        this.g = g;
    }

    /**
     * Changes the green value of the background color of the Window by using += value
     * @param value green value
     */
    public void changeG(float value) {
        this.g += value;
    }

    /**
     * @return returns the blue value of the background color of the Window
     */
    public float getB() {
        return b;
    }
    /**
     * Sets the blue value of the background color of the Window
     * @param b blue value
     */
    public void setB(float b) {
        this.b = b;
    }
    /**
     * Changes the blue value of the background color of the Window by using += value
     * @param value blue value
     */
    public void changeB(float value) {
        this.b += value;
    }

    /**
     * @return returns the alpha value of the background color of the Window
     */
    public float getAlpha() {
        return alpha;
    }
    /**
     * Sets the alpha value of the background color of the Window
     * @param alpha alpha value
     */
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
    /**
     * Changes the alpha value of the background color of the Window by using += value
     * @param value alpha value
     */
    public void changeAlpha(float value) {
        this.alpha += value;
    }

    /**
     * Sets the color of the background as the rgb parameters
     * @param r red value
     * @param g green value
     * @param b blue value
     */
    public void setColor(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * Runs the necessary function to build and start a Window
     */
    public void run() {
        System.out.println("Starting using LWJGL" + Version.getVersion());

        init();
        loop();

        alcDestroyContext(audioContext);
        alcCloseDevice(audioDevice);

        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }


    //Initializes everything that is needed to make a window function and creates key and mouse listeners to the window
    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        ///glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);

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

        String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        audioDevice = alcOpenDevice(defaultDeviceName);
        int[] attributes = {0};
        audioContext = alcCreateContext(audioDevice, attributes);
        alcMakeContextCurrent(audioContext);
        ALCCapabilities alcCapabilities = ALC.createCapabilities(audioDevice);
        ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);
        if (!alCapabilities.OpenAL10) {
            assert false : "Audio library not supported";
        }

        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
    }

    /**
     * This is for code that runs at the creation of the Window, runs right before the update loop starts
     */
    public void setup() {

    }

    /**
     * This is for code that runs every frame of the Window
     * @param deltaTime time in between frames
     */
    public void update(float deltaTime) {

    }


    /**
     * Creates a simple loop and limits the fps to what ever the input is
     */
    public void loop() {
        setup();
        double lastTime = glfwGetTime();
        float beginTime = (float)glfwGetTime();
        float endTime;
        float deltaTime = -1.0f;

        while (!glfwWindowShouldClose(glfwWindow)) {
            glfwPollEvents();

            glClearColor(r, g, b, alpha);
            glClear(GL_COLOR_BUFFER_BIT);

            update(deltaTime);

            glfwSwapBuffers(glfwWindow);

            endTime = (float)glfwGetTime();
            deltaTime = endTime - beginTime;
            beginTime = endTime;

            while (glfwGetTime() < lastTime  + 1.0/fps) {

            }
            lastTime += 1.0/fps;
        }
    }
}
