package top.yolopluto.seckill.service;

import top.yolopluto.seckill.dto.GoodsDTO;

import java.util.List;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/16 19:11
 * @description: 商品表 服务类
 * @Modified By:
 */
public interface GoodsService {
    List<GoodsDTO> findGoodsList();

    GoodsDTO findGoodsById(Long goodsId);
}
