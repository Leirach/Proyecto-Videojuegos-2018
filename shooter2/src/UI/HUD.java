package UI;

import java.awt.Graphics;
import shooter2.Game;

/**
 * HUD object to add Display of different stats
 */
public class HUD {
    private HPbar hp;       // health-bar
    private Game game;      // game
    
    /**
     * create player HUD 
     * @param game <code>Game</code> game object
     */
    public HUD(Game game){
        this.game = game;
        hp = new HPbar(game.getPlayer().getLife(), game.getHeight());
    }

    /**
     * get health-Bar object to modify the max-health
     * @return <code>HPBar</code> object of the Health bar
     */
    public HPbar getHp() {
        return hp;
    }
    
    /**
     * render hud elements
     * @param g <code>Graphics</code> drawing buffer
     */
    public void render (Graphics g){
        hp.render(g, game.getPlayer().getLife());
    }
}
