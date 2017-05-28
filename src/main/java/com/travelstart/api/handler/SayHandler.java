package com.travelstart.api.handler;

import com.travelstart.api.model.Message;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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
    public Object handle(@Body Object body, @Header("message") String q) throws InterruptedException, UnsupportedEncodingException {
        Message m = new Message();
        m.setMsg("hello " + URLDecoder.decode(q, "UTF-8"));
        log.info("hello: \"{}\"", m.getMsg());
        return m;
    }
}
