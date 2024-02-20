package top.yolopluto.seckill.exception;

import top.yolopluto.seckill.dto.RequBeanEnum;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/12 19:07
 * @description: 全局异常类
 * @Modified By:
 */
public class GlobalException extends RuntimeException {
    private RequBeanEnum requBeanEnum;

    public RequBeanEnum getRespBeanEnum() {
        return requBeanEnum;
    }

    public void setRespBeanEnum(RequBeanEnum requBeanEnum) {
        this.requBeanEnum = requBeanEnum;
    }

    public GlobalException(RequBeanEnum requBeanEnum) {
        this.requBeanEnum = requBeanEnum;
    }
}
