package brickGame;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameEngine {

    private OnAction onAction;
    private int fps = 15;
    private Timeline updatetime;
    private Timeline physicstime;
    private Timeline timeTimeline;
    public boolean isStopped = true;
    private long time = 0;

    private boolean isPaused = false;

    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    /**
     * @param fps set fps and we convert it to millisecond
     */
    public void setFps(int fps) {
        this.fps = (int) 1000 / fps;
    }

    private void initialize() {
        onAction.onInit();
    }

    public void start() {
        time = 0;
        initialize();
        createupdatetime();
        createphysicstime();
        createTimeTimeline();
        isStopped = false;
    }

    public void stop() {
        if (!isStopped) {
            isStopped = true;
            updatetime.stop();
            physicstime.stop();
            timeTimeline.stop();
        }
    }

    private void createupdatetime() {
        updatetime = new Timeline(new KeyFrame(Duration.millis(fps), event -> {
            onAction.onUpdate();
        }));
        updatetime.setCycleCount(Animation.INDEFINITE);
        updatetime.play();
    }

    private void createphysicstime() {
        physicstime = new Timeline(new KeyFrame(Duration.millis(fps), event -> {
            onAction.onPhysicsUpdate();
        }));
        physicstime.setCycleCount(Animation.INDEFINITE);
        physicstime.play();
    }

    private void createTimeTimeline() {
        timeTimeline = new Timeline(new KeyFrame(Duration.millis(1), event -> {
            time++;
            onAction.onTime(time);
        }));
        timeTimeline.setCycleCount(Animation.INDEFINITE);
        timeTimeline.play();
    }

    public void pause() {
        if (!isPaused && !isStopped) {
            isPaused = true;
            updatetime.pause();
            physicstime.pause();
            timeTimeline.pause();
        }
    }

    public void resume() {
        if (isPaused && !isStopped) {
            isPaused = false;
            updatetime.play();
            physicstime.play();
            timeTimeline.play();
        }
    }


    public interface OnAction {
        void onUpdate();

        void onInit();

        void onPhysicsUpdate();

        void onTime(long time);
    }
}
