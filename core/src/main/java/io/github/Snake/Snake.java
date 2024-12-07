package io.github.Snake;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class Snake {
    private Array<Tile> body;
    private int directionX = 0, directionY = 1;
    private int tileSize = 25;
    private int boardWidth, boardHeight;
    private boolean growing = false;

    public Snake(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth / tileSize;
        this.boardHeight = boardHeight / tileSize;
        body = new Array<>();
        body.add(new Tile(this.boardWidth / 2, this.boardHeight / 2));
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Tile tile : body) {
            shapeRenderer.rect(tile.x * tileSize, tile.y * tileSize, tileSize, tileSize);
        }
        shapeRenderer.end();
    }

    public void moveSnake(boolean hasBorders) {
        Tile head = body.first();
        Tile newHead = new Tile(head.x + directionX, head.y + directionY);

        if (hasBorders) {

            if (newHead.x < 0 || newHead.x >= boardWidth || newHead.y < 0 || newHead.y >= boardHeight) {
                throw new RuntimeException("Game Over: Snake hit the boundary!");
            }
        } else {

            if (newHead.x < 0) newHead.x = boardWidth - 1;
            if (newHead.x >= boardWidth) newHead.x = 0;
            if (newHead.y < 0) newHead.y = boardHeight - 1;
            if (newHead.y >= boardHeight) newHead.y = 0;
        }

        for (Tile tile : body) {
            if (newHead.equals(tile)) {
                throw new RuntimeException("Game Over: Snake collided with itself!");
            }
        }

        body.insert(0, newHead);

        if (!growing) {
            body.pop();
        } else {
            growing = false;
        }
    }

    public Array<Tile> getBody() {
        return body;
    }

    public void grow() {
        growing = true;
    }

    public void changeDirection(int dx, int dy) {

        if (directionX == -dx && directionY == -dy) {
            return;
        }

        this.directionX = dx;
        this.directionY = dy;
    }


    public boolean collidesWith(Food food) {
        Tile head = body.first();
        return head.x == food.getX() && head.y == food.getY();
    }

    public int getDirectionX() {
        return directionX;
    }

    public int getDirectionY() {
        return directionY;
    }
}

