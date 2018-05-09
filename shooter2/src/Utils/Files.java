/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Actor.Enemy;
import Actor.Guardia;
import Actor.Swat;
import Actor.policeOfficer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import shooter2.Game;
import tiles.Layout;

/**
 *
 * @author Juan De León
 */
public class Files {
   
    //to load a map from a .txt file

    /**
     *
     * @param path
     * @return
     */
    public static String loadLayout(String path) {        
	BufferedReader br = null;
	StringBuilder builder = new StringBuilder();
	
	try {
	    // Setup file reading
            InputStream in = Files.class.getResourceAsStream(path);
            br = new BufferedReader(new InputStreamReader(in));
	    String line;
                      
	    while((line = br.readLine()) != null){
		builder.append(line + "\n");
            }
	} catch (IOException ioe) {
	    System.err.println(ioe.toString());
	}
        return builder.toString();
    }
    
    /**
     * receives a string and returns an integer
     * @param number <code>int</code> to assign number
     * @return <code>int</code> number assigned for a char in string
     */
    public static int parseInt(String number){
		try{
			return Integer.parseInt(number);
		}catch(NumberFormatException e){
			e.printStackTrace();
			return 0;
		}
	}
    
    //load a savefile
    public static String loadFileAsString(String path) {        
	BufferedReader br = null;
        FileReader fr = null;
	StringBuilder builder = new StringBuilder();
	
	try {
	    // Setup file reading
            fr = new FileReader(path);
            br = new BufferedReader(fr);
	    String line;
                      
	    while((line = br.readLine()) != null){
		builder.append(line + "\n");
            }
	} catch (IOException ioe) {
	    System.err.println(ioe.toString());
	}
        return builder.toString();
    }
    
    /**
     * Use game resources
     * @param game <code>Game</code> Game object to access resources
     */
     public static void loadFile(Game game) {
	BufferedReader br = null;
	FileReader fr = null;
	String line;	
        int x, y, life;
        
	try {
            //reading layout file and setting layout&grid
            game.getLayout().loadWorld("curLayout.txt");
            NodeGrid G = new NodeGrid(game);
            game.setGrid(G);
            
	    // Setup file reading
	    fr = new FileReader("savefile.txt");
	    br = new BufferedReader(fr);
	    
	    //1st line, set player attributes
	    line = br.readLine();
	    String[] elements = line.split(",");
            x = parseInt(elements[0]);
            y = parseInt(elements[1]);
            
	    game.getPlayer().setX(x);
	    game.getPlayer().setY(y);
            game.getPlayer().setLife(parseInt(elements[2]));
	    
            //grid occupied by player
            game.getGrid().getNode(y/game.TILEHEIGHT, x/game.TILEWIDTH).setOccupied(true);
            
	    // 2nd line level and score
	    line = br.readLine();
            elements = line.split(",");
            game.setLevel(parseInt(elements[0]));
            game.setScore(parseInt(elements[1]));
            
            //3d line number of enemies
            line = br.readLine();
	    int numEnemies = Integer.parseInt(line);
            Enemy e = new Guardia(0,0,0,0,game);//placeholder
            ArrayList<Enemy> enemies = new ArrayList<>();
            
	    // Clear enemies list, read 'numEnemies' lines, & set enemies
	    game.getEnemies().clear();
	    for (int i = 0; i < numEnemies; i++) {
		line = br.readLine();
		elements = line.split(",");
                x = parseInt(elements[1]);
                y = parseInt(elements[2]);
                life = parseInt(elements[3]);
                switch(elements[0]){
                    case "Guardia":
                        e = new Guardia(x, y, game.TILEWIDTH, game.TILEHEIGHT, game);
                        break;
                    case "Swat":
                        e = new Swat(x, y, game.TILEWIDTH, game.TILEHEIGHT, game);
                        break;
                    case "PO":
                        e = new policeOfficer(x, y, game.TILEWIDTH, game.TILEHEIGHT, game);
                        break;
                }
                e.setLife(life);
                enemies.add(e);
                //grid occupied by enemies
                game.getGrid().getNode(y/game.TILEHEIGHT, x/game.TILEWIDTH).setOccupied(true);
	    }
            game.setEnemies(enemies);
            System.out.println("Game loaded!");
	} catch (IOException ioe) {
	    System.err.println("Error: Unable to find savefile!" + ioe.toString());
	}
    }
    
     //save the game to a .txt

    /**
     * use game resources
     * @param game <code>Game</code> Game object to access resources
     */
    public static void saveFile(Game game) {
	try {
            //print current layout to a different file to easily read it
            PrintWriter pw = new PrintWriter(new FileWriter("curLayout.txt"));
            Layout L = game.getLayout();
            pw.println(L.getMaxHeight() + " " + L.getMaxWidth());
            for(int i=0; i<L.getMaxHeight(); i++){
                for (int j=0; j<L.getMaxWidth(); j++) {
                    pw.print("" + L.getTileID(i, j));
                }
                pw.println("");
            }
            pw.close();
            
            //save player and enemy positions
	    pw = new PrintWriter(new FileWriter("savefile.txt"));
	    pw.println("" + game.getPlayer().getX() + "," + 
                    game.getPlayer().getY() + "," + game.getPlayer().getLife());
            pw.println("" + game.getLevel() + "," + game.getScore());
	    pw.println("" + game.getEnemies().size());
	    for (Enemy e : game.getEnemies()) {
		pw.println("" + e.toString() + "," + e.getX() + "," +
                        e.getY() + "," + e.getLife());
	    }
	    pw.close();
            System.out.println("Game saved!");
	} catch (IOException ioe) {
	    System.err.println("Error: Couldn't save the game!" + ioe.toString());
	}
    }

    
    
}