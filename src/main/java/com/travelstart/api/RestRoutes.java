package com.travelstart.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.travelstart.api.handler.BookingHandler;
import com.travelstart.api.handler.ErrorHandler;
import com.travelstart.api.handler.LoggingHandler;
import com.travelstart.api.model.Booking;

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
    private LoggingHandler loggingHandler;

    @Autowired
    private ErrorHandler errorHandler;

    @Autowired
    private CamelContext context;

    @Autowired
    private BookingHandler bookHandler;

    @Override
    public void configure() throws Exception {
        // @formatter:off

        restConfiguration("netty4-http")
            .bindingMode(RestBindingMode.json)
            .endpointProperty("nettySharedHttpServer", "#sharedNettyHttpServer")
            .enableCORS(true)
            //If you want to use your own headers, please add them to this list
            .corsHeaderProperty("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers")
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
            
            .get("/ping")
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

            // booking put
            .put("/booking")
                .bindingMode(RestBindingMode.json)
                .produces("application/json")
                .description("create a booking")
                .type(Booking.class)
                .outType(String.class)
                .responseMessage()
                    .code(200)
                        .message("if successful")
                    .responseModel(String.class)
                .endResponseMessage()
                .route().bean(bookHandler, "create")
            .endRest()
             
            .get("/booking/{id}")
                .bindingMode(RestBindingMode.json)
                .produces("application/json")
                .description("retrieves a booking given the ID")
                .outType(Booking.class)
                .param()
                    .name("id").type(RestParamType.path).dataType("integer").required(true)
                .endParam()
                .responseMessage()
                    .code(200)
                        .message("booking retrieved")
                    .responseModel(Booking.class)
                .endResponseMessage()
                .route().bean(bookHandler, "retrieve")
            .endRest()

            .post("/booking/{id}")
                .bindingMode(RestBindingMode.json)
                .produces("application/json")
                .description("update a booking given the id")
                .type(Booking.class)
                .outType(Integer.class)
                .param()
                    .name("id").type(RestParamType.path).dataType("integer").required(true)
                .endParam()
                .responseMessage()
                    .code(200)
                        .message("if successful, returns the ID of the booking")
                    .responseModel(Integer.class)
                .endResponseMessage()
                .route().bean(bookHandler, "update")
            .endRest()
            ;
        

        // @formatter:on   
    }
    

}
