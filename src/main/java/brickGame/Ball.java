package brickGame;

public class Ball {
    private double xb;
    private double yb;
    private final double radius;
    private double veloX;
    private double veloY;
    private boolean goDown;
    private boolean goRight;

    public Ball(double startX, double startY, double radius) {
        this.xb = startX;
        this.yb = startY;
        this.radius = radius;
        this.veloX = 1.0;
        this.veloY = 1.0;
        this.goDown = true;
        this.goRight = true;
    }

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


    public void bounceUp() {
        this.goDown = false;
    }

    public void bounceDown() {
        this.goDown = true;
    }

    public void bounceLeft() {
        this.goRight = false;
    }

    public void bounceRight() {
        this.goRight = true;
    }

    public void bounceVertically() {
        this.goDown = !this.goDown;
    }

    public void bounceHorizontally() {
        this.goRight = !this.goRight;
    }

    // Getters and setters

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

    public boolean isGoingDown() {
        return goDown;
    }

    public boolean isGoingRight() {
        return goRight;
    }

    public boolean isGoingLeft() {
        return !goRight;
    }

    public boolean isGoingUp() {
        return !goDown;
    }

    public void setGoingDown(boolean goingDown) {
        this.goDown = goingDown;
    }

    public void setGoingRight(boolean goingRight) {
        this.goRight = goingRight;
    }
}





