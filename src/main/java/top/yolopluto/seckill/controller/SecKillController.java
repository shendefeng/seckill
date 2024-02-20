package top.yolopluto.seckill.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import top.yolopluto.seckill.dto.GoodsDTO;
import top.yolopluto.seckill.dto.RequBeanEnum;
import top.yolopluto.seckill.dto.UserDTO;
import top.yolopluto.seckill.entity.Order;
import top.yolopluto.seckill.entity.SeckillOrder;
import top.yolopluto.seckill.service.GoodsService;
import top.yolopluto.seckill.service.OrderService;
import top.yolopluto.seckill.service.SeckillOrderService;

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
    @RequestMapping("/doSeckill")
    public String doSeckill(Model model, UserDTO user, Long goodsId){
        if(user == null) {
            return "login";
        }
        model.addAttribute("user", user);
        GoodsDTO goods = goodsService.findGoodsById(goodsId);
        // 判断库存
        if(goods.getStockCount() < 1){
            model.addAttribute("errmsg", RequBeanEnum.EMPTY_STOCK.getMsg());
            return "seckillFail";
        }
        // 判断是否重复抢购
        SeckillOrder seckillOrder = seckillOrderService.selectByUserIdAndGoodsId(user.getId(), goodsId);
        if(seckillOrder != null){
            model.addAttribute("errmsg", RequBeanEnum.REPEAT_ERROR.getMsg());
            return "seckillFail";
        }
        // 正常下订单, 进行秒杀业务
        Order order = orderService.seckill(user, goods);
        model.addAttribute("order", order);
        model.addAttribute("goods", goods);
        return "orderDetail";
    }
}
