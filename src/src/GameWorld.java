package src;

import src.gameobjects.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GameWorld extends JPanel implements KeyListener {

    private static final int WORLD_WIDTH = 1920 ,WORLD_HEIGHT = 1440; //WORLD_WIDTH = 2560 ,WORLD_HEIGHT = 2240;
    private static final int SCREEN_WIDTH = 800, SCREEN_HEIGHT = 600;
    private BufferedImage world;
    private JFrame jf;
    private Explorer player;
    private BufferedImage bg, panel, lives, scarab, dimScreen, brighterScreen, winnerScreen, gameOver;
    private BufferedImage beetleUp, beetleDown, scorpionLeft , scorpionRight;
    private BufferedImage wall, breakableWall, horzBlock, vertBlock;
    private BufferedImage amulet, anubis, sword;
    private BufferedImage pistol, potion;
    private BufferedImage [][] explorerImages = new BufferedImage[4][4];
    private BufferedImage [][] mummyImages = new BufferedImage[4][4];
    private boolean restartGame = false;
    private long keyTimer = System.currentTimeMillis();
    private ArrayList<GameObject> gameObjects = new ArrayList<>();

    public void addGameObject(GameObject obj){
        this.gameObjects.add(obj);
    }

    public static void main(String[] args) {
        GameWorld game = new GameWorld();
        game.init();
        try {
            while (true) {
                game.gameUpdate();
                game.checkCollisions();
                game.repaint();
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {

        }
    }

    private void init(){
        if (!restartGame) {
            this.jf = new JFrame("Pyramid Panic");
            this.jf.addKeyListener(this);
            this.world = new BufferedImage(GameWorld.WORLD_WIDTH, GameWorld.WORLD_HEIGHT, BufferedImage.TYPE_INT_RGB);
            BufferedImage bullet;

            try {
                bg = ImageIO.read(getClass().getResource("/resources/Background2.bmp"));
                panel = ImageIO.read(getClass().getResource("/resources/Panel.gif"));
                lives = ImageIO.read(getClass().getResource("/resources/Lives.gif"));
                dimScreen = ImageIO.read(getClass().getResource("/resources/Dim_screen.png"));
                brighterScreen = ImageIO.read(getClass().getResource("/resources/Brighter_screen.png"));
                winnerScreen = ImageIO.read(getClass().getResource("/resources/Congratulations.gif"));
                gameOver = ImageIO.read(getClass().getResource("/resources/Game_over.png"));

                beetleUp = ImageIO.read(getClass().getResource("/resources/Beetle_up.gif"));
                beetleDown = ImageIO.read(getClass().getResource("/resources/Beetle_down.gif"));
                scorpionLeft = ImageIO.read(getClass().getResource("/resources/Scorpion_left.gif"));
                scorpionRight = ImageIO.read(getClass().getResource("/resources/Scorpion_right.gif"));

                // Explorer up images
                explorerImages[0][0] = ImageIO.read(getClass().getResource("/resources/Explorer_up.gif"));
                explorerImages[0][1] = ImageIO.read(getClass().getResource("/resources/Explorer_up0.gif"));
                explorerImages[0][2] = ImageIO.read(getClass().getResource("/resources/Explorer_up.gif"));
                explorerImages[0][3] = ImageIO.read(getClass().getResource("/resources/Explorer_up1.gif"));
                // Explorer down images
                explorerImages[1][0] = ImageIO.read(getClass().getResource("/resources/Explorer_down.gif"));
                explorerImages[1][1] = ImageIO.read(getClass().getResource("/resources/Explorer_down0.gif"));
                explorerImages[1][2] = ImageIO.read(getClass().getResource("/resources/Explorer_down.gif"));
                explorerImages[1][3] = ImageIO.read(getClass().getResource("/resources/Explorer_down1.gif"));
                // Explorer left images
                explorerImages[2][0] = ImageIO.read(getClass().getResource("/resources/Explorer_left.gif"));
                explorerImages[2][1] = ImageIO.read(getClass().getResource("/resources/Explorer_left0.gif"));
                explorerImages[2][2] = ImageIO.read(getClass().getResource("/resources/Explorer_left.gif"));
                explorerImages[2][3] = ImageIO.read(getClass().getResource("/resources/Explorer_left1.gif"));
                // Explorer right images
                explorerImages[3][0] = ImageIO.read(getClass().getResource("/resources/Explorer_right.gif"));
                explorerImages[3][1] = ImageIO.read(getClass().getResource("/resources/Explorer_right0.gif"));
                explorerImages[3][2] = ImageIO.read(getClass().getResource("/resources/Explorer_right.gif"));
                explorerImages[3][3] = ImageIO.read(getClass().getResource("/resources/Explorer_right1.gif"));

                // Mummy up images
                mummyImages[0][0] = ImageIO.read(getClass().getResource("/resources/Mummy_up.gif"));
                mummyImages[0][1] = ImageIO.read(getClass().getResource("/resources/Mummy_up0.gif"));
                mummyImages[0][2] = ImageIO.read(getClass().getResource("/resources/Mummy_up.gif"));
                mummyImages[0][3] = ImageIO.read(getClass().getResource("/resources/Mummy_up1.gif"));
                // Mummy down images
                mummyImages[1][0] = ImageIO.read(getClass().getResource("/resources/Mummy_down.gif"));
                mummyImages[1][1] = ImageIO.read(getClass().getResource("/resources/Mummy_down0.gif"));
                mummyImages[1][2] = ImageIO.read(getClass().getResource("/resources/Mummy_down.gif"));
                mummyImages[1][3] = ImageIO.read(getClass().getResource("/resources/Mummy_down1.gif"));
                // Mummy left images
                mummyImages[2][0] = ImageIO.read(getClass().getResource("/resources/Mummy_left.gif"));
                mummyImages[2][1] = ImageIO.read(getClass().getResource("/resources/Mummy_left0.gif"));
                mummyImages[2][2] = ImageIO.read(getClass().getResource("/resources/Mummy_left.gif"));
                mummyImages[2][3] = ImageIO.read(getClass().getResource("/resources/Mummy_left1.gif"));
                // Mummy right images
                mummyImages[3][0] = ImageIO.read(getClass().getResource("/resources/Mummy_right.gif"));
                mummyImages[3][1] = ImageIO.read(getClass().getResource("/resources/Mummy_right0.gif"));
                mummyImages[3][2] = ImageIO.read(getClass().getResource("/resources/Mummy_right.gif"));
                mummyImages[3][3] = ImageIO.read(getClass().getResource("/resources/Mummy_right1.gif"));

                wall = ImageIO.read(getClass().getResource("/resources/Wall1.gif"));
                breakableWall = ImageIO.read(getClass().getResource("/resources/Wall2.gif"));
                horzBlock = ImageIO.read(getClass().getResource("/resources/Block_hor.gif"));
                vertBlock = ImageIO.read(getClass().getResource("/resources/Block_vert.gif"));

                amulet = ImageIO.read(getClass().getResource("/resources/Treasure1.gif"));
                anubis = ImageIO.read(getClass().getResource("/resources/Treasure2.gif"));
                scarab = ImageIO.read(getClass().getResource("/resources/Scarab.gif"));
                sword = ImageIO.read(getClass().getResource("/resources/Sword.gif"));

                pistol = ImageIO.read(getClass().getResource("/resources/Pistol.gif"));
                potion = ImageIO.read(getClass().getResource("/resources/Potion.gif"));
                bullet = ImageIO.read(getClass().getResource("/resources/Bullet.gif"));
                Bullet.setImage(bullet);

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/resources/Map.txt")));
            String line;
            int j = 0; // Keeps track of rows

            while((line = br.readLine())!= null){ // Reads Map.txt file line by line
                String[] tokens = line.split(" ");
                for(int i = 0; i < tokens.length; i++){
                    switch(tokens[i]) {
                        case "1": // Wall
                            gameObjects.add(new Wall(i*32, j*32, wall, false));
                            break;
                        case "2": // Walk-through wall
                            gameObjects.add(new Wall(i*32, j*32, breakableWall, true));
                            break;
                        case "3": // Horizontal movable block
                            gameObjects.add(new MovableBlock(i*32, j*32,horzBlock,false));
                            break;
                        case "4": // Vertical movable block
                            gameObjects.add(new MovableBlock(i*32, j*32,vertBlock,true));
                            break;
                        case "5": // Amulet
                            gameObjects.add(new Treasure(i*32, j*32, amulet,true,false, false,false));
                            break;
                        case "6": // Anubis
                            gameObjects.add(new Treasure(i*32, j*32, anubis,false,true, false,false));
                            break;
                        case "7": // Scarab
                            gameObjects.add(new Treasure(i*32, j*32, scarab,false,false, true,false));
                            break;
                        case "8": // Sword
                            gameObjects.add(new Treasure(i*32, j*32, sword,true,false, false,true));
                            break;
                        case "9": // Pistol
                            gameObjects.add(new PowerUp(i*32, j*32, pistol,true));
                            break;
                        case "10": // Potion
                            gameObjects.add(new PowerUp(i*32, j*32, potion, false));
                            break;
                        case "11": // Beetle
                            gameObjects.add(new Beetle(i*32, j*32, 1, beetleDown, beetleUp));
                            break;
                        case "12": // Mummy
                            gameObjects.add(new Mummy(i*32, j*32, mummyImages));
                            break;
                        case "13": // Scorpion
                            gameObjects.add(new Scorpion(i*32, j*32, 1, scorpionRight, scorpionLeft));
                            break;
                        default:
                            break;
                    }
                }
                j++;
            }

            br.close();

        } catch (IOException e) {
            System.out.println("File failed to open!");
        }

        player = new Explorer(WORLD_WIDTH / 2, WORLD_HEIGHT - 128, explorerImages,this);
        gameObjects.add(player);
        ExplorerControls ec = new ExplorerControls(player, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE, KeyEvent.VK_SHIFT, KeyEvent.VK_ENTER);
        this.jf.addKeyListener(ec);

        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);
        this.jf.setSize(GameWorld.SCREEN_WIDTH , GameWorld.SCREEN_HEIGHT + 74);
        this.jf.setResizable(false);

        jf.setLocationRelativeTo(null);
        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);
    }

    private void gameUpdate() {
        for (int i = 0; i < gameObjects.size(); i++) {
            if (gameObjects.get(i).isAlive()) {
                gameObjects.get(i).update();
            }else{
                gameObjects.remove(i);
                i--;
            }
        }
    }

    private void checkCollisions () {
        for(int i=0; i < gameObjects.size(); i++) {
            for (int j = i + 1; j < gameObjects.size(); j++) {
                Collidable co1 = (Collidable) gameObjects.get(i);
                Collidable co2 = (Collidable) gameObjects.get(j);
                if (((GameObject) co1).getRec().getBounds().intersects(((GameObject) co2).getRec().getBounds())) {
                    co1.collision(co2);
                    co2.collision(co1);
                }
            }
        }
    }

    private int getScreenXCoord(Explorer exp){
        int xCoord = exp.getX();
        if (xCoord < SCREEN_WIDTH / 2) {
            xCoord = SCREEN_WIDTH / 2;
        }
        if (xCoord > WORLD_WIDTH - SCREEN_WIDTH / 2) {
            xCoord = WORLD_WIDTH - SCREEN_WIDTH / 2;
        }
        return xCoord - SCREEN_WIDTH / 2;
    }

    private int getScreenYCoord(Explorer exp){
        int yCoord = exp.getY();
        if (yCoord < SCREEN_HEIGHT / 2) {
            yCoord = SCREEN_HEIGHT / 2;
        }
        if (yCoord > WORLD_HEIGHT - SCREEN_HEIGHT / 2) {
            yCoord = WORLD_HEIGHT - SCREEN_HEIGHT / 2;
        }
        return yCoord - SCREEN_HEIGHT / 2;
    }

    public void paintComponent (Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        super.paintComponent(g2);

        for (int i = 0; i < WORLD_WIDTH; i += 640) { // Refresh background tiles
            for (int j = 0; j < WORLD_HEIGHT; j += 448)
                buffer.drawImage(bg, i, j, 640, 448, this);
        }

        for (int i = 0; i < gameObjects.size(); i++) { // Draw all game objects
            gameObjects.get(i).drawImage(buffer);
        }

        BufferedImage window = world.getSubimage(getScreenXCoord(player), getScreenYCoord(player), SCREEN_WIDTH, SCREEN_HEIGHT);
        g2.drawImage(window, 0, 0, null);

        if(player.hasSword()){
            if (player.isLightPressed()) {
                g2.drawImage(brighterScreen, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
            } else{
                int minX, maxX, minY, maxY;

                if ((player.getX() - SCREEN_WIDTH / 2) < 0) {
                    maxX = Integer.max(SCREEN_WIDTH / 2 - (SCREEN_WIDTH / 2 - player.getX()) + SCREEN_WIDTH / 8, SCREEN_WIDTH / 8);
                    minX = Integer.max(maxX - SCREEN_WIDTH / 4, 0);
                    if (minX == 0){
                        maxX = SCREEN_WIDTH/4;
                    }
                } else if ((player.getX() + SCREEN_WIDTH / 2) > WORLD_WIDTH) {
                    maxX = Integer.min(SCREEN_WIDTH - (WORLD_WIDTH - player.getX()) + SCREEN_WIDTH / 8, SCREEN_WIDTH);
                    minX = maxX - SCREEN_WIDTH / 4;
                    if (maxX == SCREEN_WIDTH){
                        minX = SCREEN_WIDTH * 3 / 4;
                    }
                } else {
                    minX = SCREEN_WIDTH * 3 / 8 ;
                    maxX = SCREEN_WIDTH * 5 / 8;
                }

                if ((player.getY() - SCREEN_HEIGHT / 2) < 0) {
                    maxY = Integer.max(SCREEN_HEIGHT / 2 - (SCREEN_HEIGHT / 2 - player.getY()) + SCREEN_HEIGHT / 6, SCREEN_HEIGHT / 6);
                    minY = Integer.max(maxY - SCREEN_HEIGHT / 3, 0);
                    if (minY == 0){
                        maxY = SCREEN_WIDTH / 3;
                    }
                } else if ((player.getY() + SCREEN_HEIGHT / 2) > WORLD_HEIGHT) {
                    maxY = Integer.min(SCREEN_HEIGHT - (WORLD_HEIGHT - player.getY()) + SCREEN_HEIGHT / 6, SCREEN_HEIGHT);
                    minY = maxY - SCREEN_HEIGHT / 3;
                    if (maxY == SCREEN_HEIGHT){
                        minY = SCREEN_HEIGHT * 2 / 3;
                    }
                } else {
                    minY = SCREEN_HEIGHT / 3;
                    maxY = SCREEN_HEIGHT * 2 / 3;
                }

                for (int i = 0; i < SCREEN_WIDTH; i++) {
                    for (int j = 0; j < SCREEN_HEIGHT; j++) {
                        if (i < minX || i > maxX || j < minY || j > maxY) {
                            window.setRGB(i, j, 0);
                        }
                    }
                }

                g2.drawImage(window, 0, 0, null);
                g2.drawImage(dimScreen, minX - 1 , minY - 1, 205, 205, null);
            }
        }

        g2.drawImage(panel, 0, SCREEN_HEIGHT , SCREEN_WIDTH , panel.getHeight() + 5 , null); // Draws panel to fit screen

        for (int i = 0; i < player.getLives(); i++) { // Draws number of lives
            g2.drawImage(lives, 110 + i * (lives.getWidth() + 4), SCREEN_HEIGHT + 2, lives.getWidth(), lives.getHeight(), null);
        }

        g2.drawImage(scarab, SCREEN_HEIGHT / 2 + 80, SCREEN_HEIGHT + 2, scarab.getWidth() , panel.getHeight(), null); // Draw scarab icon

        g2.setFont(new Font("SansSerif", Font.BOLD, 30));
        g2.setColor(Color.YELLOW);
        g2.drawString(Integer.toString(player.getScarab()),SCREEN_WIDTH / 2 + 25, SCREEN_HEIGHT + 30); // Draw number of scarabs
        g2.drawString(Integer.toString(player.getScore()), SCREEN_WIDTH / 2 + 275, SCREEN_HEIGHT + 30); // Draw score

        if (!player.isAlive()){
            g2.drawImage(gameOver, 0,0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
        }
        if (player.hasWon()){
            g2.drawImage(winnerScreen, 0,0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!player.isAlive() || player.hasWon() && System.currentTimeMillis() - keyTimer > 5000) {
                this.gameObjects.clear();
                this.restartGame = !player.isAlive() || player.hasWon();
                init();
                keyTimer = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode()== KeyEvent.VK_SPACE) {
            this.restartGame = false;
        }
    }
}