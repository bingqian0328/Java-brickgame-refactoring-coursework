package brickGame;

/**
 * The {@code Ball} class represents the main ball in the Brick Game.
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
     * setters and getters methods related to ball's parameters
     * @return
     */
    public double getXb() {
        return xb;
    }

    public void setXb(double xb) {
        this.xb = xb;
    }

    public double getYb() {
        return yb;
    }

    public void setYb(double yb) {
        this.yb = yb;
    }

    public double getRadius() {
        return radius;
    }

    public double getVeloX() {
        return veloX;
    }

    public void setVeloX(double veloX) {
        this.veloX = veloX;
    }

    public double getVeloY() {
        return veloY;
    }

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





