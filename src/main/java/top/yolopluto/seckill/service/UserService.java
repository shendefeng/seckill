package top.yolopluto.seckill.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.yolopluto.seckill.dto.LoginFormDTO;
import top.yolopluto.seckill.dto.RequBean;
import top.yolopluto.seckill.dto.UserDTO;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/11 16:27
 * @description: 用户Service层
 * @Modified By:
 */
public interface UserService {
    RequBean doLogin(LoginFormDTO loginDTO, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException;

    UserDTO getUserByCooike(String userTicket, HttpServletRequest request, HttpServletResponse response);
}
