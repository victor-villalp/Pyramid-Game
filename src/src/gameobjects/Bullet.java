package src.gameobjects;

import src.Collidable;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject implements Collidable {

    private int vx, vy, angle;
    private static BufferedImage bulletImg;

    Bullet(int x, int y, int angle){
        super(x, y, bulletImg);
        this.vx = (int) Math.round(3*Math.cos(Math.toRadians(angle)));
        this.vy = (int) Math.round(3*Math.sin(Math.toRadians(angle)));
        this.angle = angle;
    }

    public static void setImage(BufferedImage bulletImg){
        Bullet.bulletImg = bulletImg;
    }

    @Override
    public void update() {
        x += vx;
        y += vy;
        this.getRec().setLocation(x,y);
    }

    @Override
    public void collision(Collidable obj) {
        if(obj instanceof  Beetle || obj instanceof MovableBlock || obj instanceof Mummy || obj instanceof Scorpion || obj instanceof Wall){
            this.alive = false;
        }
    }

    @Override
    public void drawImage(Graphics2D g2d) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        if(this.alive){
            g2d.drawImage(this.img, rotation, null);
        }
    }
}
