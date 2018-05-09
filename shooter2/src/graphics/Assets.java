/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import Sounds.SoundClip;
import java.awt.image.BufferedImage;
import tiles.Tile;

/**
 *
 * @author antoniomejorado
 */
public class Assets {

    /**
     * initializing the images of the game
     */

    //game screens
    public static BufferedImage gameOver;                  

    /**
     * title image
     */
    public static BufferedImage title;                     

    /**
     * button for new game
     */
    public static BufferedImage newGame;                    

    /**
     *
     */
    public static BufferedImage loadGame;                  

    /**
     * button to load game
     */
    public static BufferedImage exit;                       

  
    /**
     * exit game button
     */
    public static BufferedImage backgroundP;
 
    
    /**
     *stair message
     */
    public static BufferedImage stairMessage;

    /**
     * pause screen 
     */
    public static BufferedImage pauseScreen;                

    /**
     *  save game button
     */
    public static BufferedImage pauseSave;                 

    /**
     * load game button
     */
    public static BufferedImage pauseLoad;                  

    /**
     * exit game from pause
     */
    public static BufferedImage pauseExitGame;         

    /**
     * resume game
     */
    public static BufferedImage pauseResume;                

    //game assets

    /**
     * asset for the floor
     */
    public static BufferedImage floors;                    

    /**
     *  asset for the diferent types of tyles
     */
    public static BufferedImage tiles[][];                 

    /**
     * enemy asset
     */
    public static BufferedImage enemy;                     

    /**
     * frame for the health-Bar
     */
    public static BufferedImage barFrame;                    

    /**
     * store the sprites
     */
    public static BufferedImage sprites;                   

    /**
     * store the sprites
     */
    public static BufferedImage spritec;                    

    /**
     *  store the sprites
     */
    public static BufferedImage spritew;  
    

// store the sprites

    /**
     *
     */
    public static BufferedImage spritePO;                

    /**
     * store the sprites
     */
    public static BufferedImage spriteSW;                    

    /**
     * player asset
     */
    public static BufferedImage playerUp[];                 

    /**
     *  player asset
     */
    public static BufferedImage playerDown[];               

    /**
     *  player asset
     */
    public static BufferedImage playerRight[];              

    /**
     *
     */
    public static BufferedImage playerLeft[];  
    

// player asset

    /**
     * player asset
     */
    public static BufferedImage playerStillFireR;          

    /**
     * player asset
     */
    public static BufferedImage playerStillPointR;         

    /**
     * player asset
     */
    public static BufferedImage playerStillFireL;           

    /**
     * player asset
     */
    public static BufferedImage playerStillPointL;         
    
    //Cop 

    /**
     *   cop asset up
     */
    public static BufferedImage wCopUp[];                  

    /**
     * cop asset down
     */
    public static BufferedImage wCopDown[];                

    /**
     * cop asset right
     */
    public static BufferedImage wCopRight[];                

    /**
     * enemy asset left
     */
    public static BufferedImage wCopLeft[];                 
    
    //Police Officer

    /**
     *   PoliceOfficer asset up
     */
    public static BufferedImage POUp[];                   

    /**
     *  PoliceOfficer asset down
     */
    public static BufferedImage PODown[];                 

    /**
     *  PoliceOfficer asset right
     */
    public static BufferedImage PORight[];                

    /**
     * PoliceOfficer asset left
     */
    public static BufferedImage POLeft[];                

    // Swat Unit

    /**
     *  swat asset up
     */
    public static BufferedImage swUp[];                   

    /**
     * swat asset down
     */
    public static BufferedImage swDown[];                 

    /**
     * swat asset right
     */
    public static BufferedImage swRight[];                

    /**
     * swat asset left
     */
    public static BufferedImage swLeft[];                
    
    // SoundClips

    /**
     *  Sound asset shot 
     */
    public static SoundClip Shot;                        

    /**
     * Sound asset stab
     */
    public static SoundClip Stab;                         
    /**
     * sound asset select
     */
    public static SoundClip Select;                       

    /**
     *  Sound asset Background
     */
    public static SoundClip Background;                   

    /**
     * Sound asset backstair
     */
    public static SoundClip backStair;

    /**
     * medkit sound
     */
    public static SoundClip medkitSound;

    /**
     * ak gun sound
     */
    public static SoundClip akS;

    /**
     * armor pick sound
     */
    public static SoundClip ArmorPicked;

    /**
     * opening sound for player
     */
    public static SoundClip gates;
    
    //items

    /**
     * pistol
     */
    public static BufferedImage gun;                        

    /**
     * riffle
     */
    public static BufferedImage ak;                         

    /**
     * medkit
     */
    public static BufferedImage medkit;                     

                        

    /**
     * armor
     */
    public static BufferedImage armor;                    

    /**
     * armor
     */

    public static void init() {
        floors = ImageLoader.loadImage("/Images/dungeon_tiles_black2.png");
        // enemy = ImageLoader.loadImage("/Images/enemy.png");
        barFrame = ImageLoader.loadImage("/Images/barframe.png");
        gameOver = ImageLoader.loadImage("/Images/gameover.png");
        title = ImageLoader.loadImage("/Images/titulo.png");
        newGame = ImageLoader.loadImage("/Images/Nueva Partida.png");
        loadGame = ImageLoader.loadImage("/Images/Load Game.png");
        exit = ImageLoader.loadImage("/Images/Exit.png");
        backgroundP = ImageLoader.loadImage("/Images/backgroundPrision.png");
        
        stairMessage = ImageLoader.loadImage("/Images/StairsMessage.png");
        

        pauseScreen = ImageLoader.loadImage("/Images/pause.png");
        pauseSave = ImageLoader.loadImage("/Images/Save.png");
        pauseLoad = ImageLoader.loadImage("/Images/Load.png");
        pauseExitGame = ImageLoader.loadImage("/Images/ExitGamePause.png");
        pauseResume = ImageLoader.loadImage("/Images/Resume.png");


        //getting sprites from the image
        sprites = ImageLoader.loadImage("/Images/Jack.png");
        spritec = ImageLoader.loadImage("/Images/guard.png");
        spritew = ImageLoader.loadImage("/Images/shootJack.png");
        spritePO = ImageLoader.loadImage("/Images/policeOfficer.png");

        spriteSW = ImageLoader.loadImage("/Images/swat.png");

        //items
        gun = ImageLoader.loadImage("/Images/testPistol.png");
        ak = ImageLoader.loadImage("/Images/testAK.png");
        medkit = ImageLoader.loadImage("/Images/medkit.png");
        armor = ImageLoader.loadImage("/Images/kevlar.png");



        // Load Sounds
        Shot = new SoundClip("/Sounds/gunshot.wav");
        Stab = new SoundClip("/Sounds/stab.wav");
        Select = new SoundClip("/Sounds/select.wav");
        Background = new SoundClip ("/Sounds/FondoM.wav");
        backStair = new SoundClip("/Sounds/stairssound.wav");
        medkitSound = new SoundClip("/Sounds/medkit.wav");
        akS = new SoundClip("/Sounds/akS.wav");
        ArmorPicked = new SoundClip("/Sounds/pickArmor.wav");
        gates = new SoundClip("/Sounds/gatesS.wav");


        //Crop sprites

        SpriteSheet spritesheet = new SpriteSheet(sprites);
        SpriteSheet copSheet = new SpriteSheet(spritec);
        SpriteSheet shootSheet = new SpriteSheet(spritew);
        SpriteSheet POsheet = new SpriteSheet(spritePO);
        SpriteSheet swSheet = new SpriteSheet(spriteSW);

        // Player sheet
        playerUp = new BufferedImage[6];
        playerDown = new BufferedImage[6];
        playerRight = new BufferedImage[6];
        playerLeft = new BufferedImage[6];

        //Cop sheet
        wCopUp = new BufferedImage[7];
        wCopDown = new BufferedImage[7];
        wCopRight = new BufferedImage[7];
        wCopLeft = new BufferedImage[7];

        //PoliceOfficer sheet
        POUp = new BufferedImage[4];
        PODown = new BufferedImage[4];
        PORight = new BufferedImage[4];
        POLeft = new BufferedImage[4];

        //swat sheet
        swUp = new BufferedImage[3];
        swDown = new BufferedImage[3];
        swRight = new BufferedImage[3];
        swLeft = new BufferedImage[3];

        // cropping pictures from the sprite to the array
        //player
        for (int i = 0; i < 6; i++) {
            playerRight[i] = spritesheet.crop(i * 32, 0, 32, 32);
            playerUp[i] = spritesheet.crop(i * 32, 32, 32, 32);
            playerLeft[i] = spritesheet.crop(i * 32, 64, 32, 32);
            playerDown[i] = spritesheet.crop(i * 32, 96, 32, 32);
        }

        //Cop
        for (int i = 0; i < 7; i++) {
            wCopLeft[i] = copSheet.crop(i * 32, 0, 32, 32);
            wCopRight[i] = copSheet.crop(i * 32, 32, 32, 32);
            wCopDown[i] = copSheet.crop(i * 32, 64, 32, 32);
            wCopUp[i] = copSheet.crop(i * 32, 96, 32, 32);
        }

        //PoliceOfficer
        for (int i = 0; i < 4; i++) {
            PODown[i] = POsheet.crop(i * 32, 0, 32, 32);
            POLeft[i] = POsheet.crop(i * 32, 32, 32, 32);
            PORight[i] = POsheet.crop(i * 32, 64, 32, 32);
            POUp[i] = POsheet.crop(i * 32, 96, 32, 32);
        }
        // Swat
        for (int i = 0; i < 3; i++) {
            swUp[i] = swSheet.crop(i * 32, 96, 32, 32);
            swDown[i] = swSheet.crop(i * 32, 0, 32, 32);
            swRight[i] = swSheet.crop(i * 32, 32, 32, 32);
            swLeft[i] = swSheet.crop(i * 32, 64, 32, 32);
        }

        //player shoot
        // Shooting sheet
        playerStillFireL = shootSheet.crop(0, 32, 32, 32);
        playerStillPointR = shootSheet.crop(32, 0, 32, 32);
        playerStillFireR = shootSheet.crop(0, 0, 32, 32);
        playerStillPointL = shootSheet.crop(32, 32, 32, 32);

        cropSprites();
    }

    

    /**
     * Function to cut sprites
     */
    public static void cropSprites() {
        //crop for the tiles in the dungeon
        SpriteSheet floorSheet = new SpriteSheet(floors);
        tiles = new BufferedImage[5][11];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 11; j++) {
                tiles[i][j] = floorSheet.crop(j * 16, i * 16, 16, 16);
            }
        }
        tiles[0][4] = ImageLoader.loadImage("/Images/stairs.png");
    }

}
