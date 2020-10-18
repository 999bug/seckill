package com.ncst.seckill.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2020/10/17 15:08
 * @Author by LSY
 * @Description
 */
@Configuration
public class MqConfig {
    /**
     * Direct 模型
     */
    public static final String DIRECT_QUEUE = "direct_queue";
    public static final String SECKILL_DIRECT_QUEUE = "secKill.queue";
    /**
     * Topic 模型
     */
    public static final String TOPIC_QUEUE1 = "topic.queue1";
    public static final String TOPIC_QUEUE2 = "topic.queue2";
    public static final String TOPIC_EXCHANGE = "topic.exchange";
    public static final String TOPIC_ROUTING_KEY1 = "topic.key1";
    public static final String TOPIC_ROUTING_KEY2 = "topic.#";

    /**
     * Fanout 模型
     */
    public static final String FANOUT_EXCHANGE = "fanout.exchange";

    /**
     * Headers 模型
     */
    public static final String HEADER_EXCHANGE = "header.exchange";
    public static final String HEADER_QUEUE = "header.queue";

    /**
     * Direct 模型
     */
    @Bean
    public Queue directQueue() {
        return new Queue(DIRECT_QUEUE, true);
    }

    @Bean
    public Queue secKillQueue(){
        return new Queue(SECKILL_DIRECT_QUEUE,true);
    }

    /**
     * Topic 模型
     */
    @Bean
    public Queue topicQueue1() {
        return new Queue(TOPIC_QUEUE1, true);
    }

    @Bean
    public Queue topicQueue2() {
        return new Queue(TOPIC_QUEUE2, true);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding topicBinding() {
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with(TOPIC_ROUTING_KEY1);
    }

    @Bean
    public Binding topicBinding1() {
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with(TOPIC_ROUTING_KEY2);
    }

    /**
     * Fanout模型
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Binding fanoutBinding1() {
        return BindingBuilder.bind(topicQueue1()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(fanoutExchange());
    }

    /**
     * Header 模型
     */
    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange(HEADER_EXCHANGE);
    }
    @Bean
    public Queue headerQueue(){
        return new Queue(HEADER_QUEUE,true);
    }
    @Bean
    public Binding headerBinding(){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("header1", "value1");
        map.put("header2", "value2");
        //全部满足时 才可以通信
        return BindingBuilder.bind(headerQueue()).to(headersExchange()).whereAll(map).match();
    }

}
