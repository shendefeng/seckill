package top.yolopluto.seckill.rabbitmq;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import top.yolopluto.seckill.config.RabbitMQTopicConfig;

import static top.yolopluto.seckill.config.RabbitMQTopicConfig.EXCHANGE;

/**
 * @author: yolopluto
 * @Date: created in 2024/3/7 16:16
 * @description: 消息发送者
 * @Modified By:
 */
@Service
@Slf4j
public class MQSender {
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送秒杀消息
     * @param message
     */
    public void sendSeckillMessage(String message) {
        log.info("send message: " + message);
        rabbitTemplate.convertAndSend(EXCHANGE, "seckill.message", message);
    }
}
