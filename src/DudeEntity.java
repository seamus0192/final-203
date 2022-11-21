import processing.core.PImage;

import java.util.*;

public abstract class DudeEntity extends MovableEntity implements TransformableEntity, ExecutableEntity{
    private int resourceLimit;

    public DudeEntity(String id, Point position, List<PImage> images, int animationPeriod,int actionPeriod, int resourceLimit) {
        super(id, position, images,animationPeriod,actionPeriod);
        this.resourceLimit = resourceLimit;
    }

    public int getResourceLimit() {return resourceLimit;}

    public Point nextPosition(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.getX() - this.getPosition().getX());
        Point newPos = new Point(this.getPosition().getX() + horiz, this.getPosition().getY());

        if (horiz == 0 || world.isOccupied(newPos) && !(world.getOccupancyCell(newPos) instanceof StumpEntity)) {
            int vert = Integer.signum(destPos.getY() - this.getPosition().getY());
            newPos = new Point(this.getPosition().getX(), this.getPosition().getY() + vert);

            if (vert == 0 || world.isOccupied(newPos) && !(world.getOccupancyCell(newPos) instanceof StumpEntity)) {
                newPos = this.getPosition();
            }
        }

        return newPos;
    }
}
