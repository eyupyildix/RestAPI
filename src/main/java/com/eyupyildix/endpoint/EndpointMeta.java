package com.eyupyildix.endpoint;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EndpointMeta {
    String path();
    HttpMethod[] supportedMethods();
    boolean authNeeded();

}
