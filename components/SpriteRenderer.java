package HIOF.GameEnigne2D.components;

import HIOF.GameEnigne2D.modules.Component;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {
    private Vector4f color;

    public SpriteRenderer(Vector4f color) {
        this.color = color;
    }

    @Override
    public void start() {

    }

    @Override
    public void update(float deltaTime) {

    }

    public Vector4f getColor() {
        return color;
    }
    public void setColor(Vector4f color) {
        this.color = color;
    }
}
