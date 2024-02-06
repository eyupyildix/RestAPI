package com.eyupyildix.endpoint;

import com.sun.net.httpserver.HttpExchange;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class Endpoint {

    private final Map<HttpMethod, Function<HttpExchange, Object>> registeredMethods = new HashMap<>();

    public Endpoint() {
        registeredMethods.put(HttpMethod.CONNECT, this::responseConnect);
        registeredMethods.put(HttpMethod.DELETE, this::responseDelete);
        registeredMethods.put(HttpMethod.GET, this::responseGet);
        registeredMethods.put(HttpMethod.HEAD, this::responseHead);
        registeredMethods.put(HttpMethod.OPTIONS, this::responseOptions);
        registeredMethods.put(HttpMethod.POST, this::responsePost);
        registeredMethods.put(HttpMethod.PUT, this::responsePut);
        registeredMethods.put(HttpMethod.TRACE, this::responseTrace);
    }

    public Object response(HttpMethod method, HttpExchange exchange) {
        return this.registeredMethods.get(method).apply(exchange);
    }

    public Object responseConnect(HttpExchange exchange) throws EndpointException {
        return null;
    }

    public Object responseDelete(HttpExchange exchange) throws EndpointException {
        return null;
    }

    public Object responseGet(HttpExchange exchange) throws EndpointException {
        return null;
    }

    public Object responseHead(HttpExchange exchange) throws EndpointException {
        return null;
    }

    public Object responseOptions(HttpExchange exchange) throws EndpointException {
        return null;
    }

    public Object responsePost(HttpExchange exchange) throws EndpointException {
        return null;
    }

    public Object responsePut(HttpExchange exchange) throws EndpointException {
        return null;
    }

    public Object responseTrace(HttpExchange exchange) throws EndpointException {
        return null;
    }
}
