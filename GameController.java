import javax.swing.JFrame;

/**
 * GameController.java
 * 
 * Main controller class that wires together the MVC components.
 * Contains the entry point (main method) for the application.
 * 
 * Responsibilities:
 * - Create and initialize the game window
 * - Instantiate GameModel and GameView
 * - Set up the game loop
 * - Handle keyboard input
 * - Manage the execution flow
 */
public class GameController {
    
    private GameModel model;
    private GameView view;
    private JFrame frame;
    
    /**
     * Constructs the game controller and initializes all components.
     */
    public GameController() {
        // Initialize the model
        model = new GameModel();
        
        // Initialize the view
        view = new GameView(model);
        
        // Create the main window
        frame = new JFrame("Space Invaders");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(view);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    /**
     * Starts the game.
     */
    public void start() {
        model.start();
        // TODO: Start game loop
        // TODO: Set up input handlers
    }
    
    /**
     * Main entry point for the Space Invaders game.
     * Creates and starts the game.
     * 
     * @param args Command line arguments (unused)
     */
    public static void main(String[] args) {
        GameController controller = new GameController();
        controller.start();
    }
}
