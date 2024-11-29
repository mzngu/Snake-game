package io.github.Snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
    private Sound eatSound;
    private Sound gameOverSound;
    private Music backgroundMusic;
    private boolean soundEnabled = true;
    private boolean musicEnabled = true;
    private float soundVolume = 0.5f;
    private float musicVolume = 0.3f;

    public SoundManager() {
        try {

            eatSound = Gdx.audio.newSound(Gdx.files.internal("sounds/eat.wav"));
            gameOverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/gameover.wav"));

            backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/background.mp3"));
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(musicVolume);

        } catch (Exception e) {
            System.err.println("Error loading sound files: " + e.getMessage());
        }
    }

    public void playEatSound() {
        if (soundEnabled && eatSound != null) {
            eatSound.play(soundVolume);
        }
    }

    public void playGameOverSound() {
        if (soundEnabled && gameOverSound != null) {
            gameOverSound.play(soundVolume);
        }
    }

    public void startBackgroundMusic() {
        if (musicEnabled && backgroundMusic != null && !backgroundMusic.isPlaying()) {
            backgroundMusic.play();
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isPlaying()) {
            backgroundMusic.stop();
        }
    }

    public void pauseBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isPlaying()) {
            backgroundMusic.pause();
        }
    }

    public void resumeBackgroundMusic() {
        if (musicEnabled && backgroundMusic != null && !backgroundMusic.isPlaying()) {
            backgroundMusic.play();
        }
    }

    public void toggleSound() {
        soundEnabled = !soundEnabled;
    }

    public void toggleMusic() {
        musicEnabled = !musicEnabled;
        if (musicEnabled) {
            startBackgroundMusic();
        } else {
            stopBackgroundMusic();
        }
    }

    public void dispose() {
        if (eatSound != null) eatSound.dispose();
        if (gameOverSound != null) gameOverSound.dispose();
        if (backgroundMusic != null) backgroundMusic.dispose();
    }
}
