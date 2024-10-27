package io.github.Farm.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import io.github.Farm.Main;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new Main(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Farm");

        // Thiết lập màn hình full screen với độ phân giải 1920x1080
        configuration.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());

        configuration.useVsync(true);
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);

        //// Thay đổi icon của cửa sổ game nếu cần
        configuration.setWindowIcon("pixil-frame-0.png", "pixil-frame-0.png", "pixil-frame-0.png", "pixil-frame-0.png");
        return configuration;
    }
}
