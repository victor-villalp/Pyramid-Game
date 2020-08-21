package src.gameobjects;

import src.Collidable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Treasure extends GameObject implements Collidable {

    private boolean isAmulet, isAnubis, isScarab, isSword;

    public Treasure(int x, int y, BufferedImage img, Boolean isAmulet, Boolean isAnubis, Boolean isScarab, Boolean isSword) {
        super(x, y, img);
        this.isAnubis = isAnubis;
        this.isAmulet = isAmulet;
        this.isScarab = isScarab;
        this.isSword = isSword;
    }

    boolean isAmulet() {
        return this.isAmulet;
    }

    boolean isAnubis() {
        return this.isAnubis;
    }

    boolean isScarab() {
        return this.isScarab;
    }

    boolean isSword() {
        return this.isSword;
    }

    @Override
    public void collision(Collidable obj) {
        if(obj instanceof Explorer){
            this.alive = false;
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void drawImage(Graphics2D g2d) {
        if (this.alive) {
            g2d.drawImage(this.img, x, y, null);
        }
    }
}
