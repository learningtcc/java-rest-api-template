package com.travelstart.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.travelstart.api.handler.ErrorHandler;
import com.travelstart.api.handler.FailHandler;
import com.travelstart.api.handler.LoggingHandler;
import com.travelstart.api.handler.NewRelicHandler;
import com.travelstart.api.handler.SayHandler;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestRoutes extends RouteBuilder {

    @Autowired
    private SayHandler sayHandler;

    @Autowired
    private NewRelicHandler newRelicHandler;

    @Autowired
    private LoggingHandler loggingHandler;

    @Autowired
    private FailHandler failHandler;

    @Autowired
    private ErrorHandler errorHandler;

    @Autowired
    private CamelContext context;

    @Override
    public void configure() throws Exception {
        // @formatter:off

        restConfiguration("netty4-http")
            .bindingMode(RestBindingMode.json)
            .endpointProperty("nettySharedHttpServer", "#sharedNettyHttpServer")
        ;

        // log requests
        interceptFrom("*")
            .to("log:headers?level=INFO&showHeaders=true&multiline=true")
            .bean(loggingHandler, "logRequest")
            .bean(newRelicHandler)
        ;

        // log responses
        onCompletion()
            .bean(loggingHandler, "logResponse")
        ;

        JsonDataFormat errorJson = new JsonDataFormat(JsonLibrary.Jackson);
        errorJson.setPrettyPrint(true);
        errorJson.setInclude(JsonInclude.Include.NON_NULL.toString());

        
        // error handler
        onException(Exception.class)
            .bean(errorHandler)
            .handled(true)
            .marshal(errorJson)
            .stop()
        ;

        
        // the REST API
        rest().path("/api")
            .produces("application/json")
            .bindingMode(RestBindingMode.off)
            .get("/ping.json")
                .route().transform(constant("{\"ok\":true}")).routeId("ping-get-route")
            .endRest()
            .post("/ping.json")
                .route().transform(constant("{\"ok\":true}"))
            .endRest()
            .head("/ping.json")
                .route().transform(constant("{\"ok\":true}"))
            .endRest()
            .setId("pingRoute")
        ;
        
        rest().path("/api")
            .get("/fail")
                .produces("application/json")
                .route().bean(failHandler, "letItFail")
            .endRest()
            .head("/fail")
                .produces("application/json")
                .route().bean(failHandler, "letItFail")
            .endRest()
            .setId("failRoute")
            
        ;

        rest().path("/say")
            .get("/hello")
                .produces("application/json")
                .route().bean(sayHandler)
            .endRest()
            .post("/hello")
                .produces("application/json")
                .route().bean(sayHandler)
            .endRest()
            .setId("sayRoute");
        ;

        // @formatter:on   
    }
    

}
