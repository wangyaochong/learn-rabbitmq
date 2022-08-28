package com.wangyaochong.rabbitmqspringboot.api.dlx;

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
        String exchangeName = "test_dlx_exchange";
        String routingKey = "dlx.save";
        String message = "hello dlx message";
        channel.confirmSelect();
        channel.addReturnListener((replyCode, replyText, exchange, routingKey1, properties, body) -> {
            System.out.println("-----handle return-----------");
            System.out.println("replyCode," + replyCode);
            System.out.println("replyText," + replyText);
            System.out.println("exchange," + exchange);
            System.out.println("routingKey1," + routingKey1);
            System.out.println("properties," + properties);
            System.out.println("body," + new String(body));
        });

        Map<String, Object> headers = new HashMap<>();
        headers.put("my1", "111");
        headers.put("my2", "222");
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .expiration("10000")
                .headers(headers)
                .build();
        for (int i = 0; i < 5; i++) {
            channel.basicPublish(exchangeName, routingKey, true, properties, message.getBytes());
        }

    }
}
