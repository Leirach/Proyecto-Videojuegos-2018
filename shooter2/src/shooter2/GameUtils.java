/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shooter2;

import static shooter2.Game.TILEHEIGHT;
import static shooter2.Game.TILEWIDTH;

/**
 *
 * @author Juan De León
 */
public class GameUtils {
    
    /**
     * use the game object
     * @param game <code>Game</code> Game object to access resources
     */
    public static void playerReposition(Game game){
            // player reposition
        int playerX = (int) (Math.random() * game.getLayout().getMaxWidth());
        int playerY = (int) (Math.random() * game.getLayout().getMaxHeight());
        while (game.getGrid().getNode(playerY, playerX).isOccupied() ||
                !game.getGrid().getNode(playerY, playerX).isWalkable()) {
            playerX = (int) (Math.random() * game.getLayout().getMaxWidth());
            playerY = (int) (Math.random() * game.getLayout().getMaxHeight());
        }
        
        game.getPlayer().setX(playerX *32);
        game.getPlayer().setY(playerY *32);
    }
    
    /**
     *
     * @param game <code>Game</code> Game object to access resources
     */
    public static void playerOnStairs(Game game) {
        int playerX = (int) (game.getPlayer().getX() / TILEWIDTH);
        int playerY = (int) (game.getPlayer().getY() / TILEHEIGHT);

        if(game.getLayout().getTileID(playerY,playerX) == 2){
            if(game.getKeyManager().isKey("SPA")) {
                game.nextLevel();
            }
        }
    }
    
    /**
     * show message for the player
     */
    public static void armorBrokenMsg(){
        //display a message for 3 seconds
    }
}
