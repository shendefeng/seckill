package top.yolopluto.seckill.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.yolopluto.seckill.entity.Order;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/16 20:44
 * @description: 订单类 Mapper层
 * @Modified By:
 */
@Mapper
public interface OrderMapper {
    int insert(Order order);
}
