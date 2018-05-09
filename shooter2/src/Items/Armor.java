package Items;

import graphics.Assets;
import java.awt.Graphics;
import shooter2.Game;

/**
 * Armor that increases max health of the player
 */
public class Armor extends Item{

    private int armorHP;            // amount of health increase
    
    /**
     * Create armor item
     * @param x <code>int</code> x position of gun
     * @param y <code>int</code> y position of gun
     * @param width <code>int</code> width of gun
     * @param height <code>int</code> height of gun
     * @param game <code>Game</code> Game object to access resources
     */
    public Armor(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
       armorHP = 30; 
    }
    
    /**
     * get the amount of health increased by armor
     * @return <code>int</code> amount increased
     */
    public int getArmorHP(){
        return armorHP;
    }
    
    /**
     * set the increased amount of health. Used by multiplier of stats
     * @param armorHP <code>int</code> new amount of increase in health
     */
    public void setArmorHP(int armorHP) {
        this.armorHP = armorHP;
    }
    
    /**
     * override parent render
     * @param g <code>Graphics</code> object that draws the images on screen
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.armor, getX(), getY(), width, height, null);
    }
    
}
