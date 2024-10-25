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
import io.github.Farm.player.PlayerController;
import io.github.Farm.player.PlayerRenderer;

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
    private String options = "Exit";
    private int selectedIndex = 0;
    private boolean isGameOverActive = false;

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
        if (isGameOverActive && !WinGame.getInstance().isWin()) {
            batch.begin();

            // Tính toán vị trí panel Game Over dựa trên vị trí của nhân vật
            float panelWidth = gameOverPanel.getWidth() * 0.3f;
            float panelHeight = gameOverPanel.getHeight() * 0.3f;
            float panelX = playerPosition.x - panelWidth / 2; // Giữa nhân vật
            float panelY = playerPosition.y - 60; // Vị trí phía trên nhân vật

            // Vẽ panel Game Over với kích thước mới
            batch.draw(gameOverPanel, panelX, panelY, panelWidth, panelHeight);
            // Vẽ tùy chọn "Exit"
            layout.setText(optionsFont, options);
            float optionX = playerPosition.x - layout.width / 2; // Giữa nhân vật
            float optionY = panelY - 30;
            optionsFont.draw(batch, options, optionX, optionY);

            batch.end();
        }
    }

    public void handleInput() {
        if (isGameOverActive) {
            if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
                Gdx.app.exit();
            }
        }

    }

    public boolean isGameOverActive() {
        return isGameOverActive;
    }
    public void setGameOverActive(boolean isGameOverActive) {
        this.isGameOverActive = isGameOverActive;
    }
    public boolean isNotGameOverActive() {
        return !isGameOverActive;
    }


    public void dispose() {
        gameOverPanel.dispose();
        optionsFont.dispose();
    }
}
