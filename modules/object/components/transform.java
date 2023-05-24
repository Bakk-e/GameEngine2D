package HIOF.GameEnigne2D.modules.object.components;

import org.joml.Vector2f;

public class transform {
    private Vector2f position;
    private Vector2f scale;
    private float rotation = 0.0f;


    //This creates the position and scale for a GameObject, which then is used to map where each object is on the screen and it's size
    public transform() {
        init(new Vector2f(), new Vector2f());
    }

    public transform(Vector2f position) {
        init(position, new Vector2f());
    }

    public transform(Vector2f position, Vector2f scale) {
        init(position, scale);
    }

    public void init(Vector2f position, Vector2f scale) {
        this.position = position;
        this.scale = scale;
    }

    public Vector2f getPosition() {
        return position;
    }
    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public Vector2f getScale() {
        return scale;
    }
    public void setScale(Vector2f scale) {
        this.scale = scale;
    }

    public transform copy() {
        return new transform(new Vector2f(this.position), new Vector2f(this.scale));
    }

    public void copy(transform transform) {
        transform.position.set(this.position);
        transform.scale.set(this.scale);
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (!(object instanceof transform)) return false;

        transform transform = (HIOF.GameEnigne2D.modules.object.components.transform)object;
        return transform.position.equals(this.position) && transform.scale.equals(this.scale);
    }
}
