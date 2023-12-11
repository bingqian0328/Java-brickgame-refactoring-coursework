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
    private int heart = 3;


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
     * Gets the ball.
     *
     * @return The ball.
     */
    public Circle getBall() {
        return ball;
    }

    /**
     * Gets the rectangle.
     *
     * @return The rectangle.
     */
    public Rectangle getrect() {
        return rect;
    }

    /**
     * Sets the ball.
     *
     * @param ball The new ball.
     */
    public void setBall(Circle ball) {
        this.ball = ball;
    }

    /**
     * Sets the rectangle.
     *
     * @param rect The new rectangle.
     */
    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    /**
     * Gets the colideToBreak status.
     *
     * @return The colideToBreak status.
     */
    public boolean isColideToBreak() {
        return colideToBreak;
    }

    /**
     * Sets the colideToBreak status.
     *
     * @param colideToBreak The new colideToBreak status.
     */
    public void setColideToBreak(boolean colideToBreak) {
        this.colideToBreak = colideToBreak;
    }

    /**
     * Gets the colideToBreakAndMoveToRight status.
     *
     * @return The colideToBreakAndMoveToRight status.
     */
    public boolean isColideToBreakAndMoveToRight() {
        return colideToBreakAndMoveToRight;
    }

    /**
     * Sets the colideToBreakAndMoveToRight status.
     *
     * @param colideToBreakAndMoveToRight The new colideToBreakAndMoveToRight status.
     */
    public void setColideToBreakAndMoveToRight(boolean colideToBreakAndMoveToRight) {
        this.colideToBreakAndMoveToRight = colideToBreakAndMoveToRight;
    }

    /**
     * Gets the colideToRightWall status.
     *
     * @return The colideToRightWall status.
     */
    public boolean isColideToRightWall() {
        return colideToRightWall;
    }

    /**
     * Sets the colideToRightWall status.
     *
     * @param colideToRightWall The new colideToRightWall status.
     */
    public void setColideToRightWall(boolean colideToRightWall) {
        this.colideToRightWall = colideToRightWall;
    }

    /**
     * Gets the colideToLeftWall status.
     *
     * @return The colideToLeftWall status.
     */
    public boolean isColideToLeftWall() {
        return colideToLeftWall;
    }

    /**
     * Sets the colideToLeftWall status.
     *
     * @param colideToLeftWall The new colideToLeftWall status.
     */
    public void setColideToLeftWall(boolean colideToLeftWall) {
        this.colideToLeftWall = colideToLeftWall;
    }

    /**
     * Gets the colideToRightBlock status.
     *
     * @return The colideToRightBlock status.
     */
    public boolean isColideToRightBlock() {
        return colideToRightBlock;
    }

    /**
     * Sets the colideToRightBlock status.
     *
     * @param colideToRightBlock The new colideToRightBlock status.
     */
    public void setColideToRightBlock(boolean colideToRightBlock) {
        this.colideToRightBlock = colideToRightBlock;
    }

    /**
     * Gets the colideToBottomBlock status.
     *
     * @return The colideToBottomBlock status.
     */
    public boolean isColideToBottomBlock() {
        return colideToBottomBlock;
    }

    /**
     * Sets the colideToBottomBlock status.
     *
     * @param colideToBottomBlock The new colideToBottomBlock status.
     */
    public void setColideToBottomBlock(boolean colideToBottomBlock) {
        this.colideToBottomBlock = colideToBottomBlock;
    }

    /**
     * Gets the colideToLeftBlock status.
     *
     * @return The colideToLeftBlock status.
     */
    public boolean isColideToLeftBlock() {
        return colideToLeftBlock;
    }

    /**
     * Sets the colideToLeftBlock status.
     *
     * @param colideToLeftBlock The new colideToLeftBlock status.
     */
    public void setColideToLeftBlock(boolean colideToLeftBlock) {
        this.colideToLeftBlock = colideToLeftBlock;
    }

    /**
     * Gets the colideToTopBlock status.
     *
     * @return The colideToTopBlock status.
     */
    public boolean isColideToTopBlock() {
        return colideToTopBlock;
    }

    /**
     * Sets the colideToTopBlock status.
     *
     * @param colideToTopBlock The new colideToTopBlock status.
     */
    public void setColideToTopBlock(boolean colideToTopBlock) {
        this.colideToTopBlock = colideToTopBlock;
    }

    /**
     * Gets the checktransition status.
     *
     * @return The checktransition status.
     */
    public boolean isChecktransition (){
        return checktransition;
    }

    /**
     * Sets the checktransition status.
     *
     * @param checktransition The new checktransition status.
     */
    public void setChecktransition (boolean checktransition)
    {
        this.checktransition = checktransition;
    }

    /**
     * Gets the goldTime.
     *
     * @return The goldTime.
     */
    public long getGoldTime() {
        return goldTime;
    }

    /**
     * Sets the goldTime.
     *
     * @param goldTime The new goldTime.
     */
    public void setGoldTime(long goldTime) {
        this.goldTime = goldTime;
    }

    /**
     * Gets the goDownBall status.
     *
     * @return The goDownBall status.
     */
    public boolean isGoDownBall() {
        return goDownBall;
    }

    /**
     * Sets the goDownBall status.
     *
     * @param goDownBall The new goDownBall status.
     */
    public void setGoDownBall(boolean goDownBall) {
        this.goDownBall = goDownBall;
    }

    /**
     * Gets the goRightBall status.
     *
     * @return The goRightBall status.
     */
    public boolean isGoRightBall() {
        return goRightBall;
    }

    /**
     * Sets the goRightBall status.
     *
     * @param goRightBall The new goRightBall status.
     */
    public void setGoRightBall(boolean goRightBall) {
        this.goRightBall = goRightBall;
    }

    /**
     * Gets the vX value.
     *
     * @return The vX value.
     */
    public double getvX() {
        return vX;
    }

    /**
     * Sets the vX value.
     *
     * @param vX The new vX value.
     */
    public void setvX(double vX) {
        this.vX = vX;
    }

    /**
     * Gets the vY value.
     *
     * @return The vY value.
     */
    public double getvY() {
        return vY;
    }

    /**
     * Sets the vY value.
     *
     * @param vY The new vY value.
     */
    public void setvY(double vY) {
        this.vY = vY;
    }

    /**
     * Gets the destroyedBlockCount value.
     *
     * @return The destroyedBlockCount value.
     */
    public int getDestroyedBlockCount() {
        return destroyedBlockCount;
    }

    /**
     * Sets the destroyedBlockCount value.
     *
     * @param destroyedBlockCount The new destroyedBlockCount value.
     */
    public void setDestroyedBlockCount(int destroyedBlockCount) {
        this.destroyedBlockCount = destroyedBlockCount;
    }

    /**
     * Gets the chocos list.
     *
     * @return The chocos list.
     */
    public ArrayList<Bonus> getChocos() {
        return chocos;
    }

    /**
     * Sets the chocos list.
     *
     * @param chocos The new chocos list.
     */
    public void setChocos(ArrayList<Bonus> chocos) {
        this.chocos = chocos;
    }

    /**
     * Gets the gold status.
     *
     * @return The gold status.
     */
    public boolean isGoldStauts()
    {
        return isGoldStauts;
    }

    /**
     * Sets the gold status.
     *
     * @param goldstatus The new gold status.
     */
    public void setGoldStauts (boolean goldstatus)
    {
        this.isGoldStauts = goldstatus;
    }

    /**
     * Gets the existHeartBlock status.
     *
     * @return The existHeartBlock status.
     */
    public boolean isExistHeartBlock() {
        return isExistHeartBlock;
    }

    /**
     * Sets the existHeartBlock status.
     *
     * @param existHeartBlock The new existHeartBlock status.
     */
    public void setExistHeartBlock(boolean existHeartBlock) {
        isExistHeartBlock = existHeartBlock;
    }

    /**
     * Gets the time value.
     *
     * @return The time value.
     */
    public long getTime() {
        return time;
    }

    /**
     * Sets the time value.
     *
     * @param time The new time value.
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * Gets the level value.
     *
     * @return The level value.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the level value.
     *
     * @param level The new level value.
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Gets the heart value.
     *
     * @return The heart value.
     */
    public int getHeart() {
        return heart;
    }

    /**
     * Sets the heart value.
     *
     * @param heart The new heart value.
     */
    public void setHeart(int heart) {
        this.heart = heart;
    }

    /**
     * Gets the score value.
     *
     * @return The score value.
     */
    public int getScore()
    {
        return score;
    }

    /**
     * Sets the score value.
     *
     * @param score The new score value.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Gets the blocks list.
     *
     * @return The blocks list.
     */
    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    /**
     * Sets the blocks list.
     *
     * @param blocks The new blocks list.
     */
    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }

    /**
     * Gets the flashStatus status.
     *
     * @return The flashStatus status.
     */
    public boolean isFlashStatus() {
        return isFlashStatus;
    }

    /**
     * Sets the flashStatus status.
     *
     * @param flashStatus The new flashStatus status.
     */
    public void setFlashStatus(boolean flashStatus) {
        isFlashStatus = flashStatus;
    }

    /**
     * Gets the paddleDisappeared status.
     *
     * @return The paddleDisappeared status.
     */
    public boolean isPaddleDisappeared() {
        return isPaddleDisappeared;
    }

    /**
     * Sets the paddleDisappeared status.
     *
     * @param paddleDisappeared The new paddleDisappeared status.
     */
    public void setPaddleDisappeared(boolean paddleDisappeared) {
        isPaddleDisappeared = paddleDisappeared;
    }

    /**
     * Gets the boostTime value.
     *
     * @return The boostTime value.
     */
    public long getBoostTime() {
        return boostTime;
    }

    /**
     * Sets the boostTime value.
     *
     * @param boostTime The new boostTime value.
     */
    public void setBoostTime(long boostTime) {
        this.boostTime = boostTime;
    }

    /**
     * Gets the invisibleTime value.
     *
     * @return The invisibleTime value.
     */
    public long getInvisibleTime() {
        return invisibleTime;
    }

    /**
     * Sets the invisibleTime value.
     *
     * @param invisibleTime The new invisibleTime value.
     */
    public void setInvisibleTime(long invisibleTime) {
        this.invisibleTime = invisibleTime;
    }


}