package top.yolopluto.seckill.config;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: yolopluto
 * @Date: created in 2024/3/7 16:12
 * @description: rabbitmq配置bean
 * @Modified By:
 */

@Configuration
public class RabbitMQTopicConfig {
    public static final String EXCHANGE = "seckillExchange";
    public static final String QUEUE = "seckillQueue";
    public static final String ROUTING_KEY = "seckill.#";
    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE);
    }
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with(ROUTING_KEY);
    }

}
