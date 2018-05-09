package Actor;

import graphics.Animation;
import graphics.Assets;
import java.awt.Color;
import java.awt.Graphics;
import shooter2.Game;

/**
 * Melee type enemy, tries to get close to player to attack, has some armor, can
 * receive several hits before dying.
 */
public class Guardia extends Enemy {
    // super(x,y,width,height,game,life,range,score)

    private Animation copUp;      // animation for moving up
    private Animation copDown;    // animation for moving down
    private Animation copRight;   // animation for moving right
    private Animation copLeft;    // animation for moving right

    /**
     * Create enemy type 'Guardia'
     *
     * @param x <code>int</code> X position of the enemy
     * @param y <code>int</code> Y position of the enemy
     * @param width <code>int</code> width for the enemy
     * @param height <code>int</code> height of the enemy
     * @param game <code>Game</code> game object
     */
    public Guardia(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game, 50, 1, 15);

        this.copUp = new Animation(Assets.wCopUp, 180);
        this.copDown = new Animation(Assets.wCopDown, 180);
        this.copRight = new Animation(Assets.wCopRight, 180);
        this.copLeft = new Animation(Assets.wCopLeft, 180);

        damage = 15;
    }
    
    @Override
    public void render(Graphics g) {
                // draw border for health-bar
        g.drawImage(Assets.barFrame, getX(), getY()-5, 16, 5, null);
            // draw healthbar
        if (life > 0){
            g.setColor(Color.green);
            g.fillRect(getX(), getY()-5, life*16/maxLife, 5);
        }
            // if has a path to move, take the next move and use it as direction for movement
        if(path!= null && path.size() > 0) {
                // x is vertical position in canvas
                // y is horizontal position in canvas
            int nextY = path.get(path.size()-1).posX * Game.TILEHEIGHT;
            int nextX = path.get(path.size()-1).posY * Game.TILEWIDTH;
            
            if(nextX > this.getX()) {
                g.drawImage(copRight.getCurrentFrame(), this.getX(), this.getY(), 
                        this.getWidth(), this.getHeight(), null);
                copRight.tick();
            }
            else if(nextX < this.getX()) {
                g.drawImage(copLeft.getCurrentFrame(), this.getX(), this.getY(), 
                        this.getWidth(), this.getHeight(), null);
                copLeft.tick();
            }
            else if(nextY < this.getY()) {
                g.drawImage(copUp.getCurrentFrame(), this.getX(), this.getY(), 
                        this.getWidth(), this.getHeight(), null);
                copUp.tick();
            }
            else {
                g.drawImage(copDown.getCurrentFrame(), this.getX(), this.getY(), 
                        this.getWidth(), this.getHeight(), null);
                copDown.tick();
            }
        }
        else {
            g.drawImage(copDown.getCurrentFrame(), this.getX(), this.getY(), 
                    this.getWidth(), this.getHeight(), null);
            copDown.tick();
        }
    }
    
    /**
     * Override of toString method. Used on save and load, using the name, can load the enemy saved
     * @return 
     */
    @Override
    public String toString(){
        return "Guardia";
    }
}
