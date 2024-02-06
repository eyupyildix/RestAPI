package com.eyupyildix.timing;

import com.google.common.collect.ImmutableMap;

import java.io.Closeable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Timing implements Closeable {

    private final String operationName;
    private final Logger logger;
    private final long start;
    private final String message;

    public Timing(String operationName, Logger logger) {
        this(operationName, logger, null);
    }

    public Timing(String operationName, Logger logger, String message) {
        this.operationName = operationName;
        this.logger = logger;
        this.start = System.currentTimeMillis();
        this.message = message;
    }

    @Override
    public void close() {
        long end = System.currentTimeMillis();
        logger.log(Level.INFO, String.format(
                "%s took %s%s",
                operationName,
                formatTime(end - start),
                (message != null ? String.format(" (%s)", message) : "")
        ));
    }

    private static final Map<Float, String> units = ImmutableMap.of(
            (float) (1000 * 60 * 60), "h",
            (float) (1000 * 60), "m",
            1000f, "s"
    );

    public static String formatTime(long ms) {
        Map.Entry<Float, String> found = units.entrySet().stream().filter(entry -> ms >= entry.getKey()).findFirst().orElse(null);
        if (found == null)
            return String.format("%d%s", ms, "ms");

        return String.format("%.2f%s", (((ms / found.getKey()) % 60)), found.getValue());
    }
}
