package top.yolopluto.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.yolopluto.seckill.dto.GoodsDTO;
import top.yolopluto.seckill.entity.Order;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/27 0:24
 * @description: 订单详情类
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDeatilVo {
    private Order torder;
    private GoodsDTO goodsVo;
}
