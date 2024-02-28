package top.yolopluto.seckill.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.yolopluto.seckill.entity.SeckillGoods;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/16 20:54
 * @description: 秒杀商品 Mapper层
 * @Modified By:
 */
@Mapper
public interface SeckillGoodsMapper {
    SeckillGoods selectByGoodsId(Long goodsID);

    boolean reduceStock(Long goodsID);
}
