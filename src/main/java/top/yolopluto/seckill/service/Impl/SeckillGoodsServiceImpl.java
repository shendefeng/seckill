package top.yolopluto.seckill.service.Impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.yolopluto.seckill.entity.SeckillGoods;
import top.yolopluto.seckill.mapper.SeckillGoodsMapper;
import top.yolopluto.seckill.service.SeckillGoodsService;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/16 20:52
 * @description: 秒杀商品 服务实现类
 * @Modified By:
 */
@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {
    @Resource
    private SeckillGoodsMapper seckillGoodsMapper;
    @Override
    public SeckillGoods selectByGoodsId(Long goodsID) {
        return seckillGoodsMapper.selectByGoodsId(goodsID);
    }

    @Override
    public boolean reduceStock(Long goodsID) {
        return seckillGoodsMapper.reduceStock(goodsID);
    }
}
