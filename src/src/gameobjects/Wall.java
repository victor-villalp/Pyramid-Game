package src.gameobjects;

import src.Collidable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall extends GameObject implements Collidable{

    private final boolean walkThrough;

    public Wall(int x, int y, BufferedImage wallImg, boolean walkThrough){
        super(x, y, wallImg);
        this.walkThrough = walkThrough;
    }

    boolean isWalkThrough(){
        return this.walkThrough;
    }

    @Override
    public void update() {

    }

    @Override
    public void collision(Collidable obj) {

    }

    @Override
    public void drawImage(Graphics2D g2d) {
        if (this.alive){
            g2d.drawImage(this.img, x, y, null);
        }
    }

}
