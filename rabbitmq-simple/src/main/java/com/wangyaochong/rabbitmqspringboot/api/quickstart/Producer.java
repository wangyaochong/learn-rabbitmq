package com.wangyaochong.rabbitmqspringboot.api.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        for (int i = 0; i < 5; i++) {
            String msg = "hello rabbitMq," + i;
            channel.basicPublish("", "test001", null, msg.getBytes());
        }
        channel.close();
        connection.close();
    }
}
