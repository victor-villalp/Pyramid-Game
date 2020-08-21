package src.gameobjects;

import src.Collidable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PowerUp extends GameObject implements Collidable {

    private boolean isPistol;

    public PowerUp(int x, int y, BufferedImage img, Boolean isPistol) {
        super(x, y, img);
        this.isPistol = isPistol;
    }

    boolean isPistol() {
        return this.isPistol;
    }

    @Override
    public void collision(Collidable obj) {
        if (obj instanceof Explorer) {
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

