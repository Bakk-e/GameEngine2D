package HIOF.GameEnigne2D.worlds;

import HIOF.GameEnigne2D.modules.object.components.animationState;
import HIOF.GameEnigne2D.modules.object.components.collision;
import HIOF.GameEnigne2D.modules.object.components.spritesheet;
import HIOF.GameEnigne2D.modules.object.gameobject;
import HIOF.GameEnigne2D.modules.object.components.statemachine;
import HIOF.GameEnigne2D.modules.window.camera;
import HIOF.GameEnigne2D.modules.window.keylistener;
import HIOF.GameEnigne2D.modules.window.room;
import HIOF.GameEnigne2D.modules.window.window;
import HIOF.GameEnigne2D.modules.window.renderer.sound;
import HIOF.GameEnigne2D.modules.window.utils.assetpool;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;


public class world1 extends room {

    List<gameobject> ground;
    int once = 0;
    public world1() {
        System.out.println("World 1");
    }

    @Override
    public void init() {
        loadResources();
        window.get().setColor(1.0f, 1.0f, 1.0f);
        this.camera = new camera(0, 0);

        spritesheet playerSprites = assetpool.getSpritesheet("assets/images/marioSpritesheet.png");
        spritesheet blockSprites = assetpool.getSpritesheet("assets/images/decorationsAndBlocks.png");

        gameobject obj1 = new gameobject((window.get().getWidth() / 2), (window.get().getHeight() / 2), 128, 128, 1);
        obj1.addSprite(playerSprites.getSprite(0));
        this.addGameObject(obj1);

        animationState playerStanding = new animationState();
        playerStanding.setTitle("Standing");
        playerStanding.addFrame(playerSprites.getSprite(0), 0f);
        animationState playerRunning = new animationState();
        playerRunning.setTitle("Running");
        playerRunning.addFrame(playerSprites.getSprite(1), 0.23f);
        playerRunning.addFrame(playerSprites.getSprite(2), 0.23f);
        playerRunning.addFrame(playerSprites.getSprite(3), 0.23f);
        playerRunning.setDoesLoop(true);
        statemachine stateMachine = new statemachine();
        stateMachine.addState(playerStanding);
        stateMachine.addState(playerRunning);
        stateMachine.setDefaultState(playerStanding.getTitle());
        stateMachine.addStateTrigger(playerStanding.getTitle(), playerRunning.getTitle(), "run");
        stateMachine.addStateTrigger(playerRunning.getTitle(), playerStanding.getTitle(), "stop");
        obj1.addComponent(stateMachine);

        obj1.setLookingRight(true);


        gameobject obj2 = new gameobject((window.get().getWidth() / 2), (window.get().getHeight() / 2) - 256, 128, 128, 1);
        obj2.addSprite(blockSprites.getSprite(8));
        this.addGameObject(obj2);
        gameobject obj3 = new gameobject((window.get().getWidth() / 2) + 128, (window.get().getHeight() / 2) - 256, 128, 128, 1);
        obj3.addSprite(blockSprites.getSprite(8));
        this.addGameObject(obj3);
        gameobject obj4 = new gameobject((window.get().getWidth() / 2) + 256, (window.get().getHeight() / 2) - 256, 128, 128, 1);
        obj4.addSprite(blockSprites.getSprite(8));
        this.addGameObject(obj4);
        gameobject obj5 = new gameobject((window.get().getWidth() / 2)+ 256, (window.get().getHeight() / 2) - 256 + 128, 128, 128, 1);
        obj5.addSprite(blockSprites.getSprite(8));
        this.addGameObject(obj5);

        ground = new ArrayList<>();
        ground.add(gameobjects.get(1));
        ground.add(gameobjects.get(2));
        ground.add(gameobjects.get(3));
        ground.add(gameobjects.get(4));
    }

    @Override
    public void update(float deltaTime) {
        if (keylistener.isKeyPressed(GLFW_KEY_A) && !collision.intersectsXAtleastOne(gameobjects.get(0).getRectangleInverseWidth(), ground, -4)) {
            if (gameobjects.get(0).isLookingRight()) {
                gameobjects.get(0).getTransform().setScale(new Vector2f(-gameobjects.get(0).getTransform().getScale().x, gameobjects.get(0).getTransform().getScale().y));
                gameobjects.get(0).setLookingRight(false);
            }
            gameobjects.get(0).changeX(-4);
        }
        if (keylistener.isKeyPressed(GLFW_KEY_D) && !collision.intersectsXAtleastOne(gameobjects.get(0).getRectangle(), ground, 4)) {
            if (!gameobjects.get(0).isLookingRight()) {
                gameobjects.get(0).getTransform().setScale(new Vector2f(-gameobjects.get(0).getTransform().getScale().x, gameobjects.get(0).getTransform().getScale().y));
                gameobjects.get(0).setLookingRight(true);
            }
            gameobjects.get(0).changeX(4);
        }
        if (gameobjects.get(0).isLookingRight()) {
            if (keylistener.isKeyPressed(GLFW_KEY_W) && !collision.intersectsYAtleastOne(gameobjects.get(0).getRectangle(), ground, 4)) {
                gameobjects.get(0).changeY(4);
            }
            if (keylistener.isKeyPressed(GLFW_KEY_S) && !collision.intersectsYAtleastOne(gameobjects.get(0).getRectangle(), ground, -4)) {
                gameobjects.get(0).changeY(-4);
            }
        } else {
            if (keylistener.isKeyPressed(GLFW_KEY_W) && !collision.intersectsYAtleastOne(gameobjects.get(0).getRectangleInverseWidth(), ground, 4)) {
                gameobjects.get(0).changeY(4);
            }
            if (keylistener.isKeyPressed(GLFW_KEY_S) && !collision.intersectsYAtleastOne(gameobjects.get(0).getRectangleInverseWidth(), ground, -4)) {
                gameobjects.get(0).changeY(-4);
            }
        }
        if (keylistener.isKeyPressed(GLFW_KEY_SPACE) && once == 0) {
            once = 1;
            sound testSound = assetpool.getSound("assets/sounds/1-up.ogg");
            assert testSound != null;
            testSound.play();
        }

        if ((!keylistener.isKeyPressed(GLFW_KEY_A) && !keylistener.isKeyPressed(GLFW_KEY_D) &&
                !keylistener.isKeyPressed(GLFW_KEY_W) && !keylistener.isKeyPressed(GLFW_KEY_S)) && !gameobjects.get(0).getCurrentState().equals("Standing")) {
            gameobjects.get(0).trigger("stop");
        } else if ((keylistener.isKeyPressed(GLFW_KEY_A) || keylistener.isKeyPressed(GLFW_KEY_D) ||
                keylistener.isKeyPressed(GLFW_KEY_W) || keylistener.isKeyPressed(GLFW_KEY_S)) && !gameobjects.get(0).getCurrentState().equals("Running")) {
            gameobjects.get(0).trigger("run");
        }

        //System.out.println("FPS: " + (1.0f / deltaTime));
        for (gameobject object : this.gameobjects) {
            object.update(deltaTime);
        }
        this.renderer.render();
    }

    private void loadResources() {
        assetpool.addSpriteSheet("assets/images/marioSpritesheet.png", 16, 16, 26, 0);
        assetpool.addSpriteSheet("assets/images/decorationsAndBlocks.png", 16, 16, 81, 0);
        assetpool.addSound("assets/sounds/1-up.ogg", false);
    }
}
