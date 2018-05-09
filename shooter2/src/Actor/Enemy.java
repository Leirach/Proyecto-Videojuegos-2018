package Actor;

import Utils.Node;
import java.util.ArrayList;
import shooter2.Game;


/**
 * Enemy object, parent of other enemies
 */
public abstract class Enemy extends Actor {
    
    /**
     * enemy movement path travel to player
     */
    protected ArrayList<Node> path;

    /**
     * score points for enemy
     */
    protected int score;

    /**
     * max health of enemy
     */
    protected int maxLife;
    
    /**
     *
     * @param x <code>int</code> value of x position
     * @param y <code>int</code> value of y position
     * @param width <code>int</code> value of width position
     * @param height <code>int</code> value of height position
     * @param game <code>Game</code> object 
     * @param life <code>int </code> value to know the life of the enemy
     * @param range <code>int</code> value for the enemy attack range
     * @param score <code>int</code> score value on killing enemy
     */
    public Enemy(int x, int y, int width, int height, Game game, int life, int range, int score) {
        super(x, y, width, height, game, range);
        this.life = life;
        this.range = range;
        this.score = score;
        this.maxLife=life;
        game.getGrid().getNode((int) (y/Game.TILEHEIGHT), (int) (x/Game.TILEWIDTH)).setOwner(this);
        game.getGrid().getNode((int) (y/Game.TILEHEIGHT), (int) (x/Game.TILEWIDTH)).setOccupied(true);
        
        
    }

    /**
     * set max life of enemy object. Used on setting of multipliers
     * @param maxLife <code>int</code> maximum amount of life for enemy
     */
    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }
    
    /**
     * get the enemy death score value
     * @return <code>int</code> value of score
     */
    public int getScore() {
        return score;
    }
    
    /**
     * get the enemy death score value
     * @param score having the score value for enemy
     */ 
    public void setScore(int score) {
        this.score = score;
    }
    
    /**
     * function to set reset the occupied grid position to false, so it can be used by other enemies and player
     */
    public void setDeath() {
        game.getGrid().getNode((int)this.getY()/game.TILEHEIGHT, (int)this.getX()/game.TILEWIDTH).setOccupied(false);
        game.getGrid().getNode((int)this.getY()/game.TILEHEIGHT, (int)this.getX()/game.TILEWIDTH).setOwner(null);
        
    }
        
    /**
     * set path to traverse for enemy
     */
    public void setPath() {
            // enemy position
        int eneX = (int)this.getX()/game.TILEWIDTH;
        int eneY = (int)this.getY()/game.TILEHEIGHT;
            
            // player position
        int playX = (int)game.getPlayer().getX()/game.TILEWIDTH;
        int playY = (int)game.getPlayer().getY()/game.TILEHEIGHT;
        
            // generate path
            // the rows of the grid correspond to the height of the Canvas
            // the columns of the grid correspond to the width of the canvas
            // grid position X == actor position Y
            // grid position Y == actor position X
        path = game.getGrid().pathFinding(eneY, eneX, playY, playX);
    }
    
    @Override
    public void tick() {
            // positions in x and y
        int x = (int) getX() / game.TILEHEIGHT;
        int y = (int) getY() / game.TILEWIDTH;

            // player positions relative to the enemy
        int playerX = (int) game.getPlayer().getX() / game.TILEWIDTH - x;
        int playerY = (int) game.getPlayer().getY() / game.TILEHEIGHT - y;

            // if player is within range, shoor the player
        if (Math.abs(playerX) <= range && Math.abs(playerY) <= range) {
            game.createEnemyBullet(this.getX() + this.getWidth() / 2,
                    this.getY() + this.getHeight() / 2, damage, this);
        } 
        else {
            // create path for the enemy
            setPath();
            
                // size of path array and the next move for the enemy
            int size = path.size() > 0 ? path.size() - 1 : 0;

            if (size != 0) {
                Node nextMove = path.get(size);

                    // if the next position is occupied do not move
                if (!game.getGrid().getNode(nextMove.posX, nextMove.posY).isOccupied()) {
                        // revert occupied and change occupation of next move
                    game.getGrid().getNode(y, x).setOccupied(false);
                    game.getGrid().getNode(y, x).setOwner(null);
                    this.setX(nextMove.posY * game.TILEHEIGHT);
                    this.setY(nextMove.posX * game.TILEWIDTH);
                    game.getGrid().getNode(nextMove.posX, nextMove.posY).setOccupied(true);
                    game.getGrid().getNode(nextMove.posX, nextMove.posY).setOwner(this);

                    path.remove(path.size() - 1);
                }
            }
        }
    }
    
    @Override
    public void attacks(Actor act) {
        //if player has armor, reduce armor until it gets to 0
        if (act instanceof Player && ((Player)act).getLife() > 0){
            ((Player)act).setLife(((Player)act).getLife() - this.getDamage());
            //check if armor breaks
            if (((Player)act).getLife()<= 0){
                ((Player)act).setLife(0);
                //armorBrokenMsg();
            }
        }
        
    }

}
