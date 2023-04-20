package HIOF.GameEnigne2D;

import HIOF.GameEnigne2D.modules.Window;
import HIOF.GameEnigne2D.worlds.world1;
import HIOF.GameEnigne2D.worlds.world2;

public class Game extends Window {
    private Game(int width, int height, String title, double fps) {
        super(width, height, title, fps);
    }

    public static Window create(int width, int height, String title, double fps) {
        if (Window.window == null) {
            Window.window = new Game(width, height, title, fps);
        }
        return Window.window;
    }

    @Override
    public void setup() {
        changeRoom(0);
    }

    @Override
    public void update(float dT) {
        if (dT >= 0) {
            currentRoom.update(dT);
        }
    }

    @Override
    public void changeRoom(int newRoom) {
        switch (newRoom) {
            case 0:
                currentRoom = new world2();
                //currentRoom.init();
                break;
            case 1:
                currentRoom = new world1();
                break;
            default:
                assert false : "Room not found '" + newRoom + "'";
                break;
        }
    }
}
