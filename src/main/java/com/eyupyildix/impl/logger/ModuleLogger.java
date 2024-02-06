package com.eyupyildix.impl.logger;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Logger;

public class ModuleLogger extends Logger {

    @Inject
    public ModuleLogger(String name, @Named("logger-formatter") Formatter formatter) {
        super(name, null);

        this.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(formatter);
        this.addHandler(handler);
    }
}
