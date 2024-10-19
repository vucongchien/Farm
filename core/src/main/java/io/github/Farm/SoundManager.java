package io.github.Farm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;

public class SoundManager {
    private static SoundManager instance;
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }
    private Sound moveSound;
    private Music gameMusic;
    private Music rainSound;
    private Sound running;
    private Music inTroGame;
    private Music inTroGame1;
    private Music gameOver;
    private Music endGame;


    public SoundManager(){
        moveSound = Gdx.audio.newSound(Gdx.files.internal("soundgame/sound_movebuttonmenu.wav"));
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("soundgame/gamemusic.mp3"));
        rainSound = Gdx.audio.newMusic(Gdx.files.internal("soundgame/rain_2.mp3"));
        running = Gdx.audio.newSound(Gdx.files.internal("soundgame/running.wav"));
        inTroGame = Gdx.audio.newMusic(Gdx.files.internal("soundgame/introGAME.mp3"));
        inTroGame1 = Gdx.audio.newMusic(Gdx.files.internal("soundgame/introGAME1.mp3"));
        gameOver = Gdx.audio.newMusic(Gdx.files.internal("soundgame/game_over_1.mp3"));
        endGame = Gdx.audio.newMusic(Gdx.files.internal("soundgame/dragonballGT.mp3"));
    }

    public void playMoveSound(){
        moveSound.play(0.05f);
    }
    public void playGameMusic(){
        gameMusic.play();
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.3f);
    }
    public void pauseGameMusic(){
        gameMusic.pause();
    }
    public void stopGameMusic(){
        gameMusic.stop();
    }
    public boolean isGameMusicPlaying(){
        return gameMusic.isPlaying();
    }
    public void playRainSound(){
        rainSound.play();
        rainSound.setVolume(0.3f);
    }
    public void stopRainSound(){
        rainSound.stop();
    }
    public void playRunningSound(){
        running.play();
    }
    public void pauseRunningSound(){
        running.pause();
    }
    public void playInTroGame(){
        inTroGame.play();
        inTroGame.setVolume(0.3f);
    }
    public void stopInTroGame(){
        inTroGame.stop();
    }
    public void playInTroGame1(){
        inTroGame1.play();
        inTroGame1.setVolume(0.4f);
    }
    public void stopInTroGame1(){
        inTroGame1.stop();
    }
    public boolean isInTroGamePlaying() {
        return inTroGame.isPlaying();
    }

    public boolean isInTroGame1Playing() {
        return inTroGame1.isPlaying();
    }
    public void playGameOver(){
        gameOver.play();
        gameOver.setVolume(2.5f);
    }
    public void playEndGame(){
        endGame.play();
        endGame.setVolume(1.5f);
    }

    public void dispose(){
        moveSound.dispose();
        gameMusic.dispose();
        rainSound.dispose();
        running.dispose();
        inTroGame.dispose();
        inTroGame1.dispose();
        gameOver.dispose();
        endGame.dispose();

    }

}


