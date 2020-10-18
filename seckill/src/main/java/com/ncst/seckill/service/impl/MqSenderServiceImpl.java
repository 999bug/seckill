package com.ncst.seckill.service.impl;

import com.ncst.seckill.config.MqConfig;
import com.ncst.seckill.pojo.SecKillMsg;
import com.ncst.seckill.service.IMqSenderService;
import com.ncst.seckill.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LSY
 */
@Service
public class MqSenderServiceImpl  implements IMqSenderService {

    private static Logger log = LoggerFactory.getLogger(MqSenderServiceImpl.class);

    @Autowired
    AmqpTemplate amqpTemplate;

    @Override
    public void sendSecKill(SecKillMsg message) {
        String msg = getString(message);
        amqpTemplate.convertAndSend(MqConfig.SECKILL_DIRECT_QUEUE,msg);
    }

    @Override
    public void sendDirect(Object message) {
		String msg = getString(message);
		amqpTemplate.convertAndSend(MqConfig.DIRECT_QUEUE, msg);
    }

    @Override
    public void sendTopic(Object message){
		String msg = getString(message);
		//输出到TOPIC_ROUTING_KEY1  "topic.key1"
        // 绑定的 TOPIC_EXCHANGE 的 TOPIC_QUEUE1 中
		amqpTemplate.convertAndSend(MqConfig.TOPIC_EXCHANGE,"topic.key1",msg+"1");

        //输出到TOPIC_ROUTING_KEY2  "topic.#"
        // 绑定的 TOPIC_EXCHANGE 的 TOPIC_QUEUE2 中
		amqpTemplate.convertAndSend(MqConfig.TOPIC_EXCHANGE,"topic.key2",msg+"2");
		amqpTemplate.convertAndSend(MqConfig.TOPIC_EXCHANGE,"topic.kDemo",msg+"kDemo");
	}

	@Override
    public void sendFanout(Object message){
        String msg = getString(message);
        amqpTemplate.convertAndSend(MqConfig.FANOUT_EXCHANGE,"",msg);
    }

    @Override
    public void sendHeader(Object message){
        String msg = getString(message);
        MessageProperties properties=new MessageProperties();
        //满足设置的条件
        properties.setHeader("header1", "value1");
		properties.setHeader("header2", "value2");

        Message obj=new Message(msg.getBytes(),properties);
        amqpTemplate.convertAndSend(MqConfig.HEADER_EXCHANGE,"",obj);
    }

    private String getString(Object msg) {
        String logMsg = JsonUtils.beanToString(msg);
        log.info("send message:" + logMsg);
        return logMsg;
    }
}
