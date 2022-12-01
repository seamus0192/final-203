import java.util.*;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public abstract class Entity {

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;


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

}
