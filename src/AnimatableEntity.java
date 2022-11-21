import processing.core.PImage;

import java.util.*;

public abstract class AnimatableEntity extends Entity{

    private int animationPeriod;

    public AnimatableEntity(String id, Point position, List<PImage> images,int animationPeriod) {
        super(id, position, images);
        this.animationPeriod = animationPeriod;
    }

    public int getAnimationPeriod() {
        return this.animationPeriod;
    }

    public abstract void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore);


}
