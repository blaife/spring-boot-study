package com.blaife.rabbit.controller;

import com.blaife.rabbit.contants.RabbitConsts;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 通配符模式 Controller
 *
 * @author: magd39318
 * @date: 2022/3/21 17:20
 */
@RestController
public class TopicController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/sendTopic")
    public Object sendFanout() {
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                rabbitTemplate.convertAndSend(RabbitConsts.EXCHANGE_TOPIC, "topic.km.topic", "测试发布订阅模型：" + i, new CorrelationData(UUID.randomUUID().toString()));
            } else {
                rabbitTemplate.convertAndSend(RabbitConsts.EXCHANGE_TOPIC, "topic.km", "测试发布订阅模型：" + i, new CorrelationData(UUID.randomUUID().toString()));
            }
        }
        return "发送成功...";
    }
}
