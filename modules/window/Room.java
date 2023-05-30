package hiof.gameenigne2d.modules.window;

import hiof.gameenigne2d.modules.object.GameObject;

import hiof.gameenigne2d.modules.window.renderer.Renderer;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a base for all other rooms, it renders objects and lets you add GameObjects to the room. When creating a
 * child of this you have to override update and init function to add your own code
 */
public abstract class Room {

    protected Renderer renderer = new Renderer();
    protected Camera camera;
    private boolean isRunning = false;
    protected List<GameObject> GameObjects = new ArrayList<>();


    public Room() {

    }

    /**
     * This is a function that is called when the room is created and can be used to initialize GameObjects and other things for that room
     */
    public abstract void init();

    /**
     * This function runs for every frame in the window. So all the textures should be updated in here
     * @param deltaTime Time in between each frame
     */
    public abstract void update(float deltaTime);

    /**
     * This starts the room and then starts all the objects that are in the GameObjects list, and adds them to the renderer
     */
    public void start() {

        for (GameObject object : GameObjects) {
            object.start();
            this.renderer.add(object);
        }
        isRunning = true;
    }

    /**
     * This function adds GameObjects to a list so that they all can easily be updated at once in Update()
     * @param object GameObject
     */
    public void addGameObject(GameObject object) {
        if (!isRunning) {
            GameObjects.add(object);
        } else {
            GameObjects.add(object);
            object.start();
            this.renderer.add(object);
        }
    }

    /**
     * @return the camera that is set to the Room
     */
    public Camera getCamera() {
        return camera;
    }

    /**
     * This function destroys/deletes the object from the room
     * @param index of the object you wish to delete from GameObjects list
     */
    public void destroyObject(int index) {
        GameObject object = GameObjects.get(index);
        GameObjects.remove(index);
        this.renderer.destroyObject(object);
    }

    /**
     * This function goes through each object in the GameObjects list and calls the update function
     * @param deltaTime Time in between each frame
     */
    public void updateObjects(float deltaTime) {
        for (GameObject object : GameObjects) {
            object.update(deltaTime);
        }
    }
}
