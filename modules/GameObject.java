package HIOF.GameEnigne2D.modules;

import HIOF.GameEnigne2D.components.Sprite;
import HIOF.GameEnigne2D.components.SpriteRenderer;
import HIOF.GameEnigne2D.components.Transform;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
    private List<Component> components;
    private Transform transform;
    private int zIndex;

    //Sets up gameobjects by taking in positioning, scale, and it's zIndex (which is the layer the object is put)

    public GameObject() {
        this.zIndex = 0;
        this.components = new ArrayList<>();
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
        this.components = new ArrayList<>();
        this.transform = new Transform(new Vector2f(x, y), new Vector2f(width, height));
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component c : components) {
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
        for (int i = 0; i < components.size(); i++) {
            Component c = components.get(i);
            if (componentClass.isAssignableFrom(c.getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    /**
     * @param component new SpriteRenderer
     */
    public void addComponent(Component component) {
        this.components.add(component);
        component.gameObject = this;
    }

    public void addSprite(Sprite sprite) {
        addComponent(new SpriteRenderer(sprite));
    }

    public void update(float deltaTime) {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).update(deltaTime);
        }
    }

    public void start() {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).start();
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
}
