package com.wangyaochong.rabbitmqspringboot.api.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class ConsumerTest {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String dlxExchangeName = "dlx.exchange";
        String dlxQueueName = "dlx.queue";
        channel.exchangeDeclare(dlxExchangeName, "topic", true, false, null);
        channel.queueDeclare(dlxQueueName, true, false, false, null);
        channel.queueBind(dlxQueueName, dlxExchangeName, "#");

        String exchangeName = "test_dlx_exchange";
        String routingKey = "dlx.save";
        String queueName = "test_dlx_queue";
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "dlx.exchange");
        channel.exchangeDeclare(exchangeName, "topic", true);
        channel.queueDeclare(queueName, true, false, false, arguments);
        channel.queueBind(queueName, exchangeName, routingKey);
        channel.basicConsume(queueName, true, new ConsumerMy(channel));


    }
}
