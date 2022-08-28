package com.wangyaochong.rabbitmqspringcloudstream;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;

@SpringBootTest
class RabbitmqSpringCloudStreamApplicationTests {

    @Test
    void contextLoads() {
    }

    @Resource
    RabbitmqSender rabbitmqSender;

//    @Resource
//    RabbitmqReceiver rabbitmqReceiver;

    @Test
    public void testSendMessage() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {

            rabbitmqSender.sendMessage(new Order("1", "1", "1"), new HashMap<>());
        }
        Thread.sleep(3000);
    }

}
