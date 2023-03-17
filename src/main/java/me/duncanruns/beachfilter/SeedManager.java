package me.duncanruns.beachfilter;

import me.duncanruns.beachfilter.runner.FSGBeach;
import me.duncanruns.beachfilter.runner.FilterResult;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.atomic.AtomicBoolean;

public final class SeedManager {
    private static final AtomicBoolean findingSeed = new AtomicBoolean(false);
    private static final AtomicBoolean seedExists = new AtomicBoolean(false);
    private static FilterResult currentResult;

    private SeedManager() {
    }

    public static boolean canTake() {
        if (seedExists.get()) {
            if (System.currentTimeMillis() - currentResult.getStartTime() > 60_000) {
                seedExists.set(false);
            }
            return true;
        }
        return false;
    }

    public static boolean isFindingSeed() {
        return findingSeed.get();
    }

    public static void find() {
        if (findingSeed.get()) {
            return;
        }
        findingSeed.set(true);
        new Thread(() -> {
            FilterResult out;
            try {
                out = FSGBeach.findSeed();
            } catch (IOException | NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } finally {
                findingSeed.set(false);
            }
            BeachFilterMod.LOGGER.log(Level.INFO, "BeachFilterMod: Found seed!");
            currentResult = out;
            seedExists.set(true);
        }, "seed-finder").start();
    }

    public static FilterResult take() {
        seedExists.set(false);
        return currentResult;
    }
}
