package com.eyupyildix.impl;

import com.eyupyildix.config.Config;
import com.eyupyildix.endpoint.Endpoint;
import com.eyupyildix.module.AbstractModule;
import com.eyupyildix.module.Holder;
import com.eyupyildix.timing.Timing;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.google.inject.spi.LinkedKeyBinding;

import java.util.List;
import java.util.logging.Formatter;
import java.util.stream.Collectors;

public class PublicAPI extends AbstractModule {

    private final Config manifest;
    private final Injector injector;
    private final Holder<AbstractModule> moduleHolder;
    private final Holder<Endpoint> endpointHolder;

    @Inject
    public PublicAPI(Injector injector,
                     @Named("manifest") Config manifest,
                     @Named("logger-formatter") Formatter formatter,
                     @Named("holder-module") Holder<AbstractModule> moduleHolder,
                     @Named("holder-endpoint") Holder<Endpoint> endpointHolder) {
        super(formatter);

        this.injector = injector;
        this.manifest = manifest;
        this.moduleHolder = moduleHolder;
        this.endpointHolder = endpointHolder;
    }

    @Override
    public String getName() {
        return "Main";
    }

    @Override
    public void start() {
        this.enable();

        getLogger().info(String.format("RestAPI Version: %s", manifest.getValue("Implementation-Format-Version")));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }

        registerHolders(injector);

        getLogger().info("Starting modules...");
        List<AbstractModule> modules_to_start = moduleHolder.getRegisteredValues().stream()
                .filter(module -> !module.isEnabled()).toList();
        try (Timing ignored = new Timing("Starting modules", this.getLogger(), String.format("Modules started: %s", modules_to_start.stream().map(AbstractModule::getName).collect(Collectors.joining(", "))))) {
            modules_to_start.forEach(AbstractModule::start);
        }
    }

    private void registerHolders(Injector injector) {
        injector.getAllBindings().keySet().stream().
                filter(key -> key.getTypeLiteral().getRawType().isAssignableFrom(AbstractModule.class))
                .forEach(key ->
                        moduleHolder.registerValue((AbstractModule) ((LinkedKeyBinding<?>) injector.getBinding(key)).getProvider().get()));

        injector.getAllBindings().keySet().stream().
                filter(key -> key.getTypeLiteral().getRawType().isAssignableFrom(Endpoint.class))
                .forEach(key ->
                        endpointHolder.registerValue((Endpoint) ((LinkedKeyBinding<?>) injector.getBinding(key)).getProvider().get()));

    }
}
