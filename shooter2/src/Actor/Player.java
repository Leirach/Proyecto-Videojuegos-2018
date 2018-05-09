package Actor;

import graphics.Animation;
import graphics.Assets;
import java.awt.Graphics;
import shooter2.Game;

/**
 * Create Player object
 */
public class Player extends Actor {
    private boolean moved;              // get whether the player has moved
    private Animation animationUp;      // animation for moving up
    private Animation animationDown;    // animation for moving down
    private Animation animationRight;   // animation for moving right
    private Animation animationLeft;    // animation for moving right
    private int preX, preY;             // previous position of player
    private int moves;                  // Current moves from player
    private int maxLife;                // max amount of health for player


    /**
     * Generate player object
     * @param x <code>int</code> x position for player
     * @param y <code>int</code> y position for player
     * @param width <code>int</code> width of player
     * @param height <code>int</code> height of player
     * @param game <code>Game</code> game object
     * @param range <code>int</code> range of attack for player
     */
    public Player(int x, int y, int width, int height, Game game, int range) {
        super(x, y, width, height, game, range);
        moved = false;
        life = 140;
        maxLife = life;
        this.animationUp = new Animation(Assets.playerUp,180);
        this.animationDown = new Animation(Assets.playerDown,180);
        this.animationRight = new Animation(Assets.playerRight,180);
        this.animationLeft = new Animation(Assets.playerLeft,180);
        damage = 30;
        preX = x;
        preY = y;
            // set position in grid as occupied
        game.getGrid().getNode((int)y/game.TILEHEIGHT, (int)x/game.TILEWIDTH).setOccupied(true);
        game.getGrid().getNode((int)y/game.TILEHEIGHT, (int)x/game.TILEWIDTH).setOwner(this);
    }

    @Override
    public int getDamage() {
        return super.getDamage(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Set the damage dealt by player
     * @param damage <code>int</code> amount of damage dealt
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * get if the player has moved
     * @return <code>boolean</code> value with the movement
     */
    public boolean isMoved() {
        return moved;
    }

    /**
     * set the moves left for the actor
     * @param moved <code>boolean</code>whether the player has moved
     */
    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    /**
     * get moves done
     * @return <code>int</code> new value for type
     */
    public int getMoves(){
        return moves;
    }

    /**
     * set the moves that player has had
     * @param moves <code>int</code> amount of moves taken
     */
    public void setMoves(int moves){
        this.moves=moves;
    }

    /**
     * Get the maximum health of the player
     * @return <code>int</code> max health
     */
    public int getMaxLife() {
        return maxLife;
    }

    /**
     * set the maximum health of the player. Used by powerUps
     * @param maxLife <code>int</code> new maximum health
     */
    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }
    
    @Override
    public void setLife(int life) {
            // not exceed max life amount
        life = life > maxLife ? maxLife : life;
        super.setLife(life); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void tick() {
            // player movement logic
            // A is left
            // D is Right
            // W is up
            // S is down
        int x = (int)this.getX()/game.TILEWIDTH;
        int y = (int)this.getY()/game.TILEHEIGHT;

            // check pressed key
        if (game.getKeyManager().isKey("A")) {
                    moves++;

                // check if x wont leave map
            if(x > 0) {
                    // check if player can move over next position
                if(game.getLayout().getTile(y, x - 1).isPassable() && !game.getGrid().getNode(y, x - 1).isOccupied()) {
                        // set previous position to use on rendering
                    preX = this.getX();
                    preY = this.getY();
                        // reset occupation of previous position and set in next position
                        // move player
                    game.getGrid().getNode(y, x).setOccupied(false);
                    game.getGrid().getNode(y, x).setOwner(null);
                    setX(getX() - Game.TILEWIDTH);
                    game.getGrid().getNode(y, x - 1).setOccupied(true);
                    game.getGrid().getNode(y, x).setOwner(this);
                    moved = true;
                }
            }
        }
        else if (game.getKeyManager().isKey("D")) {
                    moves++;

            if(x < game.getLayout().getMaxWidth() - 1) {
                if(game.getLayout().getTile(y, x + 1).isPassable() && !game.getGrid().getNode(y, x + 1).isOccupied()) {
                    preX = this.getX();
                    preY = this.getY();

                    game.getGrid().getNode(y, x).setOccupied(false);
                    game.getGrid().getNode(y, x).setOwner(null);
                    setX(getX() + Game.TILEWIDTH);
                    game.getGrid().getNode(y, x + 1).setOccupied(true);
                    game.getGrid().getNode(y, x).setOwner(this);
                    moved = true;
                }
            }
        }
        else if (game.getKeyManager().isKey("W")) {
                    moves++;

            if(y > 0) {
                if(game.getLayout().getTile(y - 1, x).isPassable() && !game.getGrid().getNode(y - 1, x).isOccupied()) {
                    preX = this.getX();
                    preY = this.getY();

                    game.getGrid().getNode(y, x).setOccupied(false);
                    game.getGrid().getNode(y, x).setOwner(null);
                    setY(getY() - Game.TILEHEIGHT);
                    game.getGrid().getNode(y - 1, x).setOccupied(true);
                    game.getGrid().getNode(y, x).setOwner(this);
                    moved = true;
                }
            }
        }
        else if (game.getKeyManager().isKey("S")) {
                    moves++;

            if(y < game.getLayout().getMaxHeight() - 1) {
                if(game.getLayout().getTile(y + 1, x).isPassable() && !game.getGrid().getNode(y + 1, x).isOccupied()) {
                    preX = this.getX();
                    preY = this.getY();

                    game.getGrid().getNode(y, x).setOccupied(false);
                    game.getGrid().getNode(y, x).setOwner(null);
                    setY(getY() + Game.TILEHEIGHT);
                    game.getGrid().getNode(y + 1, x).setOccupied(true);
                    game.getGrid().getNode(y, x).setOwner(this);
                    moved = true;
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {

        if(preX > this.getX() ) {
            g.drawImage(animationLeft.getCurrentFrame(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
            animationLeft.tick();
        }
        else if (preX < this.getX()){
            g.drawImage(animationRight.getCurrentFrame(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
            animationRight.tick();
        }
        else if (preY > this.getY()){
            g.drawImage(animationUp.getCurrentFrame(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
            animationUp.tick();
        }
        else {
            g.drawImage(animationDown.getCurrentFrame(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
            animationDown.tick();
        }
    }

    @Override
    public void attacks(Actor act) {
        act.setLife(act.getLife() - this.getDamage());
        game.getMulti().addEnemyHealthLoss(this.getDamage());
    }
}
