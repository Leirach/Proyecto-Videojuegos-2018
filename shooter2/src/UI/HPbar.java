package UI;

import graphics.Assets;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * HealthBar UI object for player to show remaining health
 */
public class HPbar {
    private double totalHP;             // total health of player
    private int yPos;                   // vertical position of Health bar
    
    /**
     * Create a Health bar for player
     * @param totalHP <code>int</code> maximum health
     * @param gameHeight <code>int</code> vertical position
     */
    public HPbar (int totalHP, int gameHeight) {
        this.totalHP = totalHP;
        this.yPos = gameHeight - 32;
    }

    /**
     * set total of the player. Used, when player gets armor
     * @param totalHP <code>int</code> new amount of health
     */
    public void setTotalHP(double totalHP) {
        this.totalHP = totalHP;
    }
    
    /**
     * render the health-bar
     * @param g <code>Graphics</code> rendering buffer
     * @param hp <code>int</code> health amount
     */
    public void render(Graphics g, int hp){
        // draw border for health-bar
        g.drawImage(Assets.barFrame, 15, yPos-5, 210, 26, null);
        if (hp > 0){
            g.setColor(Color.green);
            g.fillRect(20, yPos, (int) (hp/totalHP * 200), 16);
        }
        g.setFont(new Font("default", Font.BOLD, 14));
        g.setColor(Color.WHITE);
        g.drawString("Health Points: " + hp + " / " + (int)totalHP, 25, yPos+13);
    }
}
