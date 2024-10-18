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

    public SoundManager(){
        moveSound = Gdx.audio.newSound(Gdx.files.internal("soundgame/sound_movebuttonmenu.wav"));
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("soundgame/gamemusic.mp3"));
        rainSound = Gdx.audio.newMusic(Gdx.files.internal("soundgame/rain_2.mp3"));
        running = Gdx.audio.newSound(Gdx.files.internal("soundgame/running.wav"));
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

    public void dispose(){
        moveSound.dispose();
        gameMusic.dispose();
        rainSound.dispose();
    }

}


