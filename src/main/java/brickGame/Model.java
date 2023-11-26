package brickGame;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;

public class Model {
    private int level = 0;
    private double xBreak = 0.0f;
    private double yBreak = 640.0f;
    private int breakWidth = 130;
    private int breakHeight = 30;

    private int sceneHeigt = 700;

    private int heart = 3;

    private View view;

    private Circle createball;

    private Circle ball;
    private double xBall;
    private double yBall;

    private Main main;

    private Ball bball;
    private Paddle paddle;

    private Rectangle rect;
    private int ballRadius = 10;

    private ArrayList<Block> blocks = new ArrayList<>();

    private boolean isGoldStauts = false;
    private boolean isExistHeartBlock = false;

    private GameEngine engine;

    private long hitTime = 0;

    private long time = 0;

    Score score = new Score();

    private int sceneWidth = 500;

    private boolean colideToBreak = false;
    private boolean colideToBreakAndMoveToRight = false;
    private boolean colideToRightWall = false;
    private boolean colideToLeftWall = false;
    private boolean colideToRightBlock = false;
    private boolean colideToBottomBlock = false;
    private boolean colideToLeftBlock = false;
    private boolean colideToTopBlock = false;

    private double vX = 1.000;
    private double vY = 1.000;

    private Color[]          colors = new Color[]{
            Color.MAGENTA,
            Color.RED,
            Color.GOLD,
            Color.CORAL,
            Color.AQUA,
            Color.VIOLET,
            Color.GREENYELLOW,
            Color.ORANGE,
            Color.PINK,
            Color.SLATEGREY,
            Color.YELLOW,
            Color.TOMATO,
            Color.TAN,
    };

    public Model() {
        initBoard(blocks, level,isExistHeartBlock);
        bball =initBall();
        paddle = initBreak();
        this.ball = ball;
    }

    public void initBoard(ArrayList<Block> blocks, int level,boolean isExistHeartBlock) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < level + 1; j++) {
                int r = new Random().nextInt(500);
                if (r % 5 == 0) {
                    continue;
                }
                int type;
                if (r % 10 == 1) {
                    type = Block.BLOCK_CHOCO;
                } else if (r % 10 == 2) {
                    if (!isExistHeartBlock) {
                        type = Block.BLOCK_HEART;
                        isExistHeartBlock = true;
                    } else {
                        type = Block.BLOCK_NORMAL;
                    }
                } else if (r % 10 == 3) {
                    type = Block.BLOCK_STAR;
                } else {
                    type = Block.BLOCK_NORMAL;
                }
                blocks.add(new Block(j, i, colors[r % (colors.length)], type));
            }
        }
    }


    // Private method for ball initialization
    public Ball initBall() {
        ball = new Circle();
        ball.setRadius(ballRadius);
        ball.setFill(new ImagePattern(new Image("ball.png")));

        xBall = xBreak + (breakWidth / 2);
        yBall = yBreak - ballRadius - 40;
        return new Ball(xBall, yBall, ballRadius);
    }

    public Paddle initBreak() {
        createrect();
        paddleimg();
        return new Paddle(0, 640, 130, 30);
    }

    private void createrect() {
        rect = new Rectangle();
        rect.setWidth(breakWidth);
        rect.setHeight(breakHeight);
        rect.setX(xBreak);
        rect.setY(yBreak);
    }




        public void resetColideFlags() {
            colideToBreak = false;
            colideToBreakAndMoveToRight = false;
            colideToRightWall = false;
            colideToLeftWall = false;
            colideToRightBlock = false;
            colideToLeftBlock = false;
            colideToTopBlock = false;
            colideToBottomBlock = false;
        }



    private void paddleimg() {
        ImagePattern pattern = new ImagePattern(new Image("block.jpg"));
        rect.setFill(pattern);
    }

    public Circle getBall() {
        return ball;
    }

    public Rectangle getrect() {
        return rect;
    }


    // Setter for ball
    public void setBall(Circle ball) {
        this.ball = ball;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }





}
