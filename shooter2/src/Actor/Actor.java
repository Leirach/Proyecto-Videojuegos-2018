package Actor;

import java.awt.Graphics;
import java.awt.Rectangle;
import shooter2.Game;

/**
 * Actor class to use as parent for player and enemy objects
 */
public abstract class Actor {

    /**
     * variable  to store x position 
     */
    protected int x;       

    /**
     *  to store y position
     */
    protected int y;       

    /**
     * width of the actor
     */
    protected int width;    

    /**
     * actor height
     */
    protected int height;   

    /**
     *  game object
     */
    protected Game game;   

    /**
     * range of actor
     */
    protected int range;    

    /**
     * damage caused by the actor
     */
    protected int damage;   

    /**
     *  life of the actor
     */
    protected int life;    
    
    /**
     * Set the initial values to create the item
     * @param x <code>x</code> position of the object
     * @param y <code>y</code> position of the object
     * @param width <code>int</code> width of actor
     * @param height <code>int</code> height of actor
     * @param game <code>Game</code> game object
     * @param range <code>int</code> attack range of actor
     */
    public Actor(int x, int y, int width, int height, Game game, int range) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.game = game;
        this.range = range;
    }

    /**
     * Get x value
     * @return x 
     */
    public int getX() {
        return x;
    }
    
    /**
     * Get y value
     * @return y 
     */
    public int getY() {
        return y;
    }
    
    /**
     * Get the width value
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height value
     * @return height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Set x value
     * @param x to modify
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set y value
     * @param y to modify
     */
    public void setY(int y) {
        this.y = y;
    }
    
    
      /**
     * Set range value
     * @param range to modify
     */
    public void setRange(int range) {
        this.range = range;
    }
    
    /**
     * get the range of the actor 
     * @return <code>int</code> range of the actor
     * 
     */
    public int getRange() {
        return range;
    }
    
    /**
     * get the damage of the actor
     * @return <code>int</code> damage amount
     */
    public int getDamage() {
        return damage;
    }
 
    /**
     * set enemy life
     * @param life <code>int</code> new life value
     */
    public void setLife(int life) {
        this.life = life;
    }

    /**
     * get amount of life left
     * @return <code>int</code> amount of life
     */
    public int getLife() {
        return life;
    }
    
    /**
     * Set the width value
     * @param width to modify
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Set the height value
     * @param height to modify
     */
    public void setHeight(int height) {
        this.height = height;
    }
    
    /**
     * get a rectangle that contains an object
     * @return <code>Rectangle</code> object containing the Actor
     */
    private Rectangle getBounds () {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
    
    /**
     * set the attacking actor
     * @param act <code>act</code> actor for the objective 
     */
    public abstract void attacks(Actor act);
    
    
    /**
     * return whether one object somehow intersects another
     * @param obj <code>Object</code> object to calculate intersection with
     * @return <code>boolean</code> value indicating if there is an intersection
     */
    public boolean intersects (Object obj) {
        //Item casting on Object type
        return (obj instanceof Actor 
                && this.getBounds().intersects(((Actor) obj).getBounds()));
    }
    
    /**
     * check whether one object contain a coordinate
     * @param x <code>int</code> value of x coordinate
     * @param y <code>int</code> value of y coordinate
     * @return <code>boolean</code> value indicating one containing another
     */
    public boolean contains(int x, int y) {
        return this.getBounds().contains((double) x, (double) y);
    }
    
    /**
     * To update positions of the item for every tick
     */
    public abstract void tick();
    
    /**
     * To paint the item
     * @param g <b>Graphics</b> object to paint the item
     */
    public abstract void render(Graphics g);
}
