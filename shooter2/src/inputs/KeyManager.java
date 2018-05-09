/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author antoniomejorado
 */
public class KeyManager implements KeyListener {

    /**
     * flag to move up the player
     */
    public boolean W;     

    /**
     * flag to move down the player
     */
    public boolean S;     

    /**
     * flag to move left the player
     */
    public boolean A;     

    /**
     * flag to move right the player
     */
    public boolean D;      

    /**
     * to pause the game
     */
    public boolean esc;      

    /**
     * pause the game
     */
    public boolean P;       

    /**
     * reset
     */
    public boolean R;       

    /**
     * Store for keys pressed
     */
    public boolean spa;   //flag to continue to next level
    private boolean keys[];     // to store all the flags for every key
    private boolean last[];     // store value of previous press 

    /**
     * constructor 
     */
    public KeyManager() {
        keys = new boolean[256];
        last = new boolean[256];
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // set true to every key pressed
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // set false to every key released
        keys[e.getKeyCode()] = false;
    }
    /**
     * get whether the key was pressed and not kept pressed
     * @param key <code>String</code> name of the key pressed
     * @return <code>boolean</code> value of the movement
     */
    public boolean isKey(String key) {
            // variables to use
        boolean tecla = false;
        int EventKey = 0;
        key.toUpperCase();
            // setting variable value according to parameters
        switch (key) {
            case "W":
                tecla = W;
                EventKey = KeyEvent.VK_W;
                break;
            case "A":
                tecla = A;
                EventKey = KeyEvent.VK_A;
                break;
            case "S":
                tecla = S;
                EventKey = KeyEvent.VK_S;
                break;
            case "D":
                tecla = D;
                EventKey = KeyEvent.VK_D;
                break;
            case "ESC":
                tecla = esc;
                EventKey = KeyEvent.VK_ESCAPE;
                break;
            case "P":
                tecla = P;
                EventKey = KeyEvent.VK_P;
                break;
            case "R":
                tecla = R;
                EventKey = KeyEvent.VK_R;
                break;
           case "SPA":
                tecla = spa;
                EventKey = KeyEvent.VK_SPACE;
                break;
                
        }
            // logic for one movement on press
        if (tecla) {
            if (tecla != last[EventKey]) {
                last[EventKey] = true;
                return true;
            }
        }
        last[EventKey] = tecla;
        return false;
    }
    
    /**
     * to enable or disable moves on every tick
     */
    public void tick() {
        W = keys[KeyEvent.VK_W];
        S = keys[KeyEvent.VK_S];
        A = keys[KeyEvent.VK_A];
        D = keys[KeyEvent.VK_D];
        esc = keys[KeyEvent.VK_ESCAPE];
        P = keys[KeyEvent.VK_P];
        R = keys[KeyEvent.VK_R];
        spa = keys[KeyEvent.VK_SPACE];
    }
}
