package com.blaife.rabbit.contants;

/**
 * RabbitMQ 常量池
 *
 * @author: magd39318
 * @date: 2022/3/18 14:20
 */
public class RabbitConsts {

    /* 工作模式 WORK */

    public static final String QUEUE_WORK = "queue_work";

    /* 发布订阅模式 FANOUT */

    public static final String QUEUE_FANOUT_ONE = "queue_fanout_1";

    public static final String QUEUE_FANOUT_TWO = "queue_fanout_2";

    public static final String EXCHANGE_FANOUT = "exchange_fanout";

    /* 通配符模式 TOPIC */

    public static final String QUEUE_TOPIC_ONE = "queue_topic_1";

    public static final String QUEUE_TOPIC_TWO = "queue_topic_2";

    public static final String EXCHANGE_TOPIC = "exchange_topic";

}
