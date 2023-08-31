package dy.whatsong.global.config.redis;

import dy.whatsong.global.constant.Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;

@Configuration
@RequiredArgsConstructor
@EnableRedisRepositories
public class TemplateConfig {

    private final Properties.RedisProperties redisProperties;

    /*private RedisServer redisServer;
    @PostConstruct
    public void startRedis() throws IOException, URISyntaxException {
        System.out.println("Redis="+redisProperties.getPort());
        redisServer = RedisServer.builder()
                .port(redisProperties.getPort())
                .setting("maxmemory 128M") //maxheap 128M
                .build();
        redisServer.start();
    }*/
    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        return new LettuceConnectionFactory(redisProperties.getHost(),redisProperties.getPort());
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
}
