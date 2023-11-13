package brickGame;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Score {
    public void show(final double x, final double y, int score, final Main main) {
        String sign = (score >= 0) ? "+" : "";
        final Label label = new Label(sign + score);
        label.setTranslateX(x);
        label.setTranslateY(y);

        Platform.runLater(() -> main.root.getChildren().add(label));

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

    public void showMessage(String message, final Main main) {
        final Label label = new Label(message);
        label.setTranslateX(220);
        label.setTranslateY(340);

        Platform.runLater(() -> main.root.getChildren().add(label));

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

    public void showGameOver(final Main main) {
        Platform.runLater(() -> {
            Label label = new Label("Game Over :(");
            label.setTranslateX(200);
            label.setTranslateY(250);
            label.setScaleX(2);
            label.setScaleY(2);

            Button restart = new Button("Restart");
            restart.setTranslateX(220);
            restart.setTranslateY(300);
            restart.setOnAction(event -> main.restartGame());

            main.root.getChildren().addAll(label, restart);
        });
    }

    public void showWin(final Main main) {
        Platform.runLater(() -> {
            Label label = new Label("You Win :)");
            label.setTranslateX(200);
            label.setTranslateY(250);
            label.setScaleX(2);
            label.setScaleY(2);

            main.root.getChildren().addAll(label);
        });
    }
}
