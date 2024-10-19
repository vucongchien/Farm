package io.github.Farm.ui;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;

public class IntroGame {
    private static IntroGame instance;
    public static IntroGame getInstance() {
        if (instance == null){
            instance = new IntroGame();

        }
        return instance;
    }

    private Animation<TextureRegion> backgroundAnimation;
    private boolean isIntro;
    private float elapsedTime;
    private BitmapFont font;


    public IntroGame() {
        // Tạo một mảng để lưu các frame
        Array<TextureRegion> frames = new Array<>();

        // Giả sử bạn đã tách GIF thành các file ảnh với tên định dạng như "frame_00_delay-0.1s.png", "frame_01_delay-0.1s.png",...
        int totalFrames = 173; // Số lượng frame mà bạn có (có thể thay đổi tùy theo số frame bạn tách từ GIF)

        for (int i = 12; i <= totalFrames; i++) {

            String fileName = String.format("ui1/microsoft_cinematic_teaser (1080p)_000/microsoft_cinematic_teaser (1080p)_%03d.png", i);

            Texture frameTexture = new Texture(Gdx.files.internal(fileName));
            TextureRegion frameRegion = new TextureRegion(frameTexture);
            frames.add(frameRegion);
        }
        // Tạo animation với tốc độ 10 frame mỗi giây (có thể điều chỉnh)
        backgroundAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
        isIntro = false;
        elapsedTime = 0;

        font = new BitmapFont();
        font.setColor(Color.WHITE);
    }

    public void render(SpriteBatch batch) {
        if (isIntro) {
            elapsedTime += Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = backgroundAnimation.getKeyFrame(elapsedTime);
            batch.begin();
            batch.draw(currentFrame, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            // Vẽ chữ "Press Enter to Skip" ở dưới cùng màn hình
            String message = "Press Enter to Skip";
            float x = (Gdx.graphics.getWidth() - message.length()) / 2; // Căn giữa màn hình
            float y = 50; // Vị trí cách dưới cùng màn hình 50 pixels
            font.draw(batch, message, x, y);
            batch.end();
        }
    }
    public boolean isIntro() {
        return isIntro;
    }

    public void setIntro(boolean isIntro) {
        this.isIntro = isIntro;
        if (isIntro) {
            elapsedTime = 0; // Reset elapsedTime khi bắt đầu intro
        }
    }
    public float getElapsedTime() {
        return elapsedTime;
    }

    public void dispose() {
        for (TextureRegion frame : backgroundAnimation.getKeyFrames()) {
            if (frame.getTexture() != null) {
                frame.getTexture().dispose();
            }
        }
        font.dispose();
    }

}
