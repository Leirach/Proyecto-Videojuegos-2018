/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Items;

import graphics.Assets;
import java.awt.Graphics;
import shooter2.Game;

/**
 *
 * @author Juan De León
 */
public class Medkit extends Item{
    
    private int healthCure;
    
    /**
     * Create Mekit Item
     * @param x <code>int</code> x position of gun
     * @param y <code>int</code> y position of gun
     * @param width <code>int</code> width of gun
     * @param height <code>int</code> height of gun
     * @param game <code>Game</code> Game object to access resources
     */
    public Medkit(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        healthCure = 30;
    }

    /**
     * get amount of health it will cure
     * @return <code>int</code> amount of cured health
     */
    public int getHealthCure() {
        return healthCure;
    }

    /**
     * set the amount of health to cure. Used by multipliers of stats
     * @param healthCure <code>int</code> new health cure
     */
    public void setHealthCure(int healthCure) {
        this.healthCure = healthCure;
    }    
    
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.medkit, this.getX(), this.getY(), width, height, null);
    }
    
}
