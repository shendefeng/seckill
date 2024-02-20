package top.yolopluto.seckill.service.Impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.yolopluto.seckill.entity.SeckillOrder;
import top.yolopluto.seckill.mapper.SeckillOrderMapper;
import top.yolopluto.seckill.service.SeckillOrderService;

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
    @Override
    public SeckillOrder selectByUserIdAndGoodsId(Long userId, Long goodsId) {
        return seckillOrderMapper.selectByUserIdAndGoodsId(userId, goodsId);
    }

    @Override
    public int insert(SeckillOrder seckillOrder) {
        return seckillOrderMapper.insert(seckillOrder);
    }
}
