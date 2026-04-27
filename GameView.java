import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

/**
 * GameView.java
 * 
 * Renders the game state to the screen.
 * Extends JPanel and handles all drawing operations.
 * 
 * Responsibilities:
 * - Draw game background
 * - Draw player sprite
 * - Draw enemies
 * - Draw projectiles
 * - Display score and HUD elements
 * - Handle repaint timing
 */
public class GameView extends JPanel {
    
    private GameModel model;
    
    /**
     * Constructs the game view with a reference to the model.
     * 
     * @param model The game model to render
     */
    public GameView(GameModel model) {
        this.model = model;
        setBackground(Color.BLACK);
        setFocusable(true);
    }
    
    /**
     * Paints the game scene.
     * Called by Swing whenever the component needs to be redrawn.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // TODO: Draw background
        // TODO: Draw player
        // TODO: Draw enemies
        // TODO: Draw projectiles
        // TODO: Draw HUD (score, lives, etc.)
    }
}
