package com.travelstart.api.handler;

import com.travelstart.api.Boot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Boot.class})
public class SayHandlerTest {

    @Test
    public void test1() throws InterruptedException {
        SayHandler handler = new SayHandler();
        handler.handle("body", "message test");
    }
}
