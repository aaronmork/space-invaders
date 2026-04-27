import javax.swing.JFrame;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

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
public class GameController implements KeyListener {
    
    private GameModel model;
    private GameView view;
    private JFrame frame;
    private boolean running;
    
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
        
        // Set up keyboard input
        view.addKeyListener(this);
        
        // Start the game loop
        running = true;
        Thread gameLoopThread = new Thread(() -> {
            long lastTime = System.nanoTime();
            double amountOfTicks = 60.0; // 60 FPS
            double ns = 1000000000 / amountOfTicks;
            double delta = 0;
            
            while (running) {
                long now = System.nanoTime();
                delta += (now - lastTime) / ns;
                lastTime = now;
                
                if (delta >= 1) {
                    // Update game state
                    model.update();
                    
                    // Repaint the view
                    view.repaint();
                    
                    delta--;
                }
            }
        });
        gameLoopThread.setDaemon(true);
        gameLoopThread.start();
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
    
    /**
     * Handles key press events for player input.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_LEFT) {
            model.movePlayerLeft();
        } else if (key == KeyEvent.VK_RIGHT) {
            model.movePlayerRight();
        } else if (key == KeyEvent.VK_SPACE) {
            model.firePlayerBullet();
        }
    }
    
    /**
     * Handles key release events for player input.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_LEFT) {
            model.stopPlayerLeft();
        } else if (key == KeyEvent.VK_RIGHT) {
            model.stopPlayerRight();
        }
    }
    
    /**
     * Handles key typed events (not used in this game).
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }
}
