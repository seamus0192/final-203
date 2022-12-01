import processing.core.PImage;

import java.util.List;

public class ExplosionEntity extends AnimatableEntity
{
    public ExplosionEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, animationPeriod);
    }


    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                new AnimationAction(this, 0),
                getAnimationPeriod());
    }
}
