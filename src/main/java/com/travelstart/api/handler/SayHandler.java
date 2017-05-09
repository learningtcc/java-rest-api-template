package com.travelstart.api.handler;

import com.newrelic.api.agent.Trace;
import com.travelstart.api.model.Message;

import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.camel.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SayHandler {
    private Logger log = LoggerFactory.getLogger(SayHandler.class);

    @Handler
    @Trace
    public Object handle(@Body Object body, @Header("q") String q) throws InterruptedException {
        Message m = new Message();
        m.setMsg("hello " + q);
        log.info("hello:{}", q);
        Thread.sleep(100);
        return m;
    }
}
