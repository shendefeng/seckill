package top.yolopluto.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/10 22:33
 * @description: 测试类
 * @Modified By:
 */
@RestController
@RequestMapping("/demo")
public class DemoController {
    /**
     * 测试页面跳转
     * @param model
     * @return
     */
    @RequestMapping("/hello")
    public String hello(Model model){
        model.addAttribute("name", "yolopluto");
        return "hello";
    }
}
