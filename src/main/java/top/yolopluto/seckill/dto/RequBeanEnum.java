package top.yolopluto.seckill.dto;

import lombok.*;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/11 12:46
 * @description: Result对象的枚举
 * @Modified By:
 */
@ToString
@AllArgsConstructor
@Getter
public enum RequBeanEnum {
    // 通用
    SUCCESS(200, "success"),
    ERROR(500, "服务端异常"),
    // 继续细化，是为了更好的定位问题
    // 登录模块 5002XX
    LOGIN_ERROR(500210, "用户名或密码不能为空"),
    MOBILE_ERROR(500211, "手机号格式错误"),
    BIND_ERROR(500212, "参数校验异常"),
    SESSION_ERROR(500215, "用户不存在"),
    // 秒杀模块 5005XX
    EMPTY_STOCK(500500, "库存不足"),
    REPEAT_ERROR(500501, "该商品没人限购一件"),
    REQUEST_ILLEGAL(500502, "请求非法,请重新尝试"),
    CAPTCHA_ERROR(500503, "验证码错误"),
    ACCESS_LIMIT_REACHED(500504, "访问过于频繁，请稍后重试"),
    //订单模块5003xx
    ORDER_NOT_EXIST(500300, "订单不存在"),
    ;
    private final Integer code;
    private final String msg;
}
