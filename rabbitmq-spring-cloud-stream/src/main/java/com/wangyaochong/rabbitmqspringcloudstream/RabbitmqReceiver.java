package com.wangyaochong.rabbitmqspringcloudstream;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
@EnableBinding(InputOutputChannel.class)
public class RabbitmqReceiver {

    @StreamListener(InputOutputChannel.INPUT_CHANNEL)
    public void receiveMessage(Message message) throws Exception {
        Channel channel = (Channel) message.getHeaders().get(AmqpHeaders.CHANNEL);
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        System.out.println("接收数据：" + message);
        channel.basicAck(deliveryTag, false);
    }

//    @StreamListener(InputOutputChannel.INPUT_CHANNEL)
//    public void receiveMessage(@Payload String message) throws Exception {
////        Channel channel = (Channel) message.getHeaders().get(AmqpHeaders.CHANNEL);
////        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
//        System.out.println("接收数据：" + message);
////        channel.basicAck(deliveryTag, false);
//    }
}
