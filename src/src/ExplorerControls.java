package src;

import src.gameobjects.Explorer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ExplorerControls implements KeyListener {

    private final int up, down, right, left, light, scarab, shoot;
    private final Explorer player;

    ExplorerControls(Explorer explorer, int up, int down, int left, int right, int light, int scarab, int shoot){
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.light = light;
        this.shoot = shoot;
        this.scarab = scarab;
        this.player = explorer;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        int pressedKey = e.getKeyCode();

        if(pressedKey == up){
            this.player.toggleUpPressed();
        }
        if(pressedKey == down){
            this.player.toggleDownPressed();
        }
        if(pressedKey == left){
            this.player.toggleLeftPressed();
        }
        if(pressedKey == right){
            this.player.toggleRightPressed();
        }
        if(pressedKey == light){
            this.player.toggleLightPressed();
        }
        if(pressedKey == scarab){
            this.player.toggleScarabPressed();
        }
        if(pressedKey == shoot){
            this.player.toggleShootPressed();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int releasedKey = e.getKeyCode();

        if(releasedKey == up){
            this.player.unToggleUpPressed();
        }
        if(releasedKey == down){
            this.player.unToggleDownPressed();
        }
        if(releasedKey == left){
            this.player.unToggleLeftPressed();
        }
        if(releasedKey == right){
            this.player.unToggleRightPressed();
        }
        if(releasedKey == light){
            this.player.unToggleLightPressed();
        }
        if(releasedKey == scarab){
            this.player.unToggleScarabPressed();
        }
        if(releasedKey == shoot){
            this.player.unToggleShootPressed();
        }
    }
}
