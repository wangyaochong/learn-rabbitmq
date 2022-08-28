package com.wangyaochong.rabbitmqspringboot.api.ack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.Objects;

public class ConsumerMy extends DefaultConsumer {


    private Channel channel;

    public ConsumerMy(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("-----consumer message----------");
        System.out.println("consumerTag:" + consumerTag);
        System.out.println("envelope:" + envelope);
        System.out.println("properties:" + properties);
        System.out.println("body:" + new String(body));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object num = properties.getHeaders().get("num");
        if (Objects.equals(num, 0)) {
            channel.basicNack(envelope.getDeliveryTag(), false, true);
        } else {
            channel.basicAck(envelope.getDeliveryTag(), false);
        }
    }

}
