package brickGame;


import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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

    public Pane root;

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


    public Rectangle createrect() {
        rect = new Rectangle();
        rect.setWidth(breakWidth);
        rect.setHeight(breakHeight);
        rect.setX(xBreak);
        rect.setY(yBreak);

        ImagePattern pattern = new ImagePattern(new Image("block.jpg"));
        rect.setFill(pattern);
        return rect;
    }
    public Circle getBall() {
        return ball;
    }

    public void goldstatusimage(Ball bball,Pane root )
    {
            ball.setFill(new ImagePattern(new Image("ball.png")));
            root.getStyleClass().remove("goldRoot");
    }

    public void showchoco(Bonus choco,Pane root)
    {
        System.out.println("You Got it and +3 score for you");
        choco.taken = true;
        choco.choco.setVisible(false);
        new Score().show(choco.x, choco.y, 3, root);
    }

    public void backgrdimg(Pane root){
        ImageView backgroundImage = new ImageView(new Image("bg.jpg"));
        backgroundImage.setFitWidth(500);
        backgroundImage.setFitHeight(700);
        root.getChildren().add(backgroundImage);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
    }

}



