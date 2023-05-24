package HIOF.GameEnigne2D.modules.object.components;

import org.joml.Vector2f;

public class sprite {
    private HIOF.GameEnigne2D.modules.window.renderer.texture texture;
    private Vector2f[] textureCords;

    public sprite() {

    }

    //Creates a new texture and maps its vertices in the correct order
    public sprite(HIOF.GameEnigne2D.modules.window.renderer.texture texture) {
        this.texture = texture;
        textureCords = new Vector2f[] {
                new Vector2f(1, 1),
                new Vector2f(1, 0),
                new Vector2f(0, 0),
                new Vector2f(0, 1)
        };
    }

    public sprite(HIOF.GameEnigne2D.modules.window.renderer.texture texture, Vector2f[] textureCords) {
        this.texture = texture;
        this.textureCords = textureCords;
    }

    public HIOF.GameEnigne2D.modules.window.renderer.texture getTexture() {
        return texture;
    }

    public void setTexture(HIOF.GameEnigne2D.modules.window.renderer.texture texture) {
        this.texture = texture;
    }

    public Vector2f[] getTextureCords() {
        return this.textureCords;
    }

    public void setTextureCords(Vector2f[] textureCords) {
        this.textureCords = textureCords;
    }
}
