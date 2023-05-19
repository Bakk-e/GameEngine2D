package HIOF.GameEnigne2D.modules.object.components;

import HIOF.GameEnigne2D.modules.window.utils.AssetPool;

import java.util.ArrayList;
import java.util.List;

public class AnimationState {
    private String title;
    private List<Frame> animationFrames = new ArrayList<>();
    private static Sprite defaultSprite = new Sprite();
    private float timeTracker = 0.0f;
    private transient int currentSprite = 0;
    private boolean doesLoop = false;

    public void addFrame(Sprite sprite, float frameTime) {
        animationFrames.add(new Frame(sprite, frameTime));
    }

    public void setDoesLoop(boolean doesLoop) {
        this.doesLoop = doesLoop;
    }

    public void update(float deltaTime) {
        if (currentSprite < animationFrames.size()) {
            timeTracker -= deltaTime;
            if (timeTracker <= 0) {
                if (currentSprite != animationFrames.size() - 1 || doesLoop) {
                    currentSprite = (currentSprite + 1) % animationFrames.size();
                }
                timeTracker = animationFrames.get(currentSprite).getFrameTime();
            }
        }
    }

    public Sprite getCurrentSprite() {
        if (currentSprite < animationFrames.size()) {
            return animationFrames.get(currentSprite).getSprite();
        }

        return defaultSprite;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void refreshTextures() {
        for (Frame frame : animationFrames) {
            frame.getSprite().setTexture(AssetPool.getTexture(frame.getSprite().getTexture().getFilepath()));
        }
    }
}
