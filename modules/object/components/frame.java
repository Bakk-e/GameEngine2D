package HIOF.GameEnigne2D.modules.object.components;

public class frame {
    private HIOF.GameEnigne2D.modules.object.components.sprite sprite;
    private float frameTime;

    public frame() {

    }

    public frame(HIOF.GameEnigne2D.modules.object.components.sprite sprite, float frameTime) {
        this.sprite = sprite;
        this.frameTime = frameTime;
    }

    public HIOF.GameEnigne2D.modules.object.components.sprite getSprite() {
        return sprite;
    }
    public void setSprite(HIOF.GameEnigne2D.modules.object.components.sprite sprite) {
        this.sprite = sprite;
    }

    public float getFrameTime() {
        return frameTime;
    }
    public void setFrameTime(float frameTime) {
        this.frameTime = frameTime;
    }
}
