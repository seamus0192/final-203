public class ActivityAction implements Action{
        private ExecutableEntity entity;
        private WorldModel world;
        private ImageStore imageStore;

        public ActivityAction(
                ExecutableEntity entity,
                WorldModel world,
                ImageStore imageStore)
        {
            this.entity = entity;
            this.world = world;
            this.imageStore = imageStore;
        }

        public void executeAction(EventScheduler scheduler) {
                    entity.executeActivity( this.world, this.imageStore, scheduler);
        }
}

