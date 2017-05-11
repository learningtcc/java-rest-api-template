package com.travelstart.api.handler;

import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.camel.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FailHandler {
    private Logger log = LoggerFactory.getLogger(FailHandler.class);

    @Handler
    public void letItFail(@Body Object body, @Header("q") String q) throws InterruptedException {
        if (true) {
            throw new NullPointerException("failing on demand");
        }
    }


}
