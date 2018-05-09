/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shooter2;

import Actor.Enemy;

/**
 *
 * @author Nissim
 */
public class Multipliers {
    private double enemyHealthScore;                // health score
    private double enemyHealthMult;                 // health score multiplier
    private double enemyMaxSpawnBase;              // spawn enemies score
    private double enemyMaxSpawnMult;               // spawn enemies multiplier
    private double powerUpSpawnScore;               // powerup spawn chance score
    private double powerUpSpawnMult;                // powerup spawn multiplier
    private double powerUpStatScore;                // powerup stat score
    private double powerUpStatMult;                 // powerup stat multiplier
    private double scoreIncrease;                   // score gain increase
    private double scoreMult;                       // score gain multiplier
    private Game game;                              // game object to access game
    
    private double preScore;                           // score before end level
    private double preHealth;                          // player health before end level
    private double enemiesHealth;                      // enemies starting total health
    private double enemyHealthLoss;                    // enemies health loss
    private double initialEnemies;                     // number of enemies at beginning
    private double turnToFinish;
    
    /**
     * class that determines at the end of a level, what are the multipliers for difficulty in the next level.
     * several parameters that change during the game, different aspects of the level will change, 
     * like enemy and power-up generation, score, and enemy health.
     * @param game 
     */
    public Multipliers(Game game) {
        this.game = game;
        
        this.enemyHealthScore = 1;
        this.enemyHealthMult = 1;
        
        this.enemyMaxSpawnBase = 1;
        this.enemyMaxSpawnMult = 1;
        
        this.powerUpSpawnScore = 1;
        this.powerUpSpawnMult = 1;
        
        this.powerUpStatScore = 1;
        this.powerUpStatMult = 1;
        
        this.scoreIncrease = 1;
        this.scoreMult = 1;
        
        this.preScore = game.getScore() > 0 ? game.getScore() : 1;
        this.preHealth = game.getPlayer().getLife();
        this.enemiesHealth = 0;
        this.enemyHealthLoss = 0;
        this.initialEnemies = 0;
        this.turnToFinish = 0;
    }
    
    /**
     *
     */
    public void setMultipliers() {
            // health lost by enemies || damage done by player
        double playerDamage = enemyHealthLoss / enemiesHealth;
                
            // enemies killed
        double killed = (double) (game.getEnemies().size()) / initialEnemies;

            // player health lost
        double playerH = 1 - (double) (game.getPlayer().getLife()) / ((double) preHealth);

            // number of moves to end
        double movesH = game.getTurns() / turnToFinish;

            // score gained
        double scoreG = game.getScore() / preScore;
                
        
        /**
         * REDUCIR DIF MULT
         *      mucho dano recibido, poco infligido
         *      pocos enemigos matados, mucho dano infligido
         *      muchos movimientos al objetivo y mucho dano recibido
         *      mucho dano recibido, pocos enemigos matados
         * AUMENTAR DIFF MULT
         *      poco dano recibido, mucho infligido
         *      todos enemigos matados
         *      exactos los movimientos al objetivo
         *      poco mas de los movimientos al objetivo y poco dano recibido
         *      
         */
        //REDUCE DIFFICULTY
            // low damage output and high damage received
        if(playerDamage <= 0.25 && playerH >= 0.75) {
            enemyHealthMult -= 0.05;
            enemyMaxSpawnMult -= 0.15;
            powerUpSpawnMult += 0.05;
            powerUpStatMult += 0.1;
            
            enemyHealthScore -= 0.0025;
            enemyMaxSpawnBase -= 0.0015;
            powerUpSpawnScore += 0.002;
            powerUpStatScore += 0.01;
        }
            // killed little enemies, did much damage
        if(killed <= 0.85 && playerDamage >= 0.75) {
            enemyHealthMult -= 0.025;
            enemyMaxSpawnMult -= 0.10;
            powerUpSpawnMult += 0.2;
            powerUpStatMult += 0.15;
            
            enemyHealthScore -= 0.001;
            enemyMaxSpawnBase -= 0.0025;
            powerUpSpawnScore += 0.005;
            powerUpStatScore += 0.01;
        }
            // many moves to objective, lot of damage received
        if(movesH >= 2 && playerH >= 0.65) {
            enemyHealthMult -= 0.05;
            enemyMaxSpawnMult -= 0.10;
            powerUpSpawnMult += 0.075;
            powerUpStatMult += 0.15;
            
            enemyHealthScore -= 0.0025;
            enemyMaxSpawnBase -= 0.002;
            powerUpSpawnScore += 0.0025;
            powerUpStatScore += 0.01;
        }
            // killed little enemies, received much damage
        if(playerH >= 0.75 && killed <= 0.2) {
            enemyHealthMult -= 0.025;
            enemyMaxSpawnMult -= 0.075;
            powerUpSpawnMult += 0.15;
            powerUpStatMult += 0.2;
            
            enemyHealthScore -= 0.0015;
            enemyMaxSpawnBase -= 0.002;
            powerUpSpawnScore += 0.005;
            powerUpStatScore += 0.01;
        }
            // player with low health
        if(playerH >= 0.8) {
            powerUpSpawnMult += 0.25;
            powerUpSpawnScore += 0.05;
            powerUpStatMult += 0.3;
            powerUpStatScore += 0.05;
        }
        
        // INCREASE DIFFICULTY
            // low damage input, high damage output
        if(playerH <= 0.25 && playerDamage >= 0.5) {
            enemyHealthMult += 0.025;
            enemyMaxSpawnMult += 0.20;
            powerUpSpawnMult -= 0.075;
            powerUpStatMult -= 0.05;
            
            enemyHealthScore += 0.0025;
            enemyMaxSpawnBase += 0.005;
            powerUpSpawnScore -= 0.003;
            powerUpStatScore -= 0.001;
        }
            // all enemies killed
        if(killed == 0) {
            enemyHealthMult += 0.05;
            enemyMaxSpawnMult += 0.2;
            powerUpSpawnMult -= 0.03;
            powerUpStatMult -= 0.05;
            
            enemyHealthScore += 0.0035;
            enemyMaxSpawnBase += 0.0015;
            powerUpSpawnScore -= 0.0025;
            powerUpStatScore -= 0.01;
        }
            // exact number of moves to stairs
        if(movesH <= 1.1) {
            enemyHealthMult += 0.05;
            enemyMaxSpawnMult += 0.1;
            powerUpSpawnMult -= 0.05;
            powerUpStatMult -= 0.025;
            
            enemyHealthScore += 0.005;
            enemyMaxSpawnBase += 0.0015;
            powerUpSpawnScore -= 0.0025;
            powerUpStatScore -= 0.001;
        }
            // little more moves to stairs and little damage received
        if(movesH <= 1.5 && playerH <= 0.3) {
            enemyHealthMult += 0.055;
            enemyMaxSpawnMult += 0.2;
            powerUpSpawnMult -= 0.075;
            powerUpStatMult -= 0.05;
            
            enemyHealthScore += 0.0015;
            enemyMaxSpawnBase += 0.002;
            powerUpSpawnScore -= 0.0015;
            powerUpStatScore -= 0.001;
        }
            // if no damage done to enemy
        if(playerDamage <= 0.05) {
            enemyHealthMult += 0.05;
            enemyMaxSpawnMult += 0.15;
            powerUpSpawnMult -= 0.075;
            powerUpStatMult -= 0.05;
            
            enemyHealthScore += 0.0025;
            enemyMaxSpawnBase += 0.0015;
            powerUpSpawnScore -= 0.0035;
            powerUpStatScore -= 0.001;
        }
            // increase difficulty by default
        enemyHealthMult += 0.075;
        enemyMaxSpawnMult += 0.05;
        powerUpSpawnMult += 0.025;
        powerUpStatMult += 0.015;
        
        enemyHealthScore += 0.005;
        enemyMaxSpawnBase += 0.0015;
        powerUpSpawnScore += 0.0025;
        powerUpStatScore += 0.001;
        
    }
    
    /**
     * set a heuristic amount of turns for player to reach stairs
     */
    public void setAvgTurns() {
        int number = 0;
        int stairX = game.getLayout().getStairX();
        int stairY = game.getLayout().getStairY();
        
        int playerX = (int) (game.getPlayer().getX() / 32);
        int playerY = (int) (game.getPlayer().getY() / 32); 
        number += Math.abs(playerY - stairX);
        number += Math.abs(playerX - stairY);
        this.turnToFinish = number;
    }
    
    /**
     * set amount of enemies at start of level
     * @param enemies <code>int</code> amount of enemies
     */
    public void setInitialEnemies(int enemies) {
        this.initialEnemies = enemies;
    }
    
    /**
     * add the health of enemies, to be used on enemy generation so as to not iterate through them
     */
    public void setEnemyHealth() {
        for(Enemy ene : game.getEnemies()) {
            enemiesHealth += ene.getLife();
        }
    }
    
    /**
     * add amount of health lost by enemies overall. To be used on player attacks enemy
     * @param health <code>int</code> amount of health los
     */
    public void addEnemyHealthLoss(int health) {
        this.enemyHealthLoss += health;
    }

    /**
     * get enemy health score, to add to enemies that spawn on level.
     * @return <code>double</code> value of base health
     */
    public double getEnemyHealthScore() {
        return enemyHealthScore;
    }

    /**
     * set the score of the enemy spawn on level creation
     * @param enemyHealthScore <code>double</code> value of new score
     */
    public void setEnemyHealthScore(double enemyHealthScore) {
        this.enemyHealthScore = enemyHealthScore;
    }

    /**
     * get the multiplier for enemies health
     * @return <code>double</code> value of multiplier
     */
    public double getEnemyHealthMult() {
        return enemyHealthMult;
    }

    /**
     * set the health multiplier of enemies on level creation
     * @param enemyHealthMult <code>double</code> value of new multiplier
     */
    public void setEnemyHealthMult(double enemyHealthMult) {
        this.enemyHealthMult = enemyHealthMult;
    }

    /**
     * get the max enemies spawn on level base number
     * @return <code>double</code> value of base number
     */
    public double getEnemyMaxSpawnBase() {
        return enemyMaxSpawnBase;
    }

    /**
     * set the max number of enemies spawned on level
     * @param enemyMaxSpawnScore <code>double</code> value of new base spawn score
     */
    public void setEnemyMaxSpawnBase(double enemyMaxSpawnScore) {
        this.enemyMaxSpawnBase = enemyMaxSpawnScore;
    }

    /**
     * get the max enemy generation multiplier
     * @return <code>double</code> value of new multiplier
     */
    public double getEnemyMaxSpawnMult() {
        return enemyMaxSpawnMult;
    }

    /**
     * set the max number of enemies spawned multiplier
     * @param enemyMaxSpawnMult <code>double</code> value of new multiplier
     */
    public void setEnemyMaxSpawnMult(double enemyMaxSpawnMult) {
        this.enemyMaxSpawnMult = enemyMaxSpawnMult;
    }

    /**
     * get the power-up spawn chance base score
     * @return <code>double</code> value of base chance
     */
    public double getPowerUpSpawnScore() {
        return powerUpSpawnScore;
    }

    /**
     * set the score of power-up spawn chance
     * @param powerUpSpawnScore <code>double</code> value of new score
     */
    public void setPowerUpSpawnScore(double powerUpSpawnScore) {
        this.powerUpSpawnScore = powerUpSpawnScore;
    }

    /**
     * get the power-up spawn multiplier
     * @return <code>double</code> value of multiplier
     */
    public double getPowerUpSpawnMult() {
        return powerUpSpawnMult;
    }

    /**
     * set the new multiplier for power-up spawn
     * @param powerUpSpawnMult <code>double</code> value of new multiplier
     */
    public void setPowerUpSpawnMult(double powerUpSpawnMult) {
        this.powerUpSpawnMult = powerUpSpawnMult;
    }

    /**
     * get the power-up stat base increase/decrease
     * @return <code>double</code> value of power-up stat base score
     */
    public double getPowerUpStatScore() {
        return powerUpStatScore;
    }

    /**
     * set the power-up stat base score
     * @param powerUpStatScore <code>double</code> value of new base stat score
     */
    public void setPowerUpStatScore(double powerUpStatScore) {
        this.powerUpStatScore = powerUpStatScore;
    }

    /**
     * get power-up stat multiplier 
     * @return <code>double</code> value of multiplier
     */
    public double getPowerUpStatMult() {
        return powerUpStatMult;
    }

    /**
     * set power-up multiplier
     * @param powerUpStatMult <code>double</code> new multiplier
     */
    public void setPowerUpStatMult(double powerUpStatMult) {
        this.powerUpStatMult = powerUpStatMult;
    }

    /**
     * get score increase
     * @return <code>double</code> value of score increase per enemy kill
     */
    public double getScoreIncrease() {
        return scoreIncrease;
    }

    /**
     * set the score increase base score
     * @param scoreIncrease <code>double</code> value of new base score
     */
    public void setScoreIncrease(double scoreIncrease) {
        this.scoreIncrease = scoreIncrease;
    }

    /**
     * get score multiplier
     * @return <score>double</code> value of enemy score increase multiplier
     */
    public double getScoreMult() {
        return scoreMult;
    }

    /**
     * set enemies score multiplier
     * @param scoreMult <code>double</code> value of new score multiplier
     */
    public void setScoreMult(double scoreMult) {
        this.scoreMult = scoreMult;
    }    
}
