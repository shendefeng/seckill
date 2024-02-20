package top.yolopluto.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/10 23:07
 * @description: MD5加密工具类
 * @Modified By:
 */
@Component
public class MD5Utils {
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }
    private static final String salt = "1a2b3c4d";

    /**
     * MD5第一次加密: 前端输入密码转换为form表单密码
     * @param inputPass
     * @return
     */
    public static String inputPassToFromPass(String inputPass) {

        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * MD5第二次加密: form表单密码转换为数据库密码
     * @param formPass
     * @param salt
     * @return
     */
    public static String formPassToDBPass(String formPass, String salt) {
        String str = salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }
    public static String inputPassToDBPass(String input, String saltDB) {
        return formPassToDBPass(inputPassToFromPass(input), saltDB);
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(inputPassToDBPass("123456", salt));
    }



}
