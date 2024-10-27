package io.github.Farm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

public class SoundManager implements Disposable {
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


    private Music footStep;
    private Music watering;
    private Sound hurt;
    private Sound shovel;
    private Sound pickUp;

    private Music inTroGame;
    private Music inTroGame1;
    private Music gameOver;
    private Music endGame;


    public SoundManager(){
        moveSound = Gdx.audio.newSound(Gdx.files.internal("soundgame/sound_movebuttonmenu.wav"));
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("soundgame/gamemusic.mp3"));
        rainSound = Gdx.audio.newMusic(Gdx.files.internal("soundgame/rain_2.mp3"));


        footStep = Gdx.audio.newMusic(Gdx.files.internal("soundgame/running-in-grass-6237.mp3"));
        watering =Gdx.audio.newMusic(Gdx.files.internal("soundgame/watering.mp3"));
        hurt = Gdx.audio.newSound(Gdx.files.internal("soundgame/hurt.mp3"));
        shovel=Gdx.audio.newSound(Gdx.files.internal("soundgame/shovel.mp3"));
        pickUp=Gdx.audio.newSound(Gdx.files.internal("soundgame/take-item-sound-effect.mp3"));

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
        gameMusic.setVolume(0.5f);
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
        rainSound.setVolume(0.1f);
        rainSound.play();
        rainSound.setVolume(0.3f);
    }
    public void stopRainSound(){
        rainSound.stop();
    }



    public void playFootStep() {
        if(footStep.isPlaying())return;
        footStep.play();
        footStep.setLooping(true);
        footStep.setVolume(5f);
    }
    public void stopFootStep() {
        footStep.stop();
    }
    public void playPickUp(){
        pickUp.play();
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



    @Override
    public void dispose(){
        moveSound.dispose();
        gameMusic.dispose();
        rainSound.dispose();
        footStep.dispose();
        hurt.dispose();
        shovel.dispose();
        watering.dispose();
        inTroGame.dispose();
        inTroGame1.dispose();
        gameOver.dispose();
        endGame.dispose();

    }

}


