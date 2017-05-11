package com.travelstart.api.handler;

import com.newrelic.api.agent.NewRelic;

import java.io.IOException;

import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.camel.Header;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NewRelicHandler {
    private Logger log = LoggerFactory.getLogger(NewRelicHandler.class);

    @Handler
    public void handler(@Header("CamelHttpUri") String uri, @Body Object body) throws IOException {
        if (StringUtils.isNotBlank(uri)) {
            NewRelic.setTransactionName(null, uri);
        }

    }
}
