package top.yolopluto.seckill.service.Impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yolopluto.seckill.dto.GoodsDTO;
import top.yolopluto.seckill.dto.UserDTO;
import top.yolopluto.seckill.entity.Order;
import top.yolopluto.seckill.entity.SeckillGoods;
import top.yolopluto.seckill.entity.SeckillOrder;
import top.yolopluto.seckill.mapper.OrderMapper;
import top.yolopluto.seckill.service.OrderService;
import top.yolopluto.seckill.service.SeckillGoodsService;
import top.yolopluto.seckill.service.SeckillOrderService;

import java.util.Date;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/16 20:43
 * @description: 订单表 实现类
 * @Modified By:
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private SeckillGoodsService seckillGoodsService;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private SeckillOrderService seckillOrderService;
    @Transactional
    @Override
    public Order seckill(UserDTO user, GoodsDTO goods) {
        Long goodsID = goods.getId();
        // 减库存: 秒杀表里的库存
        SeckillGoods seckillGoods = seckillGoodsService.selectByGoodsId(goodsID);
        // TODO 秒杀商品表删掉一个
        seckillGoodsService.reduceStock(goodsID);
        // 创建订单
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goodsID);
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);
        // TODO 生成秒杀订单表
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        // 前后运行有依赖关系
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goodsID);
        seckillOrderService.insert(seckillOrder);
        return order;
    }
}
