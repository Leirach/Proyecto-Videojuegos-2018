/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.HashSet;
import shooter2.Game;

/**
 *
 * @author Nissim
 */
public class NodeGrid {
    private ArrayList<ArrayList<Node> > grid;       // grid matrix
    private Game game;                              // game object
    
    /**
     * Generate grid of nodes to represent map as Node
     * @param game
     */
    public NodeGrid(Game game) {
            // new grid filled with nodes
        grid = new ArrayList<ArrayList<Node> >();
        for(int i = 0; i < game.getLayout().getMaxHeight(); i++) {
            grid.add(new ArrayList<Node>());
            for(int j = 0; j < game.getLayout().getMaxWidth(); j++) {
                grid.get(i).add(j, new Node(i, j, game.getLayout().getTile(i, j).isPassable()));
            }
        }
    }
    
    /**
     * get node in position i (row), j (col)
     * @param i <code>int</code> row position
     * @param j <code>int</code> column position
     * @return <code>Node</code> node in position
     */
    public Node getNode(int i, int j) {
        return grid.get(i).get(j);
    }
    
    /**
     * Reset grid.
     * Old g values are reset and parents are made null
     * Used every time a path is generated, to not confuse algorithm,
     * since starting and end points change
     */
    public void updateGrid() {
        for(int i = 0; i < grid.size(); i++) {
            for(int j = 0; j < grid.get(0).size(); j++) {
                grid.get(i).get(j).setGCost(Integer.MAX_VALUE);
                grid.get(i).get(j).setParent(null);
                
            }
        }
    }
    
    /**
     * A* algorithm to generate shortest path between 2 position converted into nodes
     * Used by Enemy to generate their path
     * @param enemyX <code>int</code> enemy x position
     * @param enemyY <code>int</code> enemy y position
     * @param playerX <code>int</code> player x position
     * @param playerY <code>int</code> player y position
     * @return <code>ArrayList</code> shortest path, starting from endpoint
     */
    public ArrayList<Node> pathFinding(int enemyX, int enemyY, int playerX, int playerY) {
        
            // reset grid
        updateGrid();
        
            // path list
        ArrayList<Node> path = new ArrayList<Node>();
            // start and end nodes
        Node start = grid.get(enemyX).get(enemyY);
        Node end = grid.get(playerX).get(playerY);
                
            // set costs of starting point
        start.setGCost(0);
        start.setFCost(getDistance(start,end));
        
            // open and closed sets
            // open are the nodes to use
            // closed are those already used
        PriorityQueue<Node> open = new PriorityQueue<Node>(1,new comparator());
        HashSet<Node> closed = new HashSet<Node>();
        
            // add starting node int open set
        open.add(start);
        
        while(!open.isEmpty()) {
                // get top-most node
                // the one with the lowest FCost and HCost
            Node current = open.peek();
            
                // if reached end, finish pathfinding
            if(current == end) {
                    // regenerate path from the node parents
                while (current != start) {
                    path.add(current);
                    current = current.getParent();
                }

                return path;
            }
            
            open.remove(current);
            closed.add(current);
            
                // iterate through neighbor nodes
            for(Node n : getNeighbors(current)) {
                    // if node is occupied or is in closed set, do nothing
                if(!n.isWalkable() || closed.contains(n) || (n.isOccupied() && n.posX != end.posX && n.posY != end.posY)) {
                    continue;
                }
                
                    // set heuristic cost of node
                n.setHCost(getDistance(n, end));
                
                    // if node is not in open, add it
                if(!open.contains(n)) {
                    open.add(n);
                }
                
                    // tentative g cost, to check if the node generates a shorter 
                    // transition between the neighbor and 'current' node
                int tent_g = current.getGCost() + getDistance(current, n);
                    // if tentative G cost is higher or same, the path is not shorter,
                    // do nothing
                if(tent_g >= n.getGCost()) {
                    continue;
                }
                
                    //set parent node, g value and f cost
                n.setParent(current);
                n.setGCost(tent_g);
                n.setFCost(n.getGCost() + getDistance(n, end));
                
            }
        }
        
        return path;
    }
    
    /**
     * get the distance between 2 nodes, using orthogonal distances take 10 points
     * and diagonal take 14. Based on properties of triangles with sides 1, 1 and sqrt(2)
     * @param a <code>Node</code> node of the starting position
     * @param b <code>Node</code> node of finish position
     * @return <code>int</code> value of distance
     */
    public int getDistance(Node a, Node b) {
            // distance between the (x,y) coordinates
        int dstX = Math.abs(a.posX - b.posX);
        int dstY = Math.abs(a.posY - b.posY);
        
            // get the distance. Multiplying the minimum amount of diagonals by 14
            // and the diference between de distances by 10
        if(dstX > dstY) {
            return 14*dstY + 10*(dstX-dstY);
        }
        return 14*dstX + 10*(dstY-dstX);
    }
    
    /**
     * get neighboring nodes of start node.
     * Does not take into account the nodes in edges, that have beside them a wall or a non-passable object
     * @param start <code>Node</code> starting node
     * @return <code>ArrayList</code> list with the neighbors
     */
    public ArrayList<Node> getNeighbors(Node start) {
            // list of neighbors
        ArrayList<Node> neighbors = new ArrayList<Node>();
        
            // iterate through those around it
        for(int i = -1; i <= 1; i++) {
            for(int j = -1; j <= 1; j++) {
                    // (0,0) is position of self
                if(i == 0 && j == 0) {
                    continue;
                }
                
                    // position of neighbor to check
                int checkX = start.posX + i;
                int checkY = start.posY + j;
                
                    // if check positions do not leeave the grid
                if(checkX >= 0 && checkX < grid.size() && checkY >= 0 && checkY < grid.get(0).size()) {
                        
                        // if the node is in an edge, check if the node is beside a wall
                    if(Math.abs(i) == Math.abs(j)) {
                            // side nodes to check
                        Node side1 = (i < 0) ? grid.get(checkX + 1).get(checkY) : grid.get(checkX - 1).get(checkY);
                        Node side2 = (j < 0) ? grid.get(checkX).get(checkY + 1) : grid.get(checkX).get(checkY - 1);
                            // if either cant be moved onto, do nothing
                        if(!side1.isWalkable() || !side2.isWalkable() || side1.isOccupied() || side2.isOccupied()) {
                            continue;
                        }
                    }
                    
                        // if passed restrictions, add into list
                    neighbors.add(grid.get(checkX).get(checkY));
                    
                }
            }
        }
        return neighbors;
    }
}

/**
 * Comparator class to use in priority queue to sort based on nodes, h and f costs
 * @author Nissim
 */
class comparator implements Comparator<Node> {

    /**
     * Comparation between 2 nodes
     * @param o1 <code>Node</code> first node
     * @param o2 <code>Node</code> second node
     * @return <code>int</code> where to place the node in PriorityQueue
     */
    public int compare(Node o1, Node o2) {
        if(o1.getFCost() < o2.getFCost() && o1.getHCost() < o2.getHCost()) {
            return -1;
        }
        else if(o1.getFCost() > o2.getFCost() && o1.getHCost() > o2.getHCost()) {
            return 1;
        }
        return 0;
    }
    
}