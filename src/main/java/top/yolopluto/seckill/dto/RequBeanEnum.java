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
    // 秒杀模块 5005XX
    EMPTY_STOCK(500500, "库存不足"),
    REPEAT_ERROR(500501, "该商品没人限购一件"),
    ;
    private final Integer code;
    private final String msg;
}
