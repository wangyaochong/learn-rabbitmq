package com.wangyaochong.rabbitmqspringcloudstream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Map;

@EnableBinding(InputOutputChannel.class)
@Service
public class RabbitmqSender {

    @Autowired
    private InputOutputChannel inputOutputChannel;

    public String sendMessage(Object message, Map<String, Object> properties) {
        MessageHeaders mhs = new MessageHeaders(properties);
        Message msg = MessageBuilder.createMessage(message, mhs);
        boolean sendStatus = inputOutputChannel.output().send(msg);
        System.out.println("发送数据，message=" + message + ",sendStatus=" + sendStatus);
        return null;
    }

}
