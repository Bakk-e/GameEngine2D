package HIOF.GameEnigne2D;

import HIOF.GameEnigne2D.modules.Window;

public class Main {
    public static void main(String[] args) {
        //Window window = Window.create(1920, 1080, "Test", 60.0);

        //The code underneath shows a test of the framework, where when you start the game, it enters world1. Where if
        // you press space it will slowly fade to black and change to world2

        Window window = Game.create(1920, 1080, "Test", 60.0);

        window.run();
    }
}
