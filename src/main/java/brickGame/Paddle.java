package brickGame;

import javafx.scene.shape.Rectangle;


/**
 * The {@code Paddle} class represents the game paddle in the Brick Game.
 * It encapsulates properties and behaviors related to the paddle's position and dimensions.
 */
public class Paddle {
    /**
     * The X-coordinate of the paddle.
     */
    private double x;

    /**
     * The Y-coordinate of the paddle.
     */
    private double y;

    /**
     * The width of the paddle.
     */
    private int width;

    /**
     * The height of the paddle.
     */
    private int height;

    /**
     * Half of the width of the paddle.
     */
    private int halfWidth;

    /**
     * Constructs a new Paddle with the specified initial coordinates, width, and height.
     *
     * @param x      The initial X-coordinate of the paddle.
     * @param y      The initial Y-coordinate of the paddle.
     * @param width  The width of the paddle.
     * @param height The height of the paddle.
     */
    public Paddle(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.halfWidth = width / 2;
    }

    /**
     * Gets the X-coordinate of the paddle.
     *
     * @return The X-coordinate of the paddle.
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the X-coordinate of the paddle.
     *
     * @param x The new X-coordinate of the paddle.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets the Y-coordinate of the paddle.
     *
     * @return The Y-coordinate of the paddle.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the Y-coordinate of the paddle.
     *
     * @param y The new Y-coordinate of the paddle.
     */
    public void setY(double y)
    {
        this.y = y;
    }

    /**
     * Gets the width of the paddle.
     *
     * @return The width of the paddle.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the paddle.
     *
     * @return The height of the paddle.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets half of the width of the paddle.
     *
     * @return Half of the width of the paddle.
     */
    public int getHalfWidth() {
        return halfWidth;
    }

    /**
     * Gets the X-coordinate of the center of the paddle.
     *
     * @return The X-coordinate of the center of the paddle.
     */
    public double getCenterX() {
        return x + halfWidth;
    }

    /**
     * Sets the X-coordinate of the center of the paddle.
     *
     * @param centerX The new X-coordinate of the center of the paddle.
     */
    public void setCenterX(double centerX) {
        this.x = centerX - halfWidth;
    }
}
