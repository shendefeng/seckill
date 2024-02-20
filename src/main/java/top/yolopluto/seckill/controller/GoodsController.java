package top.yolopluto.seckill.controller;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import top.yolopluto.seckill.dto.GoodsDTO;
import top.yolopluto.seckill.dto.RequBean;
import top.yolopluto.seckill.dto.UserDTO;
import top.yolopluto.seckill.entity.User;
import top.yolopluto.seckill.service.GoodsService;
import top.yolopluto.seckill.service.UserService;

import java.util.Date;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/12 19:49
 * @description: 商品Controller层
 * @Modified By:
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Resource
    private UserService userService;
    @Resource
    private GoodsService goodsService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @RequestMapping("/toList")
    public String toList(Model model, UserDTO user){
        // 配置了自定义参数解析器后，可以直接在方法参数中获取到UserDTO对象
//        if(StrUtil.isBlank(userTicket)) {
//            return "login";
//        }
//        UserDTO user = userService.getUserByCooike(userTicket, request, response);
//        if(user == null) {
//            return "login";
//        }
        model.addAttribute("user", user);
        // 需要找到秒杀商品的信息列表
        model.addAttribute("goodsList", goodsService.findGoodsList());
        return "goodsList";
    }
    @GetMapping("/detail/{goodsId}")
    public String toDetail(Model model, UserDTO user, @PathVariable Long goodsId){
        model.addAttribute("user", user);
        GoodsDTO goodsDTO = goodsService.findGoodsById(goodsId);
        Date startDate = goodsDTO.getStartDate();
        Date endDate = goodsDTO.getEndDate();
        Date nowDate = new Date();
        //秒杀状态
        int seckillStatus = 0;
        //秒杀倒计时
        int remainSeconds = 0;

        if (nowDate.before(startDate)) {
            //秒杀还未开始0
            remainSeconds = (int) ((startDate.getTime() - nowDate.getTime()) / 1000);
        } else if (nowDate.after(endDate)) {
            //秒杀已经结束
            seckillStatus = 2;
            remainSeconds = -1;
        } else {
            //秒杀进行中
            seckillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("goods", goodsDTO);
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("seckillStatus", seckillStatus);
        return "goodsDetail";
    }
}
