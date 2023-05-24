package HIOF.GameEnigne2D.modules.window;

import HIOF.GameEnigne2D.modules.object.gameobject;

import HIOF.GameEnigne2D.modules.window.renderer.renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class room {

    protected HIOF.GameEnigne2D.modules.window.renderer.renderer renderer = new renderer();
    protected HIOF.GameEnigne2D.modules.window.camera camera;
    private boolean isRunning = false;
    protected List<gameobject> gameobjects = new ArrayList<>();


    //This is a base for all other rooms, it renders objects and lets you add GameObjects to the room. When creating a
    // child of this you have to override update and init function to add your own code
    public room() {

    }

    public abstract void init();
    public abstract void update(float deltaTime);

    public void start() {

        for (gameobject object : gameobjects) {
            object.start();
            this.renderer.add(object);
        }
        isRunning = true;
    }

    /**
     * @param object GameObject
     */
    public void addGameObject(gameobject object) {
        if (!isRunning) {
            gameobjects.add(object);
        } else {
            gameobjects.add(object);
            object.start();
            this.renderer.add(object);
        }
    }

    public HIOF.GameEnigne2D.modules.window.camera getCamera() {
        return camera;
    }

    public void destroyObject(int index) {
        gameobject object = gameobjects.get(index);
        gameobjects.remove(index);
        this.renderer.destroyObject(object);
    }

    public void updateObjects(float deltatime) {
        for (gameobject object : gameobjects) {
            object.update(deltatime);
        }
    }
}
