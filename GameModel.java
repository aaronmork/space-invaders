import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    
    // Player dimensions and movement
    private static final int PLAYER_WIDTH = 40;
    private static final int PLAYER_HEIGHT = 40;
    private static final int PLAYER_SPEED = 7;
    private static final int PLAYER_Y = 550;
    
    // Alien dimensions and movement
    private static final int ALIEN_WIDTH = 30;
    private static final int ALIEN_HEIGHT = 30;
    private static final int ALIEN_ROWS = 5;
    private static final int ALIEN_COLS = 11;
    private static final int ALIEN_SPEED = 2;
    private static final int ALIEN_STARTING_X = 50;
    private static final int ALIEN_STARTING_Y = 30;
    
    // Projectile dimensions
    private static final int BULLET_SPEED = 10;
    private static final int ALIEN_BULLET_SPEED = 6;
    
    // Timing
    private static final int ALIEN_FIRE_INTERVAL = 60; // frames between alien shots
    
    // Player state
    private int playerX;
    private boolean playerMovingLeft;
    private boolean playerMovingRight;
    
    // Player bullet
    private Bullet playerBullet;
    
    // Alien formation
    private boolean[][] aliens; // [row][col] - true if alive
    private int alienX; // left edge of formation
    private int alienY; // top edge of formation
    private int alienDirection; // 1 for right, -1 for left
    private int alienMoveCounter; // for timing alien movement
    
    // Alien bullets
    private List<Bullet> alienBullets;
    private Random random;
    private int alienFireCounter;
    
    // Game state
    private int score;
    private int lives;
    private boolean isRunning;
    private boolean gameOver;
    private boolean gameWon;
    
    /**
     * Initializes the game model with starting values.
     */
    public GameModel() {
        this.isRunning = false;
        this.gameOver = false;
        this.gameWon = false;
        this.score = 0;
        this.lives = 3;
        this.random = new Random();
        
        // Initialize player
        this.playerX = GAME_WIDTH / 2 - PLAYER_WIDTH / 2;
        this.playerMovingLeft = false;
        this.playerMovingRight = false;
        
        // Initialize player bullet
        this.playerBullet = null;
        
        // Initialize alien formation
        this.aliens = new boolean[ALIEN_ROWS][ALIEN_COLS];
        for (int row = 0; row < ALIEN_ROWS; row++) {
            for (int col = 0; col < ALIEN_COLS; col++) {
                aliens[row][col] = true;
            }
        }
        this.alienX = ALIEN_STARTING_X;
        this.alienY = ALIEN_STARTING_Y;
        this.alienDirection = 1; // start moving right
        this.alienMoveCounter = 0;
        
        // Initialize alien bullets
        this.alienBullets = new ArrayList<>();
        this.alienFireCounter = 0;
    }
    
    /**
     * Updates the game state for the current frame.
     * Called regularly by the game loop.
     */
    public void update() {
        if (!isRunning || gameOver || gameWon) {
            return;
        }
        
        // Update player position based on input
        updatePlayer();
        
        // Update player bullet
        updatePlayerBullet();
        
        // Update alien formation
        updateAliens();
        
        // Update alien bullets
        updateAlienBullets();
        
        // Fire alien bullets
        fireAlienBullets();
        
        // Check collisions
        checkCollisions();
        
        // Check win/lose conditions
        checkGameConditions();
    }
    
    /**
     * Updates player position based on movement flags.
     */
    private void updatePlayer() {
        if (playerMovingLeft) {
            playerX -= PLAYER_SPEED;
            if (playerX < 0) {
                playerX = 0;
            }
        }
        if (playerMovingRight) {
            playerX += PLAYER_SPEED;
            if (playerX + PLAYER_WIDTH > GAME_WIDTH) {
                playerX = GAME_WIDTH - PLAYER_WIDTH;
            }
        }
    }
    
    /**
     * Updates the player's bullet position.
     */
    private void updatePlayerBullet() {
        if (playerBullet != null) {
            playerBullet.y -= BULLET_SPEED;
            
            // Remove bullet if it goes off screen
            if (playerBullet.y < 0) {
                playerBullet = null;
            }
        }
    }
    
    /**
     * Updates the alien formation's position.
     */
    private void updateAliens() {
        alienMoveCounter++;
        
        if (alienMoveCounter >= ALIEN_SPEED) {
            alienMoveCounter = 0;
            
            // Check if formation hits the edge
            boolean touchingEdge = false;
            if (alienDirection == 1) {
                // Moving right - check right edge
                int rightEdge = alienX + (ALIEN_COLS * (ALIEN_WIDTH + 10));
                if (rightEdge >= GAME_WIDTH) {
                    touchingEdge = true;
                }
            } else {
                // Moving left - check left edge
                if (alienX <= 0) {
                    touchingEdge = true;
                }
            }
            
            if (touchingEdge) {
                // Move down and reverse direction
                alienY += ALIEN_HEIGHT + 20;
                alienDirection *= -1;
            } else {
                // Move horizontally
                alienX += alienDirection * 10;
            }
        }
    }
    
    /**
     * Updates positions of alien bullets.
     */
    private void updateAlienBullets() {
        for (int i = alienBullets.size() - 1; i >= 0; i--) {
            Bullet bullet = alienBullets.get(i);
            bullet.y += ALIEN_BULLET_SPEED;
            
            // Remove bullets that go off screen
            if (bullet.y > GAME_HEIGHT) {
                alienBullets.remove(i);
            }
        }
    }
    
    /**
     * Fires alien bullets at random intervals.
     */
    private void fireAlienBullets() {
        alienFireCounter++;
        
        if (alienFireCounter >= ALIEN_FIRE_INTERVAL) {
            alienFireCounter = 0;
            
            // Find a random alive alien
            int col = random.nextInt(ALIEN_COLS);
            int row = -1;
            
            for (int r = ALIEN_ROWS - 1; r >= 0; r--) {
                if (aliens[r][col]) {
                    row = r;
                    break;
                }
            }
            
            if (row != -1) {
                // Calculate alien position
                int alienScreenX = alienX + col * (ALIEN_WIDTH + 10) + ALIEN_WIDTH / 2;
                int alienScreenY = alienY + row * (ALIEN_HEIGHT + 10) + ALIEN_HEIGHT;
                
                // Create alien bullet
                Bullet bullet = new Bullet(alienScreenX, alienScreenY);
                alienBullets.add(bullet);
            }
        }
    }
    
    /**
     * Checks for collisions between bullets and aliens/player.
     */
    private void checkCollisions() {
        // Check player bullet vs aliens
        if (playerBullet != null) {
            for (int row = 0; row < ALIEN_ROWS; row++) {
                for (int col = 0; col < ALIEN_COLS; col++) {
                    if (aliens[row][col]) {
                        int alienScreenX = alienX + col * (ALIEN_WIDTH + 10);
                        int alienScreenY = alienY + row * (ALIEN_HEIGHT + 10);
                        
                        if (playerBullet.collidesWith(alienScreenX, alienScreenY, ALIEN_WIDTH, ALIEN_HEIGHT)) {
                            aliens[row][col] = false;
                            playerBullet = null;
                            score += 10;
                            return; // Only one collision per bullet per frame
                        }
                    }
                }
            }
        }
        
        // Check alien bullets vs player
        for (int i = alienBullets.size() - 1; i >= 0; i--) {
            Bullet bullet = alienBullets.get(i);
            if (bullet.collidesWith(playerX, PLAYER_Y, PLAYER_WIDTH, PLAYER_HEIGHT)) {
                alienBullets.remove(i);
                lives--;
                if (lives <= 0) {
                    gameOver = true;
                }
            }
        }
    }
    
    /**
     * Checks win/lose conditions.
     */
    private void checkGameConditions() {
        // Check if all aliens are destroyed
        boolean allDestroyed = true;
        for (int row = 0; row < ALIEN_ROWS; row++) {
            for (int col = 0; col < ALIEN_COLS; col++) {
                if (aliens[row][col]) {
                    allDestroyed = false;
                    break;
                }
            }
            if (!allDestroyed) break;
        }
        
        if (allDestroyed) {
            gameWon = true;
        }
        
        // Check if aliens reached the bottom
        if (alienY + ALIEN_ROWS * (ALIEN_HEIGHT + 10) >= PLAYER_Y) {
            gameOver = true;
        }
    }
    
    /**
     * Moves the player left.
     */
    public void movePlayerLeft() {
        playerMovingLeft = true;
    }
    
    /**
     * Stops the player moving left.
     */
    public void stopPlayerLeft() {
        playerMovingLeft = false;
    }
    
    /**
     * Moves the player right.
     */
    public void movePlayerRight() {
        playerMovingRight = true;
    }
    
    /**
     * Stops the player moving right.
     */
    public void stopPlayerRight() {
        playerMovingRight = false;
    }
    
    /**
     * Fires a player bullet if one isn't already in flight.
     */
    public void firePlayerBullet() {
        if (playerBullet == null) {
            playerBullet = new Bullet(playerX + PLAYER_WIDTH / 2, PLAYER_Y);
        }
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
    
    /**
     * Returns the number of lives remaining.
     */
    public int getLives() {
        return lives;
    }
    
    /**
     * Returns whether the game is over (lost).
     */
    public boolean isGameOver() {
        return gameOver;
    }
    
    /**
     * Returns whether the game is won.
     */
    public boolean isGameWon() {
        return gameWon;
    }
    
    /**
     * Returns the player's X position.
     */
    public int getPlayerX() {
        return playerX;
    }
    
    /**
     * Returns the player's Y position.
     */
    public int getPlayerY() {
        return PLAYER_Y;
    }
    
    /**
     * Returns the player's width.
     */
    public int getPlayerWidth() {
        return PLAYER_WIDTH;
    }
    
    /**
     * Returns the player's height.
     */
    public int getPlayerHeight() {
        return PLAYER_HEIGHT;
    }
    
    /**
     * Returns the alien formation's X position (left edge).
     */
    public int getAlienX() {
        return alienX;
    }
    
    /**
     * Returns the alien formation's Y position (top edge).
     */
    public int getAlienY() {
        return alienY;
    }
    
    /**
     * Returns the alien width.
     */
    public int getAlienWidth() {
        return ALIEN_WIDTH;
    }
    
    /**
     * Returns the alien height.
     */
    public int getAlienHeight() {
        return ALIEN_HEIGHT;
    }
    
    /**
     * Returns the 2D array indicating which aliens are alive.
     */
    public boolean[][] getAliens() {
        return aliens;
    }
    
    /**
     * Returns the spacing between aliens.
     */
    public int getAlienSpacing() {
        return 10;
    }
    
    /**
     * Returns the player's bullet, or null if none is in flight.
     */
    public Bullet getPlayerBullet() {
        return playerBullet;
    }
    
    /**
     * Returns the list of alien bullets currently in flight.
     */
    public List<Bullet> getAlienBullets() {
        return alienBullets;
    }
    
    /**
     * Inner class representing a bullet with basic collision detection.
     */
    public static class Bullet {
        public int x;
        public int y;
        
        public Bullet(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        /**
         * Checks if this bullet collides with an object at the given position and size.
         */
        public boolean collidesWith(int objX, int objY, int objWidth, int objHeight) {
            return x >= objX && x <= objX + objWidth &&
                   y >= objY && y <= objY + objHeight;
        }
    }
}
