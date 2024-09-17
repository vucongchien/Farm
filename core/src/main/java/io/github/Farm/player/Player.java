package io.github.Farm.player;

import io.github.Farm.Map.MapInteractionHandler;
import io.github.Farm.Plants.PlantManager;
import io.github.Farm.player.old.PlayerController;
import io.github.Farm.player.old.PlayerImageManager;
import io.github.Farm.player.old.PlayerRenderer;

public class Player {
    private PlayerRenderer playerRenderer;
    private PlayerController playerController;
    private PlayerImageManager playerImageManager;


    public Player(MapInteractionHandler mapInteractionHandler,PlantManager plantManager){
        //playerController = new PlayerController(new Vector2(200, 200), 100, mapInteractionHandler, plantManager); // Use tile size
        playerImageManager = new PlayerImageManager();
        playerRenderer = new PlayerRenderer(playerController, playerImageManager, 64);
    }

















//    public PlayerController getPlayerController() {
//        return playerController;
//    }
//
//    public PlayerRenderer getPlayerRenderer() {
//        return playerRenderer;
//    }
//
//    public PlayerImageManager getPlayerImageManager() {
//        return playerImageManager;
//    }
}
