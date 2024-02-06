package com.eyupyildix.impl.module.http;

import com.eyupyildix.endpoint.Endpoint;
import com.eyupyildix.endpoint.EndpointException;
import com.eyupyildix.endpoint.EndpointMeta;
import com.eyupyildix.endpoint.HttpMethod;
import com.eyupyildix.module.Holder;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.lang3.EnumUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultHttpHandler implements HttpHandler {

    private final Logger logger;
    @Inject
    @Named("holder-endpoint")
    private Holder<Endpoint> holder;

    @Inject
    public DefaultHttpHandler(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            URI uri = exchange.getRequestURI();
            String[] path = uri.getPath().replaceFirst("^/", "").split("/");
            String endpointName = path[0];
            Endpoint requestedEndpoint = holder.getRegisteredValues().stream().filter(endpoint ->
                    endpoint.getClass().getAnnotation(EndpointMeta.class).path().equalsIgnoreCase("/" + endpointName)
            ).findFirst().orElse(null);

            if (requestedEndpoint == null)
                throw new EndpointException("Invalid endpoint", 404);

            EndpointMeta meta = requestedEndpoint.getClass().getAnnotation(EndpointMeta.class);
            if (meta.authNeeded()) {
                // TODO: AUTH
            }

            String method = exchange.getRequestMethod();
            if (!EnumUtils.isValidEnum(HttpMethod.class, method))
                throw new EndpointException("Invalid request method", 400);

            HttpMethod requestMethod = HttpMethod.valueOf(method);
            if (!Arrays.asList(meta.supportedMethods()).contains(requestMethod))
                throw new EndpointException(String.format("This endpoint does not support %s method", method));

            JSONObject response = new JSONObject().put("status", true);
            Object endpointResponse = requestedEndpoint.response(requestMethod, exchange);

            if (endpointResponse instanceof JSONObject || endpointResponse instanceof JSONArray)
                this.putInto(response, endpointResponse);
            else if (endpointResponse != null)
                response.put("message", endpointResponse);

            this.response(exchange, 200, response.toString());
        } catch (EndpointException endpointException) {
            responseError(exchange, endpointException.getStatusCode(), endpointException.getMessage());
        } catch (Throwable throwable) {
            responseError(exchange, 500, "Server error");
            this.logger.log(Level.WARNING, throwable.getMessage(), throwable);
        }
    }

    private void responseError(HttpExchange exchange, int statusCode, String jsonMessage) throws IOException {
        response(exchange, statusCode,
                new JSONObject(ImmutableMap.of(
                        "status", false,
                        "message", jsonMessage
                )).toString());
    }

    private void response(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.setAttribute("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.length());
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes(StandardCharsets.UTF_8));
        outputStream.close();
    }

    private void putInto(JSONObject parent, Object child) {
        if (child instanceof JSONObject)
            putInto(parent, (JSONObject) child);
        else
            putInto(parent, (JSONArray) child);
    }

    private void putInto(JSONObject parent, JSONObject child) {
        for (String key : child.keySet()) {
            parent.put(key, child.get(key));
        }
    }

    private void putInto(JSONObject parent, JSONArray array) {
        ListIterator<Object> iterator = array.toList().listIterator();
        while(iterator.hasNext()) {
            parent.put(String.valueOf(iterator.nextIndex()), iterator.next());
        }
    }
}
