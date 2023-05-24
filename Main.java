package HIOF.GameEnigne2D;

import HIOF.GameEnigne2D.modules.window.window;

public class Main {
    public static void main(String[] args) {
        window window = Game.create(1920, 1080, "Test", 60.0);

        window.run();
    }
}
