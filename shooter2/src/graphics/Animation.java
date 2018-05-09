/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.image.BufferedImage;

/**
 *
 * @author Juan De León
 */
public class Animation {

    private int speed;
    private int index;
    private long lastTime;
    private long timer;
    private BufferedImage[] frames;

    /**
     *
     * @param frames
     * @param speed
     */
    public Animation(BufferedImage[] frames, int speed) {
        this.frames = frames;
        this.speed = speed;
        index = 0;
        timer = 0;
        lastTime = System.currentTimeMillis();
    }

    /**
     * get current animation image
     * @return <code>BufferedImage</code> object with the image to render
     */
    public BufferedImage getCurrentFrame() {
        return frames[index];
    }

    /**
     * get the speed for the animation to play
     * @return <code>int</code> value for the speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * set the animation play speed
     * @param speed <code>int<\code> value for the speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     *
     */
    public void tick() {
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        if (timer > speed) {
            index++;
            timer = 0;
            if (index >= frames.length) {
                index = 0;
            }
        }
    }
}
