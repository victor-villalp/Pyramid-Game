package src.gameobjects;

import src.Collidable;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.*;
import static src.gameobjects.Explorer.getVx;

public class Mummy extends GameObject implements Collidable {

    private int initX, initY, prevX, prevY, vx, vy;
    private int imgRow, frames, currentFrame = 0, index = 0;
    private static int expX, expY;
    private static long lastTimeScarabUsed;
    private static Boolean isScared;
    private  BufferedImage[][] mummyImages;

    public Mummy(int x, int y, BufferedImage[][] mummyImages) {
        super(x, y, mummyImages[0][0]);
        this.initX = x;
        this.initY = y;
        this.mummyImages = mummyImages;
        this.frames = mummyImages.length;
        isScared = false;
    }

    boolean isScared(){
        return isScared;
    }

    static void setIsScared(Boolean scared) {
        isScared = scared;
    }

    static void setLastTimeScarabUsed(long time) {
        lastTimeScarabUsed = time;
    }

    static void setExplorerCoord(int expX, int expY){
        Mummy.expX = expX;
        Mummy.expY = expY;
    }

    @Override
    public void update() {
        this.prevX = x;
        this.prevY = y;

        double distanceToExplorer = sqrt( pow((x-expX), 2) +  pow((y-expY), 2)  );

        if (distanceToExplorer < 200 ) { // Mummy follows explorer when distance between the two is < 200
            if (!isScared ) {
                if (this.y > expY && getVx() == 0) { // Move up
                    imgRow = 0;
                    vx = 0;
                    vy = -1;
                }
                if (this.y < expY && getVx() == 0) { // Move down
                    imgRow = 1;
                    vx = 0;
                    vy = 1;
                }
                if (this.x > expX && Explorer.getVy() == 0) { // Move left
                    imgRow = 2;
                    vx = -1;
                    vy = 0;
                }
                if (this.x < expX && Explorer.getVy() == 0) { // Move right
                    imgRow = 3;
                    vx = 1;
                    vy = 0;
                }
            } else{
                if (System.currentTimeMillis() - lastTimeScarabUsed < 10000 ) { // When the mummies are they walk away from explorer for 10 seconds
                    if (this.y < expY && getVx() == 0) { // Move down
                        imgRow = 0;
                        vx = 0;
                        vy = -1;
                    }
                    if (this.y > expY && getVx() == 0) { // Move up
                        imgRow = 1;
                        vx = 0;
                        vy = 1;
                    }
                    if (this.x < expX && Explorer.getVy() == 0) { // Move left
                        imgRow = 2;
                        vx = -1;
                        vy = 0;
                    }
                    if (this.x > expX && Explorer.getVy() == 0) { // Move Right
                        imgRow = 3;
                        vx = 1;
                        vy = 0;
                    }
                } else {
                    isScared = false;
                }
            }
        } else {
            vx = 0;
            vy = 0;
        }
        x += vx;
        y += vy;
        runAnimation();
        this.getRec().setLocation(x,y);
    }

    private void runAnimation(){
        if (index > 25) {
            index = 0;
            for (int i = 0; i < frames; i++){
                if (currentFrame == i){
                    this.img = mummyImages[imgRow][i];
                }
            }
            currentFrame++;
            if(currentFrame > frames){
                currentFrame =0;
            }
        }
        index++;
    }

    @Override
    public void collision(Collidable obj) {
        if (obj instanceof Explorer){
            this.x = this.initX;
            this.y = this.initY;
        }
        if (obj instanceof Bullet || (obj instanceof Explorer && isScared)){
            alive = false;
        }
        if (obj instanceof Beetle || obj instanceof Scorpion){
            this.x = prevX;
            this.y = prevY;
        }
        if (obj instanceof Wall){
            this.x = prevX;
            this.y = prevY;
            if ((Math.abs(this.x - ((Wall) obj).getX()) < 16 && this.vy == 0) ||
                    (Math.abs(this.y - ((Wall) obj).getY()) < 16 && this.vx == 0)){
                alive = false;
            }
        }
        if (obj instanceof MovableBlock) {
            if (((MovableBlock) obj).getVx() != 0){
                if (((MovableBlock) obj).getVx() < 0){
                    this.x -= ((MovableBlock) obj).getVx() + 3;
                }
                if (((MovableBlock) obj).getVx() > 0){
                    this.x += ((MovableBlock) obj).getVx() + 3;
                }
            } else {
                this.x = prevX;
            }
            if (((MovableBlock) obj).getVy()!=0) {
                if (((MovableBlock) obj).getVy() < 0){
                    this.y -= ((MovableBlock) obj).getVy() + 4;
                }
                if (((MovableBlock) obj).getVy() > 0){
                    this.y += ((MovableBlock) obj).getVy() + 3;
                }
            } else {
                this.y = prevY;
            }
        }
    }

    @Override
    public void drawImage(Graphics2D g2d) {
        if (this.vx == 0 && this.vy ==0){
            this.img = mummyImages[imgRow][0];
        }
        g2d.drawImage(this.img, x, y, null);
    }
}
