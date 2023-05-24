package HIOF.GameEnigne2D;

import HIOF.GameEnigne2D.worlds.world1;


//Ths class is to show how you would make your own window class
public class Game extends HIOF.GameEnigne2D.modules.window.window {

    //For the child of the window class to work you will need a game constructor and a create function that returns
    // the window object back to the user
    private Game(int width, int height, String title, double fps) {
        super(width, height, title, fps);
    }

    public static HIOF.GameEnigne2D.modules.window.window create(int width, int height, String title, double fps) {
        if (HIOF.GameEnigne2D.modules.window.window.window == null) {
            HIOF.GameEnigne2D.modules.window.window.window = new Game(width, height, title, fps);
        }
        return HIOF.GameEnigne2D.modules.window.window.window;
    }

    //In setup, you put everything you want to happen before the game loop begins
    @Override
    public void setup() {
        setCurrentRoom(new world1());
    }

    //In update, you put all the code that is going to run every frame of your game
    @Override
    public void update(float deltaTime) {
        if (deltaTime >= 0) {
            currentRoom.update(deltaTime);
        }
    }
}
