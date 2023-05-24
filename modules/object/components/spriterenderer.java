package HIOF.GameEnigne2D.modules.object.components;

import HIOF.GameEnigne2D.modules.object.component;
import HIOF.GameEnigne2D.modules.window.renderer.texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class spriterenderer extends component {
    private Vector4f color;
    private HIOF.GameEnigne2D.modules.object.components.sprite sprite;
    private transform lastTransform;
    private boolean isDirty = true;

    //Takes the sprite object and makes sure the object's position and scale is updated per frame
    public spriterenderer(Vector4f color) {
        this.color = color;
        this.sprite = new sprite(null);
    }

    /**
     * @param sprite sprite object
     */
    public spriterenderer(HIOF.GameEnigne2D.modules.object.components.sprite sprite) {
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

    public texture getTexture() {
        return this.sprite.getTexture();
    }
    public void setTexture(texture texture) {
        this.sprite.setTexture(texture);
    }

    public Vector2f[] getTextureCords() {
        return this.sprite.getTextureCords();
    }

    public void setSprite(HIOF.GameEnigne2D.modules.object.components.sprite sprite) {
        this.sprite = sprite;
        isDirty = true;
    }

    public boolean isDirty() {
        return isDirty;
    }

    public void setClean() {
        this.isDirty = false;
    }

    public void setDirty() {
        this.isDirty = true;
    }
}
