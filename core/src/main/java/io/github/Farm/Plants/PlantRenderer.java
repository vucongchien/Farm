    package io.github.Farm.Plants;

    import com.badlogic.gdx.graphics.Camera;
    import com.badlogic.gdx.graphics.g2d.SpriteBatch;
    import com.badlogic.gdx.graphics.Texture;
    import com.badlogic.gdx.math.Rectangle;
    import com.badlogic.gdx.math.Vector2;
    import com.badlogic.gdx.utils.TimeUtils;
    import io.github.Farm.Interface.RenderableEntity;
    import io.github.Farm.Interface.Collider;
    import io.github.Farm.inventory.ItemManager;
    import io.github.Farm.player.PlayerController;

    public class PlantRenderer implements Collider, RenderableEntity {
        private final Vector2 position;
        private PlantType type;
        private PlantStage stage;
        private long plantTime;
        private long lastStageChangeTime;
        private long needWaterTime;
        private boolean isWatered;
        private boolean isHarvestable;

        private static final long WATERING_DURATION = 12000;
        private static final long GROWTH_TIME = 6000;
        private static final long NEED_WATER_TIME = 45000;




        private Texture currentTexture;
        private PlantImageManager imageManager;

        private Rectangle plantCollider;

        private float width = 16f;
        private float height = 16f;

        public PlantRenderer(Vector2 position, PlantType type) {
            this.position = position;
            this.type = type;
            this.stage = PlantStage.SPROUT;
            this.plantTime = TimeUtils.millis();
            this.lastStageChangeTime = plantTime;
            this.needWaterTime=plantTime;
            this.isWatered = false;
            this.isHarvestable = false;

            imageManager = new PlantImageManager(type);
            currentTexture = imageManager.getTexture(PlantStage.HARVESTED);

            plantCollider = new Rectangle(this.position.x * 16, this.position.y * 16, width, height);
        }

        public void update(float deltaTime) {
            long timeSinceLastStageChange = TimeUtils.timeSinceMillis(lastStageChangeTime);
            long timeSinceLastWatered = TimeUtils.timeSinceMillis(needWaterTime);

            if (isWatered) {
                if (timeSinceLastStageChange > GROWTH_TIME) {
                    advanceGrowthStage();
                }
                if (timeSinceLastWatered > WATERING_DURATION) {
                    isWatered = false;
                    needWaterTime = TimeUtils.millis();
                }
            }

            currentTexture = imageManager.getTexture(stage);

        }

        private void advanceGrowthStage() {
            switch (stage) {
                case SEED:
                    stage = PlantStage.SPROUT;
                    break;
                case SPROUT:
                    stage = PlantStage.YOUNG;
                    break;
                case YOUNG:
                    stage = PlantStage.MATURE;
                    break;
                case MATURE:
                    stage = PlantStage.HARVESTED;
                    isHarvestable = true;
                    break;
                default:
                    break;
            }

            lastStageChangeTime = TimeUtils.millis();
        }

        public void setStage(PlantStage a){stage=a;}

        public void water() {
            isWatered = true;
            needWaterTime = TimeUtils.millis();
        }

        public void grow() {
            advanceGrowthStage();
        }

        public boolean isHarvestable() {
            return isHarvestable;
        }

        public void harvest() {
            if (isHarvestable) {
                stage = PlantStage.HARVESTED;
                isHarvestable = false;
            }
        }

        public void dropItems(String namePlant,Vector2 position,boolean isPlayerFacingRight) {
            ItemManager.getInstance().addItem("FOOD_"+namePlant,position,isPlayerFacingRight,2);
            ItemManager.getInstance().addItem("SEED_"+namePlant,position,isPlayerFacingRight,2);
        }

        //use interface
        @Override
        public Rectangle getCollider() {
            return plantCollider;
        }

        @Override
        public void onCollision(Collider other) {
            if(other instanceof PlayerController){
                PlayerController playerController =(PlayerController) other;

                if(playerController.getCurrentState().startsWith("WATER_")){
                    water();
                }

                if(playerController.getCurrentState().startsWith("HIT_")){
                    dropItems(type.toString(),position,playerController.isFacingRight());
                }



            }
        }

        @Override
        public float getY() {
            return position.y*16+ 11f;
        }

        @Override
        public void render(SpriteBatch batch, Camera camera) {


            float tileSize = 16f;

            float renderX = position.x*16 + (tileSize / 2f) - (currentTexture.getWidth() / 2f);
            float renderY = position.y*16+ (tileSize / 2f-3);
            batch.begin();
            batch.draw(currentTexture, renderX, renderY, currentTexture.getWidth(), currentTexture.getHeight());
            batch.end();


        }



        public Vector2 getPosition() {
            return position;
        }

        public PlantStage getStage() {
            return stage;
        }

        public PlantType getType(){
            return type;
        }


    }
