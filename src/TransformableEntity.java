public interface TransformableEntity {

    public boolean transform(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore);
}
