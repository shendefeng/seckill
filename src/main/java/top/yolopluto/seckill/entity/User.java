package top.yolopluto.seckill.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/10 17:33
 * @description: 用户类
 * @Modified By:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nickname;
    private String password;
    private String salt;
    private String icon;
    private int loginCount;
    private Date createTime;
    private Date updateTime;
}
