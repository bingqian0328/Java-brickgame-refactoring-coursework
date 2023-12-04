package brickGame;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class bgsound {


    private MediaPlayer gamesound;
    public bgsound (){
        gamesound = loadSound("gamethemesong.mp3");
    }

    private MediaPlayer loadSound(String fileName) {
        return new MediaPlayer(new Media(getClass().getResource("/" + fileName).toString()));
    }
    private MediaPlayer mediaPlayer;

    public bgsound(String soundFile) {
        // Load the media file
        String resourcePath = getClass().getResource(soundFile) != null
                ? getClass().getResource(soundFile).toString()
                : "Resource not found";

        System.out.println("Resource Path: " + resourcePath);

        Media sound = new Media(resourcePath);

        // Create a media player
        mediaPlayer = new MediaPlayer(sound);

        // Set the volume (0.0 to 1.0)
        mediaPlayer.setVolume(0);

        // Set the cycle count (MediaPlayer.INDEFINITE for infinite loop)
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public void play() {
        gamesound.play();
    }



    public void stop() {
        gamesound.stop();
    }

}
