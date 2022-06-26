package me.jojiapp.blogserverjh.global.config;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.*;
import lombok.*;
import org.springframework.cache.*;
import org.springframework.context.annotation.*;
import org.springframework.data.redis.cache.*;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.serializer.*;

import java.time.*;

@Configuration
@RequiredArgsConstructor
public class CacheConfig {

	private final RedisConnectionFactory redisConnectionFactory;

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper()
			.findAndRegisterModules()
			.enable(SerializationFeature.INDENT_OUTPUT)
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.registerModule(new JavaTimeModule());
	}

	@Bean
	public CacheManager cacheManager() {
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
			.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
			.serializeValuesWith(RedisSerializationContext.
				SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper())))
			.entryTtl(Duration.ofSeconds(30));

		return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
			.cacheDefaults(redisCacheConfiguration).build();
	}
}
