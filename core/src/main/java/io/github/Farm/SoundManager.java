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
    private Music footStep;
    private Music watering;
    private Sound hurt;
    private Sound shovel;



    public SoundManager(){
        moveSound = Gdx.audio.newSound(Gdx.files.internal("soundgame/sound_movebuttonmenu.wav"));
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("soundgame/gamemusic.mp3"));
        rainSound = Gdx.audio.newMusic(Gdx.files.internal("soundgame/rain_2.mp3"));
        footStep = Gdx.audio.newMusic(Gdx.files.internal("soundgame/running-in-grass-6237.mp3"));
        watering =Gdx.audio.newMusic(Gdx.files.internal("soundgame/watering.mp3"));
        hurt = Gdx.audio.newSound(Gdx.files.internal("soundgame/hurt.mp3"));
        shovel=Gdx.audio.newSound(Gdx.files.internal("soundgame/shovel.mp3"));


    }

    public void playMoveSound(){
        moveSound.play(0.05f);
    }
    public void playGameMusic(){
        gameMusic.play();
        gameMusic.setLooping(true);
        gameMusic.setVolume(0f);
    }
    public void pauseGameMusic(){
        gameMusic.pause();
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

    


    public void dispose(){
        moveSound.dispose();
        gameMusic.dispose();
        rainSound.dispose();
        footStep.dispose();
        hurt.dispose();
        shovel.dispose();
        watering.dispose();
    }

}


