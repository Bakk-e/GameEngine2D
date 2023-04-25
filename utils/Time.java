package HIOF.GameEnigne2D.utils;

import static java.lang.System.nanoTime;

public class Time {
    public static float timeStarted = nanoTime();

    public static float getTime() {
        return (float)((nanoTime() - timeStarted) * 1E-9);
    }
}
