/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  net.fabricmc.loader.api.FabricLoader
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package truefulys.optimizer.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ServerOptOutConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger((String)"TruefulysCrystalOptimizer");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("truefulys_optimizer_server.json");
    private static ServerOptOutConfig instance;
    public boolean allowCrystalOptimizer = false;

    public static ServerOptOutConfig get() {
        if (instance == null) {
            ServerOptOutConfig.load();
        }
        return instance;
    }

    public static void load() {
        if (Files.exists(CONFIG_PATH, new LinkOption[0])) {
            try (BufferedReader reader = Files.newBufferedReader(CONFIG_PATH);){
                instance = (ServerOptOutConfig)GSON.fromJson((Reader)reader, ServerOptOutConfig.class);
                if (instance == null) {
                    instance = new ServerOptOutConfig();
                }
            }
            catch (IOException exception) {
                LOGGER.error("Failed to read server config, using defaults", (Throwable)exception);
                instance = new ServerOptOutConfig();
            }
        } else {
            instance = new ServerOptOutConfig();
            ServerOptOutConfig.save();
        }
    }

    public static void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent(), new FileAttribute[0]);
            try (BufferedWriter writer = Files.newBufferedWriter(CONFIG_PATH, new OpenOption[0]);){
                GSON.toJson((Object)instance, (Appendable)writer);
            }
        }
        catch (IOException exception) {
            LOGGER.error("Failed to write server config", (Throwable)exception);
        }
    }
}

