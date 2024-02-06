package com.eyupyildix.impl.endpoint;

import com.eyupyildix.endpoint.Endpoint;
import com.eyupyildix.endpoint.EndpointException;
import com.eyupyildix.endpoint.EndpointMeta;
import com.eyupyildix.endpoint.HttpMethod;
import com.sun.net.httpserver.HttpExchange;

@EndpointMeta(path="/test", supportedMethods=HttpMethod.GET, authNeeded=false)
public class TestEndpoint extends Endpoint {

    @Override
    public Object responseGet(HttpExchange exchange) throws EndpointException {
        return "Test endpoint";
    }
}
