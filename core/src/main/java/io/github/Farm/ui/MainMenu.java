package io.github.Farm.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.Animation;




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
    private Animation<TextureRegion> backgroundAnimation; // Animation cho background
    private boolean isDemoActive; // Biến để kiểm soát trạng thái màn hình demo
    private Animation<TextureRegion> demoAnimation; // Animation cho demo
    private float elapsedTime;
    private String controlsText; // Text chứa nội dung điều khiển
    private boolean isControlsActive; // Kiểm soát trạng thái hiển thị của bảng điều khiển



    public MainMenu() {
        // Sử dụng FreeTypeFontGenerator để tạo font tùy chỉnh
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font_ingame/KaushanScript-Regular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 36; // Kích thước chữ lớn hơn
        parameter.borderWidth = 2; // Độ dày của viền để tạo cảm giác in đậm
        parameter.borderColor = Color.BLACK; // Màu viền
        this.font = generator.generateFont(parameter); // Tạo font tùy chỉnh
        generator.dispose(); // Giải phóng tài nguyên của generator


        this.menuItems = new String[] {"Start Game", "Options", "Exit"};
        this.selectedIndex = 0;
        this.isMenuActive = true; // Mặc định menu đang hoạt động
        this.maxTextWidth = 0; // Khởi tạo maxTextWidth
        this.totalMenuHeight = 0; // Khởi tạo totalMenuHeight
        this.controlsText = "W S A D: Move character\n" +
            "C: Fish\n" +
            "F: Dig\n" +
            "R: Water\n" +
            "I: Open Inventory\n" +
            "ESC: Open Settings";
        this.isControlsActive = false;

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



        // Tạo một mảng để lưu các frame
        Array<TextureRegion> frames = new Array<>();

        // Giả sử bạn đã tách GIF thành các file ảnh với tên định dạng như "frame_00_delay-0.1s.png", "frame_01_delay-0.1s.png",...
        int totalFrames = 40; // Số lượng frame mà bạn có (có thể thay đổi tùy theo số frame bạn tách từ GIF)

        for (int i = 0; i < totalFrames; i++) {
            // Định dạng tên file với số có 2 chữ số
            String fileName = String.format("ui1/frame_%02d_delay-0.1s.png", i);
            Texture frameTexture = new Texture(Gdx.files.internal(fileName));
            TextureRegion frameRegion = new TextureRegion(frameTexture);
            frames.add(frameRegion);
        }
        // Tạo animation với tốc độ 10 frame mỗi giây (có thể điều chỉnh)
        backgroundAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);

    }

    public void render(SpriteBatch batch) {
        if (isMenuActive) {
            batch.begin();
            // Cập nhật thời gian đã trôi qua
            elapsedTime += Gdx.graphics.getDeltaTime();

            // Vẽ background hoạt ảnh
            TextureRegion currentFrame = backgroundAnimation.getKeyFrame(elapsedTime);
            batch.draw(currentFrame, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            float screenWidth = Gdx.graphics.getWidth(); // Lấy chiều rộng màn hình
            float screenHeight = Gdx.graphics.getHeight(); // Lấy chiều cao màn hình
            float startX = (screenWidth - maxTextWidth) / 2; // Tính toán vị trí x để căn giữa theo chiều ngang

            // Tính toán startY sao cho menu căn giữa theo chiều dọc
            float startY = (screenHeight - totalMenuHeight) / 2;

            if(!isControlsActive){
                for (int i = 0; i < menuItems.length; i++) {
                    String text = menuItems[i];
                    GlyphLayout layout = new GlyphLayout(font, text); // Đặt text để tính toán
                    if (i == selectedIndex) {
                        font.setColor(Color.RED); // Màu đỏ cho mục được chọn
                        text = "> " + text + " <"; // Highlight selected item
                    } else {
                        font.setColor(Color.WHITE); // Màu trắng cho mục không được chọn
                    }

                    float x = startX; // Vị trí x để căn giữa theo chiều ngang
                    // Điều chỉnh vị trí y để căn giữa các mục menu theo chiều dọc
                    float y = startY + totalMenuHeight - (layout.height + itemSpacing) * (i + 1) + layout.height;

                    font.draw(batch, text, x, y); // Vẽ từng mục menu
                }
            }
            else if(isControlsActive) {
                // Hiển thị bảng điều khiển ở giữa màn hình
                GlyphLayout layout = new GlyphLayout(font, controlsText);
                float x = (Gdx.graphics.getWidth() - layout.width) / 2;
                float y = (Gdx.graphics.getHeight() + layout.height) / 2;
                font.draw(batch, controlsText, x, y);
            }
            batch.end();
        }
    }


    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.DOWN)) {
            selectedIndex = (selectedIndex + 1) % menuItems.length;
            moveSound.play(0.05f); // Phát âm thanh ngay lập tức để kiểm tra
        } else if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.UP)) {
            selectedIndex = (selectedIndex - 1 + menuItems.length) % menuItems.length;
            moveSound.play(0.05f); // Phát âm thanh ngay lập tức để kiểm tra
        } else if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
            switch (selectedIndex) {

                case 0:
                    // Start game
                    isMenuActive = false;
                    break;
                case 1:
                    // Options
                    isControlsActive = true;
                    break;

                case 2:
                    // Exit
                    Gdx.app.exit();
                    break;
            }
        }
        if (isControlsActive && Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            isControlsActive = false;
            isMenuActive = true;
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

        // Giải phóng âm thanh
        if (moveSound != null) {
            moveSound.dispose(); // Giải phóng âm thanh di chuyển
        }
        if (SoundEnter != null) {
            SoundEnter.dispose(); // Giải phóng âm thanh enter
        }

        // Giải phóng tài nguyên cho các texture trong animation
        for (TextureRegion frame : backgroundAnimation.getKeyFrames()) {
            if (frame.getTexture() != null) {
                frame.getTexture().dispose(); // Giải phóng texture nếu không null
            }
        }
    }

}
