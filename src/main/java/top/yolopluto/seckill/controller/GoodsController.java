package top.yolopluto.seckill.controller;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import org.thymeleaf.util.StringUtils;
import top.yolopluto.seckill.dto.GoodsDTO;
import top.yolopluto.seckill.dto.RequBean;
import top.yolopluto.seckill.dto.UserDTO;
import top.yolopluto.seckill.entity.User;
import top.yolopluto.seckill.service.GoodsService;
import top.yolopluto.seckill.service.UserService;
import top.yolopluto.seckill.vo.GoodsDetailVo;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private ThymeleafViewResolver thymeleafViewResolver;

    @RequestMapping(value = "/toList", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    @ResponseBody
    public String toList(Model model, UserDTO user, HttpServletRequest request, HttpServletResponse response){
        // 配置了自定义参数解析器后，可以直接在方法参数中获取到UserDTO对象
//        if(StrUtil.isBlank(userTicket)) {
//            return "login";
//        }
//        UserDTO user = userService.getUserByCooike(userTicket, request, response);
//        if(user == null) {
//            return "login";
//        }
        // 页面的缓存
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsList:");
        if(!StringUtils.isEmpty(html)){
            return html;
        }
        // 需要找到秒杀商品的信息列表
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsService.findGoodsList());
        // 修改后 Spring Boot3.0：
        JakartaServletWebApplication jakartaServletWebApplication = JakartaServletWebApplication.buildApplication(request.getServletContext());
        WebContext webContext = new WebContext(jakartaServletWebApplication.buildExchange(request, response), request.getLocale(), model.asMap());

        html = thymeleafViewResolver.getTemplateEngine().process("goodsList", webContext);
        if(!StringUtils.isEmpty(html)){
            valueOperations.set("goodsList:", html, 60, TimeUnit.SECONDS);
        }
        return html;
    }
    @RequestMapping(value = "/detail/{goodsId}", method = RequestMethod.GET)
    @ResponseBody
    public RequBean toDetail(UserDTO user, @PathVariable Long goodsId){
//        ValueOperations valueOperations = redisTemplate.opsForValue();

        GoodsDTO goodsVo = goodsService.findGoodsById(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
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
//        model.addAttribute("goods", goodsVo);
//        model.addAttribute("remainSeconds", remainSeconds);
//        model.addAttribute("seckillStatus", seckillStatus);
        GoodsDetailVo detailVo = new GoodsDetailVo();
        detailVo.setGoodsVo(goodsVo);
        detailVo.setUser(user);
        detailVo.setSecKillStatus(seckillStatus);
        detailVo.setRemainSeconds(remainSeconds);

        return RequBean.success(detailVo);
    }
//    public String toDetail(Model model, UserDTO user, @PathVariable Long goodsId){
//        model.addAttribute("user", user);
//        GoodsDTO goodsDTO = goodsService.findGoodsById(goodsId);
//        Date startDate = goodsDTO.getStartDate();
//        Date endDate = goodsDTO.getEndDate();
//        Date nowDate = new Date();
//        //秒杀状态
//        int seckillStatus = 0;
//        //秒杀倒计时
//        int remainSeconds = 0;
//
//        if (nowDate.before(startDate)) {
//            //秒杀还未开始0
//            remainSeconds = (int) ((startDate.getTime() - nowDate.getTime()) / 1000);
//        } else if (nowDate.after(endDate)) {
//            //秒杀已经结束
//            seckillStatus = 2;
//            remainSeconds = -1;
//        } else {
//            //秒杀进行中
//            seckillStatus = 1;
//            remainSeconds = 0;
//        }
//        model.addAttribute("goods", goodsDTO);
//        model.addAttribute("remainSeconds", remainSeconds);
//        model.addAttribute("seckillStatus", seckillStatus);
//        return "goodsDetail";
//    }
}
