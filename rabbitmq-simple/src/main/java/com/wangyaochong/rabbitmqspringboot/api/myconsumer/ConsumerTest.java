package com.wangyaochong.rabbitmqspringboot.api.myconsumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumerTest {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_consumer_exchange";
        String routingKey = "consumer.save";
        String queueName = "test_consumer_queue";
        channel.queueDeclare(queueName, true, false, false, null);
        channel.exchangeDeclare(exchangeName, "topic", true);
        channel.queueBind(queueName, exchangeName, routingKey);
        channel.basicConsume(queueName, true, new ConsumerMy(channel));

    }
}
