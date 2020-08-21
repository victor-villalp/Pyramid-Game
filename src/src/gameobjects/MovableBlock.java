package src.gameobjects;

import src.Collidable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MovableBlock extends GameObject implements Collidable {

    private int initX, initY, vx, vy;
    private boolean isVertical, isAgstWall = false;
    private long blockTimer;

    public MovableBlock(int x, int y, BufferedImage img, boolean isVertical) {
        super(x, y, img);
        this.initX = x;
        this.initY = y;
        this.isVertical = isVertical;
    }
    
    boolean isAgstWall(){
        return this.isAgstWall;
    }

    int getVx(){
        return this.vx;
    }

    int getVy(){
        return this.vy;
    }

    @Override
    public void collision(Collidable obj) {
        if (obj instanceof Explorer){
            if (!isAgstWall) {
                if (this.isVertical) {
                    if (Explorer.getVy()!=0){ // Move block in the y axis as long as explorer's vy != 0
                        vy = Explorer.getVy();
                        y += vy;
                   }
                } else {
                    if (Explorer.getVx()!=0){ // Move block in the x axis as long as explorer's vx != 0
                        vx = Explorer.getVx();
                        x += vx;
                    }
                }
                blockTimer = System.currentTimeMillis();
            }
        }
        if (obj instanceof MovableBlock){ // If movable block collides with movable block return the blocks to initial position
            isAgstWall = true;
        }
        if (obj instanceof Wall){ // If movable wall collides with wall return movable block to initial position
            vx = 0;
            vy = 0;
            isAgstWall = true;
        }
    }

    @Override
    public void update() {
        if (System.currentTimeMillis() - blockTimer > 500) {
            if (isAgstWall){
                if (this.x < this.initX){
                    vx = -1;
                    this.x -= vx;
                } else{
                    vx = 1;
                    this.x -= vx;
                }
               if (this.y != this.initY){
                    vy = -1;
                    this.y -= vy;
                } else{
                    vy = 1;
                    this.y -= vy;
                }
                isAgstWall = false;
            } else {
                if (this.x != this.initX && !isVertical){
                    this.x -= vx;
                } else{
                    vx = 0;
                }
                if (this.y != this.initY && isVertical){
                    this.y -= vy;
                } else {
                    vy = 0;
                }
            }
        }
        this.getRec().setLocation(x, y);
    }

    @Override
    public void drawImage(Graphics2D g2d) {
        g2d.drawImage(this.img, x, y, null);
    }
}
