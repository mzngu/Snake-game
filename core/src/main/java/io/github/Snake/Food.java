package io.github.Snake;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import java.util.List;
import java.util.ArrayList;

import java.util.Random;

public class Food {
    private Tile position;
    private int boardWidth;
    private int boardHeight;
    private int tileSize = 25;
    private Random random;

    public Food(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth / tileSize;
        this.boardHeight = boardHeight / tileSize;
        random = new Random();
        position = new Tile(0, 0);
        relocate(null);
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(position.x * tileSize, position.y * tileSize, tileSize, tileSize);
        shapeRenderer.end();
    }

    public void relocate(Snake snake) {

        List<Tile> availablePositions = new ArrayList<>();

        for (int x = 0; x < boardWidth; x++) {
            for (int y = 0; y < boardHeight; y++) {
                boolean isOccupied = false;

                if (snake != null) {
                    for (Tile bodyPart : snake.getBody()) {
                        if (x == bodyPart.x && y == bodyPart.y) {
                            isOccupied = true;
                            break;
                        }
                    }
                }

                if (!isOccupied) {
                    availablePositions.add(new Tile(x, y));
                }
            }
        }

        if (!availablePositions.isEmpty()) {
            Tile randomPosition = availablePositions.get(random.nextInt(availablePositions.size()));
            position.set(randomPosition);
        }
        if (availablePositions.isEmpty()) {

            System.out.println("No available positions for food!");
        }
    }

    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < boardWidth && y >= 0 && y < boardHeight;
    }
}
