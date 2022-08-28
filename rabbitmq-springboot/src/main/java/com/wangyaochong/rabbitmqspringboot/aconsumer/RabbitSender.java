package com.wangyaochong.rabbitmqspringboot.aconsumer;

import com.wangyaochong.rabbitmqspringboot.entity.Order;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RabbitSender {

    @Autowired
    RabbitTemplate rabbitTemplate;

    RabbitTemplate.ReturnCallback returnCallback = (message, replyCode, replyText, exchange, routingKey) -> {
        System.out.println("returnCallback--->");
        System.out.println("message:" + message);
        System.out.println("replyCode:" + replyCode);
        System.out.println("replyText:" + replyText);
        System.out.println("exchange:" + exchange);
        System.out.println("routingKey:" + routingKey);
    };

    private RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, ack, cause) -> {
        System.out.println("confirmCallback--->");
        System.out.println("correlationData:" + correlationData);
        System.out.println("ack:" + ack);
        if (!ack) {
            System.out.println("异常处理。。。");
        }
    };


    public void send(Object message, Map<String, Object> properties) {
        MessageHeaders mhs = new MessageHeaders(properties);
        Message msg = MessageBuilder.createMessage(message, mhs);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        rabbitTemplate.convertAndSend("exchange-1", "springboot.hello", msg, new CorrelationData("123456789"));
    }

    public void sendOrder(Order order, Map<String, Object> properties) {
//        MessageHeaders mhs = new MessageHeaders(properties);
//        Message msg = MessageBuilder.createMessage(order, mhs);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        rabbitTemplate.convertAndSend("exchange-2", "springboot.order", order, new CorrelationData("sendOrder123456789"));
    }
}
