package com.eyupyildix.impl.endpoint;

import com.eyupyildix.endpoint.Endpoint;
import com.eyupyildix.endpoint.EndpointMeta;
import com.eyupyildix.endpoint.HttpMethod;

@EndpointMeta(path = "/favicon.ico", supportedMethods = HttpMethod.GET, authNeeded = false)
public class FaviconEndpoint extends Endpoint { // Favicon is not supported
}
