package com.blaife.rabbit.listener;

import com.blaife.rabbit.contants.RabbitConsts;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 工作模式监听
 *
 * @author: magd39318
 * @date: 2022/3/21 16:09
 */
@Component
public class WorkListener {

    @RabbitListener(queues = RabbitConsts.QUEUE_WORK)
    public void receiveMsg1(Message Message, Channel channel) throws IOException {
        System.out.println("方法1接收到消息：" + Message);
        MessageProperties properties = Message.getMessageProperties();
        long tag = properties.getDeliveryTag();
        if (true) {
            channel.basicAck(tag, false);// 消费确认
        } else {
            channel.basicNack(tag, false, true);
        }
    }


    @RabbitListener(queues = RabbitConsts.QUEUE_WORK)
    public void receiveMsg2(Message Message, Channel channel) throws IOException {
        System.out.println("方法2接收到消息：" + Message);
        MessageProperties properties = Message.getMessageProperties();
        long tag = properties.getDeliveryTag();
        if (false) {
            channel.basicAck(tag, false);// 消费确认
        } else {
            channel.basicNack(tag, false, true);
        }
    }
}
