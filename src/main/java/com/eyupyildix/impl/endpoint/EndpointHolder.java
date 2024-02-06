package com.eyupyildix.impl.endpoint;

import com.eyupyildix.endpoint.Endpoint;
import com.eyupyildix.module.Holder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EndpointHolder implements Holder<Endpoint> {

    private final List<Endpoint> endpoints;

    public EndpointHolder() {
        this.endpoints = new ArrayList<>();
    }

    @Override
    public List<Endpoint> getRegisteredValues() {
        return Collections.unmodifiableList(endpoints);
    }

    @Override
    public void registerValue(Endpoint endpoint) {
        endpoints.add(endpoint);
    }
}
