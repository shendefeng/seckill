package top.yolopluto.seckill.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/11 12:49
 * @description: Result返回对象
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequBean {
    private long code;
    private String message;
    private Object object;
    public static RequBean success() {
        return new RequBean(RequBeanEnum.SUCCESS.getCode(), RequBeanEnum.SUCCESS.getMsg(), null);
    }

    public static RequBean success(Object object) {
        return new RequBean(RequBeanEnum.SUCCESS.getCode(), RequBeanEnum.SUCCESS.getMsg(), object);
    }

    public static RequBean error(RequBeanEnum requBeanEnum) {
        return new RequBean(requBeanEnum.getCode(), requBeanEnum.getMsg(), null);
    }

    public static RequBean error(RequBeanEnum requBeanEnum, Object object) {
        return new RequBean(requBeanEnum.getCode(), requBeanEnum.getMsg(), object);
    }
}
