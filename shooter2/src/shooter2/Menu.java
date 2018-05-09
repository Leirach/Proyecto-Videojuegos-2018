/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shooter2;

import graphics.Assets;
import java.awt.Color;
//import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author gabrielcarrion
 */
public class Menu {
    
    /**
     *  new game button
     */
    private Rectangle NGbutton;    

    /**
     * load game button
     */
    public Rectangle LGbutton;

    /**
     * exit button
     */
    public Rectangle Ebutton;  
    
    private final int buttonWidth = 300, buttonHeight = 50;
    /**
     */
    public Game game; 
    
    Menu(Game game){
        this.game = game;
        NGbutton = new Rectangle (game.getWidth()/2 - buttonWidth/2, 175, 
                buttonWidth, buttonHeight);
        LGbutton = new Rectangle (game.getWidth()/2 - buttonWidth/2, 275,
                buttonWidth, buttonHeight);
        Ebutton = new Rectangle (game.getWidth()/2 - buttonWidth/2, 375,
                buttonWidth, buttonHeight);
    }  

    public Rectangle getEbutton() {
        return Ebutton;
    }

    public Rectangle getLGbutton() {
        return LGbutton;
    }

    public Rectangle getNGbutton() {
        return NGbutton;
    }
    
    
    
    /**
     *
     * @param g <code>Graphics</code> object in charge of drawing
     */
    
    public void render (Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        
        g.setColor(Color.white);
        g.drawImage(Assets.backgroundP, 0, 0,game.getWidth(),game.getHeight(),null);
        g.drawImage(Assets.title, Game.TILEWIDTH, 75, null);


        g2d.draw(NGbutton);
        g.drawImage(Assets.newGame, NGbutton.x, NGbutton.y, 300, 50, null);
        g2d.draw(LGbutton);
        g.drawImage(Assets.loadGame, LGbutton.x, LGbutton.y, 300, 50, null);
        g2d.draw(Ebutton);
        g.drawImage(Assets.exit, Ebutton.x, Ebutton.y, 300, 50, null);
    }
    
}
