/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shooter2;

import Actor.Bullet;
import Actor.Player;
import Actor.Enemy;
import Actor.Guardia;
import Actor.Swat;
import Actor.policeOfficer;
import Items.Ak;
import Items.Armor;
import Items.Gun;
import Items.Item;
import Items.Medkit;
import Items.Weapon;
import UI.HUD;

import static Utils.Files.loadFile;
import Utils.Node;
import static Utils.Files.saveFile;

import Utils.NodeGrid;
import tiles.Layout;
import graphics.Assets;
import graphics.Display;
import inputs.KeyManager;
import inputs.MouseManager;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/**
 *
 * @author antoniomejorado
 */
public class Game implements Runnable {

    //display
    private BufferStrategy bs;                  // to have several buffers when displaying
    private Graphics g;                         // to paint objects
    private Display display;                    // to display in the game

    //window
    String title;                               // title of the window
    private int width;                          // width of the window
    private int height;                         // height of the window

    //game vars
    private Thread thread;                      // thread to create the game
    private boolean running;                    // to set the game
    private boolean paused;                     // to know it the game is paused
    private Multipliers multi;                  // game difficulty multipliers
    private int levelTurns;                     // turns so far taken
    private int currentLevel;                   // current level

    //inputs
    private KeyManager keyManager;              // to manage the keyboard
    private MouseManager mouseManager;          // manage mouse click and movement
    private int minimumTime;

    //game items
    private Player player;                      // to use a player
    private ArrayList<Enemy> enemies;           // enemy object container
    private ArrayList<Bullet> enemyBullets;     // bullet list
    private Bullet playerBullet;                // bullet object
    private Layout map;                         // map to play on
    private NodeGrid grid;                      // grid to represent Layout map
    private Menu menu;                          // main menu
    private Pause pause;                        // pause screen
    private boolean shot;                       // store whether player shot
    private int score;                          // save score for the player
    private boolean onStair;                    // check if player is on the stairs
    private double guardiaChance;
    private double swatChance;
    private double copChance;
    private ArrayList<Item> items;

    //UI
    private HUD hud;

    //size of tiles, to which to size items

    /**
     * unchangeable value
     */
    public static final int TILEWIDTH = 32,

    /**
     * setting the Tile width
     */
    TILEHEIGHT = 32;

    //size of the map

    /**
     *
     */
    public static final int MAPWIDTH = 25,

    /**
     * setting the map height
     */
    MAPHEIGHT = 15;

    private enum STATE {
        MENU,
        GAME,
        GAME_OVER,
        PAUSE
    };

    private STATE state = STATE.MENU;

    /**
     * to create title, width and height and set the game is still not running
     *
     * @param title to set the title of the window
     * @param width to set the width of the window
     * @param height to set the height of the window
     */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        paused = false;
        shot = false;
        levelTurns = 1;
        currentLevel = 1;
        onStair = false;

    }

    /**
     * initializing the display window of the game
     */
    private void init() {
        display = new Display(title, getWidth(), getHeight());
        Assets.init();
        Assets.Background.setLooping(true);
        Assets.Background.play();
        map = new Layout();
        map.generateWorld(15,25);
        minimumTime = (int)(Math.random()*3.0);


        menu = new Menu(this);
        grid = new NodeGrid(this);

        int playerX = (int) (Math.random() * map.getMaxWidth());
        int playerY = (int) (Math.random() * map.getMaxHeight());

        while (grid.getNode(playerY, playerX).isOccupied() ||
                !grid.getNode(playerY, playerX).isWalkable() ||
                map.getTileTexture(playerY, playerX).getId() == 17) {
            playerX = (int) (Math.random() * map.getMaxWidth());
            playerY = (int) (Math.random() * map.getMaxHeight());
        }
        player = new Player(playerX * 32, playerY * 32, TILEWIDTH, TILEHEIGHT, this, 1);



        multi = new Multipliers(this);
        multi.setAvgTurns();

        enemies = new ArrayList<>();
        enemyGen();
        
        items = new ArrayList<>();
        itemGen();

        enemyBullets = new ArrayList<>();

        guardiaChance = 100;
        copChance = 0;
        swatChance = 0;

        pause = new Pause(this);
        hud = new HUD(this);

        multi.setInitialEnemies(enemies.size());

        display.getJframe().addKeyListener(keyManager);
        display.getJframe().addMouseListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
    }

    /**
     * regenerate map and enemies. Used when going to next level.
     */
    public void nextLevel() {
        // map regen
        map.generateWorld(MAPHEIGHT, MAPWIDTH);
        // grid regen
        grid = new NodeGrid(this);

        multi.setMultipliers();

        // player reposition
        int playerX = (int) (Math.random() * map.getMaxWidth());
        int playerY = (int) (Math.random() * map.getMaxHeight());
        while (grid.getNode(playerY, playerX).isOccupied() ||
                !grid.getNode(playerY, playerX).isWalkable() ||
                (map.getStairX() == playerX && map.getStairY() == playerY ||
                grid.getNode(playerY, playerX).getItem() != null)) {
            playerX = (int) (Math.random() * map.getMaxWidth());
            playerY = (int) (Math.random() * map.getMaxHeight());
        }

        player.setX(playerX * 32);
        player.setY(playerY * 32);

        multi.setAvgTurns();

        // enemies reset
        enemies.removeAll(enemies);
        
        // items reset
        items.removeAll(items);

        // empty enemyBullet
        enemyBullets.removeAll(enemies);

        // shot false
        shot = false;
        playerBullet = null;
        levelTurns = 1;
        currentLevel++;

        enemyGen();
        itemGen();

    }




    /**
     * reset game. Used on game over
     */
    public void resetGame() {
        // shot false
        shot = false;
        playerBullet = null;
        score = 0;
        levelTurns = 1;
        currentLevel = 1;
        
        // map regen
        map.generateWorld(MAPHEIGHT, MAPWIDTH);
        // grid regen
        grid = new NodeGrid(this);

            // new player generation on restart
        playerGen();
        hud = new HUD(this);

        // enemies reset
        enemies.removeAll(enemies);
        enemyGen();
        
        items = new ArrayList<>();
        itemGen();
        
        // empty enemyBullet
        enemyBullets.removeAll(enemyBullets);

        
        
        multi = new Multipliers(this);
        multi.setAvgTurns();
        
        state = STATE.GAME;
    }

    /**
     *
     * @param posX <code>int</code> horizontal position
     * @param posY <code>int</code> vertical position 
     * @param damage <code>int</code> damage magnitude of bullet
     * @param enemy <code>enemy</code> object to use Enemy class
     */
    public void createEnemyBullet(int posX, int posY, int damage, Enemy enemy) {
        Bullet bullet = new Bullet(posX, posY, 6, 6, this, 1, enemy);

        int playerX = player.getX() + player.getWidth() / 2;
        int playerY = player.getY() + player.getHeight() / 2;

        int enemyX = enemy.getX() + enemy.getWidth() / 2;
        int enemyY = enemy.getY() + enemy.getHeight() / 2;

        bullet.makeLine(new Point(enemyX, enemyY), new Point(playerX, playerY));
        enemyBullets.add(bullet);
    }
    
    /**
     * item random generation 
     */
    public void itemGen() {
        double powerUpSpawn = multi.getPowerUpSpawnScore();
        double powerUpSpawnMult = multi.getPowerUpSpawnMult();
        double powerUpStatScore = multi.getPowerUpStatScore();
        double powerUpStatMult = multi.getPowerUpStatMult();
        
        int rand = (int) (Math.floor(powerUpSpawn*powerUpSpawnMult + 
                (Math.random() * powerUpSpawn * powerUpSpawnMult)));
        
        for(int i = 0; i < rand; i++) {
            int posX = (int) (Math.random() * map.getMaxWidth());
            int posY = (int) (Math.random() * map.getMaxHeight());

            while (grid.getNode(posY, posX).isOccupied() ||
            !grid.getNode(posY, posX).isWalkable() || 
            grid.getNode(posY, posX).getItem() != null ||
            (map.getStairX() == posY && map.getStairY() == posX)) {
                
                posX = (int) (Math.random() * map.getMaxWidth());
                posY = (int) (Math.random() * map.getMaxHeight());
            }
            
            int temp = (int) (Math.random() * 100);
            
                // medkit prob = 0.20
                // armor prob = 0.20
                // ak prob = 0.30
                // gun prob = 0.30
            if(temp <= 20) {
                items.add(new Medkit(posX*32, posY*32, TILEWIDTH, TILEHEIGHT, this));
            }
            else if (temp <= 40) {
                items.add(new Armor(posX*32, posY*32, TILEWIDTH, TILEHEIGHT, this));
            }
            else if (temp <= 70) {
                items.add(new Ak(posX*32, posY*32, TILEWIDTH, TILEHEIGHT, this));
            }
            else if (temp <= 100) {
                items.add(new Gun(posX*32, posY*32, TILEWIDTH, TILEHEIGHT, this));
            }
                // modify stats of items created based on multipliers
            Item ite = items.get(items.size() - 1);
            if(ite instanceof Weapon) {
                ((Weapon) ite).setDamage((int) Math.floor(((Weapon) ite).getDamage()*powerUpStatScore*powerUpStatMult));
            }
            else if (ite instanceof Medkit) {
                ((Medkit) ite).setHealthCure((int) Math.floor(((Medkit) ite).getHealthCure()*powerUpStatScore*powerUpStatMult));
            }
            else if (ite instanceof Armor) {
                ((Armor) ite).setArmorHP((int) Math.floor(((Armor) ite).getArmorHP()*powerUpStatScore*powerUpStatMult));
            }
        }
    }
    

    /**
     * function to generate enemies
     */
    private void enemyGen() {
            // level 1 will have only 2 enemies (basic enemies)
        if(currentLevel == 1) {
            for(int i = 0; i < 2; i++) {
                int posX = (int) (Math.random() * map.getMaxWidth());
                int posY = (int) (Math.random() * map.getMaxHeight());

                while (grid.getNode(posY, posX).isOccupied() ||
                !grid.getNode(posY, posX).isWalkable()) {
                    posX = (int) (Math.random() * map.getMaxWidth());
                    posY = (int) (Math.random() * map.getMaxHeight());
                }
                enemies.add(new Guardia(posX * 32, posY * 32, TILEWIDTH, TILEHEIGHT, this));

            }

            multi.setEnemyHealth();
            return;
        }

        double healthMult = multi.getEnemyHealthMult();
        double healthBase = multi.getEnemyHealthScore();
        double maxEnemies = multi.getEnemyMaxSpawnBase();
        double maxEnemiesMult = multi.getEnemyMaxSpawnMult();
        double scoreIncrease = multi.getScoreIncrease();
        double scoreMult = multi.getScoreMult();

        //double randEnemies = ((Math.random() * maxEnemies * maxEnemiesMult) + maxEnemies);
        int randEnemies = (int) Math.floor(maxEnemies*maxEnemiesMult +
                (Math.random()*maxEnemies*maxEnemiesMult));
        
            // different level generates diferent probability to spawn
        switch(currentLevel) {
            case 5:
                guardiaChance = 85;
                swatChance = 5;
                copChance = 10;
                break;
            case 10:
                guardiaChance = 75;
                swatChance = 5;
                copChance = 20;
                break;
            case 15:
                guardiaChance = 65;
                swatChance = 10;
                copChance = 25;
                break;
            case 20:
                guardiaChance = 50;
                swatChance = 15;
                copChance = 35;
                break;
            case 25:
                guardiaChance = 40;
                swatChance = 20;
                copChance = 20;
                break;
        }

        // generate enemies
        for(int i = 0; i < randEnemies; i++) {
            int posX = (int) (Math.random() * map.getMaxWidth());
            int posY = (int) (Math.random() * map.getMaxHeight());


            while (grid.getNode(posY, posX).isOccupied() || 
                    !grid.getNode(posY, posX).isWalkable()) {
                posX = (int) (Math.random() * map.getMaxWidth());
                posY = (int) (Math.random() * map.getMaxHeight());
            }

            int rand = (int) (Math.random() * 100);
            if(rand <= guardiaChance) {
                enemies.add(new Guardia(posX*32, posY*32, TILEWIDTH, TILEHEIGHT, this));
            }
            else if(rand <= swatChance + guardiaChance) {
                enemies.add(new Swat(posX*32, posY*32, TILEWIDTH, TILEHEIGHT, this));
            }
            else if(rand <= swatChance + guardiaChance + copChance) {
                enemies.add(new policeOfficer(posX*32, posY*32, TILEWIDTH, TILEHEIGHT, this));
            }
            Enemy ene = enemies.get(enemies.size() - 1);
            ene.setLife((int) Math.floor(ene.getLife()*healthMult*healthBase));
            ene.setMaxLife(ene.getLife());
            ene.setScore((int) Math.floor(ene.getScore()*scoreMult*scoreIncrease));
        }
        multi.setInitialEnemies(enemies.size());
        multi.setEnemyHealth();
    }

    /**
     * create new player element
     */
    private void playerGen() {
        int playerX = (int) (Math.random() * map.getMaxWidth());
        int playerY = (int) (Math.random() * map.getMaxHeight());

        while (grid.getNode(playerY, playerX).isOccupied() ||
                !grid.getNode(playerY, playerX).isWalkable() ||
                (map.getStairX() == playerX && map.getStairY() == playerY)) {
            playerX = (int) (Math.random() * map.getMaxWidth());
            playerY = (int) (Math.random() * map.getMaxHeight());
        }
        player = new Player(playerX * 32, playerY * 32, TILEWIDTH, TILEHEIGHT, this, 1);

    }

    /**
     * check if when player is on top of the stairs, to let him go to next level
     */
    private void playerOnStairs() {
        int playerX = (int) (player.getX() / TILEWIDTH);
        int playerY = (int) (player.getY() / TILEHEIGHT);

        if(map.getStairX() == playerY && map.getStairY() == playerX){
            onStair = true;

            if(keyManager.isKey("SPA")) {
                nextLevel();
                Assets.backStair.play();
                onStair = false;
            }
        }
        else {
            onStair = false;
        }
    }
    
    private void playerOnItem() {
        int playerX = (int) (player.getX() / TILEWIDTH);
        int playerY = (int) (player.getY() / TILEHEIGHT);
        
        if(grid.getNode(playerY, playerX).getItem() != null) {
            Item ite = grid.getNode(playerY, playerX).getItem();
            if(ite instanceof Weapon) {
                player.setDamage(player.getDamage() + ((Weapon) ite).getDamage());
                Assets.akS.play();
            }
            else if (ite instanceof Medkit) {
                player.setLife(player.getLife() + ((Medkit) ite).getHealthCure());
                Assets.medkitSound.play();
            }
            else if (ite instanceof Armor) {
                player.setMaxLife(player.getMaxLife() + ((Armor) ite).getArmorHP());
                hud.getHp().setTotalHP(player.getMaxLife());
                Assets.ArmorPicked.play();
            }
            
            ite.destroyItem();
            items.remove(ite);
        }
    }

    private void tick() {
        keyManager.tick();

        //Pause
        if (keyManager.isKey("P")) {
            if (state == STATE.GAME) {
                state = STATE.PAUSE;
            } else if (state == STATE.PAUSE) {
                state = STATE.GAME;
            }
        }

        // game started and no bullets exis => no unfinished actions
        if (state == STATE.GAME && enemyBullets.isEmpty() && !shot) {
            playerOnStairs();
            playerOnItem();
            player.tick();
            // if player moved, then move the enemies, otherwise, permit the player to shoot
            if (player.isMoved() && player.getMoves()>=minimumTime) {
                player.setMoves(0);
                minimumTime = (int)(Math.random()*3.0);
                int playerX = player.getX() + player.getWidth() / 2;
                 int playerY = player.getY() + player.getHeight() / 2;
                for (int i = 0; i < enemies.size(); i++) {
                    int posX = (int) (enemies.get(i).getX()/32);
                    int posY = (int) (enemies.get(i).getY()/32);
                    if(grid.getDistance(grid.getNode((int)(playerY/32), (int)(playerX/32)), grid.getNode(posY, posX)) < 60)
                    enemies.get(i).tick();
                }
                levelTurns++;
            } else {
                    // if left button was clicked
                if (mouseManager.isLeft()) {
                    // coordinates for enemy and player
                    int posX = (int) (mouseManager.getX() / TILEWIDTH);
                    int posY = (int) (mouseManager.getY() / TILEHEIGHT);

                    int playerX = player.getX() + player.getWidth() / 2;
                    int playerY = player.getY() + player.getHeight() / 2;

                        // check for click outside map / grid
                    if(posX < map.getMaxWidth() && posY < map.getMaxHeight()) {

                            // check the position of the grid, if it is occupied by enemies, shoot
                        if (grid.getNode(posY, posX).getOwner() instanceof Enemy && grid.getDistance(grid.getNode((int)(playerY/32), (int)(playerX/32)), grid.getNode(posY, posX)) < 50) {

                            playerBullet = new Bullet(player.getX() + TILEWIDTH / 2,
                                    player.getY() + TILEHEIGHT / 2, 6, 6, this, 1, player);

                            Enemy ene = (Enemy) grid.getNode(posY, posX).getOwner();
                            // make a trayectory to enemy from player
                            playerBullet.makeLine(new Point(playerX, playerY),
                                    new Point(ene.getX() + ene.getWidth() / 2,
                                    ene.getY() + ene.getHeight() / 2));
                            shot = true;
                        }
                    }
                }
            }
            player.setMoved(false);
        } // if game started and there exists an unfinished action, finish them
        else if (state == STATE.GAME && (!enemyBullets.isEmpty() || shot)) {
            // variable to know when player bullet has just been done
            boolean playerShotDone = false;

            // if player shot, move the bullet
            if (playerBullet != null) {
                playerBullet.tick();

                int playerBulletX = (int) playerBullet.getX() / TILEWIDTH;
                int playerBulletY = (int) playerBullet.getY() / TILEWIDTH;

                // get the owner of the tile and check if it is an enemy
                if (grid.getNode(playerBulletY, playerBulletX).getOwner() instanceof Enemy) {
                    Enemy ene = (Enemy) grid.getNode(playerBulletY, playerBulletX).getOwner();

                    shot = false;
                    playerBullet = null;
                    player.attacks(ene);
                    playerShotDone = true;
                    if (ene.getLife() <= 0) {
                        score += ene.getScore();
                        ene.setDeath();
                        enemies.remove(ene);
                    }
                }
            }

            for (int j = 0; j < enemyBullets.size(); j++) {
                Bullet bullet = enemyBullets.get(j);
                bullet.tick();
                if (player.contains(bullet.getX() + bullet.getWidth() / 2,
                        bullet.getY() + bullet.getHeight() / 2)) {

                    bullet.getOwner().attacks(player);
                    enemyBullets.remove(j);

                }
            }
                // give enemies a new turn after player shooting
            if (enemyBullets.isEmpty() && playerShotDone) {
                for (Enemy ene : enemies) {
                    if(grid.getDistance(grid.getNode((int)(player.getY()/32), (int)(player.getX()/32)), grid.getNode(ene.getY()/32, ene.getX()/32)) < 50)
                    ene.tick();
                }
                levelTurns++;
            }
        } else if (state == STATE.GAME_OVER) {
            if (keyManager.isKey("R")) {
                resetGame();
                state = STATE.GAME;
            }
        }

    }

    /**
     * render elements of game
     */
    private void render() {
        // get the buffer strategy from the display
        bs = display.getCanvas().getBufferStrategy();

        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        } else {

            g = bs.getDrawGraphics();

            if (state == STATE.GAME) {
                g.setColor(Color.black);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                map.render(g);

                g.setColor(Color.white);
                this.DrawStats();

                g.setColor(Color.red);
                for(Item ite : items) {
                    ite.render(g);
                }
                player.render(g);
                for (Enemy enemy : enemies) {
                    enemy.render(g);
                }
                
                
                for (int i = 0; i < enemyBullets.size(); i++) {
                    Bullet bullet = enemyBullets.get(i);
                    bullet.render(g);
                }
                if (playerBullet != null) {
                    playerBullet.render(g);
                }
                if (player.getLife() <= 0) {
                    state = STATE.GAME_OVER;
                }

                    // if player is standing on stairs
                if(onStair) {
                    g.drawImage(Assets.stairMessage, (this.getWidth() - 187)/2, 20, 187, 13,  null);
                }
                hud.render(g);

            } else if (state == STATE.GAME_OVER) {
                g.drawImage(Assets.gameOver, 0, 0, this.getWidth(), this.getHeight(), null);

            } else if (state == STATE.MENU) {

                // Render menu
                menu.render(g);

                int tempMX = mouseManager.getX(), tempMY = mouseManager.getY();
                // Check if start
                if (menu.getNGbutton().contains(tempMX, tempMY)) {
                    Assets.gates.play();
                    state = STATE.GAME;
                }

                //Check if cargar
                if (pause.load.contains(tempMX, tempMY)) {
                    // load game
                    loadFile(this);
                    Assets.gates.play();
                    state = STATE.GAME;
                }
                //Check if exit
                if (menu.getEbutton().contains(tempMX, tempMY)) {

                    System.exit(0);
                }
            } else if (state == STATE.PAUSE) {
                pause.render(g);
                if(mouseManager.isLeft()) {
                    int tempMX = mouseManager.getX(), tempMY = mouseManager.getY();
                    if (pause.resume.contains(tempMX, tempMY)) {
                        state = STATE.GAME;
                    } else if (pause.exit.contains(tempMX, tempMY)) {
                        System.exit(0);
                    } else if (pause.load.contains(tempMX, tempMY)) {
                        // load game
                        loadFile(this);
                    } else if (pause.save.contains(tempMX, tempMY)) {
                        // save game
                        saveFile(this);
                    }
                }
            }

            bs.show();
            g.dispose();
        }

    }



    /**
     * To draw the score of the player
     *
     */
    public void DrawStats() {
        g.drawString("Score: " + this.getScore(), this.getWidth() - 80, this.getHeight() - 25);
        g.drawString("Level: " + this.getLevel(), this.getWidth() - 80, this.getHeight() - 10);
        g.drawString("Current Damage: " + player.getDamage(), this.getWidth()/2, this.getHeight() - 10);
    }

    /**
     * get multiplier object to modify enemy health loss and player health loss on attacks() function
     * @return <code>int</code> Multipliers object
     */
    public Multipliers getMulti() {
        return multi;
    }

    /**
     * get the number of turns taken thus far
     * @return <code>int</code> number of turns
     */
    public int getTurns() {
        return levelTurns;
    }

    /**
     * To get the width of the game window
     *
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * get player object for use
     *
     * @return <code>Player</code> object
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * To get the height of the game window
     *
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * set game score
     *
     * @param score <code>int</code> new score value
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * get the score of the game
     *
     * @return <code>int</code> score value
     */
    public int getScore() {
        return score;
    }

    /**
     * get Layout of tiles
     *
     * @return <code>Layout</code> tile layout
     */
    public Layout getLayout() {
        return map;
    }

    /**
     * get grid object
     *
     * @return <code>NodeGrid</code> NodeGrid object
     */
    public NodeGrid getGrid() {
        return grid;
    }

    /**
     * set a node grid
     * @param grid <code>NodeGrid</code> NodeGrid object
     */
    public void setGrid(NodeGrid grid) {
        this.grid = grid;
    }
    
    /**
     * get list of enemies
     *
     * @return <code>ArrayList</code> list with the enemies
     */
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * set a list of enemies
     * @param enemies <code>ArrayList</code> list with the enemies
     */
    public void setEnemies(ArrayList<Enemy> enemies){
        this.enemies = enemies;
    }
    
    /**
     * set the level
     * @param level <code>int</code> level player is at
     * 
     */
    public void setLevel(int level) {
        currentLevel = level;
    }

    /**
     * get the current level of the dungeon
     * @return <code>int</code> current dungeon floor
     */
    public int getLevel() {
        return currentLevel;
    }

    /**
     *
     * @return
     */
    public KeyManager getKeyManager() {
        return keyManager;
    }

    @Override
    public void run() {
        init();
        // frames per second
        int fps = 60;
        // time for each tick in nano segs
        double timeTick = 1000000000 / fps;
        // initializing delta
        double delta = 0;
        // define now to use inside the loop
        long now;
        // initializing last time to the computer time in nanosecs
        long lastTime = System.nanoTime();
        while (running) {
            // setting the time now to the actual time
            now = System.nanoTime();
            // acumulating to delta the difference between times in timeTick units
            delta += (now - lastTime) / timeTick;
            // updating the last time
            lastTime = now;

            // if delta is positive we tick the game
            if (delta >= 1) {
                tick();
                render();
                delta--;
            }
        }

        stop();
    }

    /**
     * setting the thread for the game
     */
    public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * stopping the thread
     */
    public synchronized void stop() {
        if (running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

}
