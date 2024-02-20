package top.yolopluto.seckill.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.yolopluto.seckill.dto.GoodsDTO;

import java.util.List;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/16 19:14
 * @description: 商品表 Mapper 接口
 * @Modified By:
 */
@Mapper
public interface GoodsMapper {
    public List<GoodsDTO> findGoodsList();

    GoodsDTO findGoodsById(Long goodsId);
}
