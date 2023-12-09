package brickGame;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * The Score class is responsible for displaying scores and messages in the game.
 */
public class Score {
    private Controller controller;

    /**
     * Displays a score increment or decrement message on the game screen
     * The message is animated for a visual effect.
     *
     * @param x     The x-coordinate of the message.
     * @param y     The y-coordinate of the message.
     * @param score The score value to be displayed.
     * @param root  The root Pane on which the message will be displayed.
     */
    public void show(final double x, final double y, int score, Pane root) {
        String sign = (score >= 0) ? "+" : "";
        final Label label = new Label(sign + score);
        label.setTranslateX(x);
        label.setTranslateY(y);

        Platform.runLater(() -> root.getChildren().add(label));

        Timeline timeline = new Timeline();
        for (int i = 0; i < 21; i++) {
            KeyFrame keyFrame = new KeyFrame(Duration.millis(i * 15),
                    new KeyValue(label.scaleXProperty(), i),
                    new KeyValue(label.scaleYProperty(), i),
                    new KeyValue(label.opacityProperty(), (20 - i) / 20.0));
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
    }

    /**
     * Displays a message at a fixed position on the provided root Pane.
     * The message is animated for a visual effect.
     *
     * @param message The message to be displayed.
     * @param root    The root Pane on which the message will be displayed.
     */
    public void showMessage(String message, Pane root) {
        final Label label = new Label(message);
        label.setTranslateX(220);
        label.setTranslateY(340);

        Platform.runLater(() -> root.getChildren().add(label));

        Timeline timeline = new Timeline();
        for (int i = 0; i < 21; i++) {
            KeyFrame keyFrame = new KeyFrame(Duration.millis(i * 15),
                    new KeyValue(label.scaleXProperty(), Math.abs(i - 10)),
                    new KeyValue(label.scaleYProperty(), Math.abs(i - 10)),
                    new KeyValue(label.opacityProperty(), (20 - i) / 20.0));
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
    }


    /**
     * Displays a long-lasting message at a fixed position on the provided root Pane.
     * This is different from showMessage because it stays on the screen permanently until some keys are pressed
     * The message is animated for a visual effect.
     *
     * @param message The message to be displayed.
     * @param root    The root Pane on which the message will be displayed.
     */
    public void showMessage2(String message, Pane root) {
        final Label label = new Label(message);
        label.setTranslateX(220);
        label.setTranslateY(340);

        Platform.runLater(() -> root.getChildren().add(label));

        Timeline timeline = new Timeline();

        // Add a new key frame to keep the message visible after the initial animation
        KeyFrame finalKeyFrame = new KeyFrame(Duration.millis(10000),
                new KeyValue(label.scaleXProperty(), 1.0),
                new KeyValue(label.scaleYProperty(), 1.0),
                new KeyValue(label.opacityProperty(), 1.0));
        timeline.getKeyFrames().add(finalKeyFrame);

        timeline.play();
    }

    /**
     * Displays a game over message along with a restart button on the provided root Pane.
     * The message and button are added to the JavaFX application thread for proper UI updates.
     *
     * @param root       The root Pane on which the message and button will be displayed.
     * @param controller The Controller instance for handling the restart action.
     */
    public void showGameOver(Pane root, Controller controller) {
        Platform.runLater(() -> {
            Label label = new Label("Game Over :(");
            label.setTranslateX(200);
            label.setTranslateY(250);
            label.setScaleX(2);
            label.setScaleY(2);

            Button restart = new Button("Restart");
            restart.getStyleClass().add("load");
            restart.setTranslateX(220);
            restart.setTranslateY(300);
            restart.setOnAction(event -> controller.restartGame());

            root.getChildren().addAll(label, restart);
        });
    }

    /**
     * Displays a game win message along with a back to game menu button on the provided root Pane.
     * The message and button are added to the JavaFX application thread for proper UI updates.
     *
     * @param root       The root Pane on which the message and button will be displayed.
     * @param controller The Controller instance for handling the restart action.
     */
    public void showWin(Pane root, Controller controller) {
        Platform.runLater(() -> {
            Label label = new Label("You win!!");
            label.setTranslateX(200);
            label.setTranslateY(250);
            label.setScaleX(2);
            label.setScaleY(2);

            Button restart = new Button("Back to Game Menu");
            restart.getStyleClass().add("load");
            restart.setTranslateX(220);
            restart.setTranslateY(300);
            restart.setOnAction(event -> controller.restartGame());

            root.getChildren().addAll(label, restart);
        });
    }
}
