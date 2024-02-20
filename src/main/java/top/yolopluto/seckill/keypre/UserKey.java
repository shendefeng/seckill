package top.yolopluto.seckill.keypre;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/10 20:15
 * @description: 用户相关的key前缀
 * @Modified By:
 */
public class UserKey extends BasePrefix{
    private UserKey(String prefix) {
        super(prefix);
    }
    // 定义id的前缀名， 当前类名:id == user  其实就是  UserKey：id
    public static UserKey getById = new UserKey("id");
    // 定义name的前缀名， 当前类名:name == user  其实就是  UserKey：name
    public static UserKey getByName = new UserKey("name");
}

