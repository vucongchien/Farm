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
    import io.github.Farm.ui.Other.Expression;
    import io.github.Farm.ui.Other.ExpressionManager;

    public class PlantRenderer implements Collider, RenderableEntity {
        private final Vector2 position;
        private PlantType type;
        private PlantStage stage;
        private long plantTime;
        private long lastStageChangeTime;
        private long lastWaterTime;
        private boolean isWatered;
        private boolean isHarvestable;
       // private boolean isdie;

        private static final long WATERING_DURATION = 20000;
        private static final long GROWTH_TIME = 12000;
        private static final long NEED_WATER_TIME = 45000;

        private ExpressionManager expressionManager;

        private Texture currentTexture;
        private PlantImageManager imageManager;

        private Rectangle plantCollider;

        private float width = 16f;
        private float height = 16f;

        public PlantRenderer(Vector2 position, PlantType type) {
            this.position = position.cpy();
            this.type = type;
            this.stage = PlantStage.SPROUT;
            this.plantTime = TimeUtils.millis();
            this.lastStageChangeTime = plantTime;
            this.lastWaterTime =plantTime;
            this.isWatered = false;
            this.isHarvestable = false;
            this.expressionManager=new ExpressionManager();

            this.imageManager = new PlantImageManager(type);
            this.currentTexture = imageManager.getTexture(PlantStage.HARVESTED);

            this.plantCollider = new Rectangle(this.position.x * 16, this.position.y * 16, width, height);
        }

        public PlantRenderer(Vector2 position, PlantType type,PlantStage stage) {
            this.position = position.cpy();
            this.type = type;
            this.stage = stage;
            this.plantTime = TimeUtils.millis();
            this.lastStageChangeTime = plantTime;
            this.lastWaterTime =plantTime;
            this.isWatered = false;
            this.isHarvestable = true;
            this.expressionManager=new ExpressionManager();

            this.imageManager = new PlantImageManager(type);
            this.currentTexture = imageManager.getTexture(PlantStage.HARVESTED);

            this.plantCollider = new Rectangle(this.position.x * 16, this.position.y * 16, width, height);
        }

        public void update(float deltaTime) {
           // if(isdie) return;

            long timeSinceLastStageChange = TimeUtils.timeSinceMillis(lastStageChangeTime);
            long timeSinceLastWatered = TimeUtils.timeSinceMillis(lastWaterTime);

            if (isWatered) {
                expressionManager.setExpression(Expression.NULL);
                if (timeSinceLastStageChange > GROWTH_TIME) {
                    advanceGrowthStage();
                }
                if (timeSinceLastWatered > WATERING_DURATION) {
                    isWatered = false;

                }
            }

            if(!isWatered){
                expressionManager.setExpression(Expression.WORKING);
            }

//            if(timeSinceLastWatered > NEED_WATER_TIME){
//                isdie=true;
//                expressionManager.setExpression(Expression.NULL);
//                return;
//            }


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
            lastWaterTime = TimeUtils.millis();

        }



        public void dropItems(Vector2 position,boolean isPlayerFacingRight) {
            String namePlant=type.toString();
            if(isHarvestable) {
                ItemManager.getInstance().addItemVip("FOOD_" + namePlant, position, isPlayerFacingRight, 1);
                ItemManager.getInstance().addItemVip("SEED_" + namePlant, position, isPlayerFacingRight, 2);
                return;
            }
            if(stage.toString().equals("MATURE")){
                ItemManager.getInstance().addItemVip("FOOD_" + namePlant, position, isPlayerFacingRight, 1);
                ItemManager.getInstance().addItemVip("SEED_" + namePlant, position, isPlayerFacingRight, 1);
                return;
            }
            ItemManager.getInstance().addItemVip("SEED_" + namePlant, position, isPlayerFacingRight, 1);

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
                    dropItems(position,playerController.isFacingRight());
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
            expressionManager.render(new Vector2(renderX,renderY),camera,10f,0.4f);
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

        public float getHeight() {
            return currentTexture.getHeight();
        }

        public float getWidth() {
            return currentTexture.getWidth();
        }

        public boolean isHarvestable() {
            return isHarvestable;
        }
    }
