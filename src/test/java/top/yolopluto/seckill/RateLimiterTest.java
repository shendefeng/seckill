package top.yolopluto.seckill;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

/**
 * @author: yolopluto
 * @Date: created in 2024/3/13 19:17
 * @description: RateLimiter限流
 * @Modified By:
 */
@SpringBootTest
public class RateLimiterTest {
    @Test
    public void testRateLimiter() {
        RateLimiter rateLimiter = RateLimiter.create(5, 3, TimeUnit.SECONDS);
        for (int i = 0; i < 20; i++) {
            double sleepingTime = rateLimiter.acquire(1);
            System.out.printf("get 1 tokens: %sds%n", sleepingTime);
        }
    }
}
