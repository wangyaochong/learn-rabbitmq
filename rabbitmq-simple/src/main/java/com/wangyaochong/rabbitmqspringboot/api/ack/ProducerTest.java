package com.wangyaochong.rabbitmqspringboot.api.ack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class ProducerTest {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_ack_exchange";
        String routingKey = "ack.save";
        String message = "hello ack message";
        channel.confirmSelect();

        for (int i = 0; i < 5; i++) {
            Map<String, Object> headers = new HashMap<>();
            headers.put("num", i);
            headers.put("my2", "222");
            AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                    .deliveryMode(2)
                    .contentEncoding("UTF-8")
                    .expiration("10000")
                    .headers(headers)
                    .build();

            channel.basicPublish(exchangeName, routingKey, true, properties, message.getBytes());
        }

    }
}
