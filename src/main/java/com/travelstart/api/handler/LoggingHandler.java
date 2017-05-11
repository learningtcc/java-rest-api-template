package com.travelstart.api.handler;

import java.io.IOException;

import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.apache.camel.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingHandler {
    private Logger log = LoggerFactory.getLogger(LoggingHandler.class);

    public void logRequest(@Header("CamelHttpUri") String uri, Exchange exchange) throws IOException {
        final Message in = exchange.getIn();
        String request = in.getBody(String.class);
        log.info("URI:{}", uri);
        log.info("request content:\n{}\n", request);
    }

    public void logResponse(@Header("CamelHttpUri") String uri, Exchange exchange) throws IOException {
        log.info("URI:{}", uri);
        final Message in = exchange.getIn();
        String request = in.getBody(String.class);
        log.info("response content:\n{}\n", request);
    }
}
