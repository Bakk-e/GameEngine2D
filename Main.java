package HIOF.GameEnigne2D;

import HIOF.GameEnigne2D.howto.ExampleWindow;
import HIOF.GameEnigne2D.modules.window.Window;

public class Main {
    public static void main(String[] args) {
        //Window window = Game.create(1920, 1080, "Test", 60.0);
        Window window = ExampleWindow.create(1920, 1080, "Example", 60.0);

        window.run();
    }
}
