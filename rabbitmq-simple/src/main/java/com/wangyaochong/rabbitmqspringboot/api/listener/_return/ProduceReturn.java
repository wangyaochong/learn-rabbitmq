package com.wangyaochong.rabbitmqspringboot.api.listener._return;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProduceReturn {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_return_exchange";
//        String routingKey = "return.save";
        String routingKeyError = "abc.save";
        String message = "hello return message";
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
        channel.basicPublish(exchangeName, routingKeyError, true, null, message.getBytes());

    }
}
