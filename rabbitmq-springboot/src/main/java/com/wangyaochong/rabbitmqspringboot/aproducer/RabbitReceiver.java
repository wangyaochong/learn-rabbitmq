package com.wangyaochong.rabbitmqspringboot.aproducer;

import com.rabbitmq.client.Channel;
import com.wangyaochong.rabbitmqspringboot.entity.Order;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class RabbitReceiver {

    @RabbitListener(bindings =
    @QueueBinding(value = @Queue(value = "queue-1", durable = "true"),
            exchange = @Exchange(value = "exchange-1", type = "topic"), key = "springboot.*"))

    @RabbitHandler
    public void onMessage(Message message, Channel channel) throws IOException {
        System.out.println("消费端收到消息：" + message);
        System.out.println("消费端收到消息 header：" + message.getMessageProperties().getHeaders());
        System.out.println("消费端收到消息 channel：" + channel);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(bindings =
    @QueueBinding(value = @Queue(value = "queue-2", durable = "true"),
            exchange = @Exchange(value = "exchange-2", type = "topic"), key = "springboot.*"))
    @RabbitHandler
    public void onOrderMessage(@Payload Order order, Channel channel, @Headers Map<String, Object> headers) throws IOException {
        System.out.println("消费端收到消息order：" + order);
        System.out.println("消费端收到消息 header：" + headers);
        System.out.println("消费端收到消息 channel：" + channel);
        channel.basicAck((Long) headers.get(AmqpHeaders.DELIVERY_TAG), false);
    }

}
