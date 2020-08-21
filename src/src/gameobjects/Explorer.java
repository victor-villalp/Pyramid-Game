package src.gameobjects;

import src.Collidable;
import src.GameWorld;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Explorer extends GameObject implements Collidable {

    private static int vx , vy;
    private int initX, initY, prevX, prevY, angle;
    private int bullets = 0, lives = 3, scarab = 0, score = 0;
    private int imgRow, frames, currentFrame = 0, index = 0, count = 0;
    private long lightTimer, firingTimer, scarabTimer;
    private boolean upPressed, downPressed, rightPressed, leftPressed, lightPressed, scarabPressed, shootPressed;
    private boolean hasSword, hasWon;
    private BufferedImage[][] explorerImages;
    private GameWorld gw;

    public Explorer(int x, int y, BufferedImage[][] explorerImages, GameWorld gw) {
        super(x, y, explorerImages[0][0]);
        this.initX = x;
        this.initY = y;
        this.explorerImages = explorerImages;
        this.frames = explorerImages.length;
        this.hasSword = false;
        this.hasWon = false;
        this.gw = gw;
    }

    static int getVx(){
        return vx;
    }

    static int getVy(){
        return vy;
    }

    public boolean isLightPressed() {return this.lightPressed;}

    public boolean hasSword() {return this.hasSword;}

    public boolean hasWon() {return this.hasWon;}

    public int getLives() {
        return this.lives;
    }

    public int getScarab(){
        return this.scarab;
    }

    public int getScore() {
        return this.score;
    }

    public void toggleUpPressed() {
        this.upPressed = true;
    }

    public void toggleDownPressed() {
        this.downPressed = true;
    }

    public void toggleRightPressed() {
        this.rightPressed = true;
    }

    public void toggleLeftPressed() {
        this.leftPressed = true;
    }

    public void toggleLightPressed() { this.lightPressed = true; }

    public void toggleScarabPressed() { this.scarabPressed = true; }

    public void toggleShootPressed() { this.shootPressed = true; }

    public void unToggleUpPressed() {
        this.upPressed = false;
        vy = 0;
    }

    public void unToggleDownPressed() {
        this.downPressed = false;
        vy = 0;
    }

    public void unToggleRightPressed() {
        this.rightPressed = false;
        vx = 0;
    }

    public void unToggleLeftPressed() {
        this.leftPressed = false;
        vx = 0;
    }

    public void unToggleLightPressed() { this.lightPressed = false; }

    public void unToggleScarabPressed() { this.scarabPressed = false;}

    public void unToggleShootPressed() { this.shootPressed = false;}

    @Override
    public void update() {
        this.prevX = x;
        this.prevY = y;

        if(this.upPressed){
            this.moveUp();
        }
        if(this.downPressed){
            this.moveDown();
        }
        if (this.leftPressed) {
            this.moveLeft();
        }
        if (this.rightPressed) {
            this.moveRight();
        }
        if (this.lightPressed){
            this.scareMummyWithLight();
        }
        if (this.scarabPressed){
            this.scareMummyWithScarab();
        }
        if (this.shootPressed){
            this.shoot();
        }

        runAnimation();
        this.getRec().setLocation(this.x,this.y);

        Beetle.setExplorerCoord(this.x, this.y); //pass explorer location to creatures
        Mummy.setExplorerCoord(this.x, this.y);
        Scorpion.setExplorerCoord(this.x, this.y);

        if (hasSword && score > 0 && (x >= initX && x <= initX + 64) && (y >= initY && y <= initY + 64)){
            hasWon = true;
        }
    }

    private void moveUp() {
        if (count == 2) {
            angle = -90;
            imgRow = 0;
            vy = -2;
            y += vy;
            count = 0;
        }
        count++;
    }

    private void moveDown() {
        if (count == 2 && !leftPressed || !rightPressed) {
            angle = 90;
            imgRow = 1;
            vy = 1;
            y += vy;
            count = 0;
        }
        count++;
    }

    private void moveLeft() {
        if (count == 2 && !upPressed || !downPressed) {
            angle = 180;
            imgRow = 2;
            vx = -1;
            x += vx;
            count = 0;
        }
        count++;
    }

    private void moveRight() {
        if (count == 2 && !upPressed || !downPressed) {
            angle = 0;
            imgRow = 3;
            vx = 1;
            x += vx;
            count = 0;
        }
        count++;
    }

    private void shoot() {  // Limit firing to one bullet per second
        if (bullets != 0 && (System.currentTimeMillis() - firingTimer > 1000)) {
            Bullet blt = new Bullet(x , y , angle);
            gw.addGameObject(blt);
            firingTimer = System.currentTimeMillis();
            bullets--;
        }
    }

    private void scareMummyWithLight() { // Explorer loses 300 points per second when the light is used
        if (hasSword && score > 0 && !hasWon && (System.currentTimeMillis() - lightTimer > 10/3)){
            Mummy.setIsScared(true);
            lightTimer = System.currentTimeMillis();
            score -= 10;
        } else {
            Mummy.setIsScared(false);
        }
    }

    private void scareMummyWithScarab() { // Set one second delay to prevent using more than one scarab at a time
        if (scarab != 0 && (System.currentTimeMillis() - scarabTimer > 1000)) {
            Mummy.setIsScared(true);
            Mummy.setLastTimeScarabUsed(System.currentTimeMillis());
            scarabTimer = System.currentTimeMillis();
            scarab--;
        }
    }

    private void runAnimation() {
        if (index > 25){
            index = 0;
            for (int i = 0; i < frames; i++){
                if (currentFrame == i){
                    this.img = explorerImages[imgRow][i];
                }
            }
            currentFrame++;
            if(currentFrame > frames){
                currentFrame =0;
            }
        }
        index++;
    }

    private void removeLive() {
        if (lives >= 1) {
            lives--;
            x = initX;
            y = initY;
        }
        if (hasSword || lives == 0) {
            score = 0;
            lives = 0;
            alive = false;
        }
    }

    @Override
    public void collision(Collidable obj) {
        if (obj instanceof Beetle || obj instanceof Scorpion) {
            removeLive();
        }
        if (obj instanceof MovableBlock){
            if (((MovableBlock)obj).isAgstWall()){
                this.x = prevX;
                this.y = prevY;
            }
        }
        if (obj instanceof Mummy){
            if (!((Mummy) obj).isScared()) {
                removeLive();
            }
        }
        if (obj instanceof PowerUp) {
            if (((PowerUp) obj).isPistol()) {
                this.bullets = 3;
            } else {
                lives++;
            }
        }
        if (obj instanceof Treasure) {
            if (((Treasure) obj).isAmulet()) {
                score += 10;
            }
            if (((Treasure) obj).isAnubis()) {
                score += 100;
            }
            if (((Treasure)obj).isScarab()){
                scarab += 1;
            }
            if (((Treasure)obj).isSword()){
                score += 5000;
                hasSword = true;
            }
        }
        if (obj instanceof Wall) {
            if (!((Wall) obj).isWalkThrough()) { // Explorer can walkable walls
                this.x = prevX;
                this.y = prevY;
            }
        }
    }

    @Override
    public void drawImage(Graphics2D g2d) {
        if (vx == 0 && vy == 0){
            this.img = explorerImages[imgRow][0];
        }
        g2d.drawImage(this.img, x, y, null);
    }
}