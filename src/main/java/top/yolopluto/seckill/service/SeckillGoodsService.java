package top.yolopluto.seckill.service;

import top.yolopluto.seckill.entity.SeckillGoods;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/16 20:52
 * @description: 秒杀商品 服务类
 * @Modified By:
 */
public interface SeckillGoodsService {

    SeckillGoods selectByGoodsId(Long goodsID);

    int reduceStock(Long goodsID);
}
