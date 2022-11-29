import processing.core.PImage;

import java.util.List;

public class ProjectileEntity extends MovableEntity{

    private String dir;


    public ProjectileEntity(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod, String dir) {
        super(id, position, images, animationPeriod, actionPeriod);
        this.dir = dir;
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                new AnimationAction(this,0),
                getAnimationPeriod());
    }

    @Override
    public Point nextPosition(WorldModel world, Point point) {
        switch (dir) {
            case ("right") -> {
                return new Point(point.x + 1, point.y);
            }
            case ("left") -> {
                return new Point(point.x - 1, point.y);
            }
            case ("up") -> {
                return new Point(point.x, point.y - 1);
            }
            case ("down") -> {
                return new Point(point.x, point.y + 1);
            }
        }
        return getPosition();
    }

}
