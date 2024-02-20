package top.yolopluto.seckill.validator;

import cn.hutool.core.util.StrUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import top.yolopluto.seckill.utils.RegexUtils;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/12 18:44
 * @description: 手机号码验证类
 * @Modified By:
 */
public class isPhoneValidator implements ConstraintValidator<isPhone, String>{
    /**
     * require要求输入的参数是否是必填项
     */
    private boolean required = false;
    @Override
    public void initialize(isPhone constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(required) {
            return RegexUtils.isPhoneValid(s);
        }else{
            if(StrUtil.isBlank(s)){
                return true;
            }else{
                return RegexUtils.isPhoneValid(s);
            }
        }
    }
}
