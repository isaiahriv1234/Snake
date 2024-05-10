import javax.swing.*; //swing package contains GUI features

public class App { //declare public class name App
    public static void main(String[] args) throws Exception { //main method which is entry point of program
        int boardWidth = 800; //declaring and initialize integer variable name boardWidth with 800 value
        int boardHeight = 800; // integer variable 800 canvas goes 800 pixels each direction

        JFrame frame = new JFrame("Hungry Centipede"); //title of game Jframe gives icon bar at top of canvas
        frame.setVisible(true); //makes frame visible
	frame.setSize(boardWidth, boardHeight); //sets the size of frame to boardWidth and height
        frame.setLocationRelativeTo(null); //sets location of frame to center of screen
        frame.setResizable(false); //makes frame not resizable
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //allows user to close application 

        HungryCentipede hungryCentipede = new HungryCentipede(boardWidth, boardHeight); //new hungrycentipede object with specific boardwidth/height
        frame.add(hungryCentipede); //adds centipede object to frame
        frame.pack(); //packs componets to frame
        hungryCentipede.requestFocus(); //allows the centipede to recieve input
    }
}