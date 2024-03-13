package top.yolopluto.seckill.config;

import top.yolopluto.seckill.dto.UserDTO;

/**
 * @author: yolopluto
 * @Date: created in 2024/3/9 0:18
 * @description: 存放用户
 * @Modified By:
 */
public class UserContext {
    private static final ThreadLocal<UserDTO> userHolder = new ThreadLocal<>();

    public static void setUser(UserDTO user) {
        userHolder.set(user);
    }

    public static UserDTO getUser() {
        return userHolder.get();
    }


    public static void removeUser(){
        userHolder.remove();
    }
}
