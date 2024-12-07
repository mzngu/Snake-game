package io.github.Snake;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Obstacle {
    private List<Tile> obstacles;
    private int boardWidth;
    private int boardHeight;
    private int tileSize = 25;

    public Obstacle(int boardWidth, int boardHeight, int numObstacles, Snake snake, Food food) {
        this.boardWidth = boardWidth / tileSize;
        this.boardHeight = boardHeight / tileSize;
        obstacles = new ArrayList<>();
        generateObstacles(numObstacles, snake, food);
    }

    private void generateObstacles(int numObstacles, Snake snake, Food food) {
        Random random = new Random();

        while (obstacles.size() < numObstacles) {
            int x = random.nextInt(boardWidth);
            int y = random.nextInt(boardHeight);
            Tile newObstacle = new Tile(x, y);

            boolean isValidPosition = true;
            for (Tile bodyPart : snake.getBody()) {
                if (bodyPart.x == x && bodyPart.y == y) {
                    isValidPosition = false;
                    break;
                }
            }

            if (isValidPosition &&
                (food.getX() != x || food.getY() != y) &&
                !obstacles.contains(newObstacle)) {
                obstacles.add(newObstacle);
            }
        }
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Tile obstacle : obstacles) {
            shapeRenderer.rect(obstacle.x * tileSize, obstacle.y * tileSize, tileSize, tileSize);
        }
        shapeRenderer.end();
    }

    public boolean checkCollision(Snake snake) {
        Tile head = snake.getBody().first();
        return obstacles.stream().anyMatch(obstacle ->
            obstacle.x == head.x && obstacle.y == head.y
        );
    }

    public List<Tile> getObstacles() {
        return obstacles;
    }
}
