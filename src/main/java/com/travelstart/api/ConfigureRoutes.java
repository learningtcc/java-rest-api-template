package com.travelstart.api;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class ConfigureRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // @formatter:off
        
        restConfiguration("netty4-http")
            .bindingMode(RestBindingMode.json)
            .endpointProperty("nettySharedHttpServer", "#sharedNettyHttpServer")
        ;

        // @formatter:on   
    }
    

}
