package brickGame;


import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class Block implements Serializable {
    private static Block block = new Block(-1, -1, Color.TRANSPARENT, 99);

    public int row;
    public int column;

    private double xVelocity;
    private double yVelocity;


    public boolean isDestroyed = false;

    private Color color;
    public int type;

    public int x;
    public int y;

    private int width = 100;
    private int height = 30;
    private int paddingTop = height * 2;
    private int paddingH = 50;
    public Rectangle rect;


    public static int NO_HIT = -1;
    public static int HIT_RIGHT = 0;
    public static int HIT_BOTTOM = 1;
    public static int HIT_LEFT = 2;
    public static int HIT_TOP = 3;

    private Controller controller;

    public static int BLOCK_NORMAL = 99;
    public static int BLOCK_CHOCO = 100;
    public static int BLOCK_STAR = 101;
    public static int BLOCK_HEART = 102;

    public static int BLOCK_BOOST = 103;

    public static int BLOCK_HIDE = 98;

    private boolean goRightRebounded = false;
    private boolean goDownRebounded = false;


    public Block(int row, int column, Color color, int type) {
        this.row = row;
        this.column = column;
        this.color = color;
        this.type = type;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;

        draw();
    }

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
    private void resetReboundedFlags() {
        goRightRebounded = false;
        goDownRebounded = false;
    }

    public static int getPaddingTop() {
        return block.paddingTop;
    }

    public static int getPaddingH() {
        return block.paddingH;
    }

    public static int getHeight() {
        return block.height;
    }

    public static int getWidth() {
        return block.width;
    }

}
