package com.ncst.seckill.rabbitmq;

import com.ncst.seckill.config.MqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author LSY
 */
@Service
public class MQReceiver {

    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);


//		@RabbitListener(queues= MQConfig.MIAOSHA_QUEUE)
//		public void receive(String message) {
//			log.info("receive message:"+message);
//			MiaoshaMessage mm  = RedisService.stringToBean(message, MiaoshaMessage.class);
//			MiaoshaUser user = mm.getUser();
//			long goodsId = mm.getGoodsId();
//
//			GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
//	    	int stock = goods.getStockCount();
//	    	if(stock <= 0) {
//	    		return;
//	    	}
//	    	//判断是否已经秒杀到了
//	    	MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
//	    	if(order != null) {
//	    		return;
//	    	}
//	    	//减库存 下订单 写入秒杀订单
//	    	miaoshaService.miaosha(user, goods);
//		}

    @RabbitListener(queues = MqConfig.DIRECT_QUEUE)
    public void receive(String message) {
        log.info("receive message:" + message);
    }

    @RabbitListener(queues = MqConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        log.info(" topic  queue1 message:" + message);
    }

    @RabbitListener(queues = MqConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        log.info(" topic  queue2 message:" + message);
    }

    @RabbitListener(queues = MqConfig.HEADER_QUEUE)
    public void receiveHeaderQueue(byte[] message) {
        log.info(" header  queue message:" + new String(message));
    }
}
