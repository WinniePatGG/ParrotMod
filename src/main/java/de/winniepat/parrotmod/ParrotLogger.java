package de.winniepat.parrotmod;

import de.winniepat.parrotmod.config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParrotLogger {
    public static void logInfo(Class<?> className, String message) {
        Logger logger = LoggerFactory.getLogger(ConfigManager.class);
        if (Parrotmod.DEBUG) {
            logger.info(message);
        }
    }

    public static void logError(Class<?> className, String message, Exception e) {
        Logger logger = LoggerFactory.getLogger(className);
        if (Parrotmod.DEBUG) {
            logger.error(message, e);
        }
    }
}
