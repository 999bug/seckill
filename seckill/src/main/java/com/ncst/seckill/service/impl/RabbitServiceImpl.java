package com.ncst.seckill.service.impl;

import com.ncst.seckill.config.MqConfig;
import com.ncst.seckill.service.IRabbitMq;
import com.ncst.seckill.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Date 2020/10/17 15:08
 * @Author by LSY
 * @Description
 */
@Service
public class RabbitServiceImpl implements IRabbitMq {

    private static Logger log = LoggerFactory.getLogger(RabbitServiceImpl.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void sendMsg(Object obj) {
        String string = JsonUtils.beanToString(obj);
        log.info("send message:" + string);
        amqpTemplate.convertAndSend(MqConfig.DIRECT_QUEUE, string);
    }

    @Override
    @RabbitListener(queues = MqConfig.DIRECT_QUEUE)
    public void receiveMsg(String message) {
        log.info("receive message:" + message);
    }


}
