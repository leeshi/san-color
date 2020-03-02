package ml.sansejin.sancolor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author sansejin
 * @className RedisConfiguration
 * @description TODO
 * @create 3/2/20 2:09 AM
 **/

@Configuration
public class RedisConfiguration {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //设置redisTemplate的连接工厂
        redisTemplate.setConnectionFactory(connectionFactory);

        return redisTemplate;
    }


}
