package brickGame;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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

    private int ballRadius = 10;

    private double xBreak = 0.0f;
    private double yBreak = 640.0f;
    private int breakWidth = 130;
    private int breakHeight = 30;

    private Label            scoreLabel;
    private Label            heartLabel;
    private Label            levelLabel;

    public Pane root;

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
        newGame.setTranslateX(220);
        newGame.setTranslateY(340);
    }

    public void buttonsposition()
    {
        load.setTranslateX(220);
        load.setTranslateY(300);
        newGame.setTranslateX(220);
        newGame.setTranslateY(340);
    }

    public void startgame(Stage primaryStage, Model model, int score, int level, int heart, int sceneWidth, int sceneHeight, ArrayList<Block> blocks,Pane root,Button newGame,Button load)
    {
        scoreLabel = new Label("Score: " + score);
        levelLabel = new Label("Level: " + level);
        levelLabel.setTranslateY(20);
        heartLabel = new Label("Heart : " + heart);
        heartLabel.setTranslateX(sceneWidth - 70);
        if (loadFromSave == false) {
            root.getChildren().addAll(model.getrect(), model.getBall(),scoreLabel, heartLabel, levelLabel, newGame,load);
        } else {
            root.getChildren().addAll(model.getrect(), model.getBall(), scoreLabel, heartLabel, levelLabel);
        }
        for (Block block : blocks) {
            root.getChildren().add(block.rect);
        }
        Scene scene = new Scene(root, sceneWidth, sceneHeight);
        scene.getStylesheets().add("style.css");

        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();
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








}



