package com.wangyaochong.rabbitmqspringboot.api.exchange.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumerDirect {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(3000);
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        String exchangeName = "test_direct_exchange";
        String exchangeType = "direct";
        String queueName = "test_direct_name";
        String routingKey = "test.direct";

        //声明交换机
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
        //声明队列
        channel.queueDeclare(queueName, false, false, false, null);
        //建立绑定关系
        channel.queueBind(queueName, exchangeName, routingKey);

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("收到消息，" + msg);
        }

    }
}
