package top.yolopluto.seckill.config;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.method.HandlerMethod;
import top.yolopluto.seckill.dto.RequBean;
import top.yolopluto.seckill.dto.RequBeanEnum;
import top.yolopluto.seckill.dto.UserDTO;
import top.yolopluto.seckill.service.UserService;
import top.yolopluto.seckill.utils.CookieUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @author: yolopluto
 * @Date: created in 2024/3/9 0:08
 * @description: AccessLimit拦截器实现类
 * @Modified By:
 */
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {
    @Resource
    private UserService userService;
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 拦截器前置处理
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            UserDTO user = getUser(request, response);
            UserContext.setUser(user);
            HandlerMethod hm = (HandlerMethod) handler;
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int second = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();

            String key = request.getRequestURI();
            if (needLogin) {
                if (user == null) {
                    render(response, RequBeanEnum.SESSION_ERROR);
                    return false;
                }
                key += ":" + user.getId();
            }
            ValueOperations valueOperations = redisTemplate.opsForValue();
            Integer count = (Integer) valueOperations.get(key);
            if (count == null) {
                valueOperations.set(key, 1, second, TimeUnit.SECONDS);
            } else if (count < maxCount) {
                valueOperations.increment(key);
            } else {
                render(response, RequBeanEnum.ACCESS_LIMIT_REACHED);
                return false;
            }
        }
        return true;
    }

    /**
     * 构建返回对象
     * @param response
     * @param respBeanEnum
     * @throws IOException
     */
    private void render(HttpServletResponse response, RequBeanEnum respBeanEnum) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        RequBean bean = RequBean.error(respBeanEnum);
        printWriter.write(new ObjectMapper().writeValueAsString(bean));
        printWriter.flush();
        printWriter.close();
    }

    /**
     * 获取用户
     * @param request
     * @param response
     * @return
     */
    private UserDTO getUser(HttpServletRequest request, HttpServletResponse response) {
        String userTicket = CookieUtil.getCookieValue(request, "userTicket");
        if (StrUtil.isEmpty(userTicket)) {
            return null;
        }
        return userService.getUserByCooike(userTicket, request, response);
    }
}
