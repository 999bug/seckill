package com.ncst.seckill.service;

import com.ncst.seckill.pojo.SecKillMsg;

/**
 * @Date 2020/10/18 9:30
 * @Author by LSY
 * @Description
 */
public interface IMqReceiverService {
    void receiveSecKill(String message);

    void receiveTopic1(String message);

    void receiveTopic2(String message);

    void receiveHeaderQueue(byte[] message);

}
