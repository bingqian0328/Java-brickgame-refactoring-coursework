package brickGame;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;

public class View {
    private Circle ball;

    private Rectangle rect;

    private int ballRadius = 12;

    private double xBreak = 0.0f;
    private double yBreak = 640.0f;
    private int breakWidth = 130;
    private int breakHeight = 30;

    private Label            scoreLabel;
    private Label            heartLabel;
    private Label            levelLabel;

    public Pane root;

    private Controller controller;

    private Score score;

    private boolean loadFromSave = false;

    private double xb;
    private double yb;


    private Button load=new Button("Load Game");
    private Button newGame=new Button("Start New Game");

    public View()
    {
    }

    public Circle createBall() {
        ball = new Circle();
        ball.setRadius(ballRadius);
        ball.setFill(new ImagePattern(new Image("moonball.png")));
        return ball;
    }


    public Rectangle createrect() {
        rect = new Rectangle();
        rect.setWidth(breakWidth);
        rect.setHeight(breakHeight);
        rect.setX(xBreak);
        rect.setY(yBreak);

        ImagePattern pattern = new ImagePattern(new Image("minepaddle.png"));
        rect.setFill(pattern);
        return rect;
    }

    public Rectangle invisiblerect() {
        ImagePattern pattern = new ImagePattern(new Image("minepaddle.png"));
        rect.setFill(pattern);
        return rect;
    }



    public Circle getBall() {
        return ball;
    }

    public void revgoldstatusimage(Ball bball )
    {
            ball.setFill(new ImagePattern(new Image("moonball.png")));
            root.getStyleClass().remove("goldRoot");
    }

    public void revflashstatusimage (Ball bball)
    {
        ball.setFill(new ImagePattern(new Image("moonball.png")));
    }

    public void showchoco(Bonus choco)
    {
        System.out.println("You Got it and +3 score for you");
        choco.taken = true;
        choco.choco.setVisible(false);
        new Score().show(choco.x, choco.y, 3, root);
    }

    public void setonupdate(int score, int heart)
    {
        scoreLabel.setText("Score: " + score);
        heartLabel.setText("Heart : " + heart);
    }

    public void createbuttons ()
    {
        load = new Button("Load Game");
        newGame = new Button("Start New Game");
        load.setTranslateX(220);
        load.setTranslateY(300);
        newGame.setTranslateX(210);
        newGame.setTranslateY(340);

        load.getStyleClass().add("load");
        newGame.getStyleClass().add("new-game-button");
    }

    public void setScene(Controller controller,boolean loadFromSave,Model model, ArrayList<Block> blocks, Stage primaryStage,int score,int level,int heart,Rectangle rect,Circle ball){
        root = new Pane();
        scoreLabel = new Label("Score: " + score);
        levelLabel = new Label("Level: " + level);//need to getlevel from controller afterwards
        levelLabel.setTranslateY(20);
        heartLabel = new Label("Heart : " + heart);
        heartLabel.setTranslateX(500 - 70);
        if (loadFromSave == false && level!=19) {
            root.getChildren().addAll(rect,ball, scoreLabel, heartLabel, levelLabel, newGame, load);
        } else {
            showWinText();
            root.getChildren().addAll(rect,ball, scoreLabel, heartLabel, levelLabel);
        }
        for (Block block : blocks) {
            root.getChildren().add(block.rect);
        }

        Scene scene = new Scene(root, 500, 700);
        scene.getStylesheets().add("style.css");
        scene.setOnKeyPressed(controller);

        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
    }
    public void goldballimg(Circle ball)
    {
        ball.setFill(new ImagePattern(new Image("goldball.png")));
        System.out.println("gold ball");
        root.getStyleClass().add("goldRoot");
    }

    public void flashball(Circle ball)
    {
        ball.setFill(new ImagePattern(new Image("fireball.png")));
        System.out.println("fireball");
    }


    public void showlevelup()
    {
        new Score().showMessage("Level Up :)", root);
    }

    public void showgamesaved()
    {
        new Score().showMessage("Game Saved", root);
    }

    public void showgamepaused()
    {
        new Score().showMessage2("Game Paused", root);
    }

    public void removeGamePausedMessage() {
        root.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().equals("Game Paused"));
    }

    public void showgameover (Controller controller)
    {
        new Score().showGameOver(root,controller);
    }


    public void showgamecont()
    {
        new Score().showMessage("Game Unpaused", root);
    }


    public void showWinText() {
        score = new Score();
        score.showWin(root);
        }

    public void scoreshow( )
    {
        new Score().show(500 / 2, 700 / 2, -1, root);
    }

    public void showblocks(final double x, final double y)
    {
        new Score().show(x, y, 1, root);
    }

    public void hidePaddle(Pane root, Rectangle paddle) {
        paddle.setVisible(false);
    }

    public void showPaddle(Pane root, Rectangle paddle) {
        paddle.setVisible(true);
    }

    public void setvisible()
    {
        load.setVisible(false);
        newGame.setVisible(false);
    }

    public Button getLoadButton() {
        return load;
    }

    // Setter for load button
    public void setLoadButton(Button load) {
        this.load = load;
    }

    // Getter for newGame button
    public Button getNewGameButton() {
        return newGame;
    }

    // Setter for newGame button
    public void setNewGameButton(Button newGame) {
        this.newGame = newGame;
    }

    public void setRoot(Pane root) {
        this.root = root;
    }



}



