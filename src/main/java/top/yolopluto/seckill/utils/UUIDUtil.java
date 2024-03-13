package top.yolopluto.seckill.utils;

import java.util.UUID;

/**
 * @author: yolopluto
 * @Date: created in 2024/3/8 21:31
 * @description: UUID工具类
 * @Modified By:
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
