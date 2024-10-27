package io.github.Farm.ui.Other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class Task implements Disposable {
    private Texture label_left, label_right, label_middle;
    private Texture confirm, cancel;
    private Texture indicator;


    private Texture carrot;
    private Texture pumpkin;
    private Texture potato;
    private Texture kale;
    private Texture cabbage;
    private Texture egg;
    private Texture fish;
    private Texture wood;
    private Texture rock;
    private Texture milk;


    private float dropY=0f;
    private float time=0f;
    private BitmapFont font;
    private GlyphLayout layout;

    public Task() {
        label_left = new Texture("UI/other/label_left.png");
        label_middle = new Texture("UI/other/label_middle.png");
        label_right = new Texture("UI/other/label_right.png");
        confirm = new Texture("UI/other/confirm.png");
        cancel = new Texture("UI/other/cancel.png");
        indicator = new Texture("UI/other/indicator.png");


        pumpkin = new Texture("UI/Item/FOOD/pumpkin.png");
        carrot = new Texture("UI/Item/FOOD/carrot.png");
        milk = new Texture("UI/Item/FOOD/milk.png");
        potato = new Texture("UI/Item/FOOD/potato.png");
        kale = new Texture("UI/Item/FOOD/kale.png");
        cabbage = new Texture("UI/Item/FOOD/cabbage.png");
        egg = new Texture("UI/Item/FOOD/egg.png");
        fish = new Texture("UI/Item/FOOD/fish.png");
        wood = new Texture("UI/Item/MATERIAL/wood.png");
        rock = new Texture("UI/Item/MATERIAL/rock.png");


        font = new BitmapFont();
        layout = new GlyphLayout();
    }

    public void render(SpriteBatch batch, Camera camera) {
        float x = 3900;
        float y = 950;

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(label_left, x - 20, y, 20, 110);
        batch.draw(label_middle, x, y, 120, 110);
        batch.draw(label_right, x + 120, y, 20, 110);


        float yItem=y+20;

        drawItem(batch, carrot, 9, x , yItem+0  );
        drawItem(batch, pumpkin, 9, x , yItem +15 );
        drawItem(batch, milk, 9, x , yItem+30 );
        drawItem(batch,potato,9,x,yItem+45);
        drawItem(batch,kale,9,x,yItem+60);

        float x2=x+70;
        drawItem(batch,cabbage,9,x2,yItem+0);
        drawItem(batch,egg,9,x2,yItem+15);
        drawItem(batch,fish,9,x2,yItem+30);
        drawItem(batch,wood,9,x2,yItem+45);
        drawItem(batch,rock,9,x2,yItem+60);

        dropY-= Gdx.graphics.getDeltaTime()*1.5f;
        time+=Gdx.graphics.getDeltaTime();
        if(time>0.5f){
            dropY=0f;
            time=0f;
        }
        batch.draw(indicator,x+50,y+105+dropY,indicator.getWidth()*2,indicator.getHeight()*2);

        batch.end();
    }


    private void drawItem(SpriteBatch batch, Texture itemTexture, int count, float x, float y) {
        batch.draw(itemTexture, x, y);

        layout.setText(font, String.valueOf(count)+"/50");
        if(count>=50){
            batch.draw(confirm,x+3+ layout.width+itemTexture.getWidth(),y);
        }
        else {
            batch.draw(cancel,x+3+ layout.width+itemTexture.getWidth(),y);
        }
        font.draw(batch, layout, x+itemTexture.getWidth() , y+12 );
    }


    @Override
    public void dispose() {
        label_left.dispose();
        label_middle.dispose();
        label_right.dispose();
        confirm.dispose();
        cancel.dispose();
        indicator.dispose();
        pumpkin.dispose();
        carrot.dispose();
        milk.dispose();
        potato.dispose();
        kale.dispose();
        cabbage.dispose();
        egg.dispose();
        fish.dispose();
        wood.dispose();
        rock.dispose();
        font.dispose();
    }
}
