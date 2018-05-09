package tiles;

import java.awt.image.BufferedImage;

/**
 * Empty tile object. Tile that cannot be passed through
 */
public class TileEmpty extends Tile{
    
    /**
     * Create Empty Floor tile texture
     * @param texture <code>BufferedImage</code> texture of tile
     * @param id <code>int</code> id of tile
     */ 
    public TileEmpty(BufferedImage texture, int id) {
        super(texture, id);
    }
    
    /**
     * Override isPassable. This tile is not
     * @return <code>boolean</code> whether tile can be walked over
     */
    @Override
    public boolean isPassable(){
        return false;
    }
}
