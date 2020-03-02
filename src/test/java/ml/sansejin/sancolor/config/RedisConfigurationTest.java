package ml.sansejin.sancolor.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisConfigurationTest {
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void test() {
        System.out.println("Set nice = test");
        redisTemplate.opsForValue().set("nice", "test");
        System.out.println("Get nice = " + redisTemplate.opsForValue().get("nice"));
    }
}