package com.wangyaochong.rabbitmqspringboot;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.wangyaochong.rabbitmqspringboot.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

@SpringBootTest
class RabbitmqSpringbootApplicationTests {
    @Resource
    RabbitAdmin rabbitAdmin;
    @Resource
    RabbitTemplate rabbitTemplate;

    @Test
    void contextLoads() {
    }


    @Test
    public void testAdmin() {
        rabbitAdmin.declareExchange(new DirectExchange("test.direct", false, false));
        rabbitAdmin.declareExchange(new TopicExchange("test.topic", false, false));
        rabbitAdmin.declareExchange(new FanoutExchange("test.fanout", false, false));

        rabbitAdmin.declareQueue(new Queue("test.direct.queue", false));
        rabbitAdmin.declareQueue(new Queue("test.topic.queue", false));
        rabbitAdmin.declareQueue(new Queue("test.fanout.queue", false));

        rabbitAdmin.declareBinding(new Binding("test.direct.queue", Binding.DestinationType.QUEUE, "test.direct", "direct", new HashMap<>()));
        rabbitAdmin.declareBinding(BindingBuilder.bind(new Queue("test.topic.queue", false))
                .to(new TopicExchange("test.topic", false, false)).with("test.topic"));
        rabbitAdmin.declareBinding(BindingBuilder.bind(new Queue("test.fanout.queue", false))
                .to(new FanoutExchange("test.fanout", false, false)));

        rabbitAdmin.purgeQueue("test.topic.queue");
    }

    @Test
    public void testCoverage() {
        int op1 = 5;
        int op2 = 3;
        int result = 0;
        int result2 = 0;
        result = MyOpClass.myMethod("mul", op1, op2);
        result2 = MyOpClass.myMethod("add", op1, op2);
        assert result == 15;
        assert result2 == 8;
    }


    @Test
    public void testSendMessage() throws InterruptedException {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("desc", "信息描述");
        messageProperties.getHeaders().put("type", "自定义消息类型");
        Message message = new Message("Hello rabbitMq".getBytes(), messageProperties);
        rabbitTemplate.convertAndSend("topic001", "spring.amqp", message, msg -> {
            msg.getMessageProperties().getHeaders().put("extra", "额外新增属性");
            return msg;
        });
    }

    @Test
    public void testSendMessage2() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("text/plain");
        messageProperties.getHeaders().put("desc", "信息描述");
        messageProperties.getHeaders().put("type", "自定义消息类型");
        Message message = new Message("Hello plain".getBytes(), messageProperties);
        rabbitTemplate.send("topic001", "spring.abc", message);
        rabbitTemplate.convertAndSend("topic001", "spring.amqp", "hello topic001");
        rabbitTemplate.convertAndSend("topic002", "rabbit.amqp", "hello 12345");
    }

    @Test
    public void testSendText() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("text/plain");
        messageProperties.getHeaders().put("desc", "信息描述");
        messageProperties.getHeaders().put("type", "自定义消息类型");
        Message message = new Message("Hello plain".getBytes(), messageProperties);
        rabbitTemplate.send("topic001", "spring.abc", message);
//        rabbitTemplate.convertAndSend("topic001", "spring.amqp", "hello topic001");
//        rabbitTemplate.convertAndSend("topic002", "rabbit.amqp", "hello 12345");
    }


    @Test
    public void testSendJsonMessage() throws JsonProcessingException {
        Order order = new Order();
        order.setId("001");
        order.setName("消息订单");
        order.setContent("描述信息");

        String json = JSON.toJSONString(order);
        System.out.println(json);
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        Message message = new Message(json.getBytes(), messageProperties);
        rabbitTemplate.send("topic001", "spring.order", message);
    }

    @Test
    public void testSendJavaMessage() {
        Order order = new Order();
        order.setId("001");
        order.setName("消息订单");
        order.setContent("描述信息");
        String json = JSON.toJSONString(order);
        System.out.println(json);
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        messageProperties.getHeaders().put("__TypeId__", "com.wangyaochong.rabbitmqspringboot.entity.Order");
        Message message = new Message(json.getBytes(), messageProperties);
        rabbitTemplate.send("topic001", "spring.order", message);
    }

    @Test
    public void testSendMappingMessage() {
        Order order = new Order();
        order.setId("001");
        order.setName("消息订单");
        order.setContent("描述信息");
        String json = JSON.toJSONString(order);
        System.out.println(json);
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        messageProperties.getHeaders().put("__TypeId__", "order");
        Message message = new Message(json.getBytes(), messageProperties);
        rabbitTemplate.send("topic001", "spring.order", message);
    }

    @Test
    public void testSendFileConverterMessage() throws Exception {
        byte[] body = Files.readAllBytes(Paths.get("D:/tempsnip.png"));
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("image/png");
        messageProperties.getHeaders().put("extName", "png");
        rabbitTemplate.send("", "queueImg", new Message(body, messageProperties));
    }
}
