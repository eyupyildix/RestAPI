package com.eyupyildix.module;

import com.eyupyildix.impl.logger.ModuleLogger;
import com.google.inject.name.Named;

import java.util.logging.Formatter;
import java.util.logging.Logger;

public abstract class AbstractModule {

    private final Logger logger;
    private boolean enabled;

    public AbstractModule(@Named("logger-formatter") Formatter formatter) {
        this.logger = new ModuleLogger(this.getName(), formatter);
    }

    public Logger getLogger() {
        return this.logger;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void enable() {
        this.enabled = true;
    }

    public abstract String getName();
    public abstract void start();
}
