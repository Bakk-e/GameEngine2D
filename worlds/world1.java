package HIOF.GameEnigne2D.worlds;

import HIOF.GameEnigne2D.components.SpriteRenderer;
import HIOF.GameEnigne2D.components.Transform;
import HIOF.GameEnigne2D.modules.*;
import HIOF.GameEnigne2D.utils.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector4f;


public class world1 extends Room {

    public world1() {
        System.out.println("World 1");
    }

    @Override
    public void update(float deltaTime) {
        System.out.println("FPS: " + (1.0f / deltaTime));
        for (GameObject go : this.gameObjects) {
            go.update(deltaTime);
        }
        Window.get().setColor(1.0f, 1.0f, 1.0f);
        this.renderer.render();
    }

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f(-300, -200));

        int xOffset = 10;
        int yOffset = 10;

        float totalWidth = (float)(600 - xOffset * 2);
        float totalHeight = (float)(300 - yOffset * 2);
        float sizeX = totalWidth / 100.0f;
        float sizeY = totalHeight / 100.0f;

        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                float xPos = xOffset + (x * sizeX);
                float yPos = yOffset + (y * sizeY);

                GameObject object = new GameObject(new Transform(new Vector2f(xPos, yPos), new Vector2f(sizeX, sizeY)));
                object.addComponent(new SpriteRenderer(new Vector4f(xPos / totalWidth, yPos / totalHeight, 1, 1)));
                this.addGameObject(object);
            }
        }

        loadResources();
    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
    }
}
