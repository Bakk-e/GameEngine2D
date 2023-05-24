package HIOF.GameEnigne2D.modules.object.components;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class spritesheet {
    private HIOF.GameEnigne2D.modules.window.renderer.texture texture;
    private List<sprite> sprites;


    //Takes a sprite sheet and inputs of the size of each sprite and then maps out each sprite for later use
    public spritesheet(HIOF.GameEnigne2D.modules.window.renderer.texture texture, int spriteWidth, int spriteHeight, int numSprites, int spacing) {
        this.sprites = new ArrayList<>();
        this.texture = texture;
        int currentX = 0;
        int currentY = texture.getHeight() - spriteHeight;
        for (int i = 0; i < numSprites; i++) {
            float topY = (currentY + spriteHeight) / (float)texture.getHeight();
            float rightX = (currentX + spriteWidth) / (float)texture.getWidth();
            float leftX = currentX / (float)texture.getWidth();
            float bottomY = currentY / (float)texture.getHeight();

            Vector2f[] textureCords = {
                    new Vector2f(rightX, topY),
                    new Vector2f(rightX, bottomY),
                    new Vector2f(leftX, bottomY),
                    new Vector2f(leftX, topY)
            };
            sprite sprite = new sprite();
            sprite.setTexture(this.texture);
            sprite.setTextureCords(textureCords);
            this.sprites.add(sprite);

            currentX += spriteWidth + spacing;
            if (currentX >= texture.getWidth()) {
                currentX = 0;
                currentY -= spriteHeight + spacing;
            }
        }
    }

    /**
     * @param index index of the sprite
     * @return sprite object
     */
    public sprite getSprite(int index) {
        return this.sprites.get(index);
    }
}
