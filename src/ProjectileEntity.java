import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ProjectileEntity extends MovableEntity implements ExecutableEntity{

    private String dir;


    public ProjectileEntity(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod, String dir) {
        super(id, position, images, animationPeriod, actionPeriod);
        this.dir = dir;
    }


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
        return new Point(point.x+1, point.y);
    }

    public void moveToFairy(
            WorldModel world,
            Entity target,
            EventScheduler scheduler) {
                Point nextPos = nextPosition(world, target.getPosition());

                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity( this, nextPos);
        }


    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        moveToFairy(world, VirtualWorld.theDude, scheduler);

//        if (fairyTarget.isPresent() && moveToFairy(world, fairyTarget.get(), scheduler)) {
//            world.removeEntity(VirtualWorld.theDude);
//        }

        scheduler.scheduleEvent(this,
                new ActivityAction(this, world, imageStore),
                getActionPeriod());
    }

    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                new ActivityAction(this, world, imageStore),
                getActionPeriod());
        scheduler.scheduleEvent(this,
                new AnimationAction(this,0),
                getAnimationPeriod());
    }
}
