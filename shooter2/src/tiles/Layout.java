package tiles;

import Utils.Files;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * Container and generator of map. Stores Layout of the Tiles
 */
public class Layout {
    private int width, height;
    private int tiles[][];
    private int tileTextures[][];
    private int stairX, stairY;
    private boolean v[][];
    private int temp[][];
    private int currentIndex;

    /**
     * Create Layout Object
     */
    public Layout(){

    }
    
    /**
     * get index of array
     * @return <code>int</code> value of array index
     */
    public int getIndex(){
        return currentIndex;
    }

    /**
     * set matrix as false, used in mapcheck
     * @param n <code>int</code> number of rows
     * @param m <code>int</code> number of columns
     */
    public void memsetV(int n, int m){
        for(int i=0;i<n;i++) for(int j=0;j<m;j++) v[i][j]=false;
    }

    /**
     * traverse Layout matrix
     * @param r <code>int</code> number of rows
     * @param c <code>int</code> number of columns
     * @param n <code>int</code> height
     * @param m <code>int</code> width
     */
    public void dfs(int r, int c, int n, int m){
        if(r<0 || r>=n || c<0 ||c>=m) return;
        if(v[r][c]) return;
        v[r][c]=true;
        if(temp[r][c]==0){
            return;
        }else{
            temp[r][c]=0;
            dfs(r-1,c,n,m);
            dfs(r+1,c,n,m);
            dfs(r,c-1,n,m);
            dfs(r,c+1,n,m);
        }
    }

    /**
     * check for tiles that cant be reached
     * @param n <code>int</code> height
     * @param m <code>int</code> width
     * @return <code>boolean</code> whether all tiles can be reached
     */
    public boolean canReach(int n, int m){

        for(int i=0;i<n;i++) for(int j=0;j<m;j++) temp[i][j]=tiles[i][j];
        memsetV(n,m);
        dfs(stairX,stairY,n,m);
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(temp[i][j] == 1) return false;
            }
        }
        return true;
    }

    /**
     * Generate map
     * @param n <code>int</code> height
     * @param m <code>int</code> width
     */
    public void generateWorld(int n, int m) {
        height = n;
        width = m;
        v = new boolean[n][m];
        temp  = new int[n][m];


        List<Integer>[] arr = new List[100];
        
        String file = Files.loadLayout("/Files/maptest.txt");
        String[] tokens = file.split("\\s+");

        n = Files.parseInt(tokens[0]);
        m = Files.parseInt(tokens[1]);
        tiles = new int[n][m];
        tileTextures = new int[n][m];

        boolean canR = false;
        while(!canR){
                int rMap = (int)(Math.random()*500);
                currentIndex=rMap;
        for (int i = 0; i < n; i++) {
            arr[i] = new ArrayList<Integer>();
            for (int j = 0; j < m; j++) {
                arr[i].add(0);
            }
        }
        for (int i=0; i<n; i++){
            for (int j=0; j<m; j++){
                //System.out.print(tokens[i+2].charAt(j));
                if(Character.getNumericValue(tokens[i+2+rMap*n].charAt(j))==1){
                    //arr[i].set(j, 1);
                    tiles[i][j]=1;
                }else{
                    tiles[i][j]=0;
                }
            }
        }
        // generate stairs position
        nodeExitStairsGen(height, width);
        canR = canReach(n,m);
        }


        mask(tiles);
    }

    /**
     * load world from file
     * @param path <code>String</code> value for the filename
     */
    public void loadWorld(String path){
            // file name and loading
        String file = Files.loadFileAsString(path);
        String[] tokens = file.split("\\s+"); //split in whitespaces
            // first values are width and height of world
        height = Files.parseInt(tokens[0]);
        width = Files.parseInt(tokens[1]);

        tiles = new int[height][width];
        tileTextures = new int[height][width];
        for (int i=0; i<height; i++){
            for (int j=0; j<width; j++){
               tiles[i][j] = Character.getNumericValue(tokens[i+2].charAt(j));
            }
        }

        mask(tiles);
    }

    /**
     * create masking for tile textures
     * @param tiles <code>int[][]</code> matrix with the id of the tiles
     */
    private void mask(int tiles[][]) {
        //mask
        for (int i=0; i<height; i++){
            for (int j=0; j<width; j++){
                //masking for edges
                if (tiles[i][j] == 1){
                    tileTextures[i][j] = adjacent(i, j);
                }
                //masking empty spaces to add ledges (eye candy)
                else if (tiles[i][j] == 0){
                    if (i != 0 && tiles[i-1][j]%255 > 0)
                        tileTextures[i][j] = 255;
                }
                //replace corresponding tile with stairs texture
                else if (tiles[i][j] == 2){
                    tileTextures[i][j] = 17;
                }
            }
        }
    }

    /**
     * generate stairs on map
     * @param height <code>int</code> height of map
     * @param width <code>int</code> width of map
     */
    private void nodeExitStairsGen(int height, int width) {

        //Generate a random value
        int randX = (int) (Math.random() *height);
        int randY = (int) (Math.random() *width);

        while(!getTile(randX, randY).isPassable()) {
            randX = (int) (Math.random() *height);
            randY = (int) (Math.random() *width);
        }

        getTile(randX, randY).setId(2);
        tiles[randX][randY] = 2;
        this.stairX = randX;
        this.stairY = randY;
    }

    /**
     * get mask, according to adjacent tiles
     * @param i <code>int</code> row of tile
     * @param j <code>int</code> column of tile
     * @return <code>int</code> mask of current tile
     */
    private int adjacent(int i, int j){
        int mask = 0;
        //check north tile
        if (i != 0 && tiles[i-1][j]%255 > 0)
            mask += 1;
        //check west tile
        if (j !=0 && tiles[i][j-1]%255 > 0)
            mask += 2;
        //check east tile
        if (j != width-1 && tiles[i][j+1]%255 > 0)
            mask += 4;
        //check south tile
        if (i != height-1 && tiles[i+1][j]%255 >0)
            mask +=8;

        return mask;
    }

    /**
     * get the type of tile to render
     * @param x <code>int</code> position in matrix of tile type
     * @param y <code>int</code> position in matrix of tile type
     * @return
     */
    public Tile getTile(int x, int y){
        Tile t = Tile.staticTiles[tiles[x][y]];
        if (t == null)
            return Tile.tileEmpty;
        return t;
    }

    /**
     * get the texture of the tile
     * @param x <code>int</code> position in matrix of tile type
     * @param y <code>int</code> position in matrix of tile type
     * @return
     */
    public Tile getTileTexture(int x, int y){
        Tile t = Tile.staticTiles[tileTextures[x][y]];
        if (t == null)
            return Tile.tileEmpty;
        return t;
    }

    /**
     * get the id of the tile
     * @param x <code>int</code> position in matrix of tile type
     * @param y <code>int</code> position in matrix of tile type
     * @return
     */
    public int getTileID(int x, int y){
        return tiles[x][y];
    }

    /**
     * render tiles
     * @param g <code>Graphics</code> drawing tool for the objects
     */
    public void render(Graphics g) {
        for (int i=0; i<height; i++){
            for (int j=0; j<width; j++){
                getTileTexture(i, j).render(g, j * Tile.TILEWIDTH, i * Tile.TILEHEIGHT);
            }
        }
    }

    /**
     * get max width of layout
     * @return <code>int</code> width of tiles[][]
     */
    public int getMaxWidth() {
        return tiles[0].length;
    }

    /**
     * get max heightof layout
     * @return <code>int</code> height of tiles[][]
     */
    public int getMaxHeight() {
        return tiles.length;
    }

    /**
     * get horizontal position of stairs
     * @return <code>int</code> horizontal position 
     */
    public int getStairX() {
        return this.stairX;
    }

    /**
     * get horizontal position of stairs
     * @return <code>int</code> horizontal position 
     */
    public int getStairY() {
      return this.stairY;
    }

    /**
     * get tiles matrix
     * @return <code>int[][]</code> matrix with the tiles
     */
    public int[][] getTiles() {
        return tiles;
    }
}
