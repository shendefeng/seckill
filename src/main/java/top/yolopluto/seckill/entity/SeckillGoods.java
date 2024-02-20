package top.yolopluto.seckill.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/16 18:57
 * @description: 秒杀商品表
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillGoods implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long goodsId;
    private BigDecimal seckillPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
