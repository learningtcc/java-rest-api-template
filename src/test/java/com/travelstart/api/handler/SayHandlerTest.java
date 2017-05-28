package com.travelstart.api.handler;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SayHandlerTest {

    @Test
    public void test1() throws InterruptedException, UnsupportedEncodingException {
        SayHandler handler = new SayHandler();
        handler.handle("body", "message test");
    }

    @Test
    public void test2() throws InterruptedException, UnsupportedEncodingException {
        SayHandler handler = new SayHandler();
        handler.handle(null, "message test");
    }

}