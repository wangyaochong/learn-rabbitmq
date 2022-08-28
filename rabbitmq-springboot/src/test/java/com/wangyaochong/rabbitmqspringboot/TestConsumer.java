package com.wangyaochong.rabbitmqspringboot;

import com.wangyaochong.rabbitmqspringboot.aconsumer.RabbitSender;
import com.wangyaochong.rabbitmqspringboot.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class TestConsumer {

    @Autowired
    RabbitSender rabbitSender;

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Test
    public void testSender() throws InterruptedException {
        Map<String, Object> properties = new HashMap<>();
        properties.put("number", "12345");
        properties.put("send_time", new Date());
        rabbitSender.send("hello send", properties);
    }

    @Test
    public void testSendOrder() throws InterruptedException {
        Map<String, Object> properties = new HashMap<>();
        properties.put("number", "12345");
        properties.put("send_time", new Date());
        rabbitSender.sendOrder(new Order("order-1", "order nss", "sdfsd"), properties);
    }
}
