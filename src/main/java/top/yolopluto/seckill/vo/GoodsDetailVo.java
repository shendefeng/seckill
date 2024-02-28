package top.yolopluto.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.yolopluto.seckill.dto.GoodsDTO;
import top.yolopluto.seckill.dto.UserDTO;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/26 22:54
 * @description: 商品详情类
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDetailVo {
    private GoodsDTO goodsVo ;
    private UserDTO user;
    private int secKillStatus;
    private int remainSeconds;
}
