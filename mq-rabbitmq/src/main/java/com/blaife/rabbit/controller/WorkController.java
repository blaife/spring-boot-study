package com.blaife.rabbit.controller;

import com.blaife.rabbit.contants.RabbitConsts;
import com.blaife.rabbit.message.MessageStruct;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 工作模式案例
 *
 * @author: magd39318
 * @date: 2022/3/21 16:06
 */
@RestController
public class WorkController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/sendWork")
    public Object sendWork() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend(RabbitConsts.QUEUE_WORK, new MessageStruct("" + i), new CorrelationData(UUID.randomUUID().toString()));
        }
        return "发送成功...";
    }
}
