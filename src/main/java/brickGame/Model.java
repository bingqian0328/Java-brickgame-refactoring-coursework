package brickGame;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class Model {
    private int level = 0;
    private double xBreak = 0.0f;
    private double yBreak = 640.0f;
    private int breakWidth = 130;

    private int sceneHeigt = 700;

    private int heart = 3;

    private View view;

    private Circle ball;
    private double xBall;

    private int initialblockcount;
    private double yBall;

    private Main main;

    private Ball bball;
    private Paddle paddle;

    private Rectangle rect;
    private int ballRadius = 12;

    private int score = 0;

    private Score scoree;

    private ArrayList<Block> blocks = new ArrayList<>();

    private boolean isGoldStauts = false;
    private boolean isExistHeartBlock = false;

    private boolean isFlashStatus = false;

    private boolean isPaddleDisappeared = false;

    private GameEngine engine;


    private long boostTime = 0;

    private long invisibleTime = 0;

    private long time = 0 ;

    public Pane root;

    private int sceneWidth = 500;

    private boolean colideToBreak = false;
    private boolean colideToBreakAndMoveToRight = false;
    private boolean colideToRightWall = false;
    private boolean colideToLeftWall = false;
    private boolean colideToRightBlock = false;
    private boolean colideToBottomBlock = false;
    private boolean colideToLeftBlock = false;
    private boolean colideToTopBlock = false;

    private boolean checktransition = false;

    private double centerBreakX ;

    private long goldTime = 0;

    private boolean goDownBall                  = true;
    private boolean goRightBall                 = true;

    private double vX = 1.000;
    private double vY = 1.000;

    private int destroyedBlockCount = 0;

    private LoadSave loadSave;

    private ArrayList<Bonus> chocos = new ArrayList<Bonus>();

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
        bball = new Ball(xBall, yBall, ballRadius);
        bball =initBall();
        paddle = initBreak();
        this.ball = ball;
        checkBreakCollision(level, paddle, bball);
        checkWallCollision(bball,sceneWidth);
        handleBreakAndWallCollisions(bball);
        checkBlockCollisions(bball);
        checkTopAndBottomBlockCollisions(bball);
        resetColideFlags();
        resetGame(bball);
        resetGameParameters();
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
                }
                else if (r % 10 == 4)
                {
                    type = Block.BLOCK_BOOST;
                }
                else if (r % 10 == 6 && level == 18)
                {
                    type = Block.BLOCK_HIDE;
                }
                else {
                    type = Block.BLOCK_NORMAL;
                }
                blocks.add(new Block(j, i, colors[r % (colors.length)], type));
            }
        }
    }


    // Private method for ball initialization
    public Ball initBall() {
        getBall();
        xBall = xBreak + (breakWidth / 2);
        yBall = yBreak - ballRadius - 40;
        return new Ball(xBall, yBall, ballRadius);
    }

    public Paddle initBreak() {
        getrect();
        return new Paddle(0, 640, 130, 30);
    }


    public void checkBreakCollision(int level, Paddle paddle, Ball bball) {
        if (bball.getYb() >= paddle.getY() - bball.getRadius()) {
            if (bball.getXb() >= paddle.getX() && bball.getXb() <= paddle.getX() + paddle.getWidth()) {
                handleBreakCollision(level, paddle,bball);
            }
        }
    }

    private void handleBreakCollision(int level, Paddle paddle, Ball bball) {
        resetColideFlags();
        colideToBreak = true;
        bball.bounceUp();

        double relation = (bball.getXb() - paddle.getCenterX()) / (paddle.getWidth()/ 2);

        if (Math.abs(relation) <= 0.3) {
            bball.setVeloX(Math.abs(relation));
        } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
            bball.setVeloX((Math.abs(relation) * 1.5) + (level / 3.500));
        } else {
            bball.setVeloX((Math.abs(relation) * 2) + (level / 3.500));
        }

        if (bball.getXb() - paddle.getCenterX() > 0) {
            colideToBreakAndMoveToRight = true;
        } else {
            colideToBreakAndMoveToRight = false;
        }
    }


    public void checkWallCollision(Ball bball,int sceneWidth) {
        if (bball.getXb() >= sceneWidth) {
            resetColideFlags();
            colideToRightWall = true;
        } else if (bball.getXb() <= 0) {
            resetColideFlags();
            colideToLeftWall = true;
        }
    }

    public void handleBreakAndWallCollisions(Ball bball) {
        if (colideToBreak) {
            bball.setGoingRight(colideToBreakAndMoveToRight);
        }

        if (colideToRightWall) {
            bball.setGoingRight(false);
        }

        if (colideToLeftWall) {
            bball.setGoingRight(true);
        }
    }

    public void checkBlockCollisions(Ball bball) {
        if (colideToRightBlock) {
            bball.setGoingRight(true);
        } else if (colideToLeftBlock) {
            handleLeftBlockCollision(bball);
        }
    }

    public void handleLeftBlockCollision(Ball bball) {
        bball.setGoingRight(false);

        if (colideToBottomBlock) {
            handleBottomLeftBlockCorner(bball, paddle);
        }
    }

    public void handleBottomLeftBlockCorner(Ball bball, Paddle paddle) {
        double cornerDistance = Math.sqrt(Math.pow(bball.getXb() - paddle.getX(), 2) + Math.pow(bball.getYb() - paddle.getY() - paddle.getHeight(), 2));
        if (cornerDistance <= bball.getRadius()) {
            bball.bounceHorizontally();
            bball.bounceDown();
        }
    }

    public void checkTopAndBottomBlockCollisions( Ball bball) {
        if (colideToTopBlock) {
            bball.setGoingDown(false);
        } else if (colideToBottomBlock) {
            bball.setGoingDown(true);
        }
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

    public void resetGame(Ball bball)
    {
        bball.setXb(1.000);
        resetColideFlags();
        goDownBall = true;
        isGoldStauts = false;
        isExistHeartBlock = false;
        time = 0;
        goldTime = 0;
        blocks.clear();
        chocos.clear();
        destroyedBlockCount = 0;
    }

    public void resetGameParameters() {
        level = 0;
        heart = 3;
        score = 0;
        bball.setXb(1.000);
        destroyedBlockCount = 0;
        resetColideFlags();
        goDownBall = true;
        isGoldStauts = false;
        isExistHeartBlock = false;
        time = 0;
        goldTime = 0;
        blocks.clear();
        chocos.clear();
    }

    public void loadGameData(LoadSave loadSave,Paddle paddle, Ball bball,ArrayList<Block> blocks) {
        this.setLevel(loadSave.level);
        this.setScore(loadSave.score);
        this.setHeart(loadSave.heart);

        this.bball.setXb(loadSave.xBall);
        this.bball.setYb(loadSave.yBall);
        this.paddle.setX(loadSave.xBreak);
        this.paddle.setY(loadSave.yBreak);
        this.paddle.setCenterX(loadSave.centerBreakX);

        this.setTime(loadSave.time);
        this.setGoldTime(loadSave.goldTime);
        this.setBoostTime(loadSave.boostTime);
        this.setInvisibleTime(loadSave.invisibleTime);

        this.setvX(loadSave.vX);

        this.setExistHeartBlock(loadSave.isExistHeartBlock);
        this.setGoldStauts(loadSave.isGoldStauts);

        this.setGoDownBall(loadSave.goDownBall);
        this.setGoRightBall(loadSave.goRightBall);

        this.setColideToBreak(loadSave.colideToBreak);
        this.setColideToBreakAndMoveToRight(loadSave.colideToBreakAndMoveToRight);

        this.setColideToRightWall(loadSave.colideToRightWall);
        this.setColideToLeftWall(loadSave.colideToLeftWall);

        this.setColideToRightBlock(loadSave.colideToRightBlock);
        this.setColideToBottomBlock(loadSave.colideToBottomBlock);
        this.setColideToLeftBlock(loadSave.colideToLeftBlock);
        this.setColideToTopBlock(loadSave.colideToTopBlock);

        this.setPaddleDisappeared(loadSave.isPaddleDisappeared);
        this.setFlashStatus(loadSave.isFlashStatus);

        this.setDestroyedBlockCount(loadSave.destroyedBlockCount);

        blocks.clear();
        chocos.clear();

        for (BlockSerializable ser : loadSave.blocks) {
            int r = new Random().nextInt(200);
            blocks.add(new Block(ser.row, ser.j, colors[r % colors.length], ser.type));
        }
    }

    public void makeblocks(LoadSave loadSave)
    {
        blocks.clear();
        chocos.clear();

        for (BlockSerializable ser : loadSave.blocks) {
            int r = new Random().nextInt(200);
            blocks.add(new Block(ser.row, ser.j, colors[r % colors.length], ser.type));
        }
    }


    public void updateLabels(Ball bball, Paddle paddle) {
        getrect().setX(paddle.getX());
        getrect().setY(paddle.getY());
        getBall().setCenterX(bball.getXb());
        getBall().setCenterY(bball.getYb());

        for (Bonus choco : chocos) {
            choco.choco.setY(choco.y);
        }
    }





    public void checkhitcode (int hitCode,Block block)
    {
        if (hitCode == Block.HIT_RIGHT) {
            setColideToRightBlock(true);
        } else if (hitCode == Block.HIT_BOTTOM) {
            setColideToBottomBlock(true);
        } else if (hitCode == Block.HIT_LEFT) {
            setColideToLeftBlock(true);
        } else if (hitCode == Block.HIT_TOP) {
            setColideToTopBlock(true);
        }
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


    // Getter and Setter for colideToBreak
    public boolean isColideToBreak() {
        return colideToBreak;
    }

    public void setColideToBreak(boolean colideToBreak) {
        this.colideToBreak = colideToBreak;
    }

    // Getter and Setter for colideToBreakAndMoveToRight
    public boolean isColideToBreakAndMoveToRight() {
        return colideToBreakAndMoveToRight;
    }

    public void setColideToBreakAndMoveToRight(boolean colideToBreakAndMoveToRight) {
        this.colideToBreakAndMoveToRight = colideToBreakAndMoveToRight;
    }

    // Getter and Setter for colideToRightWall
    public boolean isColideToRightWall() {
        return colideToRightWall;
    }

    public void setColideToRightWall(boolean colideToRightWall) {
        this.colideToRightWall = colideToRightWall;
    }

    // Getter and Setter for colideToLeftWall
    public boolean isColideToLeftWall() {
        return colideToLeftWall;
    }

    public void setColideToLeftWall(boolean colideToLeftWall) {
        this.colideToLeftWall = colideToLeftWall;
    }

    // Getter and Setter for colideToRightBlock
    public boolean isColideToRightBlock() {
        return colideToRightBlock;
    }

    public void setColideToRightBlock(boolean colideToRightBlock) {
        this.colideToRightBlock = colideToRightBlock;
    }

    // Getter and Setter for colideToBottomBlock
    public boolean isColideToBottomBlock() {
        return colideToBottomBlock;
    }

    public void setColideToBottomBlock(boolean colideToBottomBlock) {
        this.colideToBottomBlock = colideToBottomBlock;
    }

    // Getter and Setter for colideToLeftBlock
    public boolean isColideToLeftBlock() {
        return colideToLeftBlock;
    }

    public int getInitialBlockCount() {
        return initialblockcount;
    }

    public void setInitialBlockCount(int initialBlockCount) {
        this.initialblockcount = initialBlockCount;
    }

    public void setColideToLeftBlock(boolean colideToLeftBlock) {
        this.colideToLeftBlock = colideToLeftBlock;
    }

    // Getter and Setter for colideToTopBlock
    public boolean isColideToTopBlock() {
        return colideToTopBlock;
    }

    public void setColideToTopBlock(boolean colideToTopBlock) {
        this.colideToTopBlock = colideToTopBlock;
    }

    public boolean isChecktransition (){
        return checktransition;
    }

    public void setChecktransition (boolean checktransition)
    {
        this.checktransition = checktransition;
    }

    public long getGoldTime() {
        return goldTime;
    }

    public void setGoldTime(long goldTime) {
        this.goldTime = goldTime;
    }

    public boolean isGoDownBall() {
        return goDownBall;
    }

    public void setGoDownBall(boolean goDownBall) {
        this.goDownBall = goDownBall;
    }

    public boolean isGoRightBall() {
        return goRightBall;
    }

    public void setGoRightBall(boolean goRightBall) {
        this.goRightBall = goRightBall;
    }

    public double getvX() {
        return vX;
    }

    public void setvX(double vX) {
        this.vX = vX;
    }

    public double getvY() {
        return vY;
    }

    public void setvY(double vY) {
        this.vY = vY;
    }

    public int getDestroyedBlockCount() {
        return destroyedBlockCount;
    }

    public void setDestroyedBlockCount(int destroyedBlockCount) {
        this.destroyedBlockCount = destroyedBlockCount;
    }

    public ArrayList<Bonus> getChocos() {
        return chocos;
    }

    public void setChocos(ArrayList<Bonus> chocos) {
        this.chocos = chocos;
    }

    public boolean isGoldStauts()
    {
        return isGoldStauts;
    }

    public void setGoldStauts (boolean goldstatus)
    {
        this.isGoldStauts = goldstatus;
    }

    public boolean isExistHeartBlock() {
        return isExistHeartBlock;
    }

    public void setExistHeartBlock(boolean existHeartBlock) {
        isExistHeartBlock = existHeartBlock;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }
    public boolean isFlashStatus() {
        return isFlashStatus;
    }

    // Setter for isFlashStatus
    public void setFlashStatus(boolean flashStatus) {
        isFlashStatus = flashStatus;
    }

    // Getter for isPaddleDisappeared
    public boolean isPaddleDisappeared() {
        return isPaddleDisappeared;
    }

    // Setter for isPaddleDisappeared
    public void setPaddleDisappeared(boolean paddleDisappeared) {
        isPaddleDisappeared = paddleDisappeared;
    }

    public long getBoostTime() {
        return boostTime;
    }

    // Setter for boostTime
    public void setBoostTime(long boostTime) {
        this.boostTime = boostTime;
    }

    // Getter for invisibleTime
    public long getInvisibleTime() {
        return invisibleTime;
    }

    // Setter for invisibleTime
    public void setInvisibleTime(long invisibleTime) {
        this.invisibleTime = invisibleTime;
    }


}