package HIOF.GameEnigne2D.components;

import HIOF.GameEnigne2D.renderer.Texture;
import org.joml.Vector2f;

public class Sprite {
    private Texture texture;
    private Vector2f[] textureCords;

    public Sprite(Texture texture) {
        this.texture = texture;
        textureCords = new Vector2f[] {
                new Vector2f(1, 1),
                new Vector2f(1, 0),
                new Vector2f(0, 0),
                new Vector2f(0, 1)
        };
    }

    public Sprite(Texture texture, Vector2f[] textureCords) {
        this.texture = texture;
        this.textureCords = textureCords;
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector2f[] getTextureCords() {
        return this.textureCords;
    }
}
