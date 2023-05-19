package HIOF.GameEnigne2D.worlds;

import HIOF.GameEnigne2D.modules.object.components.AnimationState;
import HIOF.GameEnigne2D.modules.object.components.Collision;
import HIOF.GameEnigne2D.modules.object.components.Spritesheet;
import HIOF.GameEnigne2D.modules.object.GameObject;
import HIOF.GameEnigne2D.modules.object.components.StateMachine;
import HIOF.GameEnigne2D.modules.window.Camera;
import HIOF.GameEnigne2D.modules.window.KeyListener;
import HIOF.GameEnigne2D.modules.window.Room;
import HIOF.GameEnigne2D.modules.window.Window;
import HIOF.GameEnigne2D.modules.window.utils.AssetPool;
import org.joml.Vector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;


public class world1 extends Room {

    List<Rectangle> ground;
    public world1() {
        System.out.println("World 1");
    }

    @Override
    public void init() {
        loadResources();
        Window.get().setColor(1.0f, 1.0f, 1.0f);
        this.camera = new Camera(0, 0);

        Spritesheet playerSprites = AssetPool.getSpritesheet("assets/images/spritesheet.png");
        Spritesheet blockSprites = AssetPool.getSpritesheet("assets/images/decorationsAndBlocks.png");

        GameObject obj1 = new GameObject((Window.get().getWidth() / 2), (Window.get().getHeight() / 2), 128, 128, 1);
        obj1.addSprite(playerSprites.getSprite(0));
        this.addGameObject(obj1);
        AnimationState playerStanding = new AnimationState();
        playerStanding.setTitle("Standing");
        playerStanding.addFrame(playerSprites.getSprite(0), 0f);
        AnimationState playerRunning = new AnimationState();
        playerRunning.setTitle("Running");
        playerRunning.addFrame(playerSprites.getSprite(1), 0.23f);
        playerRunning.addFrame(playerSprites.getSprite(2), 0.23f);
        playerRunning.addFrame(playerSprites.getSprite(3), 0.23f);
        playerRunning.setDoesLoop(true);
        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(playerStanding);
        stateMachine.addState(playerRunning);
        stateMachine.setDefaultState(playerStanding.getTitle());
        stateMachine.addStateTrigger(playerStanding.getTitle(), playerRunning.getTitle(), "run");
        stateMachine.addStateTrigger(playerRunning.getTitle(), playerStanding.getTitle(), "stop");
        obj1.addComponent(stateMachine);


        GameObject obj2 = new GameObject((Window.get().getWidth() / 2), (Window.get().getHeight() / 2) - 256, 128, 128, 1);
        obj2.addSprite(blockSprites.getSprite(8));
        this.addGameObject(obj2);
        GameObject obj3 = new GameObject((Window.get().getWidth() / 2) + 128, (Window.get().getHeight() / 2) - 256, 128, 128, 1);
        obj3.addSprite(blockSprites.getSprite(8));
        this.addGameObject(obj3);
        GameObject obj4 = new GameObject((Window.get().getWidth() / 2) + 256, (Window.get().getHeight() / 2) - 256, 128, 128, 1);
        obj4.addSprite(blockSprites.getSprite(8));
        this.addGameObject(obj4);
        GameObject obj5 = new GameObject((Window.get().getWidth() / 2)+ 256, (Window.get().getHeight() / 2) - 256 + 128, 128, 128, 1);
        obj5.addSprite(blockSprites.getSprite(8));
        this.addGameObject(obj5);

        ground = new ArrayList<>();
        ground.add(gameObjects.get(1).getRectangle());
        ground.add(gameObjects.get(2).getRectangle());
        ground.add(gameObjects.get(3).getRectangle());
        ground.add(gameObjects.get(4).getRectangle());
    }

    @Override
    public void update(float deltaTime) {
        if (KeyListener.isKeyPressed(GLFW_KEY_A) && !Collision.intersectsXAtleastOne(gameObjects.get(0).getRectangle(), ground, - 4)) {
            if (gameObjects.get(0).isLookingRight()) {
                gameObjects.get(0).getTransform().setScale(new Vector2f(-gameObjects.get(0).getTransform().getScale().x, gameObjects.get(0).getTransform().getScale().y));
                gameObjects.get(0).setLookingRight(false);
            }
            gameObjects.get(0).changeX(-4);
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_D) && !Collision.intersectsXAtleastOne(gameObjects.get(0).getRectangle(), ground, 4)) {
            if (!gameObjects.get(0).isLookingRight()) {
                gameObjects.get(0).getTransform().setScale(new Vector2f(-gameObjects.get(0).getTransform().getScale().x, gameObjects.get(0).getTransform().getScale().y));
                gameObjects.get(0).setLookingRight(true);
            }
            gameObjects.get(0).changeX(4);
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_W) && !Collision.intersectsYAtleastOne(gameObjects.get(0).getRectangle(), ground, 4)) {
            gameObjects.get(0).changeY(4);
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_S) && !Collision.intersectsYAtleastOne(gameObjects.get(0).getRectangle(), ground, -4)) {
            gameObjects.get(0).changeY(-4);
        }
        if ((!KeyListener.isKeyPressed(GLFW_KEY_A) && !KeyListener.isKeyPressed(GLFW_KEY_D) &&
                !KeyListener.isKeyPressed(GLFW_KEY_W) && !KeyListener.isKeyPressed(GLFW_KEY_S)) && !gameObjects.get(0).getCurrentState().equals("Standing")) {
            gameObjects.get(0).trigger("stop");
        } else if ((KeyListener.isKeyPressed(GLFW_KEY_A) || KeyListener.isKeyPressed(GLFW_KEY_D) ||
                KeyListener.isKeyPressed(GLFW_KEY_W) || KeyListener.isKeyPressed(GLFW_KEY_S)) && !gameObjects.get(0).getCurrentState().equals("Running")) {
            gameObjects.get(0).trigger("run");

        }

        //System.out.println("FPS: " + (1.0f / deltaTime));
        for (GameObject object : this.gameObjects) {
            object.update(deltaTime);
        }
        this.renderer.render();
    }

    private void loadResources() {
        AssetPool.addSpriteSheet("assets/images/spritesheet.png", 16, 16, 26, 0);
        AssetPool.addSpriteSheet("assets/images/decorationsAndBlocks.png", 16, 16, 81, 0);
    }
}
