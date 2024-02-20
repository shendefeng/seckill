package top.yolopluto.seckill.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.yolopluto.seckill.dto.LoginFormDTO;
import top.yolopluto.seckill.dto.RequBean;
import top.yolopluto.seckill.service.UserService;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/11 12:32
 * @description: 登录接口
 * @Modified By:
 */
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @Resource
    private UserService userService;
    /**
     * 跳转登录页面
     * @return
     */
    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }
    @SneakyThrows
    @PostMapping("/doLogin")
    @ResponseBody
    public RequBean doLogin(@Valid LoginFormDTO loginFormDTO, HttpServletRequest request, HttpServletResponse response) {
        log.info("用户登录信息: {}", loginFormDTO);
        return userService.doLogin(loginFormDTO, request, response);
    }
}
