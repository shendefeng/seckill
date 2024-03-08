package top.yolopluto.seckill.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: yolopluto
 * @Date: created in 2024/3/7 16:10
 * @description: 秒杀商品的信息
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillMessage {
    private UserDTO userDTO;
    private Long goodsId;
}
