package hiof.gameenigne2d.modules.object.components;

import hiof.gameenigne2d.modules.window.renderer.Texture;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Spritesheet {
    private Texture texture;
    private List<Sprite> Sprites;


    /**
     * Takes a sprite sheet and inputs of the size of each sprite and then maps out each sprite for later use
     * @param texture the image you want to give it
     * @param spriteWidth each sprites width
     * @param spriteHeight each sprites height
     * @param numSprites the number of sprites in the image
     * @param spacing in between the sprites
     */
    public Spritesheet(Texture texture, int spriteWidth, int spriteHeight, int numSprites, int spacing) {
        this.Sprites = new ArrayList<>();
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
            Sprite sprite = new Sprite();
            sprite.setTexture(this.texture);
            sprite.setTextureCords(textureCords);
            this.Sprites.add(sprite);

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
    public Sprite getSprite(int index) {
        return this.Sprites.get(index);
    }
}
