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
    /**
     * Sets the OnAction listener for the game engine.
     *
     * @param onAction The OnAction listener to be set.
     */
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

    /**
     * Starts the game engine, initializing and starting the update, physics, and time timelines.
     */
    public void start() {
        time = 0;
        initialize();
        createupdatetime();
        createphysicstime();
        createTimeTimeline();
        isStopped = false;
    }

    /**
     * Stops the game engine, halting the update, physics, and time timelines.
     */
    public void stop() {
        if (!isStopped) {
            isStopped = true;
            updatetime.stop();
            physicstime.stop();
            timeTimeline.stop();
        }
    }

    /**
     * Creates and starts the timeline responsible for updating the game logic at regular intervals.
     */
    private void createupdatetime() {
        updatetime = new Timeline(new KeyFrame(Duration.millis(fps), event -> {
            onAction.onUpdate();
        }));
        updatetime.setCycleCount(Animation.INDEFINITE);
        updatetime.play();
    }

    /**
     * Creates and starts the timeline responsible for updating physics-related logic at regular intervals.
     */
    private void createphysicstime() {
        physicstime = new Timeline(new KeyFrame(Duration.millis(fps), event -> {
            onAction.onPhysicsUpdate();
        }));
        physicstime.setCycleCount(Animation.INDEFINITE);
        physicstime.play();
    }

    /**
     * Creates and starts the timeline responsible for updating the game time at an interval of 1 millisecond.
     */
    private void createTimeTimeline() {
        timeTimeline = new Timeline(new KeyFrame(Duration.millis(1), event -> {
            time++;
            onAction.onTime(time);
        }));
        timeTimeline.setCycleCount(Animation.INDEFINITE);
        timeTimeline.play();
    }


    /**
     * Pauses the game engine, halting the update, physics, and time timelines.
     */
    public void pause() {
        if (!isPaused && !isStopped) {
            isPaused = true;
            updatetime.pause();
            physicstime.pause();
            timeTimeline.pause();
        }
    }

    /**
     * Resumes the game engine, continuing the update, physics, and time timelines.
     */
    public void resume() {
        if (isPaused && !isStopped) {
            isPaused = false;
            updatetime.play();
            physicstime.play();
            timeTimeline.play();
        }
    }

    /**
     * Interface for handling different actions in the game engine.
     */
    public interface OnAction {
        /**
         * Called on each frame update to handle game logic.
         */
        void onUpdate();

        /**
         * Called when initializing the game engine.
         */
        void onInit();

        /**
         * Called on each physics update to handle physics-related logic.
         */
        void onPhysicsUpdate();

        /**
         * Called on each time update, providing the current time in milliseconds.
         *
         * @param time The current time in milliseconds.
         */
        void onTime(long time);
    }
}
