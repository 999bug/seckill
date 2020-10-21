package com.ncst.seckill.service.impl;

import com.ncst.seckill.config.MqConfig;
import com.ncst.seckill.pojo.SecKillMsg;
import com.ncst.seckill.pojo.SkUser;
import com.ncst.seckill.pojo.SkOrder;
import com.ncst.seckill.service.IGoodsService;
import com.ncst.seckill.service.IMqReceiverService;
import com.ncst.seckill.service.IOrderService;
import com.ncst.seckill.service.ISecKillService;
import com.ncst.seckill.util.JsonUtils;
import com.ncst.seckill.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LSY
 */
@Service
public class MqReceiverServiceImpl implements IMqReceiverService {

    private static Logger log = LoggerFactory.getLogger(MqReceiverServiceImpl.class);

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ISecKillService secKillService;

    @RabbitListener(queues = MqConfig.DIRECT_QUEUE)
    public void receive(String message) {
        log.info("receive message:" + message);
    }

    @RabbitListener(queues = MqConfig.SECKILL_DIRECT_QUEUE)
    @Override
    public void receiveSecKill(String message) {
        log.info("receive message:" + message);
        SecKillMsg secKillMsg = JsonUtils.stringToBean(message, SecKillMsg.class);

        SkUser user = secKillMsg.getSkUser();
        long goodsId = secKillMsg.getGoodsId();

        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);

        //判断库存
        int stockCount = goodsVo.getStockCount();
        if (stockCount <= 0) {
            return;
        }

        //判断订单是否秒杀
        SkOrder skOrder = orderService.getSecKillOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if (skOrder != null) {
            return;
        }

        //减库存 下订单 写入秒杀订单
        secKillService.secKill(user, goodsVo);

    }

    @Override
    @RabbitListener(queues = MqConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        log.info(" topic  queue1 message:" + message);
    }

    @Override
    @RabbitListener(queues = MqConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        log.info(" topic  queue2 message:" + message);
    }

    @Override
    @RabbitListener(queues = MqConfig.HEADER_QUEUE)
    public void receiveHeaderQueue(byte[] message) {
        log.info(" header  queue message:" + new String(message));
    }
}
