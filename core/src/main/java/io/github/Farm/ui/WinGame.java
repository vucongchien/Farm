package io.github.Farm.ui;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.Animation;

public class WinGame {
    private static WinGame instance;
    public static WinGame getInstance() {
        if (instance == null){
            instance = new WinGame();

        }
        return instance;
    }

    private Animation<TextureRegion> backgroundAnimation;
    private boolean isWin;
    private float elapsedTime;
    private float winDuration = 60f; // Thời gian chạy demo là 60 giây
    private float winTimer = 0f; // Bộ đếm thời gian cho màn hình Win



    public WinGame() {
        // Tạo một mảng để lưu các frame
        Array<TextureRegion> frames = new Array<>();

        // Giả sử bạn đã tách GIF thành các file ảnh với tên định dạng như "frame_00_delay-0.1s.png", "frame_01_delay-0.1s.png",...
        int totalFrames = 166; // Số lượng frame mà bạn có (có thể thay đổi tùy theo số frame bạn tách từ GIF)

        for (int i = 14; i <= totalFrames; i++) {
            // Định dạng tên file với số có 2 chữ số
//            String fileName = String.format("ui1/YouWin/frame_%02d_delay-0.1s.png", i);
            String fileName = String.format("ui1/microsoft_cinematic_teaser (1080p)_000/microsoft_cinematic_teaser (1080p)_%03d.png", i);

            Texture frameTexture = new Texture(Gdx.files.internal(fileName));
            TextureRegion frameRegion = new TextureRegion(frameTexture);
            frames.add(frameRegion);
        }
        // Tạo animation với tốc độ 10 frame mỗi giây (có thể điều chỉnh)
        backgroundAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
        isWin = false;
    }

    public void render(SpriteBatch batch) {
       if (isWin) {
           batch.begin();
           elapsedTime += Gdx.graphics.getDeltaTime();
           TextureRegion currentFrame = backgroundAnimation.getKeyFrame(elapsedTime);
           batch.draw(currentFrame, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
           batch.end();
       }
        winTimer += Gdx.graphics.getDeltaTime();

        // Kiểm tra nếu thời gian đã vượt qua winDuration (60 giây)
//        if (winTimer > winDuration) {
//            isWin = false;
//            exitWinGame();
//        }

    }

    public void handleInput() {
        // Nhấn phím P để bắt đầu demo Win Game
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.P)) {
            isWin = true; // Bật màn hình Win
            winTimer = 0f; // Đặt lại bộ đếm thời gian
            elapsedTime = 0f; // Đặt lại thời gian đã trôi qua
        }
    }

    public boolean isWin() {
        return isWin;
    }
    private void exitWinGame() {
        System.out.println("Thoát khỏi màn hình Win Game.");
    }
    public void dispose() {
        for (TextureRegion frame : backgroundAnimation.getKeyFrames()) {
            if (frame.getTexture() != null) {
                frame.getTexture().dispose();
            }
        }
    }

}
