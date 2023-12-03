package brickGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;




public class Controller extends Application implements EventHandler<KeyEvent>, GameEngine.OnAction{
    public int level =18;

    private boolean isPaused = false;

    private double xBreak = 0.0f;
    private double yBreak = 640.0f;

    private boolean isPaddleDisappeared = false;

    private int sceneWidth = 500;
    private int sceneHeigt = 700;

    private boolean checktransition = false ;
    private static int LEFT  = 1;
    private static int RIGHT = 2;
    private View view;

    private Model model;

    private Ball bball;
    private Paddle paddle;

    private bgsound bgsound;

    private boolean isGoldStauts      = false;

    private boolean isFlashStatus = false;
    private boolean isExistHeartBlock = false;
    private int destroyedBlockCount = 0;

    private int  heart    = 1;
    private int  score    = 0;
    private long time     = 0;
    private long goldTime = 0;

    private long boostTime = 0;

    private long invisibleTime = 0;

    private GameEngine engine;
    public static String savePath    = "C:/save/save.mdds";
    public static String savePathDir = "C:/save/";

    private ArrayList<Block> blocks = new ArrayList<Block>();
    private ArrayList<Bonus> chocos = new ArrayList<Bonus>();
    public  Pane             root;

    private boolean loadFromSave = false;

    Stage  primaryStage;
    Button load    = null;
    Button newGame = null;

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

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        bgsound = new bgsound();
        //bgsound.play();

        // Create an instance of the Model
        view = new View();
        model = new Model();

        startgame();
        model.setBall(view.createBall());
        model.setRect(view.createrect());
        root = new Pane();
        view.startgame(primaryStage,model,score, level,heart,sceneWidth,sceneHeigt,blocks,root,view.getNewGameButton(),view.getLoadButton());
        Scene scene = primaryStage.getScene();
        scene.setOnKeyPressed(this);

        if (loadFromSave == false) {
            if (level > 1 && level < 19) {
                view.setvisible();
                engine = new GameEngine();
                engine.setOnAction(this);
                engine.setFps(120);
                engine.start();
            }
            view.getLoadButton().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        loadGame();
                        view.setvisible();
                    }
                });
            view.getNewGameButton().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        engine = new GameEngine();
                        engine.setOnAction(Controller.this);
                        engine.setFps(120);
                        engine.start();
                        view.setvisible();
                    }
                });
        } else {
            engine = new GameEngine();
            engine.setOnAction(this);
            engine.setFps(120);
            engine.start();
            loadFromSave = false;
        }
    }

    private void startgame() {
        if (loadFromSave == false) {
            level++;
            if (level > 1) {
                view.showlevelup(root);
            }
            if (level == 19) {
                view.showgamewin(this);
                return;
            }
            bball= model.initBall();
            paddle = model.initBreak();
            model.initBoard(blocks,level,isExistHeartBlock);
            view.createbuttons();

        }
    }
    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                move(LEFT);
                break;
            case RIGHT:
                move(RIGHT);
                break;
            case S:
                saveGame();
                break;
            case P:
                pauseGame();
                break;
            case U:
                unpauseGame();
                break;
        }
    }

    private void pauseGame() {
        if (!isPaused) {
            isPaused = true;
            engine.pause();
            view.showgamepaused(root);
        }
    }

    public void unpauseGame() {
        if (isPaused) {
            isPaused = false;
            engine.resume();
            view.showgamecont(root);
            view.removeGamePausedMessage(root);
        }
    }

    private void move(final int direction) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int sleepTime = 3;
                for (int i = 0; i < 30; i++) {
                    if (paddle.getX() == (sceneWidth - paddle.getWidth()) && direction == RIGHT) {
                        return;
                    }
                    if (paddle.getX() == 0 && direction == LEFT) {
                        return;
                    }
                    if (direction == RIGHT) {
                        paddle.setX(paddle.getX() + 1);
                        paddle.setCenterX(paddle.getX() + paddle.getHalfWidth());
                    } else {
                        paddle.setX(paddle.getX() - 1);
                        paddle.setCenterX(paddle.getX() + paddle.getHalfWidth());
                    }
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (i >= 20) {
                        sleepTime = i;
                    }
                }
            }
        }).start();
    }


    private boolean goDownBall                  = true;
    private boolean goRightBall                 = true;
    private double vX = 1.000;
    private void setPhysicsToBall() {
        bball.updatePosition();
        checkTopAndBottomBoundaries();
        model.checkBreakCollision(level, paddle, bball);
        model.checkWallCollision(bball,sceneWidth);
        model.handleBreakAndWallCollisions(bball);
        model.checkBlockCollisions(bball);
        model.checkTopAndBottomBlockCollisions(bball);
    }

    private void checkTopAndBottomBoundaries() {
        if (bball.getYb() <= 0) {
            model.resetColideFlags();
            bball.bounceDown();
        } else if (bball.getYb() >= sceneHeigt) {
            handleBallOutOfBounds();
        }
    }

    private void handleBallOutOfBounds() {
        model.resetColideFlags();
        bball.bounceUp();

        if (!isGoldStauts) {
            heart--;
            view.scoreshow(root);

            if (heart == 0) {
                view.showgameover(root,this);
                engine.stop();
            }
        }
    }

    private void checkDestroyedCount() {
        if (destroyedBlockCount == blocks.size()) {
            nextLevel();
            stopBackgroundSound();
        }
    }

    private void saveGame() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new File(savePathDir).mkdirs();
                File file = new File(savePath);
                ObjectOutputStream outputStream = null;
                try {
                    outputStream = new ObjectOutputStream(new FileOutputStream(file));

                    outputStream.writeInt(level);
                    outputStream.writeInt(score);
                    outputStream.writeInt(heart);
                    outputStream.writeInt(destroyedBlockCount);

                    outputStream.writeDouble(xBreak);
                    outputStream.writeDouble(yBreak);
                    outputStream.writeLong(time);
                    outputStream.writeLong(goldTime);
                    outputStream.writeLong(boostTime);
                    outputStream.writeDouble(vX);

                    outputStream.writeBoolean(isExistHeartBlock);
                    outputStream.writeBoolean(isGoldStauts);
                    outputStream.writeBoolean(isFlashStatus);
                    outputStream.writeBoolean(isPaddleDisappeared);
                    outputStream.writeBoolean(goDownBall);
                    outputStream.writeBoolean(goRightBall);
                    outputStream.writeBoolean(model.isColideToBreak());
                    outputStream.writeBoolean(model.isColideToBreakAndMoveToRight());
                    outputStream.writeBoolean(model.isColideToRightWall());
                    outputStream.writeBoolean(model.isColideToLeftWall());
                    outputStream.writeBoolean(model.isColideToRightBlock());
                    outputStream.writeBoolean(model.isColideToBottomBlock());
                    outputStream.writeBoolean(model.isColideToLeftBlock());
                    outputStream.writeBoolean(model.isColideToTopBlock());

                    ArrayList<BlockSerializable> blockSerializables = new ArrayList<BlockSerializable>();
                    for (Block block : blocks) {
                        if (block.isDestroyed) {
                            continue;
                        }
                        blockSerializables.add(new BlockSerializable(block.row, block.column, block.type));
                    }

                    outputStream.writeObject(blockSerializables);

                    view.showgamesaved(root);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
    private void loadGame() {
        LoadSave loadSave = new LoadSave();
        loadSave.read();
        model.loadGameData(loadSave,paddle,bball);
        level = model.getLevel();
        score = model.getScore();
        heart = model.getHeart();
        try {
            loadFromSave = true;
            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void nextLevel() {

        if (checktransition)
        {
            return;
        }
        checktransition = true;
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                try {
                    model.resetGame(bball);
                    engine.stop();
                    start(primaryStage);

                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    checktransition = false;
                }
            }
        });
    }

    public void restartGame() {
        try {
            level = model.getLevel();
            score = model.getScore();
            heart = model.getHeart();
            model.resetGameParameters();
            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onUpdate() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                view.setonupdate(score,heart);
                chocos = model.getChocos();
                model.updateLabels(bball, paddle);

                if (bball.getYb() >= Block.getPaddingTop() && bball.getYb() <= (Block.getHeight() * (level + 1)) + Block.getPaddingTop()) {
                    for (final Block block : blocks) {
                        int hitCode = block.checkHitToBlock(bball.getXb(), bball.getYb(),goRightBall,goDownBall);
                        if (hitCode != Block.NO_HIT) {
                            score += 1;

                            view.showblocks(block.x, block.y, root);

                            block.rect.setVisible(false);
                            block.isDestroyed = true;
                            destroyedBlockCount++;
                            model.resetColideFlags();

                            if (block.type == Block.BLOCK_CHOCO) {
                                final Bonus choco = new Bonus(block.row, block.column);
                                choco.timeCreated = time;
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        root.getChildren().add(choco.choco);
                                    }
                                });
                                chocos.add(choco);
                            }

                            if (block.type == Block.BLOCK_STAR) {
                                goldTime = time;
                                view.goldballimg(model.getBall(),root);
                                isGoldStauts = true;
                            }

                            if (block.type == Block.BLOCK_HEART) {
                                heart++;
                            }

                            if (block.type == Block.BLOCK_BOOST) {
                                boostTime = time;
                                // Activate boost status
                                isFlashStatus = true;

                                // Set a higher velocity for the ball
                                bball.setVeloX(bball.getVeloX() * 2);
                                bball.setYb(bball.getVeloY() * 2);
                            }
                            if (block.type == Block.BLOCK_HIDE)
                            {
                                invisibleTime = time;
                                isPaddleDisappeared = true;
                                view.hidePaddle(root, model.getrect());
                            }

                            model.checkhitcode(hitCode,block);

                        }
                        checkDestroyedCount();
                    }
                }
            }
        });
    }
    @Override
    public void onInit() {
    }

    @Override
    public void onPhysicsUpdate() {
        checkDestroyedCount();
        setPhysicsToBall();
        if (time - goldTime > 5000)
        {
            view.revgoldstatusimage(bball,root);
            isGoldStauts = false;
        }
        if (time - boostTime > 5000) {
            bball.setVeloX(1);
            bball.setVeloY(1);
            isFlashStatus = false;
        }
        if (isPaddleDisappeared && time - invisibleTime > 5000)
        {
            isPaddleDisappeared = true;
            view.showPaddle(root, model.getrect());
        }
        for (Bonus choco : chocos) {
            if (choco.y > sceneHeigt || choco.taken) {
                continue;
            }
            if (choco.y >= paddle.getY() && choco.y <= paddle.getY() + paddle.getHeight() && choco.x >= paddle.getX() && choco.x <= paddle.getX() + paddle.getWidth()) {
                view.showchoco(choco,root);
                score += 3;
            }
            model.chocodrop(choco,time);
        }
    }
    @Override
    public void onTime(long time) {
        this.time = time;
    }

    public void stopBackgroundSound() {
        if (bgsound != null) {
            bgsound.stop();
        }
    }


}