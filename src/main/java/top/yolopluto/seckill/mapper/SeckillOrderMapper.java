package top.yolopluto.seckill.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.yolopluto.seckill.entity.SeckillOrder;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/16 20:21
 * @description: 秒杀订单表 Mapper 接口
 * @Modified By:
 */
@Mapper
public interface SeckillOrderMapper {
    public SeckillOrder selectByUserIdAndGoodsId(Long userId, Long goodsId);

    int insert(SeckillOrder seckillOrder);
}
