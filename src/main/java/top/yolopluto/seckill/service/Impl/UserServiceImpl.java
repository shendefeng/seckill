package top.yolopluto.seckill.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import top.yolopluto.seckill.dto.LoginFormDTO;
import top.yolopluto.seckill.dto.RequBean;
import top.yolopluto.seckill.dto.RequBeanEnum;
import top.yolopluto.seckill.dto.UserDTO;
import top.yolopluto.seckill.entity.User;
import top.yolopluto.seckill.mapper.UserMapper;
import top.yolopluto.seckill.service.UserService;
import top.yolopluto.seckill.utils.CookieUtil;
import top.yolopluto.seckill.utils.MD5Utils;
import top.yolopluto.seckill.utils.RegexUtils;

import java.util.concurrent.TimeUnit;

import static top.yolopluto.seckill.utils.RedisConstants.LOGIN_USER_KEY;
import static top.yolopluto.seckill.utils.RedisConstants.LOGIN_USER_TTL;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/11 16:28
 * @description: 用户service实现类
 * @Modified By:
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public RequBean doLogin(LoginFormDTO loginDTO, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        String mobile = loginDTO.getMobile();
        String password = loginDTO.getPassword();
        // 使用validator参数解决解耦
//        if(StrUtil.isBlank(mobile) || StrUtil.isBlank(password)) {
//            return RequBean.error(RequBeanEnum.LOGIN_ERROR);
//        }
//        if(RegexUtils.isPhoneInvalid(mobile)) {
//            return RequBean.error(RequBeanEnum.MOBILE_ERROR);
//        }
        User user = userMapper.selectById(mobile);
        // 根据手机号码获取用户
        if(user == null){
            return RequBean.error(RequBeanEnum.LOGIN_ERROR);
        }
        if(!MD5Utils.formPassToDBPass(password, user.getSalt()).equals(user.getPassword())){
            return RequBean.error(RequBeanEnum.LOGIN_ERROR);
        }
        // 需要对登录的用户信息进行状态保存
        String token = UUID.randomUUID().toString(true);
        String tokenKey = LOGIN_USER_KEY + token;
        // 把UserDTO存入Redis
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        String userJson = new ObjectMapper().writeValueAsString(userDTO);
        stringRedisTemplate.opsForValue().set(tokenKey, userJson);
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);
        // 把信息加入到Cookie中
        CookieUtil.setCookie(request, response, "userTicket", token);
        return RequBean.success(token);
    }

    @SneakyThrows
    @Override
    public UserDTO getUserByCooike(String userTicket, HttpServletRequest request, HttpServletResponse response) {
        if(StrUtil.isBlank(userTicket)) {
            return null;
        }
        String storedJson = stringRedisTemplate.opsForValue().get(LOGIN_USER_KEY + userTicket);
        UserDTO user = new ObjectMapper().readValue(storedJson, UserDTO.class);
        if(user != null) {
            CookieUtil.setCookie(request, response, "userTicket", userTicket);
        }
        return user;
    }
}
