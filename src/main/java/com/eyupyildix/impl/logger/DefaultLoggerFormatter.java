package com.eyupyildix.impl.logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class DefaultLoggerFormatter extends Formatter {

    private final DateFormat dateFormat = new SimpleDateFormat("dd/MM/y HH:mm:ss");

    @Override
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append(dateFormat.format(record.getMillis()));
        builder.append("] [");
        builder.append(record.getLevel().getName());
        builder.append("] ");

        if (!record.getLoggerName().equalsIgnoreCase("main")) {
            builder.append("[");
            builder.append(record.getLoggerName());
            builder.append("] ");
        }

        builder.append(record.getMessage());
        builder.append("\n");

        if (record.getThrown() != null) {
            StringWriter writer = new StringWriter();
            record.getThrown().printStackTrace(new PrintWriter(writer));
            builder.append(writer);
        }

        return builder.toString();
    }
}
