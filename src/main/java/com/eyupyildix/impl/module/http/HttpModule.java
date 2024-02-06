package com.eyupyildix.impl.module.http;

import com.eyupyildix.config.Config;
import com.eyupyildix.module.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Formatter;

public class HttpModule extends AbstractModule {

    private final Injector injector;
    private final Config config;

    @Inject
    public HttpModule(Injector injector, @Named("logger-formatter") Formatter formatter, @Named("bootstrap-config") Config config) {
        super(formatter);
        this.injector = injector;
        this.config = config;
    }

    @Override
    public String getName() {
        return "HttpModule";
    }

    @Override
    public void start() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            HttpHandler handler = new DefaultHttpHandler(this.getLogger());
            injector.injectMembers(handler);

            server.createContext("/", handler);
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        getLogger().info(String.format("HTTP Server listening on %s:%d", config.getValue("http_address"), Integer.parseInt((String)config.getValue("http_port"))));
    }
}
