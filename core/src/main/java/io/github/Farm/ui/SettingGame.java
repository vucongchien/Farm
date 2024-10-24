package io.github.Farm.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.audio.Music;


public class SettingGame {
    private boolean isActive;
    private BitmapFont font;
    private GlyphLayout layout;
    private String[] options;
    private int selectedOption;
    private Texture panelBackground;
    private Sound moveSound; // Biến để lưu âm thanh di chuyển
    private Texture settingsIcon;  // Icon cài đặt
    private float iconSize = 64;   // Kích thước icon
    public Music gameMusic; // Biến lưu nhạc nền
    private boolean isMusicPlaying; // Biến để theo dõi trạng thái âm thanh

    public SettingGame() {
        // Sử dụng FreeTypeFontGenerator để tạo font tùy chỉnh
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font_ingame/KaushanScript-Regular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 36; // Kích thước chữ lớn hơn
//        parameter.color = Color.BLACK; // Màu đen
        parameter.borderWidth = 2; // Độ dày của viền để tạo cảm giác in đậm
        parameter.borderColor = Color.BLACK; // Màu viền
        this.font = generator.generateFont(parameter); // Tạo font tùy chỉnh
        generator.dispose(); // Giải phóng tài nguyên của generator


        isActive = false;
        layout = new GlyphLayout();
        options = new String[]{"Continue", "Sound", "Exit"};
        selectedOption = 0;
        panelBackground = new Texture("Setting/table.png"); // Hình nền của bảng (nếu có)

        // Khởi tạo âm thanh di chuyển
         moveSound = Gdx.audio.newSound(Gdx.files.internal("soundgame/sound_movebuttonmenu.wav"));
        // Khởi tạo nhạc nền
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("soundgame/gamemusic.mp3"));
        isMusicPlaying = true; // Khởi tạo nhạc đang phát
        gameMusic.play(); // Phát nhạc khi vào game
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.3f);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            // Phát âm thanh ngay lập tức để kiểm tra
            moveSound.play(0.05f);
            isActive = !isActive;
        }

        if (isActive) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                // Phát âm thanh ngay lập tức để kiểm tra
                moveSound.play(0.05f);
                selectedOption = (selectedOption - 1 + options.length) % options.length;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                 // Phát âm thanh ngay lập tức để kiểm tra
                moveSound.play(0.05f);
                selectedOption = (selectedOption + 1) % options.length;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                performAction(selectedOption);
            }
        }

    }

    private void performAction(int option) {
        switch (option) {
            case 0:
                isActive = false; // Tiếp tục trò chơi
                break;
            case 1:
                // Tùy chọn âm thanh
                isMusicPlaying = !isMusicPlaying; // Đổi trạng thái âm thanh
                options[1] = isMusicPlaying ? "Sound: ON" : "Sound: OFF";
                if (gameMusic.isPlaying()) {
                    gameMusic.pause();
                    // Phát nhạc nền nếu âm thanh bật
                } else {
                    gameMusic.play(); // Dừng nhạc nền nếu âm thanh tắt
                }
                break;
            case 2:
                Gdx.app.exit(); // Thoát game
                break;
        }
    }

    public void render(SpriteBatch batch, Vector2 playerPosition) {

        // Nếu menu cài đặt đang hoạt động, vẽ bảng tùy chọn
        if (isActive) {
            batch.begin();

            // Lấy kích thước của màn hình
            float screenWidth = Gdx.graphics.getWidth();
            float screenHeight = Gdx.graphics.getHeight();

            // Kích thước của bảng (panel)
            float panelWidth = 400;
            float panelHeight = 300;

            // Vị trí của bảng (căn giữa nhân vật)
            float panelX = playerPosition.x - panelWidth / 2;
            float panelY = playerPosition.y - panelHeight / 2;

            // Vẽ hình nền của bảng
            batch.draw(panelBackground, panelX, panelY, panelWidth, panelHeight);

            // Căn giữa các tùy chọn trong bảng
            float optionHeight = 50;
            float totalOptionsHeight = options.length * optionHeight; // Tổng chiều cao của tất cả các tùy chọn
            float startX = panelX + (panelWidth - layout.width) / 2; // Căn giữa theo chiều ngang của bảng
            float startY = panelY + (panelHeight + totalOptionsHeight) / 2; // Căn giữa theo chiều dọc của bảng

            for (int i = 0; i < options.length; i++) {
                // Tính toán chiều rộng của text để căn giữa theo chiều ngang
                layout.setText(font, options[i]);
                float textWidth = layout.width;

                // Căn giữa text theo chiều ngang của bảng
                float x = panelX + (panelWidth - textWidth) / 2;
                float y = startY - i * optionHeight;

                if (i == selectedOption) {
                    font.setColor(1, 1, 0, 1); // Màu vàng cho tùy chọn được chọn
                } else {
                    font.setColor(1, 1, 1, 1); // Màu trắng cho các tùy chọn khác
                }

                font.draw(batch, options[i], x, y);
            }

            batch.end();
        }
    }

    public void dispose() {
        // Giải phóng tài nguyên font
        if (font != null) {
            font.dispose();
        }

        // Giải phóng hình nền bảng
        if (panelBackground != null) {
            panelBackground.dispose();
        }

        // Giải phóng âm thanh di chuyển
        if (moveSound != null) {
            moveSound.dispose(); // Giải phóng âm thanh
        }

        // Giải phóng nhạc nền
        if (gameMusic != null) {
            gameMusic.dispose(); // Giải phóng nhạc nền
        }
    }
}
