package top.yolopluto.seckill.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.yolopluto.seckill.dto.GoodsDTO;
import top.yolopluto.seckill.dto.SeckillMessage;
import top.yolopluto.seckill.dto.UserDTO;
import top.yolopluto.seckill.entity.SeckillOrder;
import top.yolopluto.seckill.service.GoodsService;
import top.yolopluto.seckill.service.OrderService;
import top.yolopluto.seckill.utils.JsonUtil;

import static top.yolopluto.seckill.config.RabbitMQTopicConfig.QUEUE;
import static top.yolopluto.seckill.utils.RedisConstants.ORDER_KEY;

/**
 * @author: yolopluto
 * @Date: created in 2024/3/7 16:24
 * @description: 消息接收者
 * @Modified By:
 */
@Service
@Slf4j
public class MQReceiver {
    @Resource
    private GoodsService goodsService;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private OrderService orderService;
    /**
     * 下单消息接收
     * @param message
     */
    @RabbitListener(queues = QUEUE)
    public void receive(String message) {
       log.info("receive message: " + message);
        SeckillMessage seckillMessage = JsonUtil.jsonStr2Object(message, SeckillMessage.class);
        Long goodsId = seckillMessage.getGoodsId();
        UserDTO user = seckillMessage.getUserDTO();
        GoodsDTO goodsDTO = goodsService.findGoodsById(goodsId);
        // 判断库存
        if(goodsDTO.getStockCount() < 1){
            return;
        }
        // 判断是否重复抢购
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get(ORDER_KEY + ":" + user.getId() + ":" + goodsId);
        if(seckillOrder != null){
            return;
        }
        // 下单操作
        orderService.seckill(user, goodsDTO);
    }
}
