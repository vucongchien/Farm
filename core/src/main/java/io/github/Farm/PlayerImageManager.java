package io.github.Farm;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class PlayerImageManager {
    private Animation<TextureRegion> walkUpAnimation;
    private Animation<TextureRegion> walkDownAnimation;
    private Animation<TextureRegion> walkLeftAnimation;
    private Animation<TextureRegion> walkRightAnimation;

    private Animation<TextureRegion> idleUpAnimation;
    private Animation<TextureRegion> idleDownAnimation;
    private Animation<TextureRegion> idleLeftAnimation;
    private Animation<TextureRegion> idleRightAnimation;

    public PlayerImageManager(
        String idleUpSheetPath, String idleDownSheetPath, String idleLeftSheetPath, String idleRightSheetPath,
        String walkUpSheetPath, String walkDownSheetPath, String walkLeftSheetPath, String walkRightSheetPath,
        int frameCols, int frameRows, float frameDuration) {

        // Tạo các animation từ sprite sheets
        this.idleUpAnimation = createAnimation(idleUpSheetPath, frameCols, frameRows, frameDuration);
        this.idleDownAnimation = createAnimation(idleDownSheetPath, frameCols, frameRows, frameDuration);
        this.idleLeftAnimation = createAnimation(idleLeftSheetPath, frameCols, frameRows, frameDuration);
        this.idleRightAnimation = createAnimation(idleRightSheetPath, frameCols, frameRows, frameDuration);

        this.walkUpAnimation = createAnimation(walkUpSheetPath, frameCols, frameRows, frameDuration);
        this.walkDownAnimation = createAnimation(walkDownSheetPath, frameCols, frameRows, frameDuration);
        this.walkLeftAnimation = createAnimation(walkLeftSheetPath, frameCols, frameRows, 0.15f);
        this.walkRightAnimation = createAnimation(walkRightSheetPath, frameCols, frameRows, 0.15f);
    }

    private Animation<TextureRegion> createAnimation(String sheetPath, int frameCols, int frameRows, float frameDuration) {
        Texture sheetTexture = new Texture(sheetPath);
        TextureRegion[][] tmpFrames = TextureRegion.split(sheetTexture, sheetTexture.getWidth() / frameCols, sheetTexture.getHeight() / frameRows);

        // Chuyển đổi sprite sheet thành mảng 1D
        TextureRegion[] frames = new TextureRegion[frameCols * frameRows];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                frames[index++] = tmpFrames[i][j];
            }
        }

        return new Animation<>(frameDuration, new Array<>(frames));
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

    public Animation<TextureRegion> getIdleAnimation(String direction) {
        switch (direction) {
            case "u_idle":
                return idleUpAnimation;
            case "d_idle":
                return idleDownAnimation;
            case "l_idle":
                return idleLeftAnimation;
            case "r_idle":
                return idleRightAnimation;
            default:
                return null;
        }
    }

    public void dispose() {
        // Giải phóng tài nguyên của các frame của từng animation
        disposeAnimationFrames(walkUpAnimation);
        disposeAnimationFrames(walkDownAnimation);
        disposeAnimationFrames(walkLeftAnimation);
        disposeAnimationFrames(walkRightAnimation);

        disposeAnimationFrames(idleUpAnimation);
        disposeAnimationFrames(idleDownAnimation);
        disposeAnimationFrames(idleLeftAnimation);
        disposeAnimationFrames(idleRightAnimation);
    }

    private void disposeAnimationFrames(Animation<TextureRegion> animation) {
        for (TextureRegion frame : animation.getKeyFrames()) {
            if (frame != null && frame.getTexture() != null) {
                frame.getTexture().dispose();
            }
        }
    }
}
