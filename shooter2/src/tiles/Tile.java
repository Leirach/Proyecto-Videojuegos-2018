/*
 *
 */
package tiles;

import graphics.Assets;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import shooter2.Game;

/**
 *
 * @author Juan De León
 */
public class Tile {
    /**
     * static tile array, loaded in Game
     */
    public static Tile[] staticTiles = new Tile[256];

    /**
     * Empty tile matrix with empty black texture
     */
    public static Tile tileEmpty = new TileEmpty(Assets.tiles[2][4], 0); 

    /**
     * alternate empty tile, ledge texture
     */
    public static Tile tileEmptyAlt = new TileEmpty(Assets.tiles[1][4], 255);


    /**
     * Bottom Right Corner
     */
    public static Tile floorCorner1 = new TileFloor(Assets.tiles[0][3], 3);

    /**
     * Bottom Left Corner
     */
    public static Tile floorCorner2 = new TileFloor(Assets.tiles[1][1], 5);

    /**
     * top Left Corner
     */
    public static Tile floorCorner3 = new TileFloor(Assets.tiles[3][0], 12);

    /**
     * Top Right Corner
     */
    public static Tile floorCorner4 = new TileFloor(Assets.tiles[2][2], 10);
    

    /**
     * Bottom Edge
     */
    public static Tile floorEdge1 = new TileFloor(Assets.tiles[1][3], 7);

    /**
     * Right Edge
     */
    public static Tile floorEdge2 = new TileFloor(Assets.tiles[2][3], 11);

    /**
     * Left Edge
     */
    public static Tile floorEdge3 = new TileFloor(Assets.tiles[3][1], 13);

    /**
     * Top Edge
     */
    public static Tile floorEdge4 = new TileFloor(Assets.tiles[3][2], 14);
    

    /**
     * Horizontal Corridor
     */
    public static Tile floorCorridor1 = new TileFloor(Assets.tiles[1][2], 6);

    /**
     * Vertical Corridor
     */
    public static Tile floorCorridor2 = new TileFloor(Assets.tiles[2][1], 9);

    /**
     * Vertical end South
     */
    public static Tile floorCorridor3 = new TileFloor(Assets.tiles[0][1], 1);

    /**
     * Vertical end North
     */
    public static Tile floorCorridor4 = new TileFloor(Assets.tiles[2][0], 8);

    /**
     * Horizontal end East
     */
    public static Tile floorCorridor5 = new TileFloor(Assets.tiles[0][2], 2);

    /**
     * Horizontal end West
     */
    public static Tile floorCorridor6 = new TileFloor(Assets.tiles[1][0], 4);
    //others

    /**
     * Solo Tile
     */
    public static Tile floorIsle = new TileFloor(Assets.tiles[0][0], 16);

    /**
     * Stair floor
     */
    public static Tile stairs = new TileFloor(Assets.tiles[0][4], 17);

    /**
     * Empty floor, no edge
     */
    public static Tile floorEmpty = new TileFloor(Assets.tiles[3][3], 15);

    /**
     * tileWidth
     */
    public static final int TILEWIDTH = Game.TILEWIDTH;

    /**
     * TileHeight
     */
    public static final int TILEHEIGHT = Game.TILEHEIGHT;

    /**
     * texture to load
     */
    protected BufferedImage texture;

    /**
     * id of tile
     */
    protected int id;         // id represents the texture of tile

    /**
     * Create tile
     * @param texture <code>BufferedImage</code> texture of the tile
     * @param id <code>int</code> value of id
     */
    public Tile(BufferedImage texture, int id){
        this.texture = texture;
        this.id = id;
        
        staticTiles[id] = this; //assign the tile id to the static array
    }


    /**
     * Render Tile in its position on Layout
     * @param g <code>Graphics</code> object that draws the tiles
     * @param x <code>int</code> horizontal position
     * @param y <code>int</code> vertical position
     */
    public void render(Graphics g, int x, int y){
        g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
    }
    /**
     * get whether the tile can be moved on
     * @return <code>boolean</code> value of the trespassing of tile
     */
    public boolean isPassable(){
        return true;
    }
    /**
     * get id of the texture of the tile
     * @return <code>int</code> value of the id
     */
    public int getId() {
        return id;
    }
    /**
     * set the id of the tile
     * @param id <code>int</code> new value for the id
     */
    public void setId(int id) {
        this.id = id;
    }

}