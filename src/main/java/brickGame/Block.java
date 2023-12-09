package brickGame;


import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

/**
 * The {@code Block} class represents a block in the Brick Game.
 * Blocks can have different types and appearances, such as normal, chocolate, heart, star, boost, and hide.
 * This class provides methods for drawing blocks, checking collisions with the ball, and handling rebound behavior.
 */

public class Block implements Serializable {
    private static Block block = new Block(-1, -1, Color.TRANSPARENT, 99);

    /**
     * The row position of the block.
     */
    public int row;

    /**
     * The column position of the block.
     */
    public int column;

    /**
     * The x-velocity of the block.
     */
    private double xVelocity;

    /**
     * The y-velocity of the block.
     */
    private double yVelocity;

    /**
     * A flag indicating whether the block is destroyed.
     */
    public boolean isDestroyed = false;

    /**
     * The color of the block.
     */
    private Color color;

    /**
     * The type of the block.
     */
    public int type;

    /**
     * The x-coordinate of the block.
     */
    public int x;

    /**
     * The y-coordinate of the block.
     */
    public int y;

    /**
     * The width of the block.
     */
    private int width = 100;

    /**
     * The height of the block.
     */
    private int height = 30;

    /**
     * The top padding of the block.
     */
    private int paddingTop = height * 2;

    /**
     * The horizontal padding of the block.
     */
    private int paddingH = 50;

    /**
     * The horizontal padding of the block.
     */
    public Rectangle rect;

    /**
     * The constant value representing no hit during a collision check.
     */
    public static int NO_HIT = -1;

    /**
     * The constant value representing a right-side hit during a collision check.
     */
    public static int HIT_RIGHT = 0;

    /**
     * The constant value representing a bottom-side hit during a collision check.
     */
    public static int HIT_BOTTOM = 1;

    /**
     * The constant value representing a left-side hit during a collision check.
     */
    public static int HIT_LEFT = 2;

    /**
     * The constant value representing a top-side hit during a collision check.
     */
    public static int HIT_TOP = 3;

    /**
     * The constant int value representing a normal block type.
     */
    public static int BLOCK_NORMAL = 99;

    /**
     * The constant int value representing a chocolate block type.
     */
    public static int BLOCK_CHOCO = 100;

    /**
     * The constant int value representing a star block type.
     */
    public static int BLOCK_STAR = 101;

    /**
     * The constant int value representing a heart block type.
     */
    public static int BLOCK_HEART = 102;

    /**
     * The constant int value representing a boost block type.
     */
    public static int BLOCK_BOOST = 103;

    /**
     * The constant int value representing a hide block type.
     */
    public static int BLOCK_HIDE = 98;

    /**
     * A flag indicating whether the block rebounded to the right during collision.
     */
    private boolean goRightRebounded = false;

    /**
     * A flag indicating whether the block rebounded downward during collision.
     */
    private boolean goDownRebounded = false;

    /**
     * Constructs a new {@code Block} instance with the specified position, color, and type.
     *
     * @param row    The row position of the block.
     * @param column The column position of the block.
     * @param color  The color of the block.
     * @param type   The type of the block.
     */
    public Block(int row, int column, Color color, int type) {
        this.row = row;
        this.column = column;
        this.color = color;
        this.type = type;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;

        draw();
    }

    /**
     * Draws the block based on its type and appearance.
     */
    private void draw() {
        x = (column * width) + paddingH;
        y = (row * height) + paddingTop;

        rect = new Rectangle();
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setX(x);
        rect.setY(y);

        if (type == BLOCK_CHOCO) {
            Image image = new Image("choco.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_HEART) {
            Image image = new Image("heart.png");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_STAR) {
            Image image = new Image("star.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        }
        else if (type == BLOCK_BOOST)
        {
            Image image = new Image("flash.png");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        }
        else if (type == BLOCK_HIDE )
        {
            Image image = new Image("disappear.png");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        }
        else {
            rect.setFill(color);
        }

    }

    /**
     * Checks for collisions between the block and the ball and handles rebound behavior.
     *
     * @param xBall      The x-coordinate of the ball.
     * @param yBall      The y-coordinate of the ball.
     * @param goRightBall A flag indicating the ball's direction to the right.
     * @param goDownBall  A flag indicating the ball's direction downward.
     * @return The hit code representing the side of the block hit during collision.
     */
    public int checkHitToBlock(double xBall, double yBall, boolean goRightBall, boolean goDownBall) {
        if (isDestroyed) {
            return NO_HIT;
        }

        double ballRadius = 10;  // Replace yourBallRadius with the actual radius of your ball

        double blockRight = x + width;
        double blockBottom = y + height;

        boolean isColliding = xBall + ballRadius > x && xBall - ballRadius < blockRight && yBall + ballRadius > y && yBall - ballRadius < blockBottom;

        if (isColliding) {
            double overlapX = Math.min(xBall + ballRadius - x, blockRight - xBall + ballRadius);
            double overlapY = Math.min(yBall + ballRadius - y, blockBottom - yBall + ballRadius);

            if (overlapX < overlapY) {
                // Horizontal collision
                if (xBall < x + width / 2) {
                    if (goRightBall && !goRightRebounded) {
                        xVelocity = -Math.abs(xVelocity);  // Rebound to the left
                        goRightRebounded = true;
                    }
                    return HIT_LEFT;
                } else {
                    if (goRightBall && !goRightRebounded) {
                        xVelocity = Math.abs(xVelocity);   // Rebound to the right
                        goRightRebounded = true;
                    }
                    return HIT_RIGHT;
                }
            } else {
                // Vertical collision
                if (yBall < y + height / 2) {
                    if (goDownBall && !goDownRebounded) {
                        yVelocity = -Math.abs(yVelocity);  // Rebound upward
                        goDownRebounded = true;
                    }
                    return HIT_TOP;
                } else {
                    if (goDownBall && !goDownRebounded) {
                        yVelocity = Math.abs(yVelocity);   // Rebound downward
                        goDownRebounded = true;
                    }
                    return HIT_BOTTOM;
                }
            }
        } else {
            // No collision, reset rebound flags
            resetReboundedFlags();
        }

        return NO_HIT;
    }

    // Helper method to reset the rebounded flags
    /**
     * Resets the rebounded flags to their initial state.
     */
    private void resetReboundedFlags() {
        goRightRebounded = false;
        goDownRebounded = false;
    }

    /**
     * Gets the top padding of the block.
     *
     * @return The top padding of the block.
     */
    public static int getPaddingTop() {
        return block.paddingTop;
    }

    /**
     * Gets the horizontal padding of the block.
     *
     * @return The horizontal padding of the block.
     */
    public static int getPaddingH() {
        return block.paddingH;
    }

    /**
     * Gets the height of the block.
     *
     * @return The height of the block.
     */
    public static int getHeight() {
        return block.height;
    }

    /**
     * Gets the width of the block.
     *
     * @return The width of the block.
     */
    public static int getWidth() {
        return block.width;
    }

}
