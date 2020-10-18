package com.ncst.seckill.controller;

import com.ncst.seckill.result.Result;
import com.ncst.seckill.service.impl.MqSenderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mq")
public class TestMqController {

    @Autowired
    private MqSenderServiceImpl sender;

    @RequestMapping("/direct")
    public Result<String> directMq() {
        sender.sendDirect("hello,ncst");
        return Result.success("Hello，world");
    }

    @RequestMapping("/topic")
    public Result<String> topicMq() {
        sender.sendTopic("hello,ncst");
        return Result.success("Hello，world");
    }

    @RequestMapping("/fanout")
    public Result<String> fanoutMq() {
        sender.sendFanout("hello,ncst");
        sender.sendFanout("hello 苍井空");
        return Result.success("Hello，world");
    }

    @RequestMapping("/header")
    public Result<String> headersMq() {
        sender.sendHeader("hello, ncst");
        return Result.success("Hello，world");
    }
}
