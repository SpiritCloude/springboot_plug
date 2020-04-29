package springboot.config;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;


@Configuration
public class RedisConfig {

	/* 
	 * 序列化
	 * 
	 *  */
	
	@Bean(name = "jacksonRedisTemplate")
	public RedisTemplate<Object, Object> jacksonRedisTemplate(RedisConnectionFactory redisConnectionFactory)throws UnknownHostException{
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
		return template;
	}
	
}
