package com.wangyaochong.rabbitmqspringboot;

import com.wangyaochong.rabbitmqspringboot.entity.Order;
import com.wangyaochong.rabbitmqspringboot.entity.Package;

import java.io.File;
import java.util.Map;

public class MessageDelegate {
    public void handleMessage(byte[] messageBody) {
        System.out.println("默认方法，消息内容：" + new String(messageBody));
    }

    public void consumeMessage(byte[] messageBody) {
        System.out.println("默认方法，消息内容：" + new String(messageBody));
    }

    public void consumeMessage(String messageBody) {
        System.out.println("字符串方法，消息内容：" + messageBody);
    }

    public void method001(String messageBody) {
        System.out.println("method001，消息内容：" + messageBody);
    }

    public void method002(String messageBody) {
        System.out.println("method002，消息内容：" + messageBody);
    }

    public void consumeMessage(Map messageBody) {
        System.out.println("map方法，" + messageBody);
    }

    public void consumeMessage(Order order) {
        System.out.println("Order方法，" + order);
    }

    public void consumeMessage(Package pac) {
        System.out.println("Order方法，" + pac);
    }

    public void consumeMessage(File pac) {
        System.out.println("File方法，" + pac);
    }
}
