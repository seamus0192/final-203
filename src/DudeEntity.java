import processing.core.PImage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;


public class DudeEntity extends MovableEntity implements ExecutableEntity{
    private int resourceLimit;
    private String facing;

    public DudeEntity(String id, Point position, List<PImage> images, int animationPeriod,int actionPeriod, int resourceLimit) {

        super(id, position, images,animationPeriod,actionPeriod);
        this.resourceLimit = resourceLimit;
        facing = "right";
    }

    public void setFacing(String facing) {
        this.facing = facing;
    }

    public int getResourceLimit() {return resourceLimit;}

    public Point nextPosition(WorldModel world, Point destPos) {
            if (!world.isOccupied(destPos)) {
                world.moveEntity(this, destPos);
                return destPos;
            }
            return this.getPosition();
        }


//        int horiz = Integer.signum(destPos.getX() - this.getPosition().getX());
//        Point newPos = new Point(this.getPosition().getX() + horiz, this.getPosition().getY());
//
//        if (horiz == 0 || world.isOccupied(newPos) && !(world.getOccupancyCell(newPos) instanceof StumpEntity)) {
//            int vert = Integer.signum(destPos.getY() - this.getPosition().getY());
//            newPos = new Point(this.getPosition().getX(), this.getPosition().getY() + vert);
//
//            if (vert == 0 || world.isOccupied(newPos) && !(world.getOccupancyCell(newPos) instanceof StumpEntity)) {
//                newPos = this.getPosition();
//            }
//        }
//
//        return newPos;

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

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        Optional<Entity> target =
                world.findNearest(getPosition(), new ArrayList<>(Arrays.asList(TreeEntity.class, SaplingEntity.class)));

        if (!target.isPresent()) {
            scheduler.scheduleEvent(this,
                    new ActivityAction(this, world, imageStore),
                    getActionPeriod());
        }
    }

}
