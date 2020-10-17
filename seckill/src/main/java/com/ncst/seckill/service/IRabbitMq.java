package com.ncst.seckill.service;

/**
 * @Date 2020/10/17 15:06
 * @Author by LSY
 * @Description
 */
public interface IRabbitMq {

     void sendMsg(Object obj);

     void receiveMsg(String message);
}
