package io.github.Farm.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import io.github.Farm.SoundManager;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class GameOverScreen {
    private static GameOverScreen INSTANCE;

    public static GameOverScreen getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameOverScreen();
        }
        return INSTANCE;
    }
    private Texture gameOverPanel;
    private BitmapFont optionsFont;
    private GlyphLayout layout;
    private String[] options = {"Play Again", "Exit"};
    private int selectedIndex = 0;
    private boolean isGameOverActive = false;
    private float spacing = 20f; // Khoảng cách giữa các tùy chọn

    public GameOverScreen() {
        gameOverPanel = new Texture(Gdx.files.internal("ui1/GameOver.png"));
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font_ingame/KaushanScript-Regular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();

        parameter.size = 36;
        parameter.color = Color.WHITE;
        optionsFont = generator.generateFont(parameter);

        generator.dispose();
        layout = new GlyphLayout();
    }

    public void render(SpriteBatch batch, Vector2 playerPosition) {
        if (isGameOverActive) {
            batch.begin();

            // Tính toán vị trí panel Game Over dựa trên vị trí của nhân vật
            float panelWidth = gameOverPanel.getWidth() * 0.3f;
            float panelHeight = gameOverPanel.getHeight() * 0.3f;
            float panelX = playerPosition.x - panelWidth / 2; // Giữa nhân vật
            float panelY = playerPosition.y - 60; // Vị trí phía trên nhân vật

            // Vẽ panel Game Over với kích thước mới
            batch.draw(gameOverPanel, panelX, panelY, panelWidth, panelHeight);
            // Vẽ các tùy chọn
            for (int i = 0; i < options.length; i++) {
                layout.setText(optionsFont, options[i]);
                float optionX = playerPosition.x - layout.width / 2; // Giữa nhân vật
                float optionY = playerPosition.y - (i * (spacing + layout.height)) - 60;
                optionsFont.setColor(i == selectedIndex ? Color.YELLOW : Color.WHITE); // Highlight tùy chọn được chọn
                optionsFont.draw(batch, options[i], optionX, optionY);
            }

            batch.end();
        }
    }

    public void handleInput() {
        if (isGameOverActive) {
            if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.UP)) {
                selectedIndex = (selectedIndex - 1 + options.length) % options.length;
                SoundManager.getInstance().playMoveSound();
            } else if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.DOWN)) {
                selectedIndex = (selectedIndex + 1) % options.length;
                SoundManager.getInstance().playMoveSound();
            } else if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
                if (selectedIndex == 0) {
                    // "Play Again" được chọn: Chơi lại game
                    restartGame();
                } else if (selectedIndex == 1) {
                    // "Exit" được chọn: Thoát khỏi game
                    Gdx.app.exit();
                }
            }
        }

        // Nhấn phím L để hiển thị màn hình Game Over
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.L)) {
            isGameOverActive = !isGameOverActive;
        }
    }

    public void restartGame() {
        // Đặt lại các biến và trạng thái cần thiết để khởi động lại game
        isGameOverActive = false;

        // Code để khởi động lại game
        // Ví dụ: có thể gọi lại màn hình chính hoặc reset trạng thái game
        // GameScreen.reset(); // Gọi phương thức reset trong GameScreen nếu có
    }

    public boolean isGameOverActive() {
        return isGameOverActive;
    }
    public boolean isNotGameOverActive() {
        return !isGameOverActive;
    }

    public void dispose() {
        gameOverPanel.dispose();
        optionsFont.dispose();
    }
}
