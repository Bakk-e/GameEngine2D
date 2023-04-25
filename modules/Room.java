package HIOF.GameEnigne2D.modules;

public abstract class Room {

    protected Camera camera;
    public Room() {

    }

    public abstract void update(float dT);

    public abstract void init();
}
