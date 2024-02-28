package top.yolopluto.seckill.controller;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.yolopluto.seckill.dto.RequBean;
import top.yolopluto.seckill.dto.RequBeanEnum;
import top.yolopluto.seckill.dto.UserDTO;
import top.yolopluto.seckill.service.OrderService;
import top.yolopluto.seckill.vo.OrderDeatilVo;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/27 0:25
 * @description: 订单Controller层
 * @Modified By:
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public RequBean detail(UserDTO user, Long orderId) {
        if(user == null){
            return RequBean.error(RequBeanEnum.SESSION_ERROR);
        }
        OrderDeatilVo detail = orderService.detail(orderId);
        return RequBean.success(detail);
    }
}
