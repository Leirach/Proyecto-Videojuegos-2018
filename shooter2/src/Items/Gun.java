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
public class Gun extends Weapon{
       
    /**
     *
     * @param x <code>int</code> x position of gun
     * @param y <code>int</code> y position of gun
     * @param width <code>int</code> width of gun
     * @param height <code>int</code> height of gun
     * @param game <code>Game</code> Game object to access resources
     */
    public Gun(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        damage = 1;
    }
    
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.gun, this.getX(), this.getY(), width, height, null);
    }
}
