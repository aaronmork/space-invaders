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
        
        // Draw player
        drawPlayer(g);
        
        // Draw aliens
        drawAliens(g);
        
        // Draw projectiles
        drawProjectiles(g);
        
        // Draw HUD (score and lives)
        drawHUD(g);
        
        // Draw game over / game won message if applicable
        if (model.isGameOver()) {
            drawGameOverMessage(g);
        } else if (model.isGameWon()) {
            drawGameWonMessage(g);
        }
    }
    
    /**
     * Draws the player sprite.
     * The player is drawn as a white square at the bottom of the screen.
     */
    private void drawPlayer(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(model.getPlayerX(), model.getPlayerY(), 
                   model.getPlayerWidth(), model.getPlayerHeight());
    }
    
    /**
     * Draws all living aliens in the formation.
     * Aliens are drawn as cyan squares in their grid positions.
     */
    private void drawAliens(Graphics g) {
        g.setColor(Color.CYAN);
        boolean[][] aliens = model.getAliens();
        
        for (int row = 0; row < aliens.length; row++) {
            for (int col = 0; col < aliens[row].length; col++) {
                if (aliens[row][col]) {
                    int x = model.getAlienX() + col * (model.getAlienWidth() + model.getAlienSpacing());
                    int y = model.getAlienY() + row * (model.getAlienHeight() + model.getAlienSpacing());
                    g.fillRect(x, y, model.getAlienWidth(), model.getAlienHeight());
                }
            }
        }
    }
    
    /**
     * Draws all projectiles (player bullet and alien bullets).
     * Player bullets are drawn in green, alien bullets in red.
     */
    private void drawProjectiles(Graphics g) {
        // Draw player bullet
        GameModel.Bullet playerBullet = model.getPlayerBullet();
        if (playerBullet != null) {
            g.setColor(Color.GREEN);
            g.fillRect(playerBullet.x - 2, playerBullet.y, 4, 10);
        }
        
        // Draw alien bullets
        g.setColor(Color.RED);
        for (GameModel.Bullet bullet : model.getAlienBullets()) {
            g.fillRect(bullet.x - 2, bullet.y, 4, 10);
        }
    }
    
    /**
     * Draws the HUD displaying score and remaining lives.
     * Displayed in the top-left and top-right corners.
     */
    private void drawHUD(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
        
        // Draw score on the left
        g.drawString("Score: " + model.getScore(), 10, 20);
        
        // Draw lives on the right
        String livesText = "Lives: " + model.getLives();
        int textWidth = g.getFontMetrics().stringWidth(livesText);
        g.drawString(livesText, getWidth() - textWidth - 10, 20);
    }
    
    /**
     * Draws a centered "GAME OVER" message on the screen.
     */
    private void drawGameOverMessage(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200)); // Semi-transparent black
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.setColor(Color.RED);
        g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 48));
        
        String message = "GAME OVER";
        int textWidth = g.getFontMetrics().stringWidth(message);
        int textHeight = g.getFontMetrics().getAscent();
        
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() - textHeight) / 2 + textHeight;
        
        g.drawString(message, x, y);
    }
    
    /**
     * Draws a centered "YOU WIN!" message on the screen.
     */
    private void drawGameWonMessage(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200)); // Semi-transparent black
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.setColor(Color.YELLOW);
        g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 48));
        
        String message = "YOU WIN!";
        int textWidth = g.getFontMetrics().stringWidth(message);
        int textHeight = g.getFontMetrics().getAscent();
        
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() - textHeight) / 2 + textHeight;
        
        g.drawString(message, x, y);
    }
}
