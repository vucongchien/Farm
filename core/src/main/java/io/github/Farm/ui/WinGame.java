package io.github.Farm.ui;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

    private final float STANDARD_WIDTH = 800f;
    private final float STANDARD_HEIGHT = 450f;
    private OrthographicCamera camera;



    // Các dòng chữ cần hiển thị
    private String titleText = "DO Án LTHDT";
    private String memberText = "Thành viên:";
    private String[] members = {"VU CONG CHIEN", "DANG VIET HUNG", "TRUONG ANH TUNG"};

    private float congratulationsY;
    private final float congratulationsSpeed = 100f;


    public WinGame() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
        parameter.size = 100;
        parameter.borderWidth = 1.5f;
        parameter.borderColor = Color.BLACK;
        font = generator.generateFont(parameter);
        font.setColor(Color.WHITE);
        generator.dispose();

        FreeTypeFontGenerator smallGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font_ingame/KaushanScript-Regular.ttf"));
        FreeTypeFontParameter smallParameter = new FreeTypeFontParameter();
        smallParameter.size = 50;
        smallParameter.borderWidth = 0.5f;
        smallParameter.borderColor = Color.YELLOW;
        font2 = smallGenerator.generateFont(smallParameter);
        font2.setColor(Color.WHITE);
        smallGenerator.dispose();

        layout = new GlyphLayout();
        isWin = false;
        congratulationsY = -20;
    }

    public void render(SpriteBatch batch, Vector2 playerPosition) {
        winTimer += Gdx.graphics.getDeltaTime();
        if (isWin) {
            batch.setProjectionMatrix(camera.combined);
            camera.update();

            batch.begin();
            elapsedTime += Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = backgroundAnimation.getKeyFrame(elapsedTime);

            float scaleX = Gdx.graphics.getWidth() / STANDARD_WIDTH;
            float scaleY = Gdx.graphics.getHeight() / STANDARD_HEIGHT;

            // Sử dụng scale để tính vị trí và kích thước chính xác của các nội dung hiển thị
            float width = STANDARD_WIDTH * scaleX;
            float height = STANDARD_HEIGHT * scaleY;

            // Vị trí trung tâm
            float x = 0;
            float y = (Gdx.graphics.getHeight() - height) / 2;

            batch.draw(currentFrame, x, y, width, height);

            layout.setText(font, "Congratulations");

            float scale = 1 + 0.5f * Math.abs((winTimer / winDuration) - 0.5f) * 2;
            font.getData().setScale(scale);

            float progress = winTimer / winDuration;
            font.setColor(new Color(1 - 0.3f * progress, 1 - 0.2f * progress, 0.8f + 0.2f * progress, 1));

            // Giữ vị trí chữ "Congratulations" cố định dựa vào tỷ lệ màn hình
            float textX = 50;
            float textY = Gdx.graphics.getHeight() * 0.95f;
            if (congratulationsY < textY) {
                congratulationsY += congratulationsSpeed * Gdx.graphics.getDeltaTime();
            } else {
                congratulationsY = textY; // Giữ cố định khi đã đến đỉnh
            }
            font.draw(batch, layout, textX, congratulationsY);
            font.getData().setScale(1);

            if(congratulationsY >= textY){
                // Các nội dung khác giữ nguyên như tiêu đề và thành viên, tính toán vị trí dựa trên chiều cao màn hình
                layout.setText(font2, titleText);
                font2.draw(batch, titleText, textX, textY - 100 * scaleY);

                layout.setText(font2, memberText);
                font2.draw(batch, memberText, textX, textY - 130 * scaleY);

                for (int i = 0; i < members.length; i++) {
                    layout.setText(font2, members[i]);
                    font2.draw(batch, members[i], textX, textY - (160 + i * 30) * scaleY);
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
        font2.dispose();
    }

}
