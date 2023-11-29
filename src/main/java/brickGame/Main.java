package brickGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application implements EventHandler<KeyEvent>, GameEngine.OnAction {

    private int level = 0;

    private double xBreak = 0.0f;
    private double centerBreakX;
    private double yBreak = 640.0f;

    private int sceneWidth = 500;
    private int sceneHeigt = 700;

    private boolean checktransition = false ;
    private static int LEFT  = 1;
    private static int RIGHT = 2;
    private double xBall;
    private double yBall;

    private View view;

    private Model model;

    private Ball bball;
    private Paddle paddle;

    private boolean isGoldStauts      = false;
    private boolean isExistHeartBlock = false;
    private int destroyedBlockCount = 0;

    private double v = 1.000;

    private int  heart    = 1;
    private int  score    = 0;
    private long time     = 0;
    private long goldTime = 0;

    private Label countdownLabel;

    private GameEngine engine;
    public static String savePath    = "C:/save/save.mdds";
    public static String savePathDir = "C:/save/";

    private ArrayList<Block> blocks = new ArrayList<Block>();
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
    public  Pane             root;
    private Label            scoreLabel;
    private Label            heartLabel;
    private Label            levelLabel;

    private boolean loadFromSave = false;

    Stage  primaryStage;
    Button load    = null;
    Button newGame = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        // Create an instance of the Model
        view = new View();
        model = new Model();
        model.setBall(view.createBall());
        model.setRect(view.createrect());
        startgame();

        root = new Pane();
        view.backgrdimg(root);

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
        Scene scene = new Scene(root, sceneWidth, sceneHeigt);
        scene.getStylesheets().add("style.css");
        scene.setOnKeyPressed(this);

        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        if (loadFromSave == false) {
            if (level > 1 && level < 18) {
                load.setVisible(false);
                newGame.setVisible(false);
                engine = new GameEngine();
                engine.setOnAction(this);
                engine.setFps(120);
                engine.start();
            }

            load.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    loadGame();

                    load.setVisible(false);
                    newGame.setVisible(false);
                }
            });

            newGame.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    engine = new GameEngine();
                    engine.setOnAction(Main.this);
                    engine.setFps(120);
                    engine.start();

                    load.setVisible(false);
                    newGame.setVisible(false);
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
    public static void main(String[] args) {
        launch(args);
    }

    private void startgame() {
        if (loadFromSave == false) {
            level++;
            if (level > 1) {
                new Score().showMessage("Level Up :)", this);
            }
            if (level == 18) {
                new Score().showWin(this);
                return;
            }
            bball= model.initBall();
            paddle = model.initBreak();
            model.initBoard(blocks,level,isExistHeartBlock);

            load = new Button("Load Game");
            newGame = new Button("Start New Game");
            load.setTranslateX(220);
            load.setTranslateY(300);
            newGame.setTranslateX(220);
            newGame.setTranslateY(340);

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
            case DOWN:
                //setPhysicsToBall();
                break;
            case S:
                saveGame();
                break;
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
    private double vY = 1.000;


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
            new Score().show(sceneWidth / 2, sceneHeigt / 2, -1, root);

            if (heart == 0) {
                new Score().showGameOver(this);
                engine.stop();
            }
        }
    }

    private void checkDestroyedCount() {
        if (destroyedBlockCount == blocks.size()) {
            nextLevel();
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

                    outputStream.writeDouble(xBall);
                    outputStream.writeDouble(yBall);
                    outputStream.writeDouble(xBreak);
                    outputStream.writeDouble(yBreak);
                    outputStream.writeDouble(centerBreakX);
                    outputStream.writeLong(time);
                    outputStream.writeLong(goldTime);
                    outputStream.writeDouble(vX);

                    outputStream.writeBoolean(isExistHeartBlock);
                    outputStream.writeBoolean(isGoldStauts);
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

                    new Score().showMessage("Game Saved", Main.this);
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
        blocks = model.getBlocks();
        chocos = model.getChocos();
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
                scoreLabel.setText("Score: " + score);
                heartLabel.setText("Heart : " + heart);
                chocos = model.getChocos();
                model.updateLabels(bball, paddle);

                if (bball.getYb() >= Block.getPaddingTop() && bball.getYb() <= (Block.getHeight() * (level + 1)) + Block.getPaddingTop()) {
                    for (final Block block : blocks) {
                        int hitCode = block.checkHitToBlock(bball.getXb(), bball.getYb(),goRightBall,goDownBall);
                        if (hitCode != Block.NO_HIT) {
                            score += 1;

                            new Score().show(block.x, block.y, 1, root);

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
                                model.getBall().setFill(new ImagePattern(new Image("goldball.png")));
                                System.out.println("gold ball");
                                root.getStyleClass().add("goldRoot");
                                isGoldStauts = true;
                            }

                            if (block.type == Block.BLOCK_HEART) {
                                heart++;
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
            view.goldstatusimage(bball,root);
            isGoldStauts = false;
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
}
