# RabbitMQ

## Rabbit 的工作原理

- `Broker`：消息队列服务进程，此进程包括两个部分：Exchange和Queue
- `Exchange`：消息队列交换机，按一定的规则将消息路由转发到某个队列，对消息进行过虑。
- `Queue`：消息队列，存储消息的队列，消息到达队列并转发给指定

**生产者发送消息的流程：**

1. 生产者和Brocker建立TCP连接
2. 生产者和Brocker建立通道
3. 生产者通过通道发送消息给Brocker，由Exchange将消息进行转发
4. Exchange将消息转发给指定的Queue

**消费者接收消息的流程：**

1. 消费者和Brocker建立TCP连接
2. 消费者和Brocker建立通道
3. 消费者监听指定的Queue
4. 当有消息到达Queue时Brocker默认将消息推送给消费者
5. 消费者接收到消息
6. ACK回复

## 消息模型

### 1. work消息模型

work 消息队列就是没有交换机(Exchange)，直接将消息推送到消息队列（Queue）中；
可以设置若干个消费者来进行消息的处理，当设置为手动确认时，就可以实现根据硬件处理性能来进行分配消息。

因为自动确认时，消息在接收到时就全部发送给了对应消费者，即多个消费者进行平均的分配；
而手动确认时，就变成了消息确认消费成功后再获取新的消息。

### 2. Fanout（广播）

广播（发布-订阅）需要使用到交换机，一个交换机绑定多个消息队列，
一次消息的发送是针对交换机来说的，而交换机将消息分别放到绑定的若干个消息队列中
```java
/* 创建多个消息队列 */
@Bean
public Queue queueFanout1() {
    return new Queue("queue_fanout1");
}
@Bean
public Queue queueFanout2() {
    return new Queue("queue_fanout2");
}

/* 创建一个交换机 */
@Bean
public FanoutExchange exchangeFanout() {
    return new FanoutExchange("exchange_fanout");
}

/* 将交换机和消息队列进行绑定 */
@Bean
public Binding bindingExchange1() {
    return BindingBuilder.bind(queueFanout1()).to(exchangeFanout());
}
@Bean
public Binding bindingExchange2() {
    return BindingBuilder.bind(queueFanout2()).to(exchangeFanout());
}

/* 发送消息时指向交换机 */
rabbitTemplate.convertAndSend("exchange_fanout", "", "测试发布订阅模型");
```


### 3. Topic（通配符）

通配符模式与广播模式的区别就是，通配符模式添加了一次过滤，符合消息队列指定的条件后才发送对应的消息队列中去

```java
/* 创建多个消息队列 */
@Bean
public Queue queueTopic1() {
 return new Queue("queue_topic1");
}
@Bean
public Queue queueTopic2() {
 return new Queue("queue_topic2");
}

/* 创建一个交换机 */
@Bean
public TopicExchange exchangeTopic() {
 return new TopicExchange("exchange_topic");
}

/* 将交换机和消息队列进行绑定 */
@Bean
public Binding bindingTopic1() {
 return BindingBuilder.bind(queueTopic1()).to(exchangeTopic()).with("topic.#");
}
@Bean
public Binding bindingTopic2() {
 return BindingBuilder.bind(queueTopic2()).to(exchangeTopic()).with("topic.*");
}

/* 发送消息时指向交换机,且第二个参数为路由值 */
rabbitTemplate.convertSendAndReceive("exchange_topic", "topic.km.topic", "测试发布订阅模型")
```


## Confim 机制
```properties
# 开启消息确认机制 confirm 异步
spring.rabbitmq.publisher-confirm-type=correlated
# 之前的旧版本 开启消息确认机制的方式
# spring.rabbitmq.publisher-confirms=true
# 消息开启手动确认
spring.rabbitmq.listener.direct.acknowledge-mode=manual
```
```java
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
        return rabbitTemplate;
    }
}
```

## Return 机制
```properties
# 开启return机制
spring.rabbitmq.publisher-returns=true
# 消息开启手动确认
spring.rabbitmq.listener.direct.acknowledge-mode=manual
```
```java
@Slf4j
@Configuration
public class RabbitConfig {
    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //设置开启Manatory，才能触发回调函数，无论消息推送结果怎么样都会强制调用回调函数
        rabbitTemplate.setMandatory(true);
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
}
```

## TTL队列、死信队列
