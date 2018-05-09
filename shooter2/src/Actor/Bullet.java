package Actor;

import graphics.Assets;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import shooter2.Game;

/**
 * bullet subclass to create a bullet for each enemy and player firing
 */
public class Bullet extends Actor {
    
    private ArrayList<Point> ret;       // array of bullet travel-trayectory points
    private int index;                  // current index in point array
    private Actor owner;                // owner of the bullet

    /**
     *
     * @param x  holds the horizontal position of the bullet
     * @param y  holds the vertical position of the bullet
     * @param width  holds the width of the bullet
     * @param height  holds the height of the bullet
     * @param game  holds the game object
     * @param range  holds the range of the bullet
     * @param owner  holds the owner of the bullet
     */
    public Bullet(int x, int y, int width, int height, Game game, int range, Actor owner) {
        super(x, y, width, height, game, range);
        this.owner = owner;
        ret = new ArrayList<Point>();
        index = 0;
        Assets.Shot.play();
    }
    
    /**
     * get the owner (who shot) of the bullet
     * @return <code>Actor</code> Actor object of the owner
     */
    public Actor getOwner() {
        return owner;
    }
    
    /**
     * generate a lineal trayectory between player and enemy for the bullet to travel
     * based on bresenham's line algorithm
     * @param start <code>Point</code> start position of the bullet
     * @param target <code>Point</code> end position of the bullet
     */
    public void makeLine(Point start, Point target) {
            // start and end coordinates
        int x0 =  start.x;
        int y0 =  start.y;

        int x1 = target.x;
        int y1 = target.y;
            // sign (positive/negative) of distance
        int sx = 0;
        int sy = 0;
            // horizontal and vertical distance
        int dx =  Math.abs(x1-x0);
        sx = x0<x1 ? 1 : -1;
        int dy = -1*Math.abs(y1-y0);
        sy = y0<y1 ? 1 : -1; 
            // error value e_xy
        int err = dx+dy, e2; 
            
            // generate the points
        for(;;){
            ret.add( new Point(x0,y0) );
            if (x0==x1 && y0==y1) break;
            e2 = 2*err;
            if (e2 >= dy) { err += dy; x0 += sx; } /* e_xy+e_x > 0 */
            if (e2 <= dx) { err += dx; y0 += sy; } /* e_xy+e_y < 0 */
        }
    }
    
    @Override
    public void tick() {
            // move the bullet to the next point
        this.setY(ret.get(index).y);
        this.setX(ret.get(index).x);
        index += 5;
    }

    @Override
    public void render(Graphics g) {
            // bullet is rendered as a white dot
        g.setColor(Color.white);
        g.fillOval(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void attacks(Actor act) {
        // the bullet itself does not attack, the owner does the calculation of damage
    }
}
