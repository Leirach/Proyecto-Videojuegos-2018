package shooter2;

import graphics.ImageLoader;

/**
 * Start Game
 */
public class VideoGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            // TODO code application logic here
        Game g = new Game("El Prisionero de Alcatraz", 800, 525);
        g.start();
    }
    
}
