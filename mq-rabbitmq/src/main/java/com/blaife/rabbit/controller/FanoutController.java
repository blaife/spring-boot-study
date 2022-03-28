package com.blaife.rabbit.controller;

import com.blaife.rabbit.contants.RabbitConsts;
import com.blaife.rabbit.message.MessageStruct;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 发布订阅模式 controller
 *
 * @author: magd39318
 * @date: 2022/3/21 16:38
 */
@RestController
public class FanoutController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/sendFanout")
    public Object sendFanout() {
        for (int i = 0; i < 5; i++) {
            rabbitTemplate.convertAndSend(RabbitConsts.EXCHANGE_FANOUT,  new MessageStruct("" + i), new CorrelationData(UUID.randomUUID().toString()));
        }
        return "发送成功...";
    }

}
