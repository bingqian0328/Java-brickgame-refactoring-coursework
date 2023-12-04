package brickGame;

import java.io.*;
import java.util.ArrayList;

public class LoadSave {
    public boolean isExistHeartBlock;
    public boolean isGoldStauts;
    public boolean goDownBall;
    public boolean goRightBall;
    public boolean colideToBreak;
    public boolean colideToBreakAndMoveToRight;
    public boolean colideToRightWall;
    public boolean colideToLeftWall;
    public boolean colideToRightBlock;
    public boolean colideToBottomBlock;
    public boolean colideToLeftBlock;
    public boolean colideToTopBlock;

    public boolean isFlashStatus;

    public boolean isPaddleDisappeared;
    public int level;
    public int score;
    public int heart;
    public int destroyedBlockCount;

    public int initialblockcount;
    public double xBall;
    public double yBall;
    public double xBreak;
    public double yBreak;
    public double centerBreakX;
    public long time;
    public long goldTime;
    public double vX;

    public long boostTime = 0;

    public long invisibleTime = 0;
    public ArrayList<BlockSerializable> blocks = new ArrayList<BlockSerializable>();

    public void read() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(Controller.savePath)))) {
            level = inputStream.readInt();
            score = inputStream.readInt();
            heart = inputStream.readInt();

            xBall = inputStream.readDouble();
            yBall = inputStream.readDouble();
            xBreak = inputStream.readDouble();
            yBreak = inputStream.readDouble();
            centerBreakX = inputStream.readDouble();
            time = inputStream.readLong();
            goldTime = inputStream.readLong();
            boostTime = inputStream.readLong();
            invisibleTime = inputStream.readLong();
            vX = inputStream.readDouble();

            isExistHeartBlock = inputStream.readBoolean();
            isGoldStauts = inputStream.readBoolean();
            goDownBall = inputStream.readBoolean();
            goRightBall = inputStream.readBoolean();
            colideToBreak = inputStream.readBoolean();
            colideToBreakAndMoveToRight = inputStream.readBoolean();
            colideToRightWall = inputStream.readBoolean();
            colideToLeftWall = inputStream.readBoolean();
            colideToRightBlock = inputStream.readBoolean();
            colideToBottomBlock = inputStream.readBoolean();
            colideToLeftBlock = inputStream.readBoolean();
            colideToTopBlock = inputStream.readBoolean();
            isPaddleDisappeared = inputStream.readBoolean();
            isFlashStatus = inputStream.readBoolean();

            destroyedBlockCount = inputStream.readInt();

            try {
                blocks = (ArrayList<BlockSerializable>) inputStream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (EOFException e) {
            // Handle EOFException (End of File)
            e.printStackTrace();
            System.err.println("Error: EOFException - The file might be empty or corrupted.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error: IOException - Unable to read from the file.");
        }
    }
}