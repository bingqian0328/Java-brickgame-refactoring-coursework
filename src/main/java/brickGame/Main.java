package brickGame;

public class Main{
    /**
     * The main method is the entry point of the Brick Game application.
     * It launches the JavaFX application by calling the launch method with the Controller class.
     *
     * @param args Command-line arguments (unused in this application).
     */
    public static void main(String[] args) {
        javafx.application.Application.launch(Controller.class, args);
    }
}
