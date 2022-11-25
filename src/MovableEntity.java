import processing.core.PImage;

import java.util.List;

public abstract class MovableEntity extends AnimatableEntity {
    private int actionPeriod;
    public MovableEntity(String id, Point position, List<PImage> images,int animationPeriod,int actionPeriod) {
        super(id, position, images, animationPeriod);
        this.actionPeriod = actionPeriod;
    }

    public int getActionPeriod() {
        return actionPeriod;
    }

    public abstract Point nextPosition(WorldModel world, Point point);

//    private static boolean withinBounds(Point p, GridValues[][] grid)
//    {
//        return p.y >= 0 && p.y < grid.length &&
//                p.x >= 0 && p.x < grid[0].length;
//    }

    public static boolean neighbors(Point p1, Point p2)
    {
        return p1.x+1 == p2.x && p1.y == p2.y ||
                p1.x-1 == p2.x && p1.y == p2.y ||
                p1.x == p2.x && p1.y+1 == p2.y ||
                p1.x == p2.x && p1.y-1 == p2.y;
    }

}
