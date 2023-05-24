package HIOF.GameEnigne2D.modules.window;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class keylistener {
    private static keylistener instance;
    private boolean keyPressed[] = new boolean[350];


    //This takes in the GLFW keyCodes and processes them
    private keylistener() {

    }

    public static keylistener get() {
        if (keylistener.instance == null) {
            keylistener.instance = new keylistener();
        }

        return keylistener.instance;
    }

    public static void keyCallback(long window, int key, int scanCode, int action, int mods) {
        if (action == GLFW_PRESS) {
            get().keyPressed[key] = true;
        } else if (action == GLFW_RELEASE) {
            get().keyPressed[key] = false;
        }
    }

    public static boolean isKeyPressed(int keyCode) {
        return get().keyPressed[keyCode];
    }
}
