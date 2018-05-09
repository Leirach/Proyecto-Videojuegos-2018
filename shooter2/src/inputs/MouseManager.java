/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Juan De León
 */
public class MouseManager implements MouseListener, MouseMotionListener {
    private boolean left;       // check left click
    private boolean right;      // check right click
    private int x;              // x position of the mouse
    private int y;              // y position of the mouse
    
    /**
     * constructor 
     */
    public MouseManager() {
        
    }
    /**
     * get whether the pressed key was the left button
     * @return <code>boolean<\code> value of the button 
     */
    public boolean isLeft () {
        return left;
    }
    
    /**
     * get whether the pressed key was the right button
     * @return <code>boolean<\code> value of the button 
     */
    public boolean isRight() {
        return right;
    }
    /**
     * set the value of the left mousekey press
     * @param left <code>boolean<\code> value of the new press
     */
    public void setLeft(boolean left) {
        this.left = left;
    }
    /**
     * get x position of the mouse
     * @return <code>int<\code> position of the mouse
     */
    public int getX() {
        return x;
    }
    /**
     * get y position of mouse
     * @return <code>int<\code> position of the mouse
     */
    public int getY() {
        return y;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            left = true;
            x = e.getX();
            y = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            left = false;
            x = e.getX();
            y = e.getY();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
       }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            left = true;
            x = e.getX();
            y = e.getY();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }
    
    
}
