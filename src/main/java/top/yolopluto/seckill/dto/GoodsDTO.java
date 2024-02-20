package top.yolopluto.seckill.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.yolopluto.seckill.entity.Goods;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/16 19:07
 * @description: 商品返回对象
 * @Modified By:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDTO extends Goods {
    private BigDecimal seckillPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
