import javax.swing.*;

public class App {

    public static void main(String[] args) {
        int boardwidth = 600;
        int boardHeight = boardwidth;

        JFrame frame = new JFrame("The Hungry Python");
        frame.setVisible(true);
        frame.setSize(boardwidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HungryPython hungryPython = new HungryPython(boardwidth, boardHeight);// passes in two parameters
        frame.add(hungryPython);
        frame.pack();
    }
}