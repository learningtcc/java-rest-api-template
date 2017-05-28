package com.travelstart.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.travelstart.api.handler.ErrorHandler;
import com.travelstart.api.handler.FailHandler;
import com.travelstart.api.handler.LoggingHandler;
import com.travelstart.api.handler.NewRelicHandler;
import com.travelstart.api.handler.SayHandler;
import com.travelstart.api.model.ErrorResponse;
import com.travelstart.api.model.Message;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
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
            .enableCORS(true)
            .host(Boot.HOST)
            .port(Boot.PORT)

            // swagger docs
            .apiContextPath("/api-doc")
                .apiProperty("api.title", "REST Template API").apiProperty("api.version", "1.0")
                // and enable CORS
                .apiProperty("cors", "true")
                .apiProperty("host", Boot.HOST + ":" + Boot.PORT)
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


        
        // error handler
        JsonDataFormat errorJson = new JsonDataFormat(JsonLibrary.Jackson);
        errorJson.setPrettyPrint(true);
        errorJson.setInclude(JsonInclude.Include.NON_NULL.toString());

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
                .description("Checks if the API is healthy")
                .responseMessage()
                    .code(200)
                        .message("If API is alive and well")
                .endResponseMessage()
                .responseMessage()
                    .code("!= 200")
                    .message("API not ok")
                .endResponseMessage()
                .route().transform(constant("{\"ok\":true}")).routeId("ping-get-route")
            .endRest()

            .post("/ping.json")
                .description("Checks if the API is healthy")
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
                .param()
                    .name("message").description("message to be sent").type(RestParamType.query).dataType("string").required(true)
                .endParam()
                .responseMessage()
                    .code(200).message("successful")
                    .responseModel(Message.class)
                .endResponseMessage()
                .responseMessage()
                    .code("!= 200").message("failure")
                    .responseModel(ErrorResponse.class)
                .endResponseMessage()
                .route().bean(sayHandler)
            .endRest()
            .setId("sayRoute");
        ;

        // @formatter:on   
    }
    

}
