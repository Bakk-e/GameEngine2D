package HIOF.GameEnigne2D.worlds;

import HIOF.GameEnigne2D.components.SpriteRenderer;
import HIOF.GameEnigne2D.components.Spritesheet;
import HIOF.GameEnigne2D.modules.*;
import HIOF.GameEnigne2D.utils.AssetPool;

import static org.lwjgl.glfw.GLFW.*;


public class world1 extends Room {

    public world1() {
        System.out.println("World 1");
    }

    @Override
    public void init() {
        loadResources();
        Window.get().setColor(1.0f, 1.0f, 1.0f);
        this.camera = new Camera(0, 0);

        Spritesheet sprites = AssetPool.getSpritesheet("assets/images/spritesheet.png");

        GameObject obj1 = new GameObject((Window.get().getWidth() / 2) - (256 / 2), (Window.get().getHeight() / 2) - (256 / 2), 256, 256, 1);
        obj1.addComponent(new SpriteRenderer(sprites.getSprite(0)));
        this.addGameObject(obj1);
    }

    @Override
    public void update(float deltaTime) {
        if (KeyListener.isKeyPressed(GLFW_KEY_D)) {
            gameObjects.get(0).changeX(4);
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_A)) {
            gameObjects.get(0).changeX(-4);
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_W)) {
            gameObjects.get(0).changeY(4);
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_S)) {
            gameObjects.get(0).changeY(-4);
        }
        System.out.println("FPS: " + (1.0f / deltaTime));
        for (GameObject object : this.gameObjects) {
            object.update(deltaTime);
        }
        this.renderer.render();
    }

    private void loadResources() {
        AssetPool.addSpriteSheet("assets/images/spritesheet.png", 16, 16, 26, 0);
    }
}
