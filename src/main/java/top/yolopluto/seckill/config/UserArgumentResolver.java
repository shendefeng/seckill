package top.yolopluto.seckill.config;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import top.yolopluto.seckill.dto.UserDTO;
import top.yolopluto.seckill.service.UserService;
import top.yolopluto.seckill.utils.CookieUtil;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/16 13:38
 * @description: 自定义用户参数
 * @Modified By:
 */
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Resource
    private UserService userService;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> parameterType = parameter.getParameterType();
        return parameterType == UserDTO.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        String userTicket = CookieUtil.getCookieValue(request, "userTicket");
        if(StrUtil.isBlank(userTicket)) {
            return null;
        }
        UserDTO user = userService.getUserByCooike(userTicket, request, response);
        return user;
    }
}
