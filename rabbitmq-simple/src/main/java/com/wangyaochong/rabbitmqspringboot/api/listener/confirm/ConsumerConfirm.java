package com.wangyaochong.rabbitmqspringboot.api.listener.confirm;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumerConfirm {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_confirm_exchange";
        String routingKey = "confirm.save";

        String queueName = "test_confirm_queue";
        channel.queueDeclare(queueName, true, false, false, null);
        channel.exchangeDeclare(exchangeName, "topic", true);
        channel.queueBind(queueName, exchangeName, routingKey);

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("消费端，" + msg);
            AMQP.BasicProperties properties = delivery.getProperties();
            System.out.println(properties);
        }
//        channel.close();
//        connection.close();
    }
}
