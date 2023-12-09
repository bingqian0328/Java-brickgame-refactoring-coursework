package brickGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.Random;

/**
 * The {@code Bonus} class represents a bonus object in the brick game.
 * It is used to create and manage bonus items that can be collected during gameplay.
 */

public class Bonus implements Serializable {
    /**
     * The rectangle representing the bonus choco item.
     */
    public Rectangle choco;

    /**
     * The x-coordinate of the bonus item.
     */
    public double x;

    /**
     * The y-coordinate of the bonus item.
     */

    public double y;

    /**
     * The time at which the bonus item was created.
     */
    public long timeCreated;
    /**
     * A flag indicating whether the bonus item has been taken by the player.
     */
    public boolean taken = false;


    /**
     * Constructs a {@code Bonus} object at the specified row and column.
     *
     * @param row    The row index of the bonus item.
     * @param column The column index of the bonus item.
     */
    public Bonus(int row, int column) {
        x = (column * (Block.getWidth())) + Block.getPaddingH() + (Block.getWidth() / 2) - 15;
        y = (row * (Block.getHeight())) + Block.getPaddingTop() + (Block.getHeight() / 2) - 15;

        draw();
    }
    /**
     * Draws and initializes the graphical representation of the bonus item.
     */
    private void draw() {
        choco = new Rectangle();
        choco.setWidth(30);
        choco.setHeight(30);
        choco.setX(x);
        choco.setY(y);

        String url;
        if (new Random().nextInt(20) % 2 == 0) {
            url = "bonus1.png";
        } else {
            url = "bonus2.png";
        }

        choco.setFill(new ImagePattern(new Image(url)));
    }



}
