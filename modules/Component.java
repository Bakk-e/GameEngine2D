package HIOF.GameEnigne2D.modules;

public abstract class Component {
    public GameObject gameObject = null;

    public void start() {

    }

    public abstract void update(float deltaTime);
}
