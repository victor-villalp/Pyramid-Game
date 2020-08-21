package src.gameobjects;

import src.Collidable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Scorpion extends GameObject implements Collidable {

    private int vx;
    static private int expX, expY;
    private BufferedImage img1, img2;
    private int count = 0;

    public Scorpion(int x, int y, int vx, BufferedImage img1, BufferedImage img2) {
        super(x, y, img1);
        this.vx = vx;
        this.img1 = img1;
        this.img2 = img2;
    }

    static void setExplorerCoord(int expX, int expY){
        Scorpion.expX = expX;
        Scorpion.expY = expY;
    }

    @Override
    public void collision(Collidable obj) {
        if (obj instanceof Bullet){
            this.alive = false;
        }
        if (obj instanceof Beetle || obj instanceof  MovableBlock || obj instanceof Mummy || obj instanceof Wall){
            vx = -vx;
            if (count == 0) { // keeps track of wall or block collisions to flip image
                this.img = img2;
                count++;
            } else {
                this.img = img1;
                count--;
            }
        }
    }

    @Override
    public void update() {
        if(y <= expY + 42 && y >= expY - 42 && (x <= expX + 200 && x >= expX - 200)){ // Speed doubles when the explorer is nearby
            x += 2*vx;
        }else{
            x += vx;
        }
        this.getRec().setLocation(x,y);
    }

    @Override
    public void drawImage(Graphics2D g2d) {
        g2d.drawImage(this.img, x, y, null);
    }
}
