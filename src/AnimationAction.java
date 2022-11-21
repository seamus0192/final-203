public class AnimationAction implements Action{
    private AnimatableEntity entity;
    private int repeatCount;

    public AnimationAction(
            AnimatableEntity entity,
            int repeatCount)
    {
        this.entity = entity;
        this.repeatCount = repeatCount;
    }


    public void executeAction(EventScheduler scheduler)
    {
        entity.nextImage();

        if (this.repeatCount != 1) {
            scheduler.scheduleEvent(this.entity,
                    new AnimationAction(this.entity,
                            Math.max(this.repeatCount - 1, 0)),
                            this.entity.getAnimationPeriod());
        }
    }

}

