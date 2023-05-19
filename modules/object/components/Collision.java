package HIOF.GameEnigne2D.modules.object.components;

import java.awt.*;
import java.util.List;

public class Collision {
    public static boolean intersectsX(Rectangle rect1, Rectangle rect2, float x) {
        double left1 = rect1.getX() + x;
        double top1 = rect1.getY();
        double right1 = rect1.getX() + x +rect1.getWidth();
        double bottom1 = rect1.getY()+rect1.getHeight();
        double left2 = rect2.getX();
        double top2 = rect2.getY();
        double right2 = rect2.getX() +rect2.getWidth();
        double bottom2 = rect2.getY()+rect2.getHeight();
        return (
                left1 < right2
                        && top1 < bottom2
                        && right1 > left2
                        && bottom1 > top2
        );
    }

    public static boolean intersectsY(Rectangle rect1, Rectangle rect2, float y) {
        double left1 = rect1.getX();
        double top1 = rect1.getY() + y;
        double right1 = rect1.getX()+rect1.getWidth();
        double bottom1 = rect1.getY()+ y +rect1.getHeight();
        double left2 = rect2.getX();
        double top2 = rect2.getY();
        double right2 = rect2.getX()+rect2.getWidth();
        double bottom2 = rect2.getY()+rect2.getHeight();
        return (
                left1 < right2
                && top1 < bottom2
                && right1 > left2
                && bottom1 > top2
        );
    }

    public static boolean intersectsXAtleastOne(Rectangle rect1, List<Rectangle> rectangles, int x) {
        for (Rectangle rect2 : rectangles) {
            if (intersectsX(rect1, rect2, x)) {
                return true;
            }
        }
        return false;
    }

    public static boolean intersectsYAtleastOne(Rectangle rect1, List<Rectangle> rectangles, int y) {
        for (Rectangle rect2 : rectangles) {
            if (intersectsY(rect1, rect2, y)) {
                return true;
            }
        }
        return false;
    }
}
