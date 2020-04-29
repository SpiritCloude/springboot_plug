package springboot.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

	// 1. Create config object
	@Bean
	public RedissonClient createClient() {
		Config config = new Config();
		config.useSingleServer()
	       .setAddress("redis://47.92.232.26:6379");
		return Redisson.create(config);
	}
	// or read config from file
	//config = Config.fromYAML(new File("config-file.yaml")); 
}
