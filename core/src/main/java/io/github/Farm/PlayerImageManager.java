package io.github.Farm;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class PlayerImageManager {
    private Texture idleTexture;
    private Animation<TextureRegion> walkUpAnimation;
    private Animation<TextureRegion> walkDownAnimation;
    private Animation<TextureRegion> walkLeftAnimation;
    private Animation<TextureRegion> walkRightAnimation;

    public PlayerImageManager(String idleTexturePath, String walkUpSheetPath, String walkDownSheetPath,
                              String walkLeftSheetPath, String walkRightSheetPath,
                              int frameCols, int frameRows, float frameDuration) {
        this.idleTexture = new Texture(idleTexturePath);

        // Tạo các animation từ sprite sheets
        this.walkUpAnimation = createAnimation(walkUpSheetPath, frameCols, frameRows, frameDuration);
        this.walkDownAnimation = createAnimation(walkDownSheetPath, frameCols, frameRows, frameDuration);
        this.walkLeftAnimation = createAnimation(walkLeftSheetPath, frameCols, frameRows, frameDuration);
        this.walkRightAnimation = createAnimation(walkRightSheetPath, frameCols, frameRows, frameDuration);
    }

    private Animation<TextureRegion> createAnimation(String sheetPath, int frameCols, int frameRows, float frameDuration) {
        Texture walkSheet = new Texture(sheetPath);
        TextureRegion[][] tmpFrames = TextureRegion.split(walkSheet, walkSheet.getWidth() / frameCols, walkSheet.getHeight() / frameRows);

        // Chuyển đổi sprite sheet thành 1D array
        TextureRegion[] walkFrames = new TextureRegion[frameCols * frameRows];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                walkFrames[index++] = tmpFrames[i][j];
            }
        }

        return new Animation<>(frameDuration, new Array<>(walkFrames));
    }

    public Texture getIdleTexture() {
        return idleTexture;
    }

    public Animation<TextureRegion> getWalkAnimation(String direction) {
        switch (direction) {
            case "up":
                return walkUpAnimation;
            case "down":
                return walkDownAnimation;
            case "left":
                return walkLeftAnimation;
            case "right":
                return walkRightAnimation;
            default:
                return null;
        }
    }

    public void dispose() {
        idleTexture.dispose();
        for (TextureRegion frame : walkUpAnimation.getKeyFrames()) {
            frame.getTexture().dispose();
        }
        for (TextureRegion frame : walkDownAnimation.getKeyFrames()) {
            frame.getTexture().dispose();
        }
        for (TextureRegion frame : walkLeftAnimation.getKeyFrames()) {
            frame.getTexture().dispose();
        }
        for (TextureRegion frame : walkRightAnimation.getKeyFrames()) {
            frame.getTexture().dispose();
        }
    }
}
