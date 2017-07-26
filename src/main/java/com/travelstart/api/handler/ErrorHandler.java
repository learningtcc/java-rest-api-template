package com.travelstart.api.handler;

import com.travelstart.api.model.ErrorResponse;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.Headers;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 */
@Component
public class ErrorHandler {
    private Logger log = LoggerFactory.getLogger(ErrorHandler.class);

    @Handler
    public Object handleError(Exception ex, Exchange exchange, @Headers Map<String, Object> headers) {

        log.error("Error:", ex);

        ErrorResponse res = new ErrorResponse();
        res.setException(ExceptionUtils.getMessage(ex));
        res.setExceptionStack(ExceptionUtils.getStackTrace(ex));

        final Map<String, String> outHeaders = new HashMap<String, String>();
        for (Map.Entry<String, Object> entry : exchange.getIn().getHeaders().entrySet()) {
            outHeaders.put(entry.getKey(), entry.getValue() == null ? "<NULL>" : entry.getValue().toString());
        }
        res.setHeaders(outHeaders);
        headers.put(Exchange.HTTP_RESPONSE_CODE, 500);
        headers.put(Exchange.CONTENT_TYPE, "application/json");
        return res;
    }

}
