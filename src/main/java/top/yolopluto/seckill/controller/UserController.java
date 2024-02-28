package top.yolopluto.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.yolopluto.seckill.dto.RequBean;
import top.yolopluto.seckill.dto.UserDTO;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/10 20:49
 * @description: 用户接口
 * @Modified By:
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @GetMapping("/info")
    public RequBean info(UserDTO user) {

        return RequBean.success(user);
    }
}
