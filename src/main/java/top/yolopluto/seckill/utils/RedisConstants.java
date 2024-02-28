package top.yolopluto.seckill.utils;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/12 20:00
 * @description: Redis的Key前缀
 * @Modified By:
 */
public class RedisConstants {
    public static final String LOGIN_USER_KEY = "login:token:";
    public static final Long LOGIN_USER_TTL = 36000L;
    public static final String ORDER_KEY = "order:";
}
