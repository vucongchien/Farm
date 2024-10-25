package io.github.Farm.Trees;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import io.github.Farm.player.PLAYER_STATE.PlayerState;

import java.util.EnumMap;

public class TreeImageManager implements Disposable {
    private final EnumMap<TreeType, Animation<TextureRegion>> animations;

    public TreeImageManager(){
        animations=new EnumMap<>(TreeType.class);

        animations.put(TreeType.mushroom,createAnimation("Trees/spr_deco_mushroom_blue_01_strip4.png",4,0.2f));
        animations.put(TreeType.tree,createAnimation("Trees/spr_deco_tree_01_strip4.png",4,0.2f));

    }

    private Animation<TextureRegion> createAnimation(String path,int frameCols,float frameDuration){
        Texture sheetTexture = new Texture(path);

        TextureRegion[][] tmp=TextureRegion.split(sheetTexture,sheetTexture.getWidth()/frameCols,sheetTexture.getHeight());
        TextureRegion[] frames=new TextureRegion[frameCols];

        for(int i=0;i<frameCols;i++){
            frames[i]=tmp[0][i];
        }
        return new Animation<>(frameDuration,new Array<>(frames));
    }

    public Animation<TextureRegion> getAnimation(TreeType state) {
        return animations.get(state);
    }



    @Override
    public void dispose() {

    }
}

