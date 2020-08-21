package src.gameobjects;

import src.Collidable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Beetle extends GameObject implements Collidable {

    private int prevY, vy;
    static private int expX, expY;
    private BufferedImage img1, img2;
    private int count = 0;

    public Beetle(int x, int y, int vy, BufferedImage img1, BufferedImage img2) {
        super(x, y, img1);
        this.prevY = y;
        this.vy = vy;
        this.img1 = img1;
        this.img2 = img2;
    }

    static void setExplorerCoord(int expX, int expY){
        Beetle.expX = expX;
        Beetle.expY = expY;
    }

    @Override
    public void collision(Collidable obj) {
        if (obj instanceof Bullet){
            this.alive = false;
        }
        if (obj instanceof MovableBlock || obj instanceof Mummy || obj instanceof Scorpion || obj instanceof Wall){
            this.y = prevY;
            vy = -vy;
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
        this.prevY = y;
        if(x <= expX + 42 && x >= expX - 42 && (y <= expY + 200 && y >= expY - 200)){ // Speed doubles when the explorer is nearby
            y += 2*vy;
        }else{
            y += vy;
        }
        this.getRec().setLocation(x,y);
    }

    @Override
    public void drawImage(Graphics2D g2d) {
        g2d.drawImage(this.img, x, y, null);
    }
}
