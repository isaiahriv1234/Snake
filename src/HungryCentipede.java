import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

//extends indicates that class is inherited from another class
public class HungryCentipede extends JPanel implements ActionListener, KeyListener {
   //keylistener allows keyboard to recieve events
   //actionlistener recieves notification when action is performed in app
    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }  
//declaring variables for board dimensions
    int boardWidth;
    int boardHeight;
    int tileSize = 30; //size of each square on the canvas
    
    //centipede features
    Tile centipedeHead; //declares tile to centipede
    ArrayList<Tile> centipedeBody; //array list is the body segments for the snake

    //food
    Tile fruit; //declares the tile for the "fruit"
    Random random; //fruit generates in random tile

    //game logic
    int velocityX; //speed in the x direction
    int velocityY; //speed in y direction
    Timer gameTimer; //timer controls pace of game

    boolean gameFinish = false; //boolean declares if game finished or not

    //constructor for hungry centipede class
    HungryCentipede(int boardWidth, int boardHeight) {
        //initialize instance variable for board width/height
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        
        //sets desired component to dimension of Jpanel label
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.MAGENTA); //background color
        addKeyListener(this); //user input
        setFocusable(true); //recieve keyboard input

        centipedeHead = new Tile(5, 5); //centipede head initialize at (5,5)
        centipedeBody = new ArrayList<Tile>(); //stores segments of body

        fruit = new Tile(10, 10); //generates random fruit at 10, 10 position
        random = new Random(); //generates random number objects
        placeFood(); //randomly places fruit on canvas 

        velocityX = 1; //speed is 1 when moving right
        velocityY = 0; // speed is 0 not moving vertically
        
        //game timer
        gameTimer = new Timer(100, this); //how long it takes to start timer, milliseconds gone between frames 
        gameTimer.start();
    }   
    
    //paints game components on screen
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    //draws game components such as grid lines , fruit, centipede, score
    public void draw(Graphics g) {
        //Grid Lines
        for(int i = 0; i < boardWidth/tileSize; i++) {
            //(x1, y1, x2, y2)
            g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
            g.drawLine(0, i*tileSize, boardWidth, i*tileSize); 
        }
//draws fruit on gameboard
        //Fruit
        g.setColor(Color.orange);
        g.fill3DRect(fruit.x*tileSize, fruit.y*tileSize, tileSize, tileSize, true);

        //Centipede Head
        g.setColor(Color.white);
        g.fill3DRect(centipedeHead.x*tileSize, centipedeHead.y*tileSize, tileSize, tileSize, true);
        
        //Centipede Body
        for (int i = 0; i < centipedeBody.size(); i++) {
            Tile snakePart = centipedeBody.get(i);
            g.fill3DRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize, true);
        }

        //displays score on screen 
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameFinish) {
            g.setColor(Color.red);
            g.drawString("GAME IS OVER: " + String.valueOf(centipedeBody.size()), tileSize - 16, tileSize);
        }
        else {
            g.drawString("SCORE IS: " + String.valueOf(centipedeBody.size()), tileSize - 16, tileSize);
        }
    }

    public void placeFood(){
        fruit.x = random.nextInt(boardWidth/tileSize); //sets x to random coordinate
        fruit.y = random.nextInt(boardHeight/tileSize);
    }

//handles movement of centipede
    public void move() {
        //eat food
        if (collision(centipedeHead, fruit)) { //check if centipede collide with fruit
            centipedeBody.add(new Tile(fruit.x, fruit.y)); //if collision with fruit centipedes body adds segment 
            placeFood(); //new fruit is placed
        }

        //move centipede body segmnents
        for (int i = centipedeBody.size()-1; i >= 0; i--) {
            Tile snakePart = centipedeBody.get(i);
            if (i == 0) { //right before the head

                snakePart.x = centipedeHead.x;
                snakePart.y = centipedeHead.y;
            }
            else { //moves it back to the other position
                Tile prevSnakePart = centipedeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
        //move centipede head
        centipedeHead.x += velocityX;
        centipedeHead.y += velocityY;

        //game over conditions
        for (int i = 0; i < centipedeBody.size(); i++) {
            Tile snakePart = centipedeBody.get(i);

            //collide with centipede head
            if (collision(centipedeHead, snakePart)) {
                gameFinish = true;
            }
        }

        if (centipedeHead.x*tileSize < 0 || centipedeHead.x*tileSize > boardWidth || //passed left border or right border
            centipedeHead.y*tileSize < 0 || centipedeHead.y*tileSize > boardHeight ) { //passed top border or bottom border
            gameFinish = true;
        }
    }

    //checks if snake collides by comparing coordinates
    public boolean collision(Tile tile1, Tile tile2) {
        //if x and y coordinates both tiles are equal they collide
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    //this method is called when gamer time ticks
    @Override
    public void actionPerformed(ActionEvent e) { //called every x milliseconds by gameLoop timer
        
        repaint(); //repaints the game board to update display
        move(); //moves centipede
        if (gameFinish) {
            gameTimer.stop();
        }
    }  

    //handles when user presses key 
    @Override
    public void keyPressed(KeyEvent e) {
        // System.out.println("KeyEvent: " + e.getKeyCode());
        if (gameFinish) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                restartGame();
            }
        } else {
            if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
                velocityX = 0;
                velocityY = -1;
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
                velocityX = 0;
                velocityY = 1;
            }
            else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
                velocityX = -1;
                velocityY = 0;
            }
            else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
                velocityX = 1;
                velocityY = 0;
            }
        }
    }

    private void restartGame() {
        centipedeHead = new Tile(5, 5);
        centipedeBody.clear();
        placeFood();
        gameFinish = false;
        gameTimer.start();
        repaint();
        velocityX = 1;
        velocityY = 0;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

}