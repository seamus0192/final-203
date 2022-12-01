import com.sun.source.tree.Tree;
import processing.core.PImage;

import java.util.*;


public class FairyEntity extends MovableEntity implements ExecutableEntity{
    private static int pathTimes = 0;

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
            EventScheduler scheduler){
            List<Class> l = new ArrayList<>();
            l.add(TreeEntity.class);
            Entity fairyTarget = VirtualWorld.theDude;
            moveToFairy(world, fairyTarget, scheduler);



//        if (fairyTarget.isPresent() && moveToFairy(world, fairyTarget.get(), scheduler)) {
//            world.removeEntity(VirtualWorld.theDude);
//        }

        scheduler.scheduleEvent(this,
                new ActivityAction(this, world, imageStore),
                getActionPeriod());
    }

    public Point nextPosition(WorldModel world, Point destPos) {
//        int horiz = Integer.signum(destPos.getX() - this.getPosition().getX());
//        Point newPos = new Point(this.getPosition().getX() + horiz, this.getPosition().getY());
//
//        if (horiz == 0 || world.isOccupied(newPos)) {
//            int vert = Integer.signum(destPos.getY() - this.getPosition().getY());
//            newPos = new Point(this.getPosition().getX(), this.getPosition().getY() + vert);
//
//            if (vert == 0 || world.isOccupied(newPos)) {
//                newPos = this.getPosition();
//            }
//        }

        int horiz = Integer.signum(destPos.getX() - this.getPosition().getX());
        Point newPos = new Point(this.getPosition().getX() + horiz, this.getPosition().getY());

        if (horiz == 0 || world.isOccupied(newPos) && !(world.getOccupancyCell(newPos) instanceof StumpEntity)) {
            int vert = Integer.signum(destPos.getY() - this.getPosition().getY());
            List<Point> newPointsList = AStarPathingStrategy.computePath(this.getPosition(), destPos,
                    p ->  world.withinBounds(p) && !(world.getOccupancyCell(p) instanceof ObstacleEntity),
                    (p1, p2) -> neighbors(p1,p2),
                    PathingStrategy.DIAGONAL_CARDINAL_NEIGHBORS);

            if(newPointsList.size() != 0)
                newPos = newPointsList.get(0);

            if (vert == 0 || world.isOccupied(newPos) && !(world.getOccupancyCell(newPos) instanceof StumpEntity)) {
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
