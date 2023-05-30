package hiof.gameenigne2d.modules.object.components;

import hiof.gameenigne2d.modules.window.utils.AssetPool;

import java.util.ArrayList;
import java.util.List;

public class AnimationState {
    private String title;
    private List<Frame> animationFrames = new ArrayList<>();
    private static Sprite defaultSprite = new Sprite();
    private float timeTracker = 0.0f;
    private transient int currentSprite = 0;
    private boolean doesLoop = false;

    /**
     * Creates a state for animation that contains a list of Sprites/Frames
     */
    public AnimationState() {

    }

    /**
     * Adds a sprite as a frame to the animation state frame list
     * @param sprite Sprite object to be added as the frame
     * @param frameTime time in between each frame/sprite
     */
    public void addFrame(Sprite sprite, float frameTime) {
        animationFrames.add(new Frame(sprite, frameTime));
    }

    /**
     * Sets if the animation state loops or not
     * @param doesLoop boolean for if animation loops
     */
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

    /**
     * @return returns the title of the animation state
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the tile of the animation state
     * @param title the string that is set as title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public void refreshTextures() {
        for (Frame frame : animationFrames) {
            frame.getSprite().setTexture(AssetPool.getTexture(frame.getSprite().getTexture().getFilepath()));
        }
    }
}
