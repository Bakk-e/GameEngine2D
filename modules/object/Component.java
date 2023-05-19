package HIOF.GameEnigne2D.modules.object;

public abstract class Component {
    public GameObject gameObject = null;


    //This is used as a base for all the components that can be put on and be used by a GameObject
    public void start() {

    }

    public abstract void update(float deltaTime);

    public void destroy() {

    };
}
