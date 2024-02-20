package top.yolopluto.seckill.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/16 19:00
 * @description: 秒杀订单表
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long userId;
    private Long orderId;
    private Long goodsId;
}
