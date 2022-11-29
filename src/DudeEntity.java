import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import processing.core.PImage;

public class DudeEntity extends MovableEntity implements ExecutableEntity {
    private int resourceLimit;
    private String facing;

    public DudeEntity(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod, int resourceLimit) {
        super(id, position, images, animationPeriod, actionPeriod);
        this.resourceLimit = resourceLimit;
        this.facing = "right";
    }

    public void setFacing(String facing) {
        this.facing = facing;
    }

    public String getFacing() {
        return this.facing;
    }

    public int getResourceLimit() {
        return this.resourceLimit;
    }

    public Point nextPosition(WorldModel world, Point destPos) {
        if (!world.isOccupied(destPos)) {
            world.moveEntity(this, destPos);
            return destPos;
        } else {
            return this.getPosition();
        }
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), (long)this.getActionPeriod());
        scheduler.scheduleEvent(this, new AnimationAction(this, 0), (long)this.getAnimationPeriod());
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> target = world.findNearest(this.getPosition(), new ArrayList(Arrays.asList(TreeEntity.class, SaplingEntity.class)));
        if (!target.isPresent()) {
            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), (long)this.getActionPeriod());
        }

    }
}
