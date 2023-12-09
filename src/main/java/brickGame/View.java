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

import java.util.ArrayList;
/**
 * The View class is responsible for managing the visual elements of the game, including the game scene, buttons, and messages.
 */

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

    /**
     * Creates a new ball object with a specific image pattern.
     *
     * @return The newly created ball object.
     */
    public Circle createBall() {
        ball = new Circle();
        ball.setRadius(ballRadius);
        ball.setFill(new ImagePattern(new Image("moonball.png")));
        return ball;
    }

    /**
     * Creates a new rectangle object (paddle) with a specific image pattern.
     *
     * @return The newly created rectangle object.
     */
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

    /**
     * Reverts the ball image pattern to the default, indicating the end of the gold status.
     *
     * @param bball The ball object to be modified.
     */
    public void revgoldstatusimage(Ball bball )
    {
            ball.setFill(new ImagePattern(new Image("moonball.png")));
            root.getStyleClass().remove("goldRoot");
    }

    /**
     * Displays a message when a bonus object is collected, updating the score.
     *
     * @param choco The bonus object that was collected.
     */
    public void showchoco(Bonus choco)
    {
        System.out.println("You Got it and +3 score for you");
        choco.taken = true;
        choco.choco.setVisible(false);
        new Score().show(choco.x, choco.y, 3, root);
    }

    /**
     * Sets the score, heart, and level labels on the game screen.
     *
     * @param score The current score value.
     * @param heart The current heart value.
     */
    public void setonupdate(int score, int heart)
    {
        scoreLabel.setText("Score: " + score);
        heartLabel.setText("Heart : " + heart);
    }

    /**
     * Creates load and new game buttons.
     */
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

    /**
     * Sets up the game scene with specified elements and styling.
     *
     * @param controller     The Controller instance for event handling.
     * @param loadFromSave   Indicates whether the game is loaded from a saved state.
     * @param model          The game model containing data.
     * @param blocks         List of blocks to be displayed on the scene.
     * @param primaryStage   The primary stage of the JavaFX application.
     * @param score          The current score value.
     * @param level          The current level.
     * @param heart          The current heart value.
     * @param rect           The paddle rectangle.
     * @param ball           The game ball.
     */
    public void setScene(Controller controller,boolean loadFromSave,Model model, ArrayList<Block> blocks, Stage primaryStage,int score,int level,int heart,Rectangle rect,Circle ball){
        root = new Pane();
        scoreLabel = new Label("Score: " + score);
        levelLabel = new Label("Level: " + level);//need to getlevel from controller afterwards
        levelLabel.setTranslateY(20);
        heartLabel = new Label("Heart : " + heart);
        heartLabel.setTranslateX(500 - 70);
        if (loadFromSave == false && level!=19) {
            root.getChildren().addAll(rect,ball, scoreLabel, heartLabel, levelLabel, newGame, load);
        } else if (level != 19) {
            root.getChildren().addAll(rect,ball, scoreLabel, heartLabel, levelLabel);
        }
        else
        {
            showWinText(controller);
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

    /**
     * Changes the ball image from default to gold ball once the gold block is hit
     *
     * @param ball The ball object to be modified.
     */
    public void goldballimg(Circle ball)
    {
        ball.setFill(new ImagePattern(new Image("goldball.png")));
        System.out.println("gold ball");
        root.getStyleClass().add("goldRoot");
    }

    /**
     * display level up text
     */
    public void showlevelup(Pane root)
    {
        new Score().showMessage("Level Up :)", root);
    }

    /**
     * display game saved text
     */
    public void showgamesaved()
    {
        new Score().showMessage("Game Saved", root);
    }

    /**
     * display game paused text
     */
    public void showgamepaused()
    {
        new Score().showMessage2("Game Paused", root);
    }

    /**
     * remove the long-lasting game paused text once unpause button is clicked
     */
    public void removeGamePausedMessage() {
        root.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().equals("Game Paused"));
    }

    /**
     * display game over text
     */
    public void showgameover (Controller controller)
    {
        new Score().showGameOver(root,controller);
    }

    /**
     * display game unpaused text
     */
    public void showgamecont()
    {
        new Score().showMessage("Game Unpaused", root);
    }

    /**
     * show game win
     * @param controller
     */
    public void showWinText(Controller controller) {
        score = new Score();
        score.showWin(root,controller);
        }


    /**
     * show heart -1
     */
    public void scoreshow( )
    {
        new Score().show(500 / 2, 700 / 2, -1, root);
    }

    /**
     * show score +1 message when block is hit
     * @param x
     * @param y
     */
    public void showblocks(final double x, final double y)
    {
        new Score().show(x, y, 1, root);
    }

    /**
     * hide the paddle once hide paddle block is hit
     * @param root
     * @param paddle
     */
    public void hidePaddle(Pane root, Rectangle paddle) {
        paddle.setVisible(false);
    }

    /**
     * unhide the paddle once hide paddle time is over
     * @param root
     * @param paddle
     */
    public void showPaddle(Pane root, Rectangle paddle) {
        paddle.setVisible(true);
    }

    public void setvisible()
    {
        load.setVisible(false);
        newGame.setVisible(false);
    }


    /**
     * setters and getters methods
     * @return
     */
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



