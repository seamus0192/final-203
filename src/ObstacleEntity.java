import processing.core.PImage;

import java.util.*;

public class ObstacleEntity extends AnimatableEntity{

    public ObstacleEntity(String id, Point position, List<PImage> images, int animationPeriod) {
        super(id,position,images,animationPeriod);
    }


    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore) {
                scheduler.scheduleEvent(this,
                        new AnimationAction(this,0),
                        getAnimationPeriod());

    }


}
