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
     * @param x x position
     * @param y y position
     * @param width width of sprite
     * @param height width of sprite
     * @param zIndex layer
     */
    public GameObject(int x, int y, int width, int height, int zIndex) {
        this.zIndex = zIndex;
        this.Components = new ArrayList<>();
        this.transform = new Transform(new Vector2f(x, y), new Vector2f(width, height));
    }

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
     * @param component new SpriteRenderer
     */
    public void addComponent(Component component) {
        this.Components.add(component);
        component.gameObject = this;
    }

    public void addSprite(Sprite sprite) {
        addComponent(new SpriteRenderer(sprite));
    }

    public void update(float deltaTime) {
        for (int i = 0; i < Components.size(); i++) {
            Components.get(i).update(deltaTime);
        }
    }

    public void start() {
        for (int i = 0; i < Components.size(); i++) {
            Components.get(i).start();
        }
    }

    public Transform getTransform() {
        return transform;
    }
    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    /**
     * @param value changes X by this value
     */
    public void changeX(int value) {
        this.transform.getPosition().x += value;
    }

    /**
     * @param value changes Y by this value
     */
    public void changeY(int value) {
        this.transform.getPosition().y += value;
    }

    public int zIndex() {
        return this.zIndex;
    }

    public void destroy() {
        this.isDead = true;
        for (int i = 0; i < Components.size(); i++) {
            Components.get(i).destroy();
        }
    }

    public Rectangle getRectangle() {
        return new Rectangle((int) this.getTransform().getPosition().x, (int) this.getTransform().getPosition().y,
                (int) this.getTransform().getScale().x, (int) this.getTransform().getScale().y);
    }

    public Rectangle getRectangleInverseWidth() {
        return new Rectangle((int) this.getTransform().getPosition().x, (int) this.getTransform().getPosition().y,
                (int) -this.getTransform().getScale().x, (int) this.getTransform().getScale().y);
    }

    public void trigger(String trigger) {
        getComponent(StateMachine.class).trigger(trigger);
    }

    public String getCurrentState() {
        return getComponent(StateMachine.class).getCurrentState().getTitle();
    }

    public boolean isLookingRight() {
        return lookingRight;
    }

    public void setLookingRight(boolean lookingRight) {
        this.lookingRight = lookingRight;
    }
}
