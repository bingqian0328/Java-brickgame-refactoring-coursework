package brickGame;

import java.io.Serializable;

/**
 * The {@code BlockSerializable} class represents a serializable version of a block in the brick game.
 * It is used for serialization and contains information about the block's position and type.
 * This class is intended for use in saving and loading game state.
 */
public class BlockSerializable implements Serializable {
    /**
     * The row index of the block.
     */
    public final int row;
    /**
     * The column index of the block.
     */
    public final int j;
    /**
     * The type of the block.
     */
    public final int type;


    /**
     * Constructs a {@code BlockSerializable} with the specified row, column, and type.
     *
     * @param row  The row index of the block.
     * @param j    The column index of the block.
     * @param type The type of the block.
     */
    public BlockSerializable(int row , int j , int type) {
        this.row = row;
        this.j = j;
        this.type = type;
    }
}
