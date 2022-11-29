import processing.core.PImage;

import java.util.*;


public class FairyEntity extends MovableEntity implements ExecutableEntity{


    private static final String SAPLING_KEY = "sapling";


    public FairyEntity(String id, Point position, List<PImage> images,  int actionPeriod, int animationPeriod) {
        super(id,position,images,animationPeriod,actionPeriod);
    }

    public boolean moveToFairy(
            WorldModel world,
            Entity target,
            EventScheduler scheduler) {
        if (adjacent(this.getPosition(), target.getPosition())) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        } else {
            Point nextPos = nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity( this, nextPos);
            }
            return false;
        }
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        Entity fairyTarget = VirtualWorld.theDude;
        Random rand = new Random();

        if (moveToFairy(world, fairyTarget, scheduler)) {
            VirtualWorld.theDude.setPosition(new Point(rand.nextInt(15), rand.nextInt(15)));
            world.addEntity(VirtualWorld.theDude);
        }


//        if (fairyTarget.isPresent() && moveToFairy(world, fairyTarget.get(), scheduler)) {
//            world.removeEntity(VirtualWorld.theDude);
//        }

        scheduler.scheduleEvent(this,
                new ActivityAction(this, world, imageStore),
                getActionPeriod());
    }

    public Point nextPosition(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.getX() - this.getPosition().getX());
        Point newPos = new Point(this.getPosition().getX() + horiz, this.getPosition().getY());

        if (horiz == 0 || world.isOccupied(newPos)) {
            int vert = Integer.signum(destPos.getY() - this.getPosition().getY());
            newPos = new Point(this.getPosition().getX(), this.getPosition().getY() + vert);

            if (vert == 0 || world.isOccupied(newPos)) {
                newPos = this.getPosition();
            }
        }

        return newPos;
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
