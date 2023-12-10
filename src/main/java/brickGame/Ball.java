package brickGame;

/**
 * The {@code Ball} class represents the game ball in the Brick Game.
 * It encapsulates properties and behaviors related to the ball's movement and collisions.
 */

public class Ball {
    /**
     * The X-coordinate of the ball.
     */
    private double xb;
    /**
     * The Y-coordinate of the ball.
     */
    private double yb;

    /**
     * The radius of the ball.
     */
    private final double radius;

    /**
     * The X-velocity of the ball.
     */
    private double veloX;

    /**
     * The Y-velocity of the ball.
     */
    private double veloY;

    /**
     * Flag indicating whether the ball is moving downward.
     */
    private boolean goDown;
    /**
     * Flag indicating whether the ball is moving to the right.
     */
    private boolean goRight;


    /**
     * Constructs a new Ball with the specified initial coordinates and radius.
     *
     * @param startX The initial X-coordinate of the ball.
     * @param startY The initial Y-coordinate of the ball.
     * @param radius The radius of the ball.
     */
    public Ball(double startX, double startY, double radius) {
        this.xb = startX;
        this.yb = startY;
        this.radius = radius;
        this.veloX = 1.0;
        this.veloY = 1.0;
        this.goDown = true;
        this.goRight = true;
    }

    /**
     * Updates the position of the ball based on its velocity and direction.
     */
    public void updatePosition() {
        if (goRight) {
            this.xb += veloX;
        } else {
            this.xb -= veloX;
        }

        if (goDown) {
            this.yb += veloY;
        } else {
            this.yb -= veloY;
        }
    }

    /**
     * Bounces the ball upward by making godown false
     */
    public void bounceUp() {
        this.goDown = false;
    }

    /**
     * Bounces the ball downward by making godown true
     */
    public void bounceDown() {
        this.goDown = true;
    }

    /**
     * Bounces the ball horizontally, reversing its horizontal direction.
     */
    public void bounceHorizontally() {
        this.goRight = !this.goRight;
    }

    /**
     * Gets the x-coordinate of the ball.
     *
     * @return The x-coordinate of the ball.
     */
    public double getXb() {
        return xb;
    }

    /**
     * Sets the x-coordinate of the ball.
     *
     * @param xb The new x-coordinate of the ball.
     */
    public void setXb(double xb) {
        this.xb = xb;
    }

    /**
     * Gets the y-coordinate of the ball.
     *
     * @return The y-coordinate of the ball.
     */
    public double getYb() {
        return yb;
    }

    /**
     * Sets the y-coordinate of the ball.
     *
     * @param yb The new y-coordinate of the ball.
     */
    public void setYb(double yb) {
        this.yb = yb;
    }

    /**
     * Gets the radius of the ball.
     *
     * @return The radius of the ball.
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Gets the x-velocity of the ball.
     *
     * @return The x-velocity of the ball.
     */
    public double getVeloX() {
        return veloX;
    }

    /**
     * Sets the x-velocity of the ball.
     *
     * @param veloX The new x-velocity of the ball.
     */
    public void setVeloX(double veloX) {
        this.veloX = veloX;
    }

    /**
     * Gets the y-velocity of the ball.
     *
     * @return The y-velocity of the ball.
     */
    public double getVeloY() {
        return veloY;
    }

    /**
     * Sets the y-velocity of the ball.
     *
     * @param veloY The new y-velocity of the ball.
     */
    public void setVeloY(double veloY) {
        this.veloY = veloY;
    }

    /**
     * Sets the downward movement status of the ball.
     *
     * @param goingDown {@code true} to set the ball moving downward, {@code false} to set it upward.
     */
    public void setGoingDown(boolean goingDown) {
        this.goDown = goingDown;
    }

    /**
     * Sets the rightward movement status of the ball.
     *
     * @param goingRight {@code true} to set the ball moving to the right, {@code false} to set it leftward.
     */
    public void setGoingRight(boolean goingRight) {
        this.goRight = goingRight;
    }
}





