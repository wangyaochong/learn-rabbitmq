package com.wangyaochong.rabbitmqspringcloudstream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
    String id;
    String name;
    String content;
}
