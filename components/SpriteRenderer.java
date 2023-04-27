package HIOF.GameEnigne2D.components;

import HIOF.GameEnigne2D.modules.Component;
import HIOF.GameEnigne2D.renderer.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {
    private Vector4f color;
    private Sprite sprite;

    public SpriteRenderer(Vector4f color) {
        this.color = color;
        this.sprite = new Sprite(null);
    }

    public SpriteRenderer(Sprite sprite) {
        this.sprite = sprite;
        this.color = new Vector4f(1, 1, 1, 1);
    }

    @Override
    public void start() {

    }

    @Override
    public void update(float deltaTime) {

    }

    public Vector4f getColor() {
        return color;
    }
    public void setColor(Vector4f color) {
        this.color = color;
    }

    public Texture getTexture() {
        return this.sprite.getTexture();
    }

    public Vector2f[] getTextureCords() {
        return this.sprite.getTextureCords();
    }
}
