package me.duncanruns.beachfilter;

import me.voidxwalker.autoreset.Atum;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class BeachFilterMod implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("beachfilter-mod");

    public static String lastToken = null;

    public static String getLastToken() {
        return lastToken;
    }

    public static void setLastToken(String lastToken) {
        BeachFilterMod.lastToken = lastToken;
        try {
            Files.write(Paths.get("beachtoken.txt"), lastToken.getBytes(StandardCharsets.UTF_8));
        } catch (IOException ignored) {
        }
    }

    public static boolean shouldRun() {
        return Atum.isRunning && Objects.equals(Atum.seed, "");
    }

    @Override
    public void onInitialize() {
        LOGGER.log(Level.INFO, "BeachFilterMod: Initializing");
        try {
            lastToken = new String(Files.readAllBytes(Paths.get("beachtoken.txt")));
        } catch (IOException ignored) {
        }
    }


}