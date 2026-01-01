package com.example.hotelservice.config;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.client.RestTemplate;

@EnableCaching
@Configuration
public class CacheConfig {

        @Bean
        public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
                RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofMinutes(10))
                                .serializeKeysWith(
                                                RedisSerializationContext.SerializationPair
                                                                .fromSerializer(new StringRedisSerializer()))
                                .serializeValuesWith(RedisSerializationContext.SerializationPair
                                                .fromSerializer(new GenericJackson2JsonRedisSerializer()))
                                .enableTimeToIdle();
                return RedisCacheManager.builder(redisConnectionFactory)
                                .cacheDefaults(cacheConfig)
                                .build();
        }

        @Bean
        @LoadBalanced
        public RestTemplate restTemplate(RestTemplateBuilder builder) {
                return builder.build();
        }
}