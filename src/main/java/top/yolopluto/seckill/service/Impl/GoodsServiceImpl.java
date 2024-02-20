package top.yolopluto.seckill.service.Impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.yolopluto.seckill.dto.GoodsDTO;
import top.yolopluto.seckill.mapper.GoodsMapper;
import top.yolopluto.seckill.service.GoodsService;

import java.util.List;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/16 19:13
 * @description: 商品表 服务实现类
 * @Modified By:
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;
    @Override
    public List<GoodsDTO> findGoodsList() {
        return goodsMapper.findGoodsList();
    }

    @Override
    public GoodsDTO findGoodsById(Long goodsId) {
        return goodsMapper.findGoodsById(goodsId);
    }

}
