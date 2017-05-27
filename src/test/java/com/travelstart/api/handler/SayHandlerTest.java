package com.travelstart.api.handler;

import org.junit.Test;

public class SayHandlerTest {

    @Test
    public void test1() throws InterruptedException {
        SayHandler handler = new SayHandler();
        handler.handle("body", "message test");
    }

    @Test
    public void test2() throws InterruptedException {
        SayHandler handler = new SayHandler();
        handler.handle(null, "message test");
    }

    @Test
    public void test3() throws InterruptedException {
        SayHandler handler = new SayHandler();
        handler.handle("body", null);
    }

}
