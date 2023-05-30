package hiof.gameenigne2d.modules.object.components;

import hiof.gameenigne2d.modules.object.GameObject;

import java.awt.*;
import java.util.List;

public class Collision {
    /**
     * Checks if any of the four sides of rect1 is colliding with rect2 on the x-axis
     * @param rect1 rectangle of the first object
     * @param rect2 rectangle of the second object
     * @param x the speed at which one of the objects are moving
     * @return returns if any of the four sides collide or not
     */
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

    /**
     * Checks if any of the four sides of rect1 is colliding with rect2 on the y-axis
     * @param rect1 rectangle of the first object
     * @param rect2 rectangle of the second object
     * @param y the speed at which one of the objects are moving
     * @return returns if any of the four sides collide or not
     */
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

    /**
     * Checks if any of the four sides of rect1 is colliding with the rectangles of the objects in the list on the x-axis
     * @param rect1 rectangle of the first object
     * @param objects list of objects
     * @param x the speed at which one of the objects are moving
     * @return returns if rect1 collides with any of the objects in the list
     */
    public static boolean intersectsXAtleastOne(Rectangle rect1, List<GameObject> objects, int x) {
        for (GameObject object : objects) {
            if (intersectsX(rect1, object.getRectangle(), x)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if any of the four sides of rect1 is colliding with the rectangles of the objects in the list on the y-axis
     * @param rect1 rectangle of the first object
     * @param objects list of objects
     * @param y the speed at which one of the objects are moving
     * @return returns if rect1 collides with any of the objects in the list
     */
    public static boolean intersectsYAtleastOne(Rectangle rect1, List<GameObject> objects, int y) {
        for (GameObject object : objects) {
            if (intersectsY(rect1, object.getRectangle(), y)) {
                return true;
            }
        }
        return false;
    }
}
