package com.wangyaochong.rabbitmqspringboot;

import com.wangyaochong.rabbitmqspringboot.converter.ImageConverter;
import com.wangyaochong.rabbitmqspringboot.converter.TextMessageConverter;
import com.wangyaochong.rabbitmqspringboot.entity.Order;
import com.wangyaochong.rabbitmqspringboot.entity.Package;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.UUID;

@Configuration
public class RabbitMqConfig {
//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("127.0.0.1");
//        connectionFactory.setUsername("guest");
//        connectionFactory.setPassword("guest");
//        connectionFactory.setVirtualHost("/");
//        return connectionFactory;
//    }

//    @Bean
//    public AmqpAdmin amqpAdmin() {
//        return new RabbitAdmin(connectionFactory());
//    }

//    @Bean
//    public RabbitTemplate rabbitTemplate() {
//        return new RabbitTemplate(connectionFactory());
//    }

    @Bean
    public Queue queue001() {
        return new Queue("queue001", true);
    }

    @Bean
    public Queue queue002() {
        return new Queue("queue002", true);
    }

    @Bean
    public Queue queue003() {
        return new Queue("queue003", true);
    }

    @Bean
    public Queue queueImg() {
        return new Queue("queueImg", true);
    }

    @Bean
    public Queue queuePdf() {
        return new Queue("queuePdf", true);
    }

    @Bean
    public TopicExchange exchange001() {
        return new TopicExchange("topic001", true, false);
    }

    @Bean
    public TopicExchange exchange002() {
        return new TopicExchange("topic002", true, false);
    }

    @Bean
    public Binding binding001() {
        return BindingBuilder.bind(queue001()).to(exchange001()).with("spring.*");
    }

    @Bean
    public Binding binding002() {
        return BindingBuilder.bind(queue002()).to(exchange002()).with("rabbit.*");
    }

    @Bean
    public Binding binding003() {
        return BindingBuilder.bind(queue003()).to(exchange001()).with("mq.*");
    }


//    @Bean
//    public SimpleMessageListenerContainer container() {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
//        container.setQueues(queue001(), queue002(), queue003(), queueImg(), queuePdf());
//        container.setConcurrentConsumers(1);
//        container.setPrefetchCount(1);
//        container.setMaxConcurrentConsumers(5);
//        container.setDefaultRequeueRejected(false);
//        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
//        container.setConsumerTagStrategy(e -> e + "_" + UUID.randomUUID().toString());
//        container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
//            byte[] body = message.getBody();
//            System.out.println("消费者：" + new String(body));
//        });
//        return container;
//    }


//    @Bean
//    public SimpleMessageListenerContainer containerDelegate() {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
//        container.setQueues(queue001(), queue002(), queue003(), queueImg(), queuePdf());
//        container.setConcurrentConsumers(1);
//        container.setPrefetchCount(1);
//        container.setMaxConcurrentConsumers(5);
//        container.setDefaultRequeueRejected(false);
//        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
//        container.setConsumerTagStrategy(e -> e + "_" + UUID.randomUUID().toString());
//        MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate(), "consumeMessage");
//        adapter.setMessageConverter(new TextMessageConverter());
//        container.setMessageListener(adapter);
//
//        return container;
//    }

//    @Bean
//    public SimpleMessageListenerContainer containerDelegateMapMethod() {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
//        container.setQueues(queue001(), queue002(), queue003(), queueImg(), queuePdf());
//        container.setConcurrentConsumers(1);
//        container.setPrefetchCount(1);
//        container.setMaxConcurrentConsumers(5);
//        container.setDefaultRequeueRejected(false);
//        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
//        container.setConsumerTagStrategy(e -> e + "_" + UUID.randomUUID().toString());
//        Map<String, String> queueOrTagToMethodName = new HashMap<>();
//        queueOrTagToMethodName.put("queue001", "method001");
//        queueOrTagToMethodName.put("queue002", "method002");
//        MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate(), "consumeMessage");
//        adapter.setQueueOrTagToMethodName(queueOrTagToMethodName);
//        adapter.setMessageConverter(new TextMessageConverter());
//        container.setMessageListener(adapter);
//        return container;
//    }

//    @Bean
//    public SimpleMessageListenerContainer containerDelegateJsonConverter() {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
//        container.setQueues(queue001(), queue002(), queue003(), queueImg(), queuePdf());
//        container.setConcurrentConsumers(1);
//        container.setPrefetchCount(1);
//        container.setMaxConcurrentConsumers(5);
//        container.setDefaultRequeueRejected(false);
//        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
//        container.setConsumerTagStrategy(e -> e + "_" + UUID.randomUUID().toString());
//        MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate(), "consumeMessage");
//        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter();
//
//        DefaultJackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();
//        javaTypeMapper.setTrustedPackages("*");
//        messageConverter.setJavaTypeMapper(javaTypeMapper);//如果不直接转换java对象，不需要这行
//        adapter.setMessageConverter(messageConverter);
//        container.setMessageListener(adapter);
//
//        return container;
//    }

//    @Bean
//    public SimpleMessageListenerContainer containerDelegateJsonMultiClassConverter() {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
//        container.setQueues(queue001(), queue002(), queue003(), queueImg(), queuePdf());
//        container.setConcurrentConsumers(1);
//        container.setPrefetchCount(1);
//        container.setMaxConcurrentConsumers(5);
//        container.setDefaultRequeueRejected(false);
//        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
//        container.setConsumerTagStrategy(e -> e + "_" + UUID.randomUUID().toString());
//        MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate(), "consumeMessage");
//        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter();
//
//        DefaultJackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();
//        HashMap<String, Class<?>> idClassMapping = new HashMap<>();
//        idClassMapping.put("order", Order.class);
//        idClassMapping.put("package", Package.class);
//        javaTypeMapper.setTrustedPackages("*");
//        javaTypeMapper.setIdClassMapping(idClassMapping);
//        messageConverter.setJavaTypeMapper(javaTypeMapper);//如果不直接转换java对象，不需要这行
//        adapter.setMessageConverter(messageConverter);
//        container.setMessageListener(adapter);
//        return container;
//    }


    @Bean
    public SimpleMessageListenerContainer containerMultiTypeDelegate(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(queue001(), queue002(), queue003(), queueImg(), queuePdf());
        container.setConcurrentConsumers(1);
        container.setPrefetchCount(1);
        container.setMaxConcurrentConsumers(5);
        container.setDefaultRequeueRejected(false);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setConsumerTagStrategy(e -> e + "_" + UUID.randomUUID().toString());
        MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate(), "consumeMessage");

        ContentTypeDelegatingMessageConverter converter = new ContentTypeDelegatingMessageConverter();

        converter.addDelegate("text", new TextMessageConverter());
        converter.addDelegate("html/text", new TextMessageConverter());
        converter.addDelegate("xml/text", new TextMessageConverter());
        converter.addDelegate("text/plain", new TextMessageConverter());

        DefaultJackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();
        HashMap<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("order", Order.class);
        idClassMapping.put("package", Package.class);
        javaTypeMapper.setTrustedPackages("*");
        javaTypeMapper.setIdClassMapping(idClassMapping);
        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter();
        messageConverter.setJavaTypeMapper(javaTypeMapper);//如果不直接转换java对象，不需要这行

        converter.addDelegate("json", messageConverter);
        converter.addDelegate("application/json", messageConverter);


        ImageConverter imageConverter = new ImageConverter();
        converter.addDelegate("image/png", imageConverter);
        converter.addDelegate("image", imageConverter);

        adapter.setMessageConverter(converter);
        container.setMessageListener(adapter);
        return container;
    }
}
