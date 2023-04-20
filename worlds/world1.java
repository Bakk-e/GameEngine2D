package HIOF.GameEnigne2D.worlds;

import HIOF.GameEnigne2D.modules.KeyListener;
import HIOF.GameEnigne2D.modules.Room;
import HIOF.GameEnigne2D.modules.Window;

import java.awt.event.KeyEvent;

public class world1 extends Room {

    private boolean changeRoom = false;
    private float timeToChangeRoom = 2.0f;
    public world1() {
        System.out.println("World 1");
        Window.get().setColor(1.0f, 1.0f, 1.0f);
    }

    @Override
    public void update(float dT) {
        if (!changeRoom && KeyListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            changeRoom = true;
        }

        if (changeRoom && timeToChangeRoom > 0) {
            timeToChangeRoom -= dT;
            Window.get().changeR(-(dT * 5.0f));
            Window.get().changeG(-(dT * 5.0f));
            Window.get().changeB(-(dT * 5.0f));
        } else if (changeRoom) {
            Window.get().changeRoom(1);
        }
    }
}
