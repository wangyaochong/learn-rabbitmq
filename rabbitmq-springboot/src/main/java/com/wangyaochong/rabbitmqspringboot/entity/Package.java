package com.wangyaochong.rabbitmqspringboot.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Package implements Serializable {
    String id;
    String name;
    String description;
}
