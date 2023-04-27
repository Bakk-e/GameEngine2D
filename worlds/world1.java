package HIOF.GameEnigne2D.worlds;

import HIOF.GameEnigne2D.components.Sprite;
import HIOF.GameEnigne2D.components.SpriteRenderer;
import HIOF.GameEnigne2D.components.Spritesheet;
import HIOF.GameEnigne2D.components.Transform;
import HIOF.GameEnigne2D.modules.*;
import HIOF.GameEnigne2D.utils.AssetPool;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;


public class world1 extends Room {

    public world1() {
        System.out.println("World 1");
    }

    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f(-250, 0));

        Spritesheet sprites = AssetPool.getSpritesheet("assets/images/spritesheet.png");

        GameObject obj1 = new GameObject(new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
        obj1.addComponent(new SpriteRenderer(sprites.getSprite(0)));
        this.addGameObject(obj1);
    }

    @Override
    public void update(float deltaTime) {
        System.out.println("FPS: " + (1.0f / deltaTime));
        for (GameObject object : this.gameObjects) {
            object.update(deltaTime);
        }
        Window.get().setColor(1.0f, 1.0f, 1.0f);
        this.renderer.render();
    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpriteSheet("assets/images/spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheet.png"), 16,
                        16, 26, 0));
    }
}
