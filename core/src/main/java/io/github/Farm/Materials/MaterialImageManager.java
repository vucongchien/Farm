package io.github.Farm.Materials;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import java.util.EnumMap;

public class MaterialImageManager implements Disposable {
    private final EnumMap<MaterialType, Animation<TextureRegion>> animations;

    public MaterialImageManager(){
        animations=new EnumMap<>(MaterialType.class);

        animations.put(MaterialType.mushroom,createAnimation("Trees/spr_deco_mushroom_blue_01_strip4.png",4,0.2f));
        animations.put(MaterialType.tree,createAnimation("Trees/spr_deco_tree_01_strip4.png",4,0.2f));
        animations.put(MaterialType.rock,createAnimation("Trees/spr_rock_32px.png",1,10));
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

    public Animation<TextureRegion> getAnimation(MaterialType state) {
        return animations.get(state);
    }



    @Override
    public void dispose() {

    }
}

