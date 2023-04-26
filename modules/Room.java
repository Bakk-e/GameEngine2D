package HIOF.GameEnigne2D.modules;

import HIOF.GameEnigne2D.renderer.Renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class Room {

    protected Renderer renderer = new Renderer();
    protected Camera camera;
    private boolean isRunning = false;
    protected List<GameObject> gameObjects = new ArrayList<>();

    public Room() {

    }

    public abstract void update(float dT);

    public abstract void init();

    public void start() {

        for (GameObject go : gameObjects) {
            go.start();
            this.renderer.add(go);
        }
        isRunning = true;
    }

    public void addGameObject(GameObject object) {
        if (!isRunning) {
            gameObjects.add(object);
        } else {
            gameObjects.add(object);
            object.start();
            this.renderer.add(object);
        }
    }

    public Camera getCamera() {
        return camera;
    }
}
