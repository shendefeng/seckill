package top.yolopluto.seckill;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/10 18:44
 * @description: redis配置测试
 * @Modified By:
 */
@SpringBootTest
public class RedisTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    void testString(){
        String number = stringRedisTemplate.opsForValue().get("seckill:stock:15");
        System.out.println("name = " + number);
    }
}
