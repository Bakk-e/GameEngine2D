package HIOF.GameEnigne2D.worlds;

import HIOF.GameEnigne2D.modules.Room;
import HIOF.GameEnigne2D.modules.Window;

public class world2 extends Room {

    public world2() {
        System.out.println("World 2");
        Window.get().setColor(1.0f, 1.0f, 1.0f);
    }
    @Override
    public void update(float dT) {

    }

    @Override
    public void init() {

    }
}
