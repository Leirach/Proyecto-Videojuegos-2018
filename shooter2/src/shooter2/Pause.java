/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shooter2;

import graphics.Assets;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Juan De León
 */
public class Pause {

   

    /**
     *  save button
     */
    public Rectangle save;

    /**
     *  load button
     */
    public Rectangle load;

    /**
     * exit button
     */
    public Rectangle exit;

    /**
     * resume button
     */
    public Rectangle resume;
    
    private Game game;
    
    /**
     *
     * @param game <code>Game</code> game object to access resources
     */
    public Pause(Game game) {
        this.game = game;
        
        resume = new Rectangle((game.getWidth()-105)/2, (game.getHeight() - 290)/2 , 210, 50);
        save = new Rectangle((game.getWidth()-105)/2, (game.getHeight() - 290)/2 + 50 + 15, 210, 50);
        load = new Rectangle((game.getWidth()-105)/2, (game.getHeight() - 290)/2 + 100 + 2*15, 210, 50);
        exit = new Rectangle((game.getWidth()-105)/2, (game.getHeight() - 290)/2 + 150 + 3*15, 210, 50);
        
    }
    
    /**
     *
     * @param g <code>Graphics</code> object in charge of drawing
     */
    public void render(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, game.getWidth(), game.getHeight());
        g.drawImage(Assets.pauseResume, resume.x, resume.y, resume.width, resume.height, null);
        g.drawImage(Assets.pauseSave, save.x, save.y, save.width, save.height, null);
        g.drawImage(Assets.pauseLoad, load.x, load.y, load.width, load.height, null);
        g.drawImage(Assets.pauseExitGame, exit.x, exit.y, exit.width, exit.height, null);
        
    }
}
