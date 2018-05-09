/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Items;

import shooter2.Game;

/**
 *
 * @author Juan De León
 */
public abstract class Weapon extends Item {

    /**
     * damage modifier to player
     */
    protected int damage;
    
    /**
     * Abstract class to generate weapons
     * @param x <code>int</code> x position of gun
     * @param y <code>int</code> y position of gun
     * @param width <code>int</code> width of gun
     * @param height <code>int</code> height of gun
     * @param game <code>Game</code> Game object to access resources
     */
    public Weapon(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);

    }
    
    /**
     * set damage to modify to player. Used by modifiers
     * @param damage <code>int</code> new damage modifier
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }
    
    /**
     * get the amount of damage added by weapon
     * @return <code>int</code> amount of damage done
     */
    public int getDamage() {
        return damage;
    }    
}
