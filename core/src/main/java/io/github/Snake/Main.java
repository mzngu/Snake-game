package io.github.Snake;

import com.badlogic.gdx.ApplicationAdapter;

public class Main extends ApplicationAdapter {
    private SnakeGame snakeGame;

    @Override
    public void create() {
        snakeGame = new SnakeGame();
        snakeGame.create();
    }

    @Override
    public void render() {
        snakeGame.render();
    }

     @Override
    public void dispose() {
        snakeGame.dispose();
    }
}
