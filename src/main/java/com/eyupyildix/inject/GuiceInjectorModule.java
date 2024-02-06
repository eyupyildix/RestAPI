package com.eyupyildix.inject;

import com.eyupyildix.config.Config;
import com.eyupyildix.endpoint.Endpoint;
import com.eyupyildix.impl.PublicAPI;
import com.eyupyildix.impl.config.DefaultConfig;
import com.eyupyildix.impl.config.ManifestFile;
import com.eyupyildix.impl.module.http.DefaultHttpHandler;
import com.eyupyildix.impl.endpoint.EndpointHolder;
import com.eyupyildix.impl.endpoint.FaviconEndpoint;
import com.eyupyildix.impl.endpoint.TestEndpoint;
import com.eyupyildix.impl.logger.DefaultLoggerFormatter;
import com.eyupyildix.impl.module.DefaultModuleHolder;
import com.eyupyildix.impl.module.http.HttpModule;
import com.eyupyildix.module.Holder;
import com.google.inject.*;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.eyupyildix.module.AbstractModule;
import com.sun.net.httpserver.HttpHandler;

import java.util.logging.Formatter;

public class GuiceInjectorModule extends com.google.inject.AbstractModule {

    @Override
    protected void configure() {
        // Modules
        bind(AbstractModule.class).annotatedWith(Names.named("module-main")).to(PublicAPI.class).asEagerSingleton();
        bind(AbstractModule.class).annotatedWith(Names.named("http-server")).to(HttpModule.class).asEagerSingleton();

        // HTTP Server Handler
        bind(HttpHandler.class).to(DefaultHttpHandler.class).asEagerSingleton();

        // Endpoints
        bind(Endpoint.class).annotatedWith(Names.named("endpoint-test")).to(TestEndpoint.class);
        bind(Endpoint.class).annotatedWith(Names.named("endpoint-favicon")).to(FaviconEndpoint.class).asEagerSingleton(); // This one is singleton due to server doesn't support Favicon icon.

        // Logger
        bind(Formatter.class).annotatedWith(Names.named("logger-formatter")).to(DefaultLoggerFormatter.class).asEagerSingleton();

        // Config
        bind(Config.class).annotatedWith(Names.named("bootstrap-config")).to(DefaultConfig.class).asEagerSingleton();
        bind(Config.class).annotatedWith(Names.named("manifest")).to(ManifestFile.class).asEagerSingleton();
    }

    @Provides
    @Named("holder-module")
    @Singleton
    @SuppressWarnings("unused")
    public Holder<AbstractModule> provideModuleHolder() {
        return new DefaultModuleHolder();
    }

    @Provides
    @Named("holder-endpoint")
    @Singleton
    @SuppressWarnings("unused")
    public Holder<Endpoint> provideEndpointHolder() {
        return new EndpointHolder();
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }
}
