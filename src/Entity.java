import java.util.*;

import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public abstract class Entity {

//    private static final String STUMP_KEY = "stump";
//    private static final String SAPLING_KEY = "sapling";
//    private static final String TREE_KEY = "tree";
//    private static final int TREE_ANIMATION_MAX = 600;
//    private static final int TREE_ANIMATION_MIN = 50;
//    private static final int TREE_ACTION_MAX = 1400;
//    private static final int TREE_ACTION_MIN = 1000;
//    private static final int TREE_HEALTH_MAX = 3;
//    private static final int TREE_HEALTH_MIN = 1;
//    private static final int SAPLING_HEALTH_LIMIT = 5;
//    private static final int SAPLING_ACTION_ANIMATION_PERIOD = 1000; // have to be in sync since grows and gains health at
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
//    private int resourceLimit;
//    private int resourceCount;
//    private int actionPeriod;
//    private int animationPeriod;
//    private int health;
//    private int healthLimit;

    public Entity(
            String id,
            Point position,
            List<PImage> images) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
    }

    public List<PImage> getImages() {
        return images;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public String getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }


    public void setPosition(Point position) {
        this.position = position;
    }

    public PImage getCurrentImage() {
        return images.get(imageIndex);
    }

    public void nextImage() {
        imageIndex = (imageIndex + 1) % images.size();
    }

    public static boolean adjacent(Point p1, Point p2) {
        return (p1.getX() == p2.getX() && Math.abs(p1.getY() - p2.getY()) == 1) || (p1.getY() == p2.getY()
                && Math.abs(p1.getX() - p2.getX()) == 1);
    }

//    public static Entity createHouse(
//            String id, Point position, List<PImage> images) {
//        return new Entity(EntityKind.HOUSE, id, position, images, 0, 0, 0,
//                0, 0, 0);
//    }

//    public static Entity createObstacle(
//            String id, Point position, int animationPeriod, List<PImage> images) {
//        return new Entity(EntityKind.OBSTACLE, id, position, images, 0, 0, 0,
//                animationPeriod, 0, 0);
//    }

//    public static Entity createTree(
//            String id,
//            Point position,
//            int actionPeriod,
//            int animationPeriod,
//            int health,
//            List<PImage> images) {
//        return new Entity(EntityKind.TREE, id, position, images, 0, 0,
//                actionPeriod, animationPeriod, health, 0);
//    }

//    public static Entity createStump(
//            String id,
//            Point position,
//            List<PImage> images) {
//        return new Entity(EntityKind.STUMP, id, position, images, 0, 0,
//                0, 0, 0, 0);
//    }

    // health starts at 0 and builds up until ready to convert to Tree
//    public static Entity createSapling(
//            String id,
//            Point position,
//            List<PImage> images) {
//        return new Entity(EntityKind.SAPLING, id, position, images, 0, 0,
//                SAPLING_ACTION_ANIMATION_PERIOD, SAPLING_ACTION_ANIMATION_PERIOD, 0, SAPLING_HEALTH_LIMIT);
//    }

//    public static Entity createFairy(
//            String id,
//            Point position,
//            int actionPeriod,
//            int animationPeriod,
//            List<PImage> images) {
//        return new Entity(EntityKind.FAIRY, id, position, images, 0, 0,
//                actionPeriod, animationPeriod, 0, 0);
//    }

    // need resource count, though it always starts at 0
//    public static Entity createDudeNotFull(
//            String id,
//            Point position,
//            int actionPeriod,
//            int animationPeriod,
//            int resourceLimit,
//            List<PImage> images) {
//        return new Entity(EntityKind.DUDE_NOT_FULL, id, position, images, resourceLimit, 0,
//                actionPeriod, animationPeriod, 0, 0);
//    }
//
//    // don't technically need resource count ... full
//    public static Entity createDudeFull(
//            String id,
//            Point position,
//            int actionPeriod,
//            int animationPeriod,
//            int resourceLimit,
//            List<PImage> images) {
//        return new Entity(EntityKind.DUDE_FULL, id, position, images, resourceLimit, 0,
//                actionPeriod, animationPeriod, 0, 0);
//    }


//    public boolean transformNotFull(
//            WorldModel world,
//            EventScheduler scheduler,
//            ImageStore imageStore) {
//        if (this.resourceCount >= this.resourceLimit) {
//            Entity miner = new DudeFullEntity(EntityKind.DUDE_FULL, this.id,
//                    this.position, this.images,this.actionPeriod,
//                    this.animationPeriod,
//                    this.resourceLimit);
//
//            world.removeEntity(this);
//            scheduler.unscheduleAllEvents(this);
//
//            world.addEntity( miner);
//            miner.scheduleActions(scheduler, world, imageStore);
//
//            return true;
//        }
//
//        return false;
//    }

//    public void transformFull(
//            WorldModel world,
//            EventScheduler scheduler,
//            ImageStore imageStore) {
//        Entity miner = new DudeNotFullEntity(EntityKind.DUDE_NOT_FULL,this.id,
//                this.position, this.images, this.actionPeriod,
//                this.animationPeriod,
//                this.resourceLimit);
//
//        world.removeEntity( this);
//        scheduler.unscheduleAllEvents(this);
//
//        world.addEntity( miner);
//        miner.scheduleActions(scheduler, world, imageStore);
//    }


//    public boolean transformPlant(WorldModel world,
//                                  EventScheduler scheduler,
//                                  ImageStore imageStore) {
//        if (this.kind == EntityKind.TREE) {
//            return transformTree(world, scheduler, imageStore);
//        } else if (this.kind == EntityKind.SAPLING) {
//            return transformSapling(world, scheduler, imageStore);
//        } else {
//            throw new UnsupportedOperationException(
//                    String.format("transformPlant not supported for %s", this));
//        }
//    }

//    public boolean transformTree(
//            WorldModel world,
//            EventScheduler scheduler,
//            ImageStore imageStore) {
//        if (this.health <= 0) {
//            Entity stump = new StumpEntity(EntityKind.STUMP, this.id,
//                    this.position,
//                    imageStore.getImageList(STUMP_KEY));
//
//            world.removeEntity(this);
//            scheduler.unscheduleAllEvents(this);
//
//            world.addEntity( stump);
//
//            return true;
//        }
//
//        return false;
//    }

//    public boolean transformSapling(
//            WorldModel world,
//            EventScheduler scheduler,
//            ImageStore imageStore) {
//        if (this.health <= 0) {
//            Entity stump = new StumpEntity(EntityKind.STUMP, this.id,
//                    this.position,
//                    imageStore.getImageList(STUMP_KEY));
//
//            world.removeEntity(this);
//            scheduler.unscheduleAllEvents(this);
//
//            world.addEntity( stump);
//
//            return true;
//        } else if (this.health >= this.healthLimit) {
//            Entity tree = new TreeEntity(EntityKind.TREE,"tree_" + this.id,
//                    this.position,imageStore.getImageList(TREE_KEY),
//                    getNumFromRange(TREE_ACTION_MAX, TREE_ACTION_MIN),
//                    getNumFromRange(TREE_ANIMATION_MAX, TREE_ANIMATION_MIN),
//                    getNumFromRange(TREE_HEALTH_MAX, TREE_HEALTH_MIN));
//
//            world.removeEntity( this);
//            scheduler.unscheduleAllEvents(this);
//
//            world.addEntity( tree);
//            tree.scheduleActions(scheduler, world, imageStore);
//
//            return true;
//        }
//
//        return false;
//    }


//    public boolean moveToFairy(
//            WorldModel world,
//            Entity target,
//            EventScheduler scheduler) {
//        if (adjacent(this.position, target.position)) {
//            world.removeEntity(target);
//            scheduler.unscheduleAllEvents(target);
//            return true;
//        } else {
//            Point nextPos = nextPositionFairy(world, target.position);
//
//            if (!this.position.equals(nextPos)) {
//                Optional<Entity> occupant = world.getOccupant(nextPos);
//                if (occupant.isPresent()) {
//                    scheduler.unscheduleAllEvents(occupant.get());
//                }
//
//                world.moveEntity( this, nextPos);
//            }
//            return false;
//        }
//    }

//    public boolean moveToNotFull(
//            WorldModel world,
//            Entity target,
//            EventScheduler scheduler) {
//        if (adjacent(this.position, target.position)) {
//            this.resourceCount += 1;
//            target.health--;
//            return true;
//        } else {
//            Point nextPos = nextPositionDude(world, target.position);
//
//            if (!this.position.equals(nextPos)) {
//                Optional<Entity> occupant = world.getOccupant(nextPos);
//                if (occupant.isPresent()) {
//                    scheduler.unscheduleAllEvents(occupant.get());
//                }
//
//                world.moveEntity(this, nextPos);
//            }
//            return false;
//        }
//    }

//    public boolean moveToFull(
//            WorldModel world,
//            Entity target,
//            EventScheduler scheduler) {
//        if (adjacent(this.position, target.position)) {
//            return true;
//        } else {
//            Point nextPos = nextPositionDude(world, target.position);
//
//            if (!this.position.equals(nextPos)) {
//                Optional<Entity> occupant = world.getOccupant(nextPos);
//                if (occupant.isPresent()) {
//                    scheduler.unscheduleAllEvents(occupant.get());
//                }
//
//                world.moveEntity( this, nextPos);
//            }
//            return false;
//        }
//    }

//    public Point nextPositionFairy(WorldModel world, Point destPos) {
//        int horiz = Integer.signum(destPos.getX() - this.position.getX());
//        Point newPos = new Point(this.position.getX() + horiz, this.position.getY());
//
//        if (horiz == 0 || world.isOccupied(newPos)) {
//            int vert = Integer.signum(destPos.getY() - this.position.getY());
//            newPos = new Point(this.position.getX(), this.position.getY() + vert);
//
//            if (vert == 0 || world.isOccupied(newPos)) {
//                newPos = this.position;
//            }
//        }
//
//        return newPos;
//    }

//    public Point nextPositionDude(WorldModel world, Point destPos) {
//        int horiz = Integer.signum(destPos.getX() - this.position.getX());
//        Point newPos = new Point(this.position.getX() + horiz, this.position.getY());
//
//        if (horiz == 0 || world.isOccupied(newPos) && world.getOccupancyCell(newPos).kind != EntityKind.STUMP) {
//            int vert = Integer.signum(destPos.getY() - this.position.getY());
//            newPos = new Point(this.position.getX(), this.position.getY() + vert);
//
//            if (vert == 0 || world.isOccupied(newPos) && world.getOccupancyCell(newPos).kind != EntityKind.STUMP) {
//                newPos = this.position;
//            }
//        }
//
//        return newPos;
//    }

//    public int getAnimationPeriod() {
//        switch (this.kind) {
//            case DUDE_FULL:
//            case DUDE_NOT_FULL:
//            case OBSTACLE:
//            case FAIRY:
//            case SAPLING:
//            case TREE:
//                return this.animationPeriod;
//            default:
//                throw new UnsupportedOperationException(
//                        String.format("getAnimationPeriod not supported for %s",
//                                this.kind));
//        }
//    }

//    public void scheduleActions(
//            EventScheduler scheduler,
//            WorldModel world,
//            ImageStore imageStore) {
//        switch (kind) {
//            case DUDE_FULL:
//                scheduler.scheduleEvent(this,
//                        new ActivityAction(this, world, imageStore),
//                        actionPeriod);
//                scheduler.scheduleEvent(this,
//                        new AnimationAction(this,0),
//                        getAnimationPeriod());
//                break;
//
//            case DUDE_NOT_FULL:
//                scheduler.scheduleEvent(this,
//                        new ActivityAction(this, world, imageStore),
//                        actionPeriod);
//                scheduler.scheduleEvent(this,
//                        new AnimationAction(this,0),
//                        getAnimationPeriod());
//                break;
//
//            case OBSTACLE:
//                scheduler.scheduleEvent(this,
//                        new AnimationAction(this,0),
//                        getAnimationPeriod());
//                break;
//
//            case FAIRY:
//                scheduler.scheduleEvent(this,
//                        new ActivityAction(this, world, imageStore),
//                        actionPeriod);
//                scheduler.scheduleEvent(this,
//                        new AnimationAction(this,0),
//                        getAnimationPeriod());
//                break;
//
//            case SAPLING:
//                scheduler.scheduleEvent(this,
//                        new ActivityAction(this, world, imageStore),
//                        actionPeriod);
//                scheduler.scheduleEvent(this,
//                        new AnimationAction(this,0),
//                        getAnimationPeriod());
//                break;
//
//            case TREE:
//                scheduler.scheduleEvent(this,
//                        new ActivityAction(this, world, imageStore),
//                        actionPeriod);
//                scheduler.scheduleEvent(this,
//                        new AnimationAction(this,0),
//                        getAnimationPeriod());
//                break;
//
//            default:
//        }
//    }

//    public void executeSaplingActivity(
//            WorldModel world,
//            ImageStore imageStore,
//            EventScheduler scheduler) {
//        health++;
//        if (!transformPlant(world, scheduler, imageStore)) {
//            scheduler.scheduleEvent(this,
//                    new ActivityAction(this, world, imageStore),
//                    actionPeriod);
//        }
//    }

//    public void executeTreeActivity(
//            WorldModel world,
//            ImageStore imageStore,
//            EventScheduler scheduler) {
//
//        if (!transformPlant(world, scheduler, imageStore)) {
//
//            scheduler.scheduleEvent(this,
//                    new ActivityAction(this, world, imageStore),
//                    actionPeriod);
//        }
//    }

//    public void executeFairyActivity(
//            WorldModel world,
//            ImageStore imageStore,
//            EventScheduler scheduler) {
//        Optional<Entity> fairyTarget =
//                world.findNearest(position, new ArrayList<>(Arrays.asList(EntityKind.STUMP)));
//
//        if (fairyTarget.isPresent()) {
//            Point tgtPos = fairyTarget.get().position;
//
//            if (moveToFairy(world, fairyTarget.get(), scheduler)) {
//                Entity sapling = new SaplingEntity(EntityKind.SAPLING,"sapling_" + id, tgtPos,
//                        imageStore.getImageList(SAPLING_KEY),0);
//
//                world.addEntity( sapling);
//                sapling.scheduleActions(scheduler, world, imageStore);
//            }
//        }
//
//        scheduler.scheduleEvent(this,
//                new ActivityAction(this, world, imageStore),
//                actionPeriod);
//    }

//    public void executeDudeNotFullActivity(
//            WorldModel world,
//            ImageStore imageStore,
//            EventScheduler scheduler) {
//        Optional<Entity> target =
//                world.findNearest(position, new ArrayList<>(Arrays.asList(EntityKind.TREE, EntityKind.SAPLING)));
//
//        if (!target.isPresent() || !moveToNotFull(world,
//                target.get(),
//                scheduler)
//                || !transformNotFull(world, scheduler, imageStore)) {
//            scheduler.scheduleEvent(this,
//                    new ActivityAction(this, world, imageStore),
//                    actionPeriod);
//        }
//    }

//    public void executeDudeFullActivity(
//            WorldModel world,
//            ImageStore imageStore,
//            EventScheduler scheduler) {
//        Optional<Entity> fullTarget =
//                world.findNearest(position, new ArrayList<>(Arrays.asList(EntityKind.HOUSE)));
//
//        if (fullTarget.isPresent() && moveToFull(world,
//                fullTarget.get(), scheduler)) {
//            transformFull(world, scheduler, imageStore);
//        } else {
//            scheduler.scheduleEvent(this,
//                    new ActivityAction(this, world, imageStore),
//                    this.actionPeriod);
//        }
//    }

//    public PImage getCurrentImage() {
//        return images.get(imageIndex);
//    }
//
//    public void nextImage() {
//        imageIndex = (imageIndex + 1) % images.size();
//    }
//
//
//    public void setPosition(Point pos) {position = pos; }
}
