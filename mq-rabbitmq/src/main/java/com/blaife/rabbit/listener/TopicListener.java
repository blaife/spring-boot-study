package com.blaife.rabbit.listener;

import com.blaife.rabbit.contants.RabbitConsts;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 通配符模式 监听
 *
 * @author: magd39318
 * @date: 2022/3/21 17:23
 */
@Component
public class TopicListener {
    @RabbitListener(queues = RabbitConsts.QUEUE_TOPIC_ONE)
    public void receiveMsg1(String msg) {
        System.out.println("消费者1接收到：" + msg);
    }

    @RabbitListener(queues = RabbitConsts.QUEUE_TOPIC_TWO)
    public void receiveMsg2(String msg) {
        System.out.println("消费者2接收到：" + msg);
    }
}
