package hiof.gameenigne2d.modules.object.components;

public class Frame {
    private Sprite sprite;
    private float frameTime;

    public Frame() {

    }

    public Frame(Sprite sprite, float frameTime) {
        this.sprite = sprite;
        this.frameTime = frameTime;
    }

    public Sprite getSprite() {
        return sprite;
    }
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public float getFrameTime() {
        return frameTime;
    }
    public void setFrameTime(float frameTime) {
        this.frameTime = frameTime;
    }
}
