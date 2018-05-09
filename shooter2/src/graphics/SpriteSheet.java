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
public class SpriteSheet {
    private BufferedImage sheet;
    
    /**
     * Create a new sprite sheet
     * @param sheet the <code>image</code> with the sprites
     */
    public SpriteSheet(BufferedImage sheet) {
        this.sheet = sheet;
    }
    
    /**
     * Crop a sprite from the sheet
     * @param x an <code>int</code> value with the x coordinates
     * @param y an <code>int</code> value with the y coordinates
     * @param width an <code>int</code> value with the width of the sprite
     * @param height an <code>int</code> value with the height of the sprite
     * @return 
     */
    public BufferedImage crop (int x, int y, int width, int height) {
        return sheet.getSubimage(x, y, width, height);
    }
    
}
