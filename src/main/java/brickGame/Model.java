package brickGame;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Random;

/**
 * The {@code Model} class represents the model in the MVC (Model-View-Controller) architecture of the Brick Game.
 * It manages the game state, collision detection, and various parameters related to the game.
 */

public class Model {

    // Fields representing various aspects of the game state and configuration

    /**
     * The current game level.
     */
    private int level = 0;

    /**
     * The X-coordinate of the break (paddle).
     */
    private double xBreak = 0.0f;

    /**
     * The Y-coordinate of the break (paddle).
     */
    private double yBreak = 640.0f;
    /**
     * The width of the break (paddle).
     */
    private int breakWidth = 130;

    /**
     * The height of the game scene.
     */
    private int sceneHeigt = 700;

    /**
     * The number of remaining lives (hearts).
     */
    private int heart = 1;


    /**
     * The main ball in the game.
     */
    private Circle ball;

    /**
     * The X-coordinate of the main ball.
     */
    private double xBall;

    /**
     * The Y-coordinate of the main ball.
     */
    private double yBall;


    /**
     * The main ball represented as an instance of the Ball class.
     */
    private Ball bball;

    /**
     * The break (paddle) represented as an instance of the Paddle class.
     */
    private Paddle paddle;

    /**
     * The rectangle representing the break (paddle).
     */
    private Rectangle rect;

    /**
     * The radius of the main ball.
     */
    private int ballRadius = 12;

    /**
     * The score of blocks hit in the game.
     */
    private int score = 0;

    /**
     * The array list of blocks in the game.
     */
    private ArrayList<Block> blocks = new ArrayList<>();

    /**
     * Flag indicating whether the gold status is active.
     */
    private boolean isGoldStauts = false;

    /**
     * Flag indicating whether a heart block exists in the game.
     */
    private boolean isExistHeartBlock = false;

    /**
     * Flag indicating whether the flash status is active.
     */
    private boolean isFlashStatus = false;

    /**
     * Flag indicating whether the paddle invisible feature is activated
     */
    private boolean isPaddleDisappeared = false;

    /**
     * The boost time duration.
     */
    private long boostTime = 0;

    /**
     * The invisible time duration.
     */
    private long invisibleTime = 0;

    /**
     * The game time elapsed.
     */
    private long time = 0 ;

    /**
     * The width of the game scene.
     */
    private int sceneWidth = 500;

    /**
     * Flags for various collision conditions.
     */
    private boolean colideToBreak = false;
    private boolean colideToBreakAndMoveToRight = false;
    private boolean colideToRightWall = false;
    private boolean colideToLeftWall = false;
    private boolean colideToRightBlock = false;
    private boolean colideToBottomBlock = false;
    private boolean colideToLeftBlock = false;
    private boolean colideToTopBlock = false;

    /**
     * Flag indicating whether a transition is in progress.
     */
    private boolean checktransition = false;

    /**
     * The time duration for gold status.
     */
    private long goldTime = 0;

    /**
     * Directional flags for the main ball's movement.
     */
    private boolean goDownBall                  = true;
    private boolean goRightBall                 = true;

    /**
     * The X-velocity of the main ball.
     */
    private double vX = 1.000;
    /**
     * The Y-velocity of the main ball.
     */
    private double vY = 1.000;

    /**
     * The count of destroyed blocks in the game.
     */
    private int destroyedBlockCount = 0;

    /**
     * The list of bonus items (chocos) present in the game.
     */
    private ArrayList<Bonus> chocos = new ArrayList<Bonus>();

    /**
     * Array of colors for block representation.
     */
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

    /**
     * Constructor for the Model class.
     * Initializes the game board, main ball, and break (paddle).
     */
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

    // Methods related to game initialization and configuration

    /**
     * Initializes the game board with blocks based on the specified level and existence of heart blocks.
     *
     * @param blocks             The list to store the blocks.
     * @param level              The current game level.
     * @param isExistHeartBlock  Flag indicating whether a heart block exists in the game.
     */
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


    /**
     * Initializes the main ball.
     *
     * @return The Ball object representing the main ball.
     */
    // Private method for ball initialization
    public Ball initBall() {
        getBall();
        xBall = xBreak + (breakWidth / 2);
        yBall = yBreak - ballRadius - 40;
        return new Ball(xBall, yBall, ballRadius);
    }

    /**
     * Initializes the break (paddle).
     *
     * @return The Paddle object representing the break.
     */
    public Paddle initBreak() {
        getrect();
        return new Paddle(0, 640, 130, 30);
    }


    /**
     * Checks for collision between the main ball and the break (paddle).
     *
     * @param level  The current game level.
     * @param paddle The break (paddle) object.
     * @param bball  The main ball object.
     */
    public void checkBreakCollision(int level, Paddle paddle, Ball bball) {
        if (bball.getYb() >= paddle.getY() - bball.getRadius()) {
            if (bball.getXb() >= paddle.getX() && bball.getXb() <= paddle.getX() + paddle.getWidth()) {
                handleBreakCollision(level, paddle,bball);
            }
        }
    }

    /**
     * Handles the collision between the main ball and the break (paddle).
     *
     * @param level  The current game level.
     * @param paddle The break (paddle) object.
     * @param bball  The main ball object.
     */
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


    /**
     * Checks for collision with the right or left wall of the game scene.
     *
     * @param bball      The main ball object.
     * @param sceneWidth The width of the game scene.
     */
    public void checkWallCollision(Ball bball,int sceneWidth) {
        if (bball.getXb() >= sceneWidth) {
            resetColideFlags();
            colideToRightWall = true;
        } else if (bball.getXb() <= 0) {
            resetColideFlags();
            colideToLeftWall = true;
        }
    }

    /**
     * Handles collision with the break and walls, determining the ball's direction accordingly.
     *
     * @param bball The main ball object.
     */
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

    /**
     * Checks for collision with blocks and updates ball direction accordingly.
     *
     * @param bball The main ball object.
     */
    public void checkBlockCollisions(Ball bball) {
        if (colideToRightBlock) {
            bball.setGoingRight(true);
        } else if (colideToLeftBlock) {
            handleLeftBlockCollision(bball);
        }
    }

    /**
     * Handles collision with the left block, updating ball direction accordingly.
     *
     * @param bball The main ball object.
     */
    public void handleLeftBlockCollision(Ball bball) {
        bball.setGoingRight(false);

        if (colideToBottomBlock) {
            handleBottomLeftBlockCorner(bball, paddle);
        }
    }

    /**
     * Handles collision with the bottom-left corner of a block, updating ball direction accordingly.
     *
     * @param bball  The main ball object.
     * @param paddle The break (paddle) object.
     */
    public void handleBottomLeftBlockCorner(Ball bball, Paddle paddle) {
        double cornerDistance = Math.sqrt(Math.pow(bball.getXb() - paddle.getX(), 2) + Math.pow(bball.getYb() - paddle.getY() - paddle.getHeight(), 2));
        if (cornerDistance <= bball.getRadius()) {
            bball.bounceHorizontally();
            bball.bounceDown();
        }
    }

    /**
     * Checks for collision with the top or bottom block boundaries, updating ball direction accordingly.
     *
     * @param bball The main ball object.
     */
    public void checkTopAndBottomBlockCollisions( Ball bball) {
        if (colideToTopBlock) {
            bball.setGoingDown(false);
        } else if (colideToBottomBlock) {
            bball.setGoingDown(true);
        }
    }

    /**
     * Resets the collision flags for various conditions.
     */
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

    // Methods related to game reset and parameter initialization

    /**
     * Resets the game state, including ball position, collision flags, and block-related information.
     *
     * @param bball The main ball object.
     */
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

    /**
     * Resets various game parameters to their initial values.
     */
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

    /**
     * Loads game data from a saved state, updating the game state and block configuration accordingly.
     *
     * @param loadSave The LoadSave object containing the saved game data.
     * @param paddle   The break (paddle) object.
     * @param bball    The main ball object.
     * @param blocks   The list of blocks in the game.
     */
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


    /**
     * Updates the position of labels and game elements for the current game state.
     *
     * @param bball  The main ball object.
     * @param paddle The break (paddle) object.
     */
    public void updateLabels(Ball bball, Paddle paddle) {
        getrect().setX(paddle.getX());
        getrect().setY(paddle.getY());
        getBall().setCenterX(bball.getXb());
        getBall().setCenterY(bball.getYb());

        for (Bonus choco : chocos) {
            choco.choco.setY(choco.y);
        }
    }

    /**
     * Checks the hit code and updates collision flags accordingly.
     *
     * @param hitCode The hit code indicating the type of collision.
     * @param block   The block object involved in the collision.
     */
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

    // Getter and Setter methods

    /**
     * setter and getter methods
     */
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