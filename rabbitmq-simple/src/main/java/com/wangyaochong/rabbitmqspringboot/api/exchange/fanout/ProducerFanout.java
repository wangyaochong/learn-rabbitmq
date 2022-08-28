package com.wangyaochong.rabbitmqspringboot.api.exchange.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerFanout {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        String exchangeName = "test_fanout_exchange";

        for (int i = 0; i < 10; i++) {
            String msg = "hello world fanout message";
            channel.basicPublish(exchangeName, "", null, msg.getBytes());
        }
        channel.close();
        connection.close();
    }
}
