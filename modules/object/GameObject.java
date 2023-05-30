package hiof.gameenigne2d.modules.object;

import hiof.gameenigne2d.modules.object.components.Sprite;
import hiof.gameenigne2d.modules.object.components.SpriteRenderer;
import hiof.gameenigne2d.modules.object.components.StateMachine;
import hiof.gameenigne2d.modules.object.components.Transform;
import org.joml.Vector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameObject {
    private List<Component> Components;
    private Transform transform;
    private int zIndex;
    private boolean isDead = false;
    private boolean lookingRight;

    //Sets up gameobjects by taking in positioning, scale, and it's zIndex (which is the layer the object is put)

    public GameObject() {
        this.zIndex = 0;
        this.Components = new ArrayList<>();
        this.transform = new Transform();
    }

    /**
     * Creates a game object with the following parameters
     * @param x x-axis position
     * @param y y-axis position
     * @param width width of the object
     * @param height width of the object
     * @param zIndex layer that the object sits on
     */
    public GameObject(int x, int y, int width, int height, int zIndex) {
        this.zIndex = zIndex;
        this.Components = new ArrayList<>();
        this.transform = new Transform(new Vector2f(x, y), new Vector2f(width, height));
    }

    /**
     * Gets the component if it has been added to the object
     * @param componentClass the component class (Name.class)
     * @return the component named in the parameter
     */
    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component c : Components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                try {
                    return componentClass.cast(c);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    assert false : "Error: Casting component";
                }
            }
        }
        return null;
    }

    /**
     * Removes the component if it has been added to the object
     * @param componentClass the component class (Name.class)
     */
    public <T extends Component> void removeComponent(Class<T> componentClass) {
        for (int i = 0; i < Components.size(); i++) {
            Component c = Components.get(i);
            if (componentClass.isAssignableFrom(c.getClass())) {
                Components.remove(i);
                return;
            }
        }
    }

    /**
     * Adds the component to the object
     * @param component the component to be added
     */
    public void addComponent(Component component) {
        this.Components.add(component);
        component.gameObject = this;
    }

    /**
     * Adds the sprite object to the GameObject as its texture
     * @param sprite Sprite object to be added to the GameObject
     */
    public void addSprite(Sprite sprite) {
        addComponent(new SpriteRenderer(sprite));
    }

    /**
     * Updates each component that has been added to the GameObject
     * @param deltaTime the frames in between each frame
     */
    public void update(float deltaTime) {
        for (Component component : Components) {
            component.update(deltaTime);
        }
    }

    /**
     * Starts each component that has been added to the GameObject
     */
    public void start() {
        for (int i = 0; i < Components.size(); i++) {
            Components.get(i).start();
        }
    }

    /**
     * @return the GameObjects Transform which contains x, y and width, height
     */
    public Transform getTransform() {
        return transform;
    }

    /**
     * Sets the GameObjets Transform as the given parameter
     * @param transform object of the Transform class which contains x, y and width, height
     */
    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    /**
     * Changes the X position by using += value
     * @param value changes X by this value
     */
    public void changeX(int value) {
        this.transform.getPosition().x += value;
    }

    /**
     * Changes the Y position by using += value
     * @param value changes Y by this value
     */
    public void changeY(int value) {
        this.transform.getPosition().y += value;
    }

    /**
     * @return the z index of the GameObject
     */
    public int zIndex() {
        return this.zIndex;
    }

    public void destroy() {
        this.isDead = true;
        for (Component component : Components) {
            component.destroy();
        }
    }

    /**
     * Creates a rectangle of the object
     * @return returns the rectangle object
     */
    public Rectangle getRectangle() {
        return new Rectangle((int) this.getTransform().getPosition().x, (int) this.getTransform().getPosition().y,
                (int) this.getTransform().getScale().x, (int) this.getTransform().getScale().y);
    }

    /**
     * Creates an inverse width rectangle of the object
     * @return returns the inverse width rectangle object
     */
    public Rectangle getRectangleInverseWidth() {
        return new Rectangle((int) this.getTransform().getPosition().x, (int) this.getTransform().getPosition().y,
                (int) -this.getTransform().getScale().x, (int) this.getTransform().getScale().y);
    }

    /**
     * Triggers the given trigger in the StateMachine component
     * @param trigger the name of the trigger
     */
    public void trigger(String trigger) {
        getComponent(StateMachine.class).trigger(trigger);
    }

    /**
     * @return returns the current state's trigger
     */
    public String getCurrentState() {
        return getComponent(StateMachine.class).getCurrentState().getTitle();
    }

    /**
     * @return returns if the GameObject is looking right
     */
    public boolean isLookingRight() {
        return lookingRight;
    }

    /**
     * @param lookingRight sets if the GameObject is looking corresponding to the boolean value
     */
    public void setLookingRight(boolean lookingRight) {
        this.lookingRight = lookingRight;
    }
}
