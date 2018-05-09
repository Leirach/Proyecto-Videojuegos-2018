/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actor;

import graphics.Animation;
import graphics.Assets;
import java.awt.Color;
import java.awt.Graphics;
import shooter2.Game;

/**
 * Swat enemy. Is tanky, takes a lot to be killed, has higher damage than Guard output, melee range attacks
 */
public class Swat extends Enemy {
      // super(x,y,width,height,game,life,range,score)
    /**
     * Create enemy type 'Swat'
     *
     * @param x <code>int</code> X position of the enemy
     * @param y <code>int</code> Y position of the enemy
     * @param width <code>int</code> width for the enemy
     * @param height <code>int</code> height of the enemy
     * @param game <code>Game</code> game object
     */
    private Animation swUp;      // animation for moving up
    private Animation swDown;    // animation for moving down
    private Animation swRight;   // animation for moving right
    private Animation swLeft;    // animation for moving right

    /**
     *
     * @param x  holds the horizontal position of  swat
     * @param y  holds the vertical position of swat
     * @param width holds the width of swat
     * @param height holds the height of swat
     * @param game holds the game object of swat
     */
    public Swat(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game, 120, 1, 40);
        
        this.swUp = new Animation(Assets.swUp, 180);
        this.swDown = new Animation(Assets.swDown, 180);
        this.swRight = new Animation(Assets.swRight, 180);
        this.swLeft = new Animation(Assets.swLeft, 180);
        
        damage = 40;
    }


    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.barFrame, getX(), getY()-5, 16, 5, null);
        if (life > 0){
            g.setColor(Color.green);
            g.fillRect(getX(), getY()-5, life*16/maxLife, 5);
        }
            // if has a path to move, take the next move and use it as direction for movement
        if(path!= null && path.size() > 0) {
                // x is vertical position in canvas
                // y is horizontal position in canvas
            int nextY = path.get(path.size()-1).posX * game.TILEHEIGHT;
            int nextX = path.get(path.size()-1).posY * game.TILEWIDTH;
            
            if(nextX > this.getX()) {
                g.drawImage(swRight.getCurrentFrame(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                swRight.tick();
            }
            else if(nextX < this.getX()) {
                g.drawImage(swLeft.getCurrentFrame(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                swLeft.tick();
            }
            else if(nextY < this.getY()) {
                g.drawImage(swUp.getCurrentFrame(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                swUp.tick();
            }
            else {
                g.drawImage(swDown.getCurrentFrame(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                swDown.tick();
            }
        }
        else {
            g.drawImage(swDown.getCurrentFrame(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
            swDown.tick();
        }
    }
    
    /**
     * Override of toString method. Used on save and load, using the name, can load the enemy saved
     * @return 
     */
    @Override
    public String toString(){
        return "Swat";
    }
}
