package Items;

import graphics.Assets;
import java.awt.Graphics;
import shooter2.Game;

/**
 * Ak weapon to be created and used by the player
 */
public class Ak extends Weapon{
    
    /**
     * Create Ak weapon
     * @param x <code>int</code> x position of gun
     * @param y <code>int</code> y position of gun
     * @param width <code>int</code> width of gun
     * @param height <code>int</code> height of gun
     * @param game <code>Game</code> Game object to access resources
     */
    public Ak(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        damage = 2;
    }
    
    /**
     * draw AK on screen
     * @param g <code>Graphics</code> object that draws the images on screen
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.ak, this.getX(), getY(), width, height, null);
    }
}
