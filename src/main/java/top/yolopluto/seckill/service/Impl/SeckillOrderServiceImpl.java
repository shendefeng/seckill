package top.yolopluto.seckill.service.Impl;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.yolopluto.seckill.entity.SeckillOrder;
import top.yolopluto.seckill.mapper.SeckillOrderMapper;
import top.yolopluto.seckill.service.SeckillOrderService;

import static top.yolopluto.seckill.utils.RedisConstants.IS_STOCK_EMPTY;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/16 20:21
 * @description: 秒杀订单表 服务实现类
 * @Modified By:
 */
@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {
    @Resource
    private SeckillOrderMapper seckillOrderMapper;
    @Resource
    private RedisTemplate redisTemplate;
    @Override
    public SeckillOrder selectByUserIdAndGoodsId(Long userId, Long goodsId) {
        return seckillOrderMapper.selectByUserIdAndGoodsId(userId, goodsId);
    }

    @Override
    public int insert(SeckillOrder seckillOrder) {
        return seckillOrderMapper.insert(seckillOrder);
    }

    /**
     * 获取秒杀结果
     * @param userId
     * @param goodsId
     * @return orderId:成功, -1:秒杀失败, 0: 排队中
     */
    @Override
    public Long getSeckillResult(Long userId, Long goodsId) {
        SeckillOrder seckillOrder = seckillOrderMapper.selectByUserIdAndGoodsId(userId, goodsId);
        if (seckillOrder != null) {
            return seckillOrder.getOrderId();
        } else if(redisTemplate.hasKey(IS_STOCK_EMPTY + goodsId)){
            return -1L;
        } else {
            return 0L;

        }
    }
}
