package io.github.Snake;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class HighScoreManager {
    private static final String HIGH_SCORE_FILE = "highscore.dat";
    private int highScore = 0;

    public HighScoreManager() {
        loadHighScore();
    }

    public void updateHighScore(int newScore) {
        if (newScore > highScore) {
            highScore = newScore;
            saveHighScore();
        }
    }

    public int getHighScore() {
        return highScore;
    }

    private void saveHighScore() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(HIGH_SCORE_FILE))) {
            dos.writeInt(highScore);
        } catch (IOException e) {
            System.err.println("Error saving high score: " + e.getMessage());
        }
    }

    private void loadHighScore() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(HIGH_SCORE_FILE))) {
            highScore = dis.readInt();
        } catch (IOException e) {
            highScore = 0;
        }
    }
}
