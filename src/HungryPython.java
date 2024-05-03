import java.awt.Color; //Imports colors
import java.awt.Dimension; //Imports size
import java.awt.Graphics; //Imports graphic
import java.awt.event.*;
import java.util.ArrayList;//stores segments of the snake
import java.util.Random;//Gets random x and y values to place the colorgul square which the snake eats
import javax.swing.*;

public class HungryPython extends JPanel { // JPanel, blank canvas that contains buttons and texts
    private class Tile {
        int x; // scales by 25 tiles
        int y; // scales by 25 tiles

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int boardWidth; // variables for width
    int boardHeight; // variables for height
    int tileSize = 25; // Number for the size of each individual square

    Tile pythonHead;

    // Creating constructor
    HungryPython(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);

        pythonHead = new Tile(5, 5);

    }

    public void paintComponet(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Grid
        for (int i = 0; i < boardWidth / tileSize; i++) {
            g.drawLine(i * tileSize, 0, i * tileSize, boardHeight);
            g.drawLine(0, )
        }
        // Snake head
        g.setColor(Color.yellow);
        g.fillRect(pythonHead.x * tileSize, pythonHead.y * tileSize, tileSize, tileSize);
    }

}
