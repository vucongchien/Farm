package io.github.Farm;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import io.github.Farm.UI.Selecbox;
import io.github.Farm.UI.TimeCoolDown;
import io.github.Farm.animal.Buffalo;
import io.github.Farm.animal.wolf;
import io.github.Farm.player.PlayerController;
import io.github.Farm.player.PlayerImageManager;
import io.github.Farm.player.PlayerRenderer;
import io.github.Farm.player.hoatdong;

import java.util.ArrayList;

import static io.github.Farm.animal.PetState.IDLE_RIGHT;
import static io.github.Farm.animal.PetState.WALK_LEFT;


public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private hoatdong player;
    private PlayerRenderer playerRenderer;
    private PlayerImageManager playerImageManager;


    private OrthographicCamera camera;
    private Gamemap map;
    private MainMenu mainMenu;
    private SettingGame settingGame;
    private boolean isInGame;

    private Selecbox selecbox;
    private TimeCoolDown timeCoolDownBar;

//............................................buffalo
    private ArrayList<Buffalo> arraybuffalo=new ArrayList<>();
    private ArrayList<wolf> arraywolf= new ArrayList<>();
    private long stopTimereproduction;
    private long stopTimehungry;
    @Override
    public void create() {
        Gdx.graphics.setWindowedMode(1920, 1080);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


//--------------------------------khoi tao map
        map = new Gamemap();
        map.create(AssentPaths.map);


// -------------------------------Khởi tạo player
        player = new hoatdong(new Vector2(100, 200), 200,map);
        playerImageManager=new PlayerImageManager();
        playerRenderer = new PlayerRenderer(player, playerImageManager,  64);


        batch = new SpriteBatch();
        map.setCamera(camera);
        mainMenu = new MainMenu();
        settingGame = new SettingGame();

//..............................khoi tao buffalo
        arraywolf.add(new wolf(new Vector2(850,850),100,true));
        arraywolf.add(new wolf(new Vector2(850,750),100,false));
        arraybuffalo.add(new Buffalo(new Vector2(650,500),100,1,1,true));
        arraybuffalo.add(new Buffalo(new Vector2(650,600),100,1,1,true));
        arraybuffalo.add(new Buffalo(new Vector2(650,550),100,1,1,true));
        stopTimereproduction = TimeUtils.millis();
        stopTimehungry = TimeUtils.millis();
    }

    @Override
    public void render() {

//............................buffalo

        if (TimeUtils.timeSinceMillis(stopTimereproduction) >= 30000) {
            if(arraybuffalo.size()<10) {
                arraybuffalo.add(new Buffalo(new Vector2(700, 550), 100, 1, 1, true));
                stopTimereproduction = TimeUtils.millis();
            }
        }
        if (TimeUtils.timeSinceMillis(stopTimehungry) >= 5000) {
            for(Buffalo x:arraybuffalo){
                if(x.gethungry()>0) {
                    x.sethungry(10);
                }
            }
            stopTimehungry = TimeUtils.millis();
        }





        camera.setToOrtho(false,500,282);
        if (mainMenu.isMenuActive()) {
            mainMenu.handleInput();
            ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
            batch.setProjectionMatrix(camera.combined);
            mainMenu.render(batch); // Vẽ menu
        } else {

            settingGame.handleInput();

            if (settingGame.isActive()) {
                ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
                map.render();
                batch.setProjectionMatrix(camera.combined);
                batch.begin();
                playerRenderer.render(batch);
//..................cho buffalo

                for (int i = 0; i < arraybuffalo.size(); i++) {
                    Buffalo x = arraybuffalo.get(i);
                    for (int j =0; j < arraybuffalo.size(); j++) {
                        if(i!=j) {
                            Buffalo y = arraybuffalo.get(j);
                            x.dam(y);
                        }
                    }
                    if(arraybuffalo.get(i).getmau()==0){
                        arraybuffalo.remove(i);
                    }
                    x.ve(batch, 32, Gdx.graphics.getDeltaTime(),camera);
                    arraywolf.get(i).hoatdong(arraywolf,arraybuffalo,batch,32,Gdx.graphics.getDeltaTime(),camera);

                }

                batch.end();
                settingGame.render(batch);
            } else {
                float deltaTime = Gdx.graphics.getDeltaTime();
                player.update(deltaTime);
                camera.position.set(player.getPosition().x, player.getPosition().y, 0);
                camera.update();
                ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
                map.render();
                batch.setProjectionMatrix(camera.combined);
//......................cho buffalo
                for (int i = 0; i < arraybuffalo.size(); i++) {
                    Buffalo x = arraybuffalo.get(i);
                    for (int j = 0; j < arraybuffalo.size(); j++) {
                        if(i!=j) {
                            Buffalo y = arraybuffalo.get(j);
                            x.dam(y);
                        }
                    }
                    x.ve(batch, 32, Gdx.graphics.getDeltaTime(),camera);
                    if(arraybuffalo.get(i).getmau()==0){
                        arraybuffalo.remove(i);
                    }
                }
                for(int i=0;i<arraywolf.size();i++){
                    arraywolf.get(i).hoatdong(arraywolf,arraybuffalo,batch,37.67f,Gdx.graphics.getDeltaTime(),camera);
                }

//--------------------------cho player

                playerRenderer.render(batch);
                selecbox=new Selecbox(player.getPosition(),batch,map);
                if(player.isPlowing()) {
                    timeCoolDownBar = new TimeCoolDown(player.getPosition(), player.getStartPlow(), player.getTimeToPlow(), batch, map);
                }
            }
        }
    }


    @Override
    public void dispose() {
        batch.dispose();
        playerRenderer.dispose();
        map.dispose();
        mainMenu.dispose();
        settingGame.dispose();

    }
}
