package Actor;

import graphics.Animation;
import graphics.Assets;
import java.awt.Color;
import java.awt.Graphics;
import shooter2.Game;

/**
 * Ranged enemy, deals low damage and has little life
 */
public class policeOfficer extends Enemy  {
    
    private Animation PoUp;      // animation for moving up
    private Animation PoDown;    // animation for moving down
    private Animation PoRight;   // animation for moving right
    private Animation PoLeft;    // animation for moving right

    
    /**
     *
     * @param x  holds the horizontal position of policeOfficer
     * @param y  holds the vertical position of policeOfficer
     * @param width holds the width of policeOfficer
     * @param height holds the height of policeOfficer
     * @param game holds the game object 
     */
    public policeOfficer(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game, 40, 3, 30);
        this.PoUp = new Animation(Assets.POUp, 180);
        this.PoDown = new Animation(Assets.PODown, 180);
        this.PoRight = new Animation(Assets.PORight, 180);
        this.PoLeft = new Animation(Assets.POLeft, 180);
        damage = 10;
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
                g.drawImage(PoRight.getCurrentFrame(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                PoRight.tick();
            }
            else if(nextX < this.getX()) {
                g.drawImage(PoLeft.getCurrentFrame(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                PoLeft.tick();
            }
            else if(nextY < this.getY()) {
                g.drawImage(PoUp.getCurrentFrame(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                PoUp.tick();
            }
            else {
                g.drawImage(PoDown.getCurrentFrame(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                PoDown.tick();
            }
        }
        else {
            g.drawImage(PoDown.getCurrentFrame(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
            PoDown.tick();
        }
    }
    
    /**
     * Override of toString method. Used on save and load, using the name, can load the enemy saved
     * @return 
     */
    @Override
    public String toString(){
        return "PO";
    }
}
