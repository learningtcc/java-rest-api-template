package com.travelstart.api.handler;

import com.travelstart.api.model.Booking;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.apache.camel.Headers;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BookingHandler {
    private Logger log = LoggerFactory.getLogger(BookingHandler.class);

    // our db ID sequencer
    private static int ID = 0;

    // our db
    private Map<Integer, Booking> db = new HashMap();

    public Integer create(@Body Booking booking) throws InterruptedException, UnsupportedEncodingException {
        log.info(ToStringBuilder.reflectionToString(booking));
        db.put(++ID, booking);
        return ID;
    }

    public Booking retrieve(@Header("id") int bookingId, @Headers Map<String, Object> headers) {
        final Booking booking = db.get(bookingId);
        if (booking != null) {
            return booking;
        } else {
            headers.put(Exchange.HTTP_RESPONSE_CODE, 404);
            return null;
        }
    }

    public Integer update(@Header("id") int bookingId, @Headers Map<String, Object> headers, @Body Booking booking) {
        final Booking existingBooking = db.get(bookingId);
        if (existingBooking != null) {
            db.put(bookingId, booking);
            return bookingId;
        } else {
            headers.put(Exchange.HTTP_RESPONSE_CODE, 404);
            return null;
        }
    }
}
