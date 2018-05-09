/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Actor.Actor;
import Items.Item;

/**
 *
 * @author Nissim
 */
public class Node {

    private Node parent;                // parent node, to generate path
    private boolean walkable;           // whether the tile is walkable
    private int g;                      // movement cost between nodes
    private int h;                      // cost heuristics between node and target
    private int f;                      // total cost between node and target
    private boolean occupied;           // whether node is occupied
    private Actor owner;                // actor on tile
    private Item item;

    /**
     *
     */
    public int posX, 

    /**
     *
     */
    posY;              // position in grid of node

    /**
     * Create a node for representation of map points
     *
     * @param posX <code>int</code> value for horizontal position in grid
     * @param posY <code>int</code> value for vertical position in grid
     * @param walk <code>boolean</code> value whether position is walkable
     */
    public Node(int posX, int posY, boolean walk) {
        this.posX = posX;
        this.posY = posY;
        this.walkable = walk;
        // value for trnasition movement cost between this node and neighbor
        this.g = Integer.MAX_VALUE;
        // total value of movement between this and objective node
        this.f = Integer.MAX_VALUE;
        // node starts as not occupied
        occupied = false;
    }

    /**
     *
     * @return
     */
    public Item getItem() {
        return item;
    }

    /**
     *
     * @param item
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * set if node is occupied
     *
     * @param occ <code>boolean</code> value of occupation
     */
    public void setOccupied(boolean occ) {
        this.occupied = occ;
    }

    /**
     * get whether the node is occupied
     *
     * @return <code>boolean</code> value of occupation
     */
    public boolean isOccupied() {
        return occupied;
    }

    /**
     * get the total cost of movement between node and target
     *
     * @return <code>int</code> total cost
     */
    public int getFCost() {
        return f;
    }

    /**
     * set total cost of movement cost
     *
     * @param f <code>int</code> new total cost
     */
    public void setFCost(int f) {
        this.f = f;
    }

    /**
     * get the heuristic cost of movement
     *
     * @return <code>int</code> the heuristic approximation
     */
    public int getHCost() {
        return h;
    }

    /**
     * set the heuristic cost of node
     *
     * @param h <code>int</code> new heuristic cost
     */
    public void setHCost(int h) {
        this.h = h;
    }

    /**
     * get the movement cost between two nodes
     *
     * @return <code>int</code> transition cost
     */
    public int getGCost() {
        return g;
    }

    /**
     * set the transition cost changes according to starting point
     *
     * @param g <code>int</code> new transition cost
     */
    public void setGCost(int g) {
        this.g = g;
    }

    /**
     * get whether node is walkable
     *
     * @return <code>boolean</code> value of 'walkability'
     */
    public boolean isWalkable() {
        return walkable;
    }

    /**
     * get parent node
     *
     * @return <code>Node</code> parent node
     */
    public Node getParent() {
        return parent;
    }

    /**
     * set parent node of this node
     *
     * @param p <code>Node</code> new Parent node
     */
    public void setParent(Node p) {
        parent = p;
    }
    
    /**
     * set the owner of the tile, the actor occupying it
     * @param act <code>Actor</code> object containing the owner
     */
    public void setOwner(Actor act) {
        owner = act;
    }
    
    /**
     * get the actor owner/occupier of the tile
     * @return <code>Actor</code> owner of the tile
     */
    public Actor getOwner() {
        return owner;
    }
}
