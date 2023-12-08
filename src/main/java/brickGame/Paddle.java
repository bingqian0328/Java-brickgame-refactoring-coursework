package brickGame;

import javafx.scene.shape.Rectangle;

public class Paddle {
    private double x;
    private double y;
    private int width;
    private int height;
    private int halfWidth;

    public Paddle(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.halfWidth = width / 2;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getHalfWidth() {
        return halfWidth;
    }

    public double getCenterX() {
        return x + halfWidth;
    }

    public void setCenterX(double centerX) {
        this.x = centerX - halfWidth;
    }
}
