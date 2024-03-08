package top.yolopluto.seckill.service.Impl;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yolopluto.seckill.dto.GoodsDTO;
import top.yolopluto.seckill.dto.RequBean;
import top.yolopluto.seckill.dto.RequBeanEnum;
import top.yolopluto.seckill.dto.UserDTO;
import top.yolopluto.seckill.entity.Order;
import top.yolopluto.seckill.entity.SeckillGoods;
import top.yolopluto.seckill.entity.SeckillOrder;
import top.yolopluto.seckill.exception.GlobalException;
import top.yolopluto.seckill.mapper.OrderMapper;
import top.yolopluto.seckill.service.GoodsService;
import top.yolopluto.seckill.service.OrderService;
import top.yolopluto.seckill.service.SeckillGoodsService;
import top.yolopluto.seckill.service.SeckillOrderService;
import top.yolopluto.seckill.vo.OrderDeatilVo;

import java.util.Date;

import static top.yolopluto.seckill.utils.RedisConstants.IS_STOCK_EMPTY;
import static top.yolopluto.seckill.utils.RedisConstants.ORDER_KEY;

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
    @Resource
    private GoodsService goodsService;
    @Resource
    private RedisTemplate redisTemplate;
    @Transactional
    @Override
    public Order seckill(UserDTO user, GoodsDTO goods) {
        Long goodsId = goods.getId();
        // 减库存: 秒杀表里的库存
        SeckillGoods seckillGoods = seckillGoodsService.selectByGoodsId(goodsId);
        // 程序里面删掉一个库存
        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
        // 秒杀商品表删掉一个
        boolean result = seckillGoodsService.reduceStock(goodsId);
        if(seckillGoods.getStockCount() < 1){
            redisTemplate.opsForValue().set(IS_STOCK_EMPTY + goodsId, "0");
        }
        // 创建订单
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goodsId);
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);
        //  生成秒杀订单表
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        // 前后运行有依赖关系
        // 订单表加上唯一索引和Transaction结合，保证一个人只能下一单的情况
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goodsId);
        seckillOrderService.insert(seckillOrder);
        redisTemplate.opsForValue().set(ORDER_KEY + ":" + user.getId() + ":" + goodsId, seckillOrder);
        return order;
    }

    @Override
    public OrderDeatilVo detail(Long orderId) {
        if(orderId == null){
            throw new GlobalException(RequBeanEnum.ORDER_NOT_EXIST);
        }
        Order order = orderMapper.selectById(orderId);
        GoodsDTO goodsDTO = goodsService.findGoodsById(order.getGoodsId());
        OrderDeatilVo detail = new OrderDeatilVo();
        detail.setTorder(order);
        detail.setGoodsVo(goodsDTO);
        return detail;


    }
}
