package HIOF.GameEnigne2D.components;

import HIOF.GameEnigne2D.modules.Component;
import HIOF.GameEnigne2D.renderer.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {
    private Vector4f color;
    private Sprite sprite;
    private Transform lastTransform;
    private boolean isDirty = true;

    //Takes the sprite object and makes sure the object's position and scale is updated per frame
    public SpriteRenderer(Vector4f color) {
        this.color = color;
        this.sprite = new Sprite(null);
    }

    /**
     * @param sprite sprite object
     */
    public SpriteRenderer(Sprite sprite) {
        this.sprite = sprite;
        this.color = new Vector4f(1, 1, 1, 1);
    }

    @Override
    public void start() {
        this.lastTransform = gameObject.getTransform().copy();
    }

    @Override
    public void update(float deltaTime) {
        if (!this.lastTransform.equals(this.gameObject.getTransform())) {
            this.gameObject.getTransform().copy(this.lastTransform);
            isDirty = true;
        }
    }

    public Vector4f getColor() {
        return color;
    }
    public void setColor(Vector4f color) {
        if (!this.color.equals(color)) {
            this.color.set(color);
            isDirty = true;
        }

    }

    public Texture getTexture() {
        return this.sprite.getTexture();
    }

    public Vector2f[] getTextureCords() {
        return this.sprite.getTextureCords();
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        isDirty = true;
    }

    public boolean isDirty() {
        return isDirty;
    }

    public void setClean() {
        this.isDirty = false;
    }
}
