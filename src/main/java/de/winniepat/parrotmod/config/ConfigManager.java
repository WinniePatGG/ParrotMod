package de.winniepat.parrotmod.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.winniepat.parrotmod.ParrotLogger;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("parrotmod.json");

    private static Config instance = new Config();
    private static final List<Consumer<Config>> LISTENERS = new ArrayList<>();

    public static Config getInstance() {
        return instance;
    }

    public static void registerListener(Consumer<Config> listener) {
        LISTENERS.add(listener);
    }

    public static void load() {
        if (!Files.exists(CONFIG_FILE)) {
            ParrotLogger.logInfo(ConfigManager.class, "No config file found, creating one with default values.");
            save();
            return;
        }

        try (var reader = Files.newBufferedReader(CONFIG_FILE)) {
            instance = GSON.fromJson(reader, Config.class);
            if (instance == null) {
                instance = new Config();
            }

            ParrotLogger.logInfo(ConfigManager.class, "Loaded config: " + GSON.toJson(instance));
            notifyListeners();
        } catch (IOException e) {
            ParrotLogger.logError(ConfigManager.class, "Failed to load config, Using defaults", e);
            instance = new Config();
        }
    }

    public static void save() {
        try {
            Files.createDirectories(CONFIG_FILE.getParent());
            try (var writer = Files.newBufferedWriter(CONFIG_FILE)) {
                GSON.toJson(instance, writer);
                notifyListeners();
            }
        } catch (IOException e) {
            ParrotLogger.logError(ConfigManager.class, "Failed to save config.", e);
        }
    }

    private static void notifyListeners() {
        for (Consumer<Config> listener : LISTENERS) {
            listener.accept(instance);
        }
    }
}
