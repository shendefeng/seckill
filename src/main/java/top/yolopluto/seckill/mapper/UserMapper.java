package top.yolopluto.seckill.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.yolopluto.seckill.entity.User;

import java.util.List;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/11 16:30
 * @description: User持久层
 * @Modified By:
 */
@Mapper
public interface UserMapper {

    /**
     * 通过手机号查询用户
     * @param mobile 手机号
     * @return 用户
     */

    public User selectById(String mobile);

    public int insertUsers(List<User> users);

    public List<User> list();
}
