import processing.core.PImage;

import java.util.*;

public class SaplingEntity extends PlantEntity{

    private static final int SAPLING_HEALTH_LIMIT = 5;
    private static final int SAPLING_ACTION_ANIMATION_PERIOD = 1000;

    private int healthLimit = SAPLING_HEALTH_LIMIT;

    private static final String STUMP_KEY = "stump";

    private static final String TREE_KEY = "tree";
    private static final int TREE_ANIMATION_MAX = 600;
    private static final int TREE_ANIMATION_MIN = 50;
    private static final int TREE_ACTION_MAX = 1400;
    private static final int TREE_ACTION_MIN = 1000;
    private static final int TREE_HEALTH_MAX = 3;
    private static final int TREE_HEALTH_MIN = 1;

    public SaplingEntity(String id, Point position, List<PImage> images,int health) {
        super( id, position, images,health,SAPLING_ACTION_ANIMATION_PERIOD,SAPLING_ACTION_ANIMATION_PERIOD);
    }

    public boolean transform(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore) {
        if (this.getHealth() <= 0) {
            StumpEntity stump = new StumpEntity(this.getId(),
                    this.getPosition(),
                    imageStore.getImageList(STUMP_KEY));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity( stump);

            return true;
        } else if (this.getHealth() >= this.healthLimit) {
            TreeEntity tree = new TreeEntity("tree_" + this.getId(),
                    this.getPosition(),imageStore.getImageList(TREE_KEY),
                    getNumFromRange(TREE_HEALTH_MAX, TREE_HEALTH_MIN),
                    getNumFromRange(TREE_ACTION_MAX, TREE_ACTION_MIN),
                    getNumFromRange(TREE_ANIMATION_MAX, TREE_ANIMATION_MIN));

            world.removeEntity( this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity( tree);
            tree.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        addHealth();
        if (!transform(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this,
                    new ActivityAction(this, world, imageStore),
                    getActionPeriod());
        }
    }

    public static int getNumFromRange(int max, int min) {
        Random rand = new Random();
        return min + rand.nextInt(max - min);
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
