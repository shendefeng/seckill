package top.yolopluto.seckill.service;

import top.yolopluto.seckill.dto.GoodsDTO;
import top.yolopluto.seckill.dto.UserDTO;
import top.yolopluto.seckill.entity.Order;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/16 20:43
 * @description: 订单表 服务类
 * @Modified By:
 */
public interface OrderService {
    /**
     * 秒杀
     * @param user
     * @param goods
     * @return
     */
    Order seckill(UserDTO user, GoodsDTO goods);
}
