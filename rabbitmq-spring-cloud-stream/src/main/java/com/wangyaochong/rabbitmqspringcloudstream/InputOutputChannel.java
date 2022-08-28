package com.wangyaochong.rabbitmqspringcloudstream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface InputOutputChannel {
    String OUTPUT_CHANNEL = "output_channel";
    String INPUT_CHANNEL = "input_channel";

    @Output(OUTPUT_CHANNEL)
    MessageChannel output();


    @Input(INPUT_CHANNEL)
    SubscribableChannel input();
}
