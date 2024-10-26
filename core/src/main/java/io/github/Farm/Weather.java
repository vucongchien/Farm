package io.github.Farm.weather;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.Farm.Map.MapManager;
import io.github.Farm.SoundManager;
import io.github.Farm.player.PlayerController;

public class Weather {
    private static Weather instance;
    public static Weather getInstance() {
        if (instance == null) {
            instance = new Weather();
        }
        return instance;
    }
    private String currentWeather;
    private float timeOfDay;
    private float weatherDuration; // Thời gian duy trì thời tiết hiện tại
    private final float minWeatherDuration = 3f; // Thời gian tối thiểu của mỗi thời tiết
    private final float maxWeatherDuration = 5f; // Thời gian tối đa của mỗi thời tiết
    private Texture rainTexture; // Hình ảnh giọt mưa
    private Texture sunnyIcon;
    private Texture nightIcon;
    private Texture rainIcon;
    private Texture cloudIcon;
    private Texture darkCloud;
    private Texture darkRain;
    private final int rainDropCount = 5; // Số giọt mưa
    private Texture cloudTexture; // Hình ảnh đám mây
    private float cloudSpeed = 30f; // Tốc độ di chuyển của mây
    private float[] cloudPositions; // Vị trí đám mây trên màn hình
    private final int cloudCount = 3; // Số lượng đám mây
    private boolean night = false;


    public Weather() {
        this.currentWeather = "Sunny"; // Giá trị mặc định
        this.weatherDuration = getRandomDuration(); // Đặt thời gian ngẫu nhiên cho thời tiết ban đầu
        this.rainTexture = new Texture(Gdx.files.internal("Weather/rain_drops-01.png")); // Tải hình ảnh giọt mưa
        this.sunnyIcon = new Texture(Gdx.files.internal("Weather/sunnyIcon.png"));
        this.nightIcon = new Texture(Gdx.files.internal("Weather/nightIcon.png"));
        this.rainIcon = new Texture(Gdx.files.internal("Weather/rainIcon.png"));
        this.cloudIcon = new Texture(Gdx.files.internal("Weather/cloudIcon.png"));
        this.darkCloud = new Texture(Gdx.files.internal("Weather/darkCloud.png"));
        this.darkRain = new Texture(Gdx.files.internal("Weather/darkRain.png"));
        this.cloudTexture = new Texture(Gdx.files.internal("Weather/cloudy.png")); // Tải hình ảnh đám mây
        this.cloudPositions = new float[cloudCount]; // Khởi tạo mảng vị trí mây
        initCloudPositions(); // Thiết lập vị trí đám mây
        this.timeOfDay = 0.0f; // Đặt giá trị mặc định là ban ngày
    }

    private void initCloudPositions() {
        for (int i = 0; i < cloudCount; i++) {
            cloudPositions[i] = (float) Math.random() * Gdx.graphics.getWidth(); // Vị trí x ngẫu nhiên
        }
    }
    public void update(float deltaTime) {
        // Cập nhật thời gian trong ngày
        timeOfDay += deltaTime / 100; // Thay đổi tỷ lệ tùy theo tốc độ bạn muốn thời gian trôi qua
        if (timeOfDay > 1) {
            timeOfDay = 0; // Reset lại khi qua 1
        }

        // Cập nhật trạng thái thời tiết nếu cần
        weatherDuration -= deltaTime; // Đếm ngược thời gian
        if (weatherDuration <= 0) { // Khi hết thời gian, thay đổi thời tiết
            changeWeather();
            weatherDuration = getRandomDuration(); // Đặt thời gian cho thời tiết mới
        }

        // Kiểm tra và thiết lập chế độ đêm
        if (timeOfDay < 0.5) {
            night = false;

        } else {
            night = true;
        }

        // Cập nhật vị trí đám mây khi thời tiết là "Cloudy"
        if (currentWeather.equals("Cloudy") || currentWeather.equals("Rainy")) {
            for (int i = 0; i < cloudCount; i++) {
                cloudPositions[i] += cloudSpeed * deltaTime; // Di chuyển mây từ trái sang phải
                if (cloudPositions[i] > Gdx.graphics.getWidth()) {
                    cloudPositions[i] = -cloudTexture.getWidth(); // Nếu mây ra khỏi màn hình, đặt lại vị trí
                }
            }
        }

    }
    public boolean getNight() {
        return night;
    }

    private void changeWeather() {
        String[] weathers = {"Sunny", "Rainy", "Cloudy"};
        currentWeather = weathers[(int) (Math.random() * weathers.length)];
    }

    private float getRandomDuration() {
        return minWeatherDuration + (float) Math.random() * (maxWeatherDuration - minWeatherDuration);
    }

    public void render(SpriteBatch batch, PlayerController player) {
        batch.setColor(Color.WHITE); // Đặt màu mặc định

        // Xác định vị trí của icon ở góc trên cùng bên phải
        float iconX =player.getPosition().x + sunnyIcon.getWidth()*11;
        float iconY =player.getPosition().y + sunnyIcon.getHeight()*6 - 20; // Cách lề trên 10 pixel


        switch (currentWeather) {
            case "Sunny":
                SoundManager.getInstance().stopRainSound();
                if(night){
                    batch.draw(nightIcon, iconX, iconY);
                }else{
                    batch.draw(sunnyIcon, iconX, iconY);
                }
                break;
            case "Rainy":
                SoundManager.getInstance().playRainSound();
                if(night){
                    batch.draw(darkRain, iconX, iconY);
                    batch.setColor(Color.GRAY.r, Color.GRAY.g, Color.GRAY.b, 0.8f);
                }else{
                    batch.draw(rainIcon, iconX, iconY);
                }
                drawClouds(batch);
                drawRain(batch,player);
                break;
            case "Cloudy":
                SoundManager.getInstance().stopRainSound();

                if(timeOfDay > 0.5){
                    batch.draw(darkCloud, iconX, iconY);
                    batch.setColor(Color.GRAY.r, Color.GRAY.g, Color.GRAY.b, 0.8f); // 0.5f cho độ trong suốt 50%
                }else{
                    batch.draw(cloudIcon, iconX, iconY);
                }
                drawClouds(batch);
                break;
        }

    }
    private void drawRain(SpriteBatch batch, PlayerController player) {
        if(night){
            batch.setColor(Color.BLUE.r, Color.BLUE.g, Color.BLUE.b, 10.0f); // 0.5f cho độ trong suốt 50%
        }else{
            batch.setColor(Color.BLUE.r, Color.BLUE.g, Color.BLUE.b, 2.5f); // 0.5f cho độ trong suốt 50%
        }
        float playerX = player.getPosition().x;
        float playerY = player.getPosition().y;

        // Giới hạn mưa bao phủ một khu vực xung quanh nhân vật với chiều rộng và cao của màn hình
        float rainAreaWidth = Gdx.graphics.getWidth();
        float rainAreaHeight = Gdx.graphics.getHeight();

        for (int i = 0; i < rainDropCount; i++) {
            float x = playerX + (float) Math.random() * rainAreaWidth - rainAreaWidth / 2;
            float y = playerY + (float) Math.random() * rainAreaHeight - rainAreaHeight / 2;
            batch.draw(rainTexture, x, y);
        }
    }

    private void drawClouds(SpriteBatch batch) {
        for (int i = 0; i < cloudCount; i++) {
            float y = Gdx.graphics.getHeight() * 1.065f; // Đặt vị trí y cao hơn
            batch.draw(cloudTexture, cloudPositions[i], y);
        }
    }

    public float getTimeOfDay() {
        return timeOfDay;
    }

    public String getCurrentWeather() {
        return currentWeather;
    }

    public void dispose() {
        rainTexture.dispose();
        cloudTexture.dispose();
    }
}
