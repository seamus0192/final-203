import processing.core.PImage;

import java.util.List;

public abstract class PlantEntity extends AnimatableEntity implements ExecutableEntity,TransformableEntity{
    private int health;
    private int actionPeriod;

    public PlantEntity( String id, Point position, List<PImage> images, int health, int actionPeriod, int animationPeriod) {
        super(id, position, images, animationPeriod);
        this.health = health;
        this.actionPeriod = actionPeriod;
    }

    public int getHealth() {
        return health;
    }

    public void addHealth(){this.health++;}

    public int getActionPeriod() {
        return actionPeriod;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
