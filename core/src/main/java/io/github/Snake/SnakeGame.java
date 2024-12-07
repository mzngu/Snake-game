package io.github.Snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class SnakeGame {
    private Snake snake;
    private Food food;
    private int boardWidth = 600;
    private int boardHeight = 600;

    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private BitmapFont hoverFont;

    private float timer = 0f;
    private float moveInterval = 0.2f;

    private GameState gameState = GameState.MENU;
    private int score = 0;

    private boolean hasBorders = false;
    private SoundManager soundManager;

    private Texture menuBackground;
    private Texture pauseBackground;
    private Texture level1Background;
    private Texture level2Background;
    private Texture level3Background;
    private Texture gameOverBackground;
    private Texture highScoreBackground;


    private Rectangle level1Button;
    private Rectangle level2Button;
    private Rectangle level3Button;
    private Rectangle musicButton;
    private Rectangle soundButton;
    private Rectangle menuButton;
    private Rectangle continueButton;
    private Rectangle restartButton;
    private Rectangle gameOverMenuButton;
    private Rectangle highScoreButton;
    private Rectangle highScoreBackButton;
    private Obstacle obstacle;
    private boolean isLevel3 = false;
    private HighScoreManager highScoreManager;
    private boolean isHighScoreScreen = false;


    public void create() {
        snake = new Snake(boardWidth, boardHeight);
        food = new Food(boardWidth, boardHeight);

        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        hoverFont = new BitmapFont();
        hoverFont.setColor(Color.YELLOW);
        hoverFont.getData().setScale(1.2f);

        menuBackground = new Texture(Gdx.files.internal("backgrounds/menu.png"));
        pauseBackground = new Texture(Gdx.files.internal("backgrounds/pause.png"));
        level1Background = new Texture(Gdx.files.internal("backgrounds/level1.png"));
        level2Background = new Texture(Gdx.files.internal("backgrounds/level2.png"));
        level3Background = new Texture(Gdx.files.internal("backgrounds/level3.png"));
        gameOverBackground = new Texture(Gdx.files.internal("backgrounds/gameover.png"));

        level1Button = new Rectangle(250, 330, 150, 40);
        level2Button = new Rectangle(250, 280, 150, 40);
        level3Button = new Rectangle(250, 180, 150, 40);
        musicButton = new Rectangle(250, 230, 150, 40);
        soundButton = new Rectangle(250, 180, 150, 40);
        highScoreButton = new Rectangle(250, 80, 150, 40);

        continueButton = new Rectangle(200, 330, 200, 40);
        restartButton = new Rectangle(200, 280, 200, 40);
        menuButton = new Rectangle(200, 230, 200, 40);

        highScoreManager = new HighScoreManager();
        highScoreBackground = new Texture(Gdx.files.internal("backgrounds/highscore.png"));

        gameOverMenuButton = new Rectangle(200, 280, 200, 40);

        soundManager = new SoundManager();
        soundManager.startBackgroundMusic();
    }

    private void drawPauseMenu() {
        spriteBatch.begin();
        spriteBatch.draw(pauseBackground, 0, 0, boardWidth, boardHeight);
        boolean continueHover = continueButton.contains(Gdx.input.getX(), boardHeight - Gdx.input.getY());
        boolean restartHover = restartButton.contains(Gdx.input.getX(), boardHeight - Gdx.input.getY());
        boolean menuHover = menuButton.contains(Gdx.input.getX(), boardHeight - Gdx.input.getY());

        (continueHover ? hoverFont : font).draw(spriteBatch, "Press C to Continue", 200, 350);
        (restartHover ? hoverFont : font).draw(spriteBatch, "Press R to Restart", 200, 300);
        (menuHover ? hoverFont : font).draw(spriteBatch, "Press M to Return to Menu", 200, 250);
        spriteBatch.end();

        if (Gdx.input.justTouched()) {
            int y = boardHeight - Gdx.input.getY();
            int x = Gdx.input.getX();

            if (continueButton.contains(x, y)) {
                gameState = GameState.PLAYING;
                soundManager.resumeBackgroundMusic();
            } else if (restartButton.contains(x, y)) {
                resetGame();
                gameState = GameState.PLAYING;
                soundManager.startBackgroundMusic();
            } else if (menuButton.contains(x, y)) {
                gameState = GameState.MENU;
                resetGame();
                soundManager.stopBackgroundMusic();
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            gameState = GameState.PLAYING;
            soundManager.resumeBackgroundMusic();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            resetGame();
            gameState = GameState.PLAYING;
            soundManager.startBackgroundMusic();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            gameState = GameState.MENU;
            resetGame();
            soundManager.stopBackgroundMusic();
        }
    }

    public void render() {
        switch (gameState) {
            case MENU -> drawMenu();
            case PLAYING -> playGame();
            case PAUSED -> drawPauseMenu();
            case GAME_OVER -> drawGameOver();
            case HIGH_SCORE -> drawHighScoreScreen();
        }
    }

    private void drawMenu() {
        spriteBatch.begin();
        spriteBatch.draw(menuBackground, 0, 0, boardWidth, boardHeight);


        level1Button = new Rectangle(250, 330, 150, 40);
        level2Button = new Rectangle(250, 280, 150, 40);
        level3Button = new Rectangle(250, 230, 150, 40);
        musicButton = new Rectangle(250, 180, 150, 40);
        soundButton = new Rectangle(250, 130, 150, 40);

        boolean level1Hover = level1Button.contains(Gdx.input.getX(), boardHeight - Gdx.input.getY());
        boolean level2Hover = level2Button.contains(Gdx.input.getX(), boardHeight - Gdx.input.getY());
        boolean level3Hover = level3Button.contains(Gdx.input.getX(), boardHeight - Gdx.input.getY());
        boolean musicHover = musicButton.contains(Gdx.input.getX(), boardHeight - Gdx.input.getY());
        boolean soundHover = soundButton.contains(Gdx.input.getX(), boardHeight - Gdx.input.getY());
        boolean highScoreHover = highScoreButton.contains(Gdx.input.getX(), boardHeight - Gdx.input.getY());

        font.draw(spriteBatch, "SNAKE GAME", 250, 400);
        (level1Hover ? hoverFont : font).draw(spriteBatch, "Click Level 1", 250, 350);
        (level2Hover ? hoverFont : font).draw(spriteBatch, "Click Level 2", 250, 300);
        (level3Hover ? hoverFont : font).draw(spriteBatch, "Click Level 3", 250, 250);
        (musicHover ? hoverFont : font).draw(spriteBatch, "Press M to toggle music", 250, 200);
        (soundHover ? hoverFont : font).draw(spriteBatch, "Press S to toggle sound effects", 250, 150);
        (highScoreHover ? hoverFont : font).draw(spriteBatch, "High Scores", 250, 100);

        spriteBatch.end();

        handleMenuInput();
    }

    private void handleMenuInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            soundManager.toggleMusic();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            soundManager.toggleSound();
        }


        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();


            if (mouseX >= 250 && mouseX <= 400 && mouseY >= 330 && mouseY <= 370) {
                startLevel1();
            }

            else if (mouseX >= 250 && mouseX <= 400 && mouseY >= 280 && mouseY <= 320) {
                startLevel2();
            }
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (level1Button.contains(mouseX, mouseY)) {
                startLevel1();
            } else if (level2Button.contains(mouseX, mouseY)) {
                startLevel2();
            } else if (level3Button.contains(mouseX, mouseY)) {
                startLevel3();
            }
            if (highScoreButton.contains(mouseX, mouseY)) {
                gameState = GameState.HIGH_SCORE;
            }
        }

    }

    private void playGame() {
        handleInput();

        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            gameState = GameState.PAUSED;
            soundManager.pauseBackgroundMusic();
            return;
        }


        if (score == 20) {
            moveInterval = 0.15f;
        }

        timer += Gdx.graphics.getDeltaTime();
        if (timer >= moveInterval) {
            timer -= moveInterval;
            try {
                snake.moveSnake(hasBorders);

                if (snake.collidesWith(food)) {
                    soundManager.playEatSound();
                    score += 2;
                    snake.grow();
                    food.relocate(snake);
                }

                if (isLevel3 && obstacle.checkCollision(snake)) {
                    throw new RuntimeException("Game Over: Snake hit an obstacle!");
                }
            } catch (RuntimeException e) {
                handleGameOver();
                return;
            }
        }

        spriteBatch.begin();
        spriteBatch.draw(
            isLevel3 ? level3Background :
                (hasBorders ? level2Background : level1Background),
            0, 0, boardWidth, boardHeight
        );
        spriteBatch.end();

        snake.draw(shapeRenderer);
        food.draw(shapeRenderer);

        if (isLevel3) {
            obstacle.draw(shapeRenderer);
        }

        drawScore();
    }

    private void drawScore() {
        spriteBatch.begin();
        font.draw(spriteBatch, "Score: " + score, 10, 590);
        spriteBatch.end();
    }

    private void drawGameOver() {
        spriteBatch.begin();
        spriteBatch.draw(gameOverBackground, 0, 0, boardWidth, boardHeight);

        boolean menuHover = gameOverMenuButton.contains(Gdx.input.getX(), boardHeight - Gdx.input.getY());

        font.draw(spriteBatch, "Game Over", 250, 400);
        font.draw(spriteBatch, "Score: " + score, 250, 350);
        (menuHover ? hoverFont : font).draw(spriteBatch, "Return to Menu", 200, 300);

        spriteBatch.end();

        if (Gdx.input.justTouched()) {
            int y = boardHeight - Gdx.input.getY();
            int x = Gdx.input.getX();

            if (gameOverMenuButton.contains(x, y)) {
                gameState = GameState.MENU;
                resetGame();
                soundManager.stopBackgroundMusic();
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            gameState = GameState.MENU;
            resetGame();
            soundManager.stopBackgroundMusic();
        }
    }

    private void handleGameOver() {
        highScoreManager.updateHighScore(score);

        soundManager.playGameOverSound();
        soundManager.pauseBackgroundMusic();
        gameState = GameState.GAME_OVER;
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && snake.getDirectionY() != -1) {
            snake.changeDirection(0, 1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && snake.getDirectionY() != 1) {
            snake.changeDirection(0, -1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && snake.getDirectionX() != 1) {
            snake.changeDirection(-1, 0);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && snake.getDirectionX() != -1) {
            snake.changeDirection(1, 0);
        }
    }

    private void startLevel1() {
        resetGame();
        hasBorders = false;
        gameState = GameState.PLAYING;
        soundManager.startBackgroundMusic();
    }

    private void startLevel2() {
        resetGame();
        hasBorders = true;
        gameState = GameState.PLAYING;
        soundManager.startBackgroundMusic();
    }

    private void startLevel3() {
        resetGame();
        hasBorders = true;
        isLevel3 = true;
        obstacle = new Obstacle(boardWidth, boardHeight, 10, snake, food);
        gameState = GameState.PLAYING;
        soundManager.startBackgroundMusic();
    }

    private void resetGame() {
        snake = new Snake(boardWidth, boardHeight);
        food = new Food(boardWidth, boardHeight);
        score = 0;
        moveInterval = 0.2f;
        timer = 0f;
        isLevel3 = false;
        obstacle = null;
        hasBorders = false;
    }

    private void drawHighScoreScreen() {
        spriteBatch.begin();
        spriteBatch.draw(highScoreBackground, 0, 0, boardWidth, boardHeight);

        highScoreBackButton = new Rectangle(200, 230, 200, 40);
        boolean backHover = highScoreBackButton.contains(Gdx.input.getX(), boardHeight - Gdx.input.getY());

        font.draw(spriteBatch, "High Score", 250, 400);
        font.draw(spriteBatch, "Highest Score: " + highScoreManager.getHighScore(), 250, 350);
        (backHover ? hoverFont : font).draw(spriteBatch, "Back to Menu", 200, 250);

        spriteBatch.end();

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (highScoreBackButton.contains(mouseX, mouseY)) {
                gameState = GameState.MENU;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gameState = GameState.MENU;
        }
    }

    public void dispose() {
        shapeRenderer.dispose();
        spriteBatch.dispose();
        font.dispose();
        hoverFont.dispose();
        soundManager.dispose();
        menuBackground.dispose();
        pauseBackground.dispose();
        level1Background.dispose();
        level2Background.dispose();
        level3Background.dispose();
        gameOverBackground.dispose();
    }
}

