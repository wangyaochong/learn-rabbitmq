package com.wangyaochong.rabbitmqspringboot.api.limit;

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
        String exchangeName = "test_qos_exchange";
        String routingKey = "qos.save";
        String queueName = "test_qos_queue";
        channel.queueDeclare(queueName, true, false, false, null);
        channel.exchangeDeclare(exchangeName, "topic", true);
        channel.queueBind(queueName, exchangeName, routingKey);
        channel.basicQos(0, 1, false);
        channel.basicConsume(queueName, false, new ConsumerMy(channel));//如果要限流，必须设置自动签收=false

    }
}
