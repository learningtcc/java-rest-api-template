package com.travelstart.api;

import org.apache.commons.lang3.RandomUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Boot.class})
public class StartTest {

    @BeforeClass
    public static void init() {
        System.setProperty("server.port", "" + RandomUtils.nextInt(10000, 60000));
    }

    @Test
    public void startTest() throws InterruptedException {
    }


}
