/**
 * GameModel.java
 * 
 * Manages the game state and logic for Space Invaders.
 * Contains no Swing libraries - purely game data and mechanics.
 * 
 * Responsibilities:
 * - Track player position and state
 * - Track enemy positions and behavior
 * - Manage projectiles and collisions
 * - Update game score and lives
 * - Handle game win/loss conditions
 */
public class GameModel {
    
    // Game dimensions
    private static final int GAME_WIDTH = 800;
    private static final int GAME_HEIGHT = 600;
    
    // Game state
    private boolean isRunning;
    private int score;
    
    /**
     * Initializes the game model with starting values.
     */
    public GameModel() {
        this.isRunning = false;
        this.score = 0;
    }
    
    /**
     * Updates the game state for the current frame.
     * Called regularly by the game loop.
     */
    public void update() {
        // TODO: Update player position
        // TODO: Update enemy positions
        // TODO: Update projectiles
        // TODO: Check for collisions
        // TODO: Update score
    }
    
    /**
     * Starts the game.
     */
    public void start() {
        isRunning = true;
    }
    
    /**
     * Stops the game.
     */
    public void stop() {
        isRunning = false;
    }
    
    /**
     * Returns whether the game is currently running.
     */
    public boolean isRunning() {
        return isRunning;
    }
    
    /**
     * Returns the current score.
     */
    public int getScore() {
        return score;
    }
}
