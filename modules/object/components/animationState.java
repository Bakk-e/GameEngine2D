package HIOF.GameEnigne2D.modules.object.components;

import HIOF.GameEnigne2D.modules.window.utils.assetpool;

import java.util.ArrayList;
import java.util.List;

public class animationState {
    private String title;
    private List<frame> animationFrames = new ArrayList<>();
    private static sprite defaultSprite = new sprite();
    private float timeTracker = 0.0f;
    private transient int currentSprite = 0;
    private boolean doesLoop = false;

    public void addFrame(sprite sprite, float frameTime) {
        animationFrames.add(new frame(sprite, frameTime));
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

    public sprite getCurrentSprite() {
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
        for (HIOF.GameEnigne2D.modules.object.components.frame frame : animationFrames) {
            frame.getSprite().setTexture(assetpool.getTexture(frame.getSprite().getTexture().getFilepath()));
        }
    }
}
