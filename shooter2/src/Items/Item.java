/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Items;
import java.awt.Graphics;
import shooter2.Game;

/**
 *
 * @author Nissim
 */
public abstract class Item {

    /**
     * store x position of item
     */
    protected int x;

    /**
     * Store Y position of Item
     */
    protected int y;

    /**
     * Store width of Item
     */
    protected int width;

    /**
     *  Store height of Item
     */
    protected int height;

    /**
     * Store Game object
     */
    protected Game game;
    
    /**
     * Create Item parent object
     * @param x <code>int</code> x position of gun
     * @param y <code>int</code> y position of gun
     * @param width <code>int</code> width of gun
     * @param height <code>int</code> height of gun
     * @param game <code>Game</code> Game object to access resources
     */
    Item (int x, int y, int width, int height, Game game) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.game = game;
        game.getGrid().getNode((int) y / Game.TILEWIDTH, (int) x / Game.TILEHEIGHT).setItem(this);
    }

    /**
     * reset the item on grid position so it can be used by others
     */
    public void destroyItem() {
        game.getGrid().getNode((int) y / Game.TILEWIDTH, (int) x / Game.TILEHEIGHT).setItem(null);
    }
    
    /**
     * get height of item
     * @return <code>int</code> height of item
     */
    public int getHeight() {
        return height;
    }

    /**
     * get width of item
     * @return <code>int</code> width of item
     */
    public int getWidth() {
        return width;
    }

    /**
     * set x position of item
     * @param x <code>int</code> new x position of item
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * set y position of item 
     * @param y <code>int</code> new y position
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * get x position of item
     * @return <code>int</code> x position of item
     */
    public int getX() {
        return x;
    }

    /**
     * get y position of item
     * @return <code>int</code> y position of item
     */ 
    public int getY() {
        return y;
    }
    
    /**
     * draw item on Canvas
     * @param g <code>Graphics</code> object in charge of drawing
     */
    public abstract void render(Graphics g);
}
