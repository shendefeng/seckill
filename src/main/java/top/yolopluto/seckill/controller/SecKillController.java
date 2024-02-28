package top.yolopluto.seckill.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.yolopluto.seckill.dto.GoodsDTO;
import top.yolopluto.seckill.dto.RequBean;
import top.yolopluto.seckill.dto.RequBeanEnum;
import top.yolopluto.seckill.dto.UserDTO;
import top.yolopluto.seckill.entity.Order;
import top.yolopluto.seckill.entity.SeckillOrder;
import top.yolopluto.seckill.service.GoodsService;
import top.yolopluto.seckill.service.OrderService;
import top.yolopluto.seckill.service.SeckillOrderService;

import static top.yolopluto.seckill.utils.RedisConstants.ORDER_KEY;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/16 20:08
 * @description: 秒杀
 * @Modified By:
 */
@Controller
@Slf4j
@RequestMapping("/seckill")
public class SecKillController {
    @Resource
    private GoodsService goodsService;
    @Resource
    private OrderService orderService;
    @Resource
    private SeckillOrderService seckillOrderService;
    @Resource
    private RedisTemplate redisTemplate;
    @RequestMapping(value = "/doSeckill", method = RequestMethod.POST)
    @ResponseBody
    public RequBean doSeckill(Model model, UserDTO user, Long goodsId){
        if(user == null) {
            return RequBean.error(RequBeanEnum.SESSION_ERROR);
        }
        GoodsDTO goods = goodsService.findGoodsById(goodsId);
        // 判断库存
        if(goods.getStockCount() < 1){
            model.addAttribute("errmsg", RequBeanEnum.EMPTY_STOCK.getMsg());
            return RequBean.error(RequBeanEnum.EMPTY_STOCK);
        }
        // 判断是否重复抢购
        // v1.0: 通过数据库获取订单信息
//        SeckillOrder seckillOrder = seckillOrderService.selectByUserIdAndGoodsId(user.getId(), goodsId);
        // 通过Redis获取订单信息
        SeckillOrder seckillOrder = (SeckillOrder)redisTemplate.opsForValue().get(ORDER_KEY + ":" + user.getId() + ":" + goodsId);
        if(seckillOrder != null){
            model.addAttribute("errmsg", RequBeanEnum.REPEAT_ERROR.getMsg());
            return RequBean.error(RequBeanEnum.REPEAT_ERROR);
        }
        // 正常下订单, 进行秒杀业务
        Order order = orderService.seckill(user, goods);
//        model.addAttribute("order", order);
//        model.addAttribute("goods", goods);
        return RequBean.success(order);
    }
}
