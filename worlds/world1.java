package hiof.gameenigne2d.worlds;

import hiof.gameenigne2d.modules.object.components.AnimationState;
import hiof.gameenigne2d.modules.object.components.Collision;
import hiof.gameenigne2d.modules.object.components.Spritesheet;
import hiof.gameenigne2d.modules.object.GameObject;
import hiof.gameenigne2d.modules.object.components.StateMachine;
import hiof.gameenigne2d.modules.window.Camera;
import hiof.gameenigne2d.modules.window.KeyListener;
import hiof.gameenigne2d.modules.window.Room;
import hiof.gameenigne2d.modules.window.Window;
import hiof.gameenigne2d.modules.window.renderer.Sound;
import hiof.gameenigne2d.modules.window.utils.AssetPool;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;


public class world1 extends Room {

    List<GameObject> ground;
    int once = 0;
    public world1() {
        System.out.println("World 1");
    }

    @Override
    public void init() {
        loadResources();
        Window.get().setColor(1.0f, 1.0f, 1.0f);
        this.camera = new Camera(0, 0);

        Spritesheet playerSprites = AssetPool.getSpritesheet("assets/images/marioSpritesheet.png");
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

        obj1.setLookingRight(true);


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
        ground.add(GameObjects.get(1));
        ground.add(GameObjects.get(2));
        ground.add(GameObjects.get(3));
        ground.add(GameObjects.get(4));
    }

    @Override
    public void update(float deltaTime) {
        if (KeyListener.isKeyPressed(GLFW_KEY_A) && !Collision.intersectsXAtleastOne(GameObjects.get(0).getRectangleInverseWidth(), ground, -4)) {
            if (GameObjects.get(0).isLookingRight()) {
                GameObjects.get(0).getTransform().setScale(new Vector2f(-GameObjects.get(0).getTransform().getScale().x, GameObjects.get(0).getTransform().getScale().y));
                GameObjects.get(0).setLookingRight(false);
            }
            GameObjects.get(0).changeX(-4);
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_D) && !Collision.intersectsXAtleastOne(GameObjects.get(0).getRectangle(), ground, 4)) {
            if (!GameObjects.get(0).isLookingRight()) {
                GameObjects.get(0).getTransform().setScale(new Vector2f(-GameObjects.get(0).getTransform().getScale().x, GameObjects.get(0).getTransform().getScale().y));
                GameObjects.get(0).setLookingRight(true);
            }
            GameObjects.get(0).changeX(4);
        }
        if (GameObjects.get(0).isLookingRight()) {
            if (KeyListener.isKeyPressed(GLFW_KEY_W) && !Collision.intersectsYAtleastOne(GameObjects.get(0).getRectangle(), ground, 4)) {
                GameObjects.get(0).changeY(4);
            }
            if (KeyListener.isKeyPressed(GLFW_KEY_S) && !Collision.intersectsYAtleastOne(GameObjects.get(0).getRectangle(), ground, -4)) {
                GameObjects.get(0).changeY(-4);
            }
        } else {
            if (KeyListener.isKeyPressed(GLFW_KEY_W) && !Collision.intersectsYAtleastOne(GameObjects.get(0).getRectangleInverseWidth(), ground, 4)) {
                GameObjects.get(0).changeY(4);
            }
            if (KeyListener.isKeyPressed(GLFW_KEY_S) && !Collision.intersectsYAtleastOne(GameObjects.get(0).getRectangleInverseWidth(), ground, -4)) {
                GameObjects.get(0).changeY(-4);
            }
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_SPACE) && once == 0) {
            once = 1;
            Sound testSound = AssetPool.getSound("assets/sounds/1-up.ogg");
            assert testSound != null;
            testSound.play();
        }

        if ((!KeyListener.isKeyPressed(GLFW_KEY_A) && !KeyListener.isKeyPressed(GLFW_KEY_D) &&
                !KeyListener.isKeyPressed(GLFW_KEY_W) && !KeyListener.isKeyPressed(GLFW_KEY_S)) && !GameObjects.get(0).getCurrentState().equals("Standing")) {
            GameObjects.get(0).trigger("stop");
        } else if ((KeyListener.isKeyPressed(GLFW_KEY_A) || KeyListener.isKeyPressed(GLFW_KEY_D) ||
                KeyListener.isKeyPressed(GLFW_KEY_W) || KeyListener.isKeyPressed(GLFW_KEY_S)) && !GameObjects.get(0).getCurrentState().equals("Running")) {
            GameObjects.get(0).trigger("run");
        }

        //System.out.println("FPS: " + (1.0f / deltaTime));
        for (GameObject object : this.GameObjects) {
            object.update(deltaTime);
        }
        this.renderer.render();
    }

    private void loadResources() {
        AssetPool.addSpriteSheet("assets/images/marioSpritesheet.png", 16, 16, 26, 0);
        AssetPool.addSpriteSheet("assets/images/decorationsAndBlocks.png", 16, 16, 81, 0);
        AssetPool.addSound("assets/sounds/1-up.ogg", false);
    }
}
