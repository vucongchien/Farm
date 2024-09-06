package io.github.Farm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.audio.Sound;

public class MainMenu {
    private BitmapFont font;
    private Texture background; // Biến để lưu background
    private String[] menuItems;
    private int selectedIndex;
    private boolean isMenuActive;
    private float maxTextWidth;
    private float totalMenuHeight;
    private float itemSpacing = 10; // Khoảng cách giữa các mục menu
    private Sound moveSound; // Biến để lưu âm thanh di chuyển
    private Sound SoundEnter; // Biến để lưu âm thanh enter


    public MainMenu() {
        // Sử dụng FreeTypeFontGenerator để tạo font tùy chỉnh
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font_ingame/KaushanScript-Regular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 36; // Kích thước chữ lớn hơn
//        parameter.color = Color.BLACK; // Màu đen
        parameter.borderWidth = 2; // Độ dày của viền để tạo cảm giác in đậm
        parameter.borderColor = Color.BLACK; // Màu viền
        this.font = generator.generateFont(parameter); // Tạo font tùy chỉnh
        generator.dispose(); // Giải phóng tài nguyên của generator

        this.background = new Texture(Gdx.files.internal("tile_map/NaUX7.png")); // Khởi tạo background
        this.menuItems = new String[] {"Start Game", "Options", "Exit"};
        this.selectedIndex = 0;
        this.isMenuActive = true; // Mặc định menu đang hoạt động
        this.maxTextWidth = 0; // Khởi tạo maxTextWidth
        this.totalMenuHeight = 0; // Khởi tạo totalMenuHeight

        // Tính toán độ rộng lớn nhất của các text và tổng chiều cao của menu
        GlyphLayout layout = new GlyphLayout();
        for (String item : menuItems) {
            layout.setText(font, item); // Đặt text để tính toán
            maxTextWidth = Math.max(maxTextWidth, layout.width); // Cập nhật độ rộng lớn nhất
            totalMenuHeight += layout.height; // Tính tổng chiều cao của các mục menu
        }
        // Thêm khoảng cách giữa các mục menu
        totalMenuHeight += (menuItems.length - 1) * itemSpacing; // Tổng chiều cao của menu cộng với khoảng cách giữa các mục
        // Khởi tạo âm thanh di chuyển
        moveSound = Gdx.audio.newSound(Gdx.files.internal("soundgame/sound_movebuttonmenu.wav"));
        moveSound.play();
        SoundEnter = Gdx.audio.newSound(Gdx.files.internal("soundgame/sound_enterbutton.wav"));
    }

    public void render(SpriteBatch batch) {
        if (isMenuActive) {
            batch.begin();

            // Vẽ background
            batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            float screenWidth = Gdx.graphics.getWidth(); // Lấy chiều rộng màn hình
            float screenHeight = Gdx.graphics.getHeight(); // Lấy chiều cao màn hình
            float startX = (screenWidth - maxTextWidth) / 2; // Tính toán vị trí x để căn giữa theo chiều ngang

            // Tính toán startY sao cho menu căn giữa theo chiều dọc
            float startY = (screenHeight - totalMenuHeight) / 2;

            for (int i = 0; i < menuItems.length; i++) {
                String text = menuItems[i];
                GlyphLayout layout = new GlyphLayout(font, text); // Đặt text để tính toán
                if (i == selectedIndex) {
                    font.setColor(Color.RED); // Màu đỏ cho mục được chọn
                    text = "> " + text + " <"; // Highlight selected item

                } else {
                    font.setColor(Color.WHITE); // Màu đen cho mục không được chọn
                }

                float x = startX; // Vị trí x để căn giữa theo chiều ngang
                // Điều chỉnh vị trí y để căn giữa các mục menu theo chiều dọc
                float y = startY + totalMenuHeight - (layout.height + itemSpacing) * (i + 1) + layout.height;

                font.draw(batch, text, x, y); // Vẽ từng mục menu
            }
            batch.end();
        }
    }

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.DOWN)) {
            selectedIndex = (selectedIndex + 1) % menuItems.length;
            moveSound.play(); // Phát âm thanh ngay lập tức để kiểm tra
        } else if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.UP)) {
            selectedIndex = (selectedIndex - 1 + menuItems.length) % menuItems.length;
            moveSound.play(); // Phát âm thanh ngay lập tức để kiểm tra
        } else if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
            switch (selectedIndex) {
                case 0:
                    // Start game
                    SoundEnter.play();
                    isMenuActive = false;
                    break;
                case 1:
                    // Options
                    SoundEnter.play();
                    break;
                case 2:
                    // Exit
                    Gdx.app.exit();
                    break;
            }
        }
    }



    public boolean isMenuActive() {
        return isMenuActive;
    }

    public void setMenuActive(boolean isMenuActive) {
        this.isMenuActive = isMenuActive;
    }

    public void dispose() {
        if (font != null) {
            font.dispose(); // Giải phóng tài nguyên font
        }
        if (background != null) {
            background.dispose(); // Giải phóng tài nguyên background
        }

    }
}
