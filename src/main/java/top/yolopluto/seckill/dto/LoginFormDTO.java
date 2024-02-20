package top.yolopluto.seckill.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import top.yolopluto.seckill.validator.isPhone;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/11 16:25
 * @description: 用户登录类
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginFormDTO {
    @NotNull
    @isPhone
    private String mobile;
    @NotNull
    @Length(min = 32)
    private String password;
    @Override
    public String toString() {
        return "LoginFormDTO{" +
                "mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
