package top.yolopluto.seckill.validator;
import org.thymeleaf.util.StringUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: yolopluto
 * @Date: created in 2024/3/8 21:24
 * @description: 手机号码校验类
 * @Modified By:
 */
public class ValidatorUtil {
    private static final Pattern mobile_patten = Pattern.compile("[1]([3-9])[0-9]{9}$");


    public static boolean isMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        Matcher matcher = mobile_patten.matcher(mobile);
        return matcher.matches();
    }
}
