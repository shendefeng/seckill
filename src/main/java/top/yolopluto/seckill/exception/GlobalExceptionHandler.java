package top.yolopluto.seckill.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.yolopluto.seckill.dto.RequBean;
import top.yolopluto.seckill.dto.RequBeanEnum;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/12 19:09
 * @description: 全局异常处理类
 * MVC框架中出现异常后，统一获取
 * @Modified By:
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public RequBean ExceptionHandler(Exception e) {
        if(e instanceof GlobalException) {
            GlobalException exception = (GlobalException) e;
            return RequBean.error(exception.getRespBeanEnum());
        }else if(e instanceof BindException){
            BindException bindException = (BindException) e;
            RequBean requBean = RequBean.error(RequBeanEnum.BIND_ERROR);
            requBean.setMessage("参数校验异常：" + bindException.getAllErrors().get(0).getDefaultMessage());
            return requBean;
        }
        System.out.println("异常信息" + e);
        return RequBean.error(RequBeanEnum.ERROR);
    }
}
