package com.ncst.seckill.service;

import com.ncst.seckill.pojo.SecKillMsg;

/**
 * @Date 2020/10/17 15:06
 * @Author by LSY
 * @Description
 */
public interface IMqSenderService {
    void sendSecKill(SecKillMsg message);

    void sendDirect(Object message);

    void sendTopic(Object message);

    void sendFanout(Object message);

    void sendHeader(Object message);


}
