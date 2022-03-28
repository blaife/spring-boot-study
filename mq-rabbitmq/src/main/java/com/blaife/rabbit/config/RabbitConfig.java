package com.blaife.rabbit.config;

import com.blaife.rabbit.contants.RabbitConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Rabbit 配置内容
 *
 * @author: magd39318
 * @date: 2022/3/21 15:27
 */
@Slf4j
@Configuration
public class RabbitConfig {

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //设置开启Manatory，才能触发回调函数，无论消息推送结果怎么样都会强制调用回调函数
        rabbitTemplate.setMandatory(true);
        // 设置确认发送到交换机的回调函数
        rabbitTemplate.setConfirmCallback((correlationData, b, s) -> {
            if (b) {
                System.out.println("confirm 消息确认成功..." + correlationData.getId());
            } else {
                System.out.println("confirm 消息确认失败..." + correlationData.getId() + " cause: " + s);
            }
        });

        //设置确认消息已发送到队列的回调
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            System.out.println("消息："+returnedMessage.getMessage());
            System.out.println("消息体："+returnedMessage.getMessage().getBody());
            System.out.println("回应码："+returnedMessage.getReplyCode());
            System.out.println("回应信息："+returnedMessage.getReplyText());
            System.out.println("交换机："+returnedMessage.getExchange());
            System.out.println("路由键："+returnedMessage.getRoutingKey());
        });
        return rabbitTemplate;
    }


    @Bean
    public Queue queueWork() {
        return new Queue(RabbitConsts.QUEUE_WORK);
    }

    @Bean
    public Queue queueFanout1() {
        return new Queue(RabbitConsts.QUEUE_FANOUT_ONE);
    }

    @Bean
    public Queue queueFanout2() {
        return new Queue(RabbitConsts.QUEUE_FANOUT_TWO);
    }

    @Bean
    public FanoutExchange exchangeFanout() {
        return new FanoutExchange(RabbitConsts.EXCHANGE_FANOUT);
    }

    @Bean
    public Binding bindingExchangeFanout1() {
        return BindingBuilder.bind(queueFanout1()).to(exchangeFanout());
    }

    @Bean
    public Binding bindingExchangeFanout2() {
        return BindingBuilder.bind(queueFanout2()).to(exchangeFanout());
    }

    @Bean
    public Queue queueTopic1() {
        return new Queue(RabbitConsts.QUEUE_TOPIC_ONE);
    }

    @Bean
    public Queue queueTopic2() {
        return new Queue(RabbitConsts.QUEUE_TOPIC_TWO);
    }

    @Bean
    public TopicExchange exchangeTopic() {
        return new TopicExchange(RabbitConsts.EXCHANGE_TOPIC);
    }

    @Bean
    public Binding bindingExchangeTopic1() {
        return BindingBuilder.bind(queueFanout1()).to(exchangeTopic()).with("topic.*");
    }

    @Bean
    public Binding bindingExchangeTopic2() {
        return BindingBuilder.bind(queueFanout2()).to(exchangeTopic()).with("topic.#");
    }
}
