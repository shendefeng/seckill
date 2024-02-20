package top.yolopluto.seckill.utils;

import cn.hutool.core.util.StrUtil;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/11 16:42
 * @description: 正则验证
 * @Modified By:
 */
public class RegexUtils {
    private static boolean match(String str, String regex){
        if (StrUtil.isBlank(str)) {
            return false;
        }
        return str.matches(regex);
    }
    /**
     * 是否是无效手机格式
     * @param phone 要校验的手机号
     * @return true:符合，false：不符合
     */
    public static boolean isPhoneValid(String phone){
        return match(phone, RegexPatterns.PHONE_REGEX);
    }
}
