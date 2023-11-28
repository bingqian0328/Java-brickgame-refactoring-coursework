package brickGame;


import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class View {
    private Circle ball;

    private Rectangle rect;

    private int ballRadius = 10;

    private double xBreak = 0.0f;
    private double yBreak = 640.0f;
    private int breakWidth = 130;
    private int breakHeight = 30;

    private double xb;
    private double yb;

    public View()
    {
    }

    public Circle createBall() {
        ball = new Circle();
        ball.setRadius(ballRadius);
        ball.setFill(new ImagePattern(new Image("ball.png")));
        return ball;
    }


    public void paddleimg() {
        ImagePattern pattern = new ImagePattern(new Image("block.jpg"));
        rect.setFill(pattern);
    }

    public void createrect() {
        rect = new Rectangle();
        rect.setWidth(breakWidth);
        rect.setHeight(breakHeight);
        rect.setX(xBreak);
        rect.setY(yBreak);
    }
    public Circle getBall() {
        return ball;
    }

}
