import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import processing.core.*;

public final class VirtualWorld extends PApplet
{
    private static final int TIMER_ACTION_PERIOD = 100;

    private static final int VIEW_WIDTH = 1120;
    private static final int VIEW_HEIGHT = 780;
    private static final int TILE_WIDTH = 26;
    private static final int TILE_HEIGHT = 26;
    private static final int WORLD_WIDTH_SCALE = 1;
    private static final int WORLD_HEIGHT_SCALE = 1;

    private static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
    private static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;
    private static final int WORLD_COLS = VIEW_COLS * WORLD_WIDTH_SCALE;
    private static final int WORLD_ROWS = VIEW_ROWS * WORLD_HEIGHT_SCALE;

    private static final String IMAGE_LIST_FILE_NAME = "imagelist";
    private static final String DEFAULT_IMAGE_NAME = "background_default";
    private static final int DEFAULT_IMAGE_COLOR = 0x808080;
    public static LasagnaEntity lasagna;

    private static String LOAD_FILE_NAME = "world.sav";

    private static final String FAST_FLAG = "-fast";
    private static final String FASTER_FLAG = "-faster";
    private static final String FASTEST_FLAG = "-fastest";
    private static final double FAST_SCALE = 0.5;
    private static final double FASTER_SCALE = 0.25;
    private static final double FASTEST_SCALE = 0.10;

    private static double timeScale = 1.0;

    private ImageStore imageStore;
    private WorldModel world;
    private WorldView view;
    private EventScheduler scheduler;
    public long nextTime;

    private TimerTask task;
    private TimerTask task2;
    private TimerTask task3;

    public static MicrowaveEntity Microwave;

    public static int bombCount;

    public static BombEntity bomb;

    public void settings() {
        size(VIEW_WIDTH, VIEW_HEIGHT);
    }

    /*
       Processing entry point for "sketch" setup.
    */
    public void setup() {

        this.imageStore = new ImageStore(
                createImageColored(TILE_WIDTH, TILE_HEIGHT,
                                   DEFAULT_IMAGE_COLOR));
        this.world = new WorldModel(WORLD_ROWS, WORLD_COLS,
                                    createDefaultBackground(imageStore));
        this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world, TILE_WIDTH,
                                  TILE_HEIGHT);
        this.scheduler = new EventScheduler(timeScale);

        task = new TimerTask() {
            public void run() {
                Random rand = new Random();
                AngryEntity f = new AngryEntity("angry",new Point(rand.nextInt(41),3),imageStore.getImageList("angry"),200,1);
                world.addEntity(f);
                f.scheduleActions( scheduler, world,imageStore);
            }
        };
        task2 = new TimerTask() {
            public void run() {
                Random rand = new Random();
                GarfieldEntity f = new GarfieldEntity("sillygarfield",findNewRandomPoint(),imageStore.getImageList("fairy"),500,1);
                world.addEntity(f);
                f.scheduleActions( scheduler, world,imageStore);
            }
        };
        task3 = new TimerTask() {
            public void run() {
                CheeseEntity f = new CheeseEntity("cheesy", findNewRandomPoint(),imageStore.getImageList("stump"));
                world.addEntity(f);
            }
        };
        //multiple to fix concurrent modification errors
        Timer t = new Timer();
        Timer t1 = new Timer();
        Timer t2 = new Timer();
        t.scheduleAtFixedRate(task, 10000,20000);
        t1.scheduleAtFixedRate(task2, 2000,5001);
        t2.scheduleAtFixedRate(task3, 2000,10003);


        loadImages(IMAGE_LIST_FILE_NAME, imageStore, this);

        lasagna = new LasagnaEntity("dude",new Point(20,25),imageStore.getImageList("dude"),220,1000,3);
        this.world.addEntity(lasagna);
        ConfusedEntity conf = new ConfusedEntity("confused",findNewRandomPoint(), imageStore.getImageList("confused"),10,15);
        this.world.addEntity(conf);


        Microwave = new MicrowaveEntity("microwave", findNewRandomPoint(), imageStore.getImageList("microwave"), 15, 15);
        this.world.addEntity(Microwave);

        bomb = new BombEntity("bomb", findNewRandomPoint(), imageStore.getImageList("bomb"), 0, 0);
        this.world.addEntity(bomb);

        loadWorld(world, LOAD_FILE_NAME, imageStore);

        scheduleActions(world, scheduler, imageStore);

        nextTime = System.currentTimeMillis() + TIMER_ACTION_PERIOD;
    }

    public Point findNewRandomPoint()
    {
        Random rand2 = new Random();
        Point finalRandom = new Point(rand2.nextInt(41),rand2.nextInt(29));
        while(this.world.isOccupied(finalRandom))
            finalRandom = new Point(rand2.nextInt(41),rand2.nextInt(29));

        return finalRandom;
    }

    public void draw() {
        long time = System.currentTimeMillis();
        if (time >= nextTime) {
            this.scheduler.updateOnTime(time);
            nextTime = time + TIMER_ACTION_PERIOD;
        }

        view.drawViewport();
        textSize(20);
        text("Kills:" + ProjectileEntity.kills,10,25);
        text("Health:" + LasagnaEntity.health,10,60);
        text("Points:" + LasagnaEntity.cooks, 1010,25);
        if (LasagnaEntity.health == 0){
            textSize(60);
            text("GAME OVER", 300,500);
            stop();
        }
    }

    // Just for debugging and for P5
    // Be sure to refactor this method as appropriate
    public void mousePressed() {
        Point pressed = mouseToPoint(mouseX, mouseY);
//        System.out.println("CLICK! " + pressed.getX() + ", " + pressed.getY());

//        Optional<Entity> entityOptional = world.getOccupant(pressed);
        if (bombCount >= 1 && world.getOccupant(pressed).isPresent() && world.getOccupancyCell(pressed) instanceof GarfieldEntity)
        {
            scheduler.unscheduleAllEvents(world.getOccupancyCell(pressed));
            world.removeEntity(world.getOccupancyCell(pressed));
            bombCount -= 1;
            //System.out.println(entity.getId() + ": " + entity.getKind() + " : " + entity.getHealth());
        }

    }

    private Point mouseToPoint(int x, int y)
    {
        return view.getViewport().viewportToWorld( mouseX/TILE_WIDTH, mouseY/TILE_HEIGHT);
    }
    public void keyPressed() {
        if (key == 's'){
            Point p = new Point(lasagna.getPosition().getX(), lasagna.getPosition().getY()+1);
            lasagna.setFacing("down");
            lasagna.nextPosition(this.world,p);
        }
        if (key == 'w'){
            Point p = new Point(lasagna.getPosition().getX(), lasagna.getPosition().getY()-1);
            lasagna.setFacing("up");
            lasagna.nextPosition(this.world,p);
//            if(world.getOccupant(lasagna.nextPosition(this.world,p)) instanceof MicrowaveEntity)
        }
        if (key == 'a'){
            Point p = new Point(lasagna.getPosition().getX()-1, lasagna.getPosition().getY());
            lasagna.setFacing("left");
            lasagna.nextPosition(this.world,p);
        }
        if (key == 'd') {
            Point p = new Point(lasagna.getPosition().getX() + 1, lasagna.getPosition().getY());
            lasagna.setFacing("right");
            lasagna.nextPosition(this.world, p);
        }
        if (key == ' '){
            Point p = lasagna.getPosition();
            switch (lasagna.getFacing()){
                case ("right") -> {
                    p = new Point(lasagna.getPosition().x + 1, lasagna.getPosition().y);
                }
                case ("left") -> {
                    p = new Point(lasagna.getPosition().x-1, lasagna.getPosition().y);
                }
                case ("up") -> {
                    p = new Point(lasagna.getPosition().x, lasagna.getPosition().y-1);
                }
                case ("down") -> {
                    p = new Point(lasagna.getPosition().x, lasagna.getPosition().y+1);
                }
            }
            if (world.getOccupant(p).isEmpty()) {
                ProjectileEntity ball = new ProjectileEntity("projectile", p, imageStore.getImageList("projectile"), 1, 10, lasagna.getFacing());
                this.world.addEntity(ball);
                ball.scheduleActions(scheduler, world, imageStore);
            }

        }
        if (key == CODED) {
            int dx = 0;
            int dy = 0;

            switch (keyCode) {
                case UP:
                    dy = -1;
                    break;
                case DOWN:
                    dy = 1;
                    break;
                case LEFT:
                    dx = -1;
                    break;
                case RIGHT:
                    dx = 1;
                    break;
            }
            view.shiftView( dx, dy);
        }
    }

    public static Background createDefaultBackground(ImageStore imageStore) {
        return new Background(DEFAULT_IMAGE_NAME,
                              imageStore.getImageList(DEFAULT_IMAGE_NAME));
    }

    public static PImage createImageColored(int width, int height, int color) {
        PImage img = new PImage(width, height, RGB);
        img.loadPixels();
        for (int i = 0; i < img.pixels.length; i++) {
            img.pixels[i] = color;
        }
        img.updatePixels();
        return img;
    }

    static void loadImages(
            String filename, ImageStore imageStore, PApplet screen)
    {
        try {
            Scanner in = new Scanner(new File(filename));
            imageStore.loadImages(in, screen);
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void loadWorld(
            WorldModel world, String filename, ImageStore imageStore)
    {
        try {
            Scanner in = new Scanner(new File(filename));
            world.load(in, imageStore);
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void scheduleActions(
            WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        for (Entity entity : world.getEntities()) {
            if (entity instanceof AnimatableEntity)
                ((AnimatableEntity)entity).scheduleActions(scheduler, world, imageStore);
        }
    }

    public static void parseCommandLine(String[] args) {
        if (args.length > 1)
        {
            if (args[0].equals("file"))
            {

            }
        }
        for (String arg : args) {
            switch (arg) {
                case FAST_FLAG:
                    timeScale = Math.min(FAST_SCALE, timeScale);
                    break;
                case FASTER_FLAG:
                    timeScale = Math.min(FASTER_SCALE, timeScale);
                    break;
                case FASTEST_FLAG:
                    timeScale = Math.min(FASTEST_SCALE, timeScale);
                    break;
            }
        }
    }


    public static void main(String[] args) {
        parseCommandLine(args);
        PApplet.main(VirtualWorld.class);
    }
}
