package io.github.Farm.ui;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.Animation;
import io.github.Farm.SoundManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;



public class WinGame {
    private static WinGame instance;
    public static WinGame getInstance() {
        if (instance == null){
            instance = new WinGame();

        }
        return instance;
    }

    private Animation<TextureRegion> backgroundAnimation;
    private float elapsedTime;
    private float winDuration = 70f;
    private float winTimer = 0f;
    private boolean isWin;

    // Khai báo các biến cho font và layout
    private BitmapFont font;
    private BitmapFont font2;
    private GlyphLayout layout;

    // Các dòng chữ cần hiển thị
    private String titleText = "DO Án LTHDT";
    private String memberText = "Thành viên:";
    private String[] members = {"VU CONG CHIEN", "DANG VIET HUNG", "TRUONG ANH TUNG"};


    public WinGame() {
        // Tạo một mảng để lưu các frame
        Array<TextureRegion> frames = new Array<>();

        // Giả sử bạn đã tách GIF thành các file ảnh với tên định dạng như "frame_00_delay-0.1s.png", "frame_01_delay-0.1s.png",...
        int totalFrames = 190; // Số lượng frame mà bạn có (có thể thay đổi tùy theo số frame bạn tách từ GIF)

        for (int i = 0; i <= totalFrames; i+=2) {
//             Định dạng tên file với số có 2 chữ số
            String fileName = String.format("ui1/EndGame/thousand-sunny-sunset-sea-one-piece-moewalls-com_%03d.png", i);

            Texture frameTexture = new Texture(Gdx.files.internal(fileName));
            TextureRegion frameRegion = new TextureRegion(frameTexture);
            frames.add(frameRegion);
        }
        backgroundAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
        // Khởi tạo font bằng FreeTypeFontGenerator
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font_ingame/KaushanScript-Regular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 40;
        parameter.borderWidth = 1.5f;
        parameter.borderColor = Color.BLACK;
        font = generator.generateFont(parameter);
        font.setColor(Color.WHITE);
        generator.dispose();

        FreeTypeFontGenerator smallGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font_ingame/KaushanScript-Regular.ttf"));
        FreeTypeFontParameter smallParameter = new FreeTypeFontParameter();
        smallParameter.size = 15; // Kích thước nhỏ hơn cho tiêu đề và tên thành viên\
        smallParameter.borderWidth = 0.5f;
        smallParameter.borderColor = Color.YELLOW;
        font2 = smallGenerator.generateFont(smallParameter);
        font2.setColor(Color.WHITE);
        smallGenerator.dispose();

        layout = new GlyphLayout();
        isWin = false;
    }

    public void render(SpriteBatch batch, Vector2 playerPosition) {
        winTimer += Gdx.graphics.getDeltaTime();
        if (isWin) {
            batch.begin();
            elapsedTime += Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = backgroundAnimation.getKeyFrame(elapsedTime);

            // Tính toán vị trí hiển thị của background animation
            float x = playerPosition.x -300; // Điều chỉnh theo vị trí nhân vật
            float y = playerPosition.y -175; // Điều chỉnh theo vị trí nhân vật

            // Kích thước mới cho khung hình nhỏ hơn
            float width = 600;  // Đặt chiều rộng mới
            float height = 350; // Đặt chiều cao mới

            // Vẽ animation ở vị trí tính toán với kích thước mới
            batch.draw(currentFrame, x, y, width, height);

            layout.setText(font, "Congratulations"); // Đặt chữ với kích thước mới

            // Tạo hiệu ứng phóng to
            float scale = 1 + 0.5f * Math.abs((winTimer / winDuration) - 0.5f) * 2; // phóng to từ 1 đến 1.5
            font.getData().setScale(scale); // Đặt kích thước cho font

            float progress = winTimer / winDuration; // Tính toán tỷ lệ phần trăm của thời gian
            float red = 1 - 0.3f * progress;
            float green =1 - 0.2f * progress;
            float blue = 0.8f + 0.2f * progress;

            // Tạo màu mới dựa trên progress
            Color color = new Color(red, green, blue, 1);
            font.setColor(color);

            float textY = playerPosition.y +winTimer * (1080/winDuration);
            float textX = playerPosition.x-290;

            // Dừng lại khi tới một khoảng cách nhất định
            float targetY = playerPosition.y + Gdx.graphics.getHeight() / 4 - 100;
            if (textY >= targetY) {
                textY = targetY;  // Dừng lại tại vị trí này
            }
            // Vẽ chữ "Congratulations"
            font.draw(batch, layout, textX, textY);
            font.getData().setScale(1); // Quay về kích thước ban đầu

            if(textY == targetY){
                // Vẽ tiêu đề và tên thành viên
                float titleY = textY - 70; // Dịch xuống dưới để hiển thị tiêu đề
                layout.setText(font2, titleText);
                float titleTextX = playerPosition.x - layout.width / 2 - 150; // Điều chỉnh vị trí x phù hợp
                font2.draw(batch, layout, titleTextX, titleY);

                float memberY = titleY - 30; // Dịch xuống dưới để hiển thị tiêu đề thành viên
                layout.setText(font2, memberText);
                float memberTextX = playerPosition.x - layout.width / 2 - 150; // Điều chỉnh vị trí x phù hợp
                font2.draw(batch, layout, memberTextX, memberY);

                // Vẽ tên từng thành viên
                for (int i = 0; i < members.length; i++) {
                    layout.setText(font2, members[i]);
                    float individualTextX = playerPosition.x - layout.width / 2 - 150; // Điều chỉnh vị trí x phù hợp
                    float individualY = memberY - (i + 1) * 30; // Dịch xuống cho từng thành viên
                    font2.draw(batch, layout, individualTextX, individualY);
                }
            }

            batch.end();
        }
        if (winTimer > winDuration) {
            isWin = false;
            exitWinGame();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            isWin = false;
            exitWinGame();
        }

    }

    public boolean isWin() {
        return isWin;
    }
    public void setIsWin(boolean isWin) {
        this.isWin = isWin;
    }
    private void exitWinGame() {
        System.out.println("Thoát khỏi màn hình Win Game.");
        Gdx.app.exit();
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
