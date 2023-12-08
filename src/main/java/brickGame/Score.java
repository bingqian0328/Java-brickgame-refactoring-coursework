package brickGame;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public class Score {
    private Controller controller;
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
    public void showWin(Pane root, Controller controller) {
        Platform.runLater(() -> {
            Label label = new Label("You win!!");
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
}
