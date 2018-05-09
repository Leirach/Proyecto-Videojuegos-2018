package tiles;

import java.awt.image.BufferedImage;

/**
 * Tile for floor texture and id
 */
public class TileFloor extends Tile {
    
    /**
     * Create Floor tile texture
     * @param texture <code>BufferedImage</code> texture of tile
     * @param id <code>int</code> id of tile
     */ 
    public TileFloor(BufferedImage texture, int id) {
            super(texture, id);
    }
    
}
