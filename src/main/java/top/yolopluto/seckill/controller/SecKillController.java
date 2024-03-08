package top.yolopluto.seckill.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.yolopluto.seckill.dto.*;
import top.yolopluto.seckill.entity.Order;
import top.yolopluto.seckill.entity.SeckillOrder;
import top.yolopluto.seckill.rabbitmq.MQSender;
import top.yolopluto.seckill.service.GoodsService;
import top.yolopluto.seckill.service.OrderService;
import top.yolopluto.seckill.service.SeckillOrderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static top.yolopluto.seckill.utils.RedisConstants.ORDER_KEY;
import static top.yolopluto.seckill.utils.RedisConstants.SECKILL_GOODS_STOCK;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/16 20:08
 * @description: 秒杀
 * @Modified By:
 */
@Controller
@Slf4j
@RequestMapping("/seckill")
public class SecKillController implements InitializingBean {
    @Resource
    private GoodsService goodsService;
    @Resource
    private OrderService orderService;
    @Resource
    private SeckillOrderService seckillOrderService;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private MQSender mqSender;
    /**
     * 内存标记, 减少空库存判断
     */
    private Map<Long, Boolean> EmptyStockMap = new HashMap<>();

    @RequestMapping(value = "/doSeckill", method = RequestMethod.POST)
    @ResponseBody
    public RequBean doSeckill(Model model, UserDTO user, Long goodsId) throws JsonProcessingException {
        if (user == null) {
            return RequBean.error(RequBeanEnum.SESSION_ERROR);
        }
        ValueOperations valueOperations = redisTemplate.opsForValue();
        // 判断是否重复抢购
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get(ORDER_KEY + ":" + user.getId() + ":" + goodsId);
        if (seckillOrder != null) {
            return RequBean.error(RequBeanEnum.REPEAT_ERROR);
        }
        // 内存标记, 减少Redis访问
        if(EmptyStockMap.get(goodsId)){
            return RequBean.error(RequBeanEnum.EMPTY_STOCK);
        }
        // 使用redis预减库存
        // 那么就需要考虑redis和mysql数据一致性的问题
        Long stock = valueOperations.decrement(SECKILL_GOODS_STOCK + goodsId);
        if (stock < 0) {
            EmptyStockMap.put(goodsId, true);
            // 避免出现-1
            valueOperations.increment(SECKILL_GOODS_STOCK + goodsId);
            return RequBean.error(RequBeanEnum.EMPTY_STOCK);
        }
        // 异步订单, 进行秒杀业务
        SeckillMessage seckillMessage = new SeckillMessage(user, goodsId);
        mqSender.sendSeckillMessage(new ObjectMapper().writeValueAsString(seckillMessage));
        return RequBean.success(0); //  接收到0，则表示排队中
    }

    /**
     * 获取秒杀结果
     * @param user
     * @param goodsId
     * @return orderId:成功, -1:秒杀失败, 0: 排队中
     */
    @RequestMapping(value = "/getResult", method = RequestMethod.GET)
    @ResponseBody
    public RequBean getResult(UserDTO user, Long goodsId) {
        if (user == null) {
            return RequBean.error(RequBeanEnum.SESSION_ERROR);
        }
        Long orderId = seckillOrderService.getSeckillResult(user.getId(), goodsId);
        return RequBean.success(orderId);
    }
    /**
     * 系统初始化, 将商品库存数量加载到Redis
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsDTO> goodsList = goodsService.findGoodsList();
        if (CollectionUtil.isEmpty(goodsList)) {
            return;
        }
        goodsList.forEach(goodsDTO -> {
            redisTemplate.opsForValue().set(SECKILL_GOODS_STOCK + goodsDTO.getId(), goodsDTO.getStockCount());
            EmptyStockMap.put(goodsDTO.getId(), false);
        });

    }
    // 通过数据库获取订单信息
//    public RequBean doSeckill2(Model model, UserDTO user, Long goodsId){
//        if(user == null) {
//            return RequBean.error(RequBeanEnum.SESSION_ERROR);
//        }
//        GoodsDTO goods = goodsService.findGoodsById(goodsId);
//        // 判断库存
//        if(goods.getStockCount() < 1){
//            model.addAttribute("errmsg", RequBeanEnum.EMPTY_STOCK.getMsg());
//            return RequBean.error(RequBeanEnum.EMPTY_STOCK);
//        }
//        // 判断是否重复抢购
//        // v1.0: 通过数据库获取订单信息
////        SeckillOrder seckillOrder = seckillOrderService.selectByUserIdAndGoodsId(user.getId(), goodsId);
//        // 通过Redis获取订单信息
//        // 使用Redis缓存, 减少数据库访问
//        SeckillOrder seckillOrder = (SeckillOrder)redisTemplate.opsForValue().get(ORDER_KEY + ":" + user.getId() + ":" + goodsId);
//        if(seckillOrder != null){
//            model.addAttribute("errmsg", RequBeanEnum.REPEAT_ERROR.getMsg());
//            return RequBean.error(RequBeanEnum.REPEAT_ERROR);
//        }
//        // 正常下订单, 进行秒杀业务
//        Order order = orderService.seckill(user, goods);
//        return RequBean.success(order);
//    }
}
