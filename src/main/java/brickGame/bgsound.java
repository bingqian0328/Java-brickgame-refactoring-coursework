package brickGame;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * The {@code bgsound} class represents the background sound player in the Brick Game.
 * It provides methods for playing and stopping the background sound.
 */
public class bgsound {

    /**
     * The {@code MediaPlayer} for the game sound.
     */
    private MediaPlayer gamesound;

    /**
     * Constructs a new {@code bgsound} instance and loads the game theme song.
     */
    public bgsound (){
        gamesound = loadSound("gamethemesong.mp3");
    }


    private MediaPlayer loadSound(String fileName) {
        return new MediaPlayer(new Media(getClass().getResource("/" + fileName).toString()));
    }
    private MediaPlayer mediaPlayer;

    /**
     * Loads a sound file and returns a {@code MediaPlayer}.
     *
     * @param soundFile The name of the sound file.
     * @return A {@code MediaPlayer} for the specified sound file.
     */
    public bgsound(String soundFile) {
        // Load the media file
        String resourcePath = getClass().getResource(soundFile) != null
                ? getClass().getResource(soundFile).toString()
                : "Resource not found";

        System.out.println("Resource Path: " + resourcePath);

        Media sound = new Media(resourcePath);

        // Create a media player
        mediaPlayer = new MediaPlayer(sound);

        // Set the cycle count (MediaPlayer.INDEFINITE for infinite loop)
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    /**
     * Plays the background sound.
     */
    public void play() {
        gamesound.play();
    }


    /**
     * Stops the background sound.
     */
    public void stop() {
        gamesound.stop();
    }

}
