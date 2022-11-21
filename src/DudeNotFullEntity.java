import processing.core.PImage;

import java.util.*;

public class DudeNotFullEntity extends DudeEntity{

    private int resourceCount;

    public DudeNotFullEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod,int resourceLimit) {
        super(id,position,images,animationPeriod,actionPeriod,resourceLimit);
        this.resourceCount = 0;
    }

    public void addResourceCount() {this.resourceCount++;}

    public int getResourceCount() {return resourceCount;}

    public boolean transform(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore) {
        if (this.getResourceCount() >= this.getResourceLimit()) {
            DudeFullEntity miner = new DudeFullEntity(this.getId(),
                    this.getPosition(), this.getImages(), this.getActionPeriod(),
                    this.getAnimationPeriod(), this.getResourceLimit());

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity( miner);
            miner.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        Optional<Entity> target =
                world.findNearest(getPosition(), new ArrayList<>(Arrays.asList(TreeEntity.class, SaplingEntity.class)));

        if (!target.isPresent() || !moveToNotFull(world,
                target.get(),
                scheduler)
                || !transform(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this,
                    new ActivityAction(this, world, imageStore),
                    getActionPeriod());
        }
    }

    public boolean moveToNotFull(
            WorldModel world,
            Entity target,
            EventScheduler scheduler) {
        if (adjacent(this.getPosition(), target.getPosition())) {
            addResourceCount();

            ((PlantEntity)target).setHealth(((PlantEntity)target).getHealth()-1);
            return true;
        } else {
            Point nextPos = nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
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
