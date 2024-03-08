package top.yolopluto.seckill.service;

import top.yolopluto.seckill.entity.SeckillOrder;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/16 20:20
 * @description: 秒杀订单表 服务类
 * @Modified By:
 */
public interface SeckillOrderService {
    SeckillOrder selectByUserIdAndGoodsId(Long userId, Long goodsId);

    int insert(SeckillOrder seckillOrder);

    Long getSeckillResult(Long userId, Long goodsId);
}
