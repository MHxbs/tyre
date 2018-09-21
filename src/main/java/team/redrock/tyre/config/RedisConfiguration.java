package team.redrock.tyre.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching

public class RedisConfiguration
        extends CachingConfigurerSupport {


    /**
     * 自定义生成key的规则
     *
     * @return
     */
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                //格式化缓存key字符串
                StringBuilder sb = new StringBuilder();
                //追加类名
                sb.append(o.getClass().getName());
                //追加方法名
                sb.append(method.getName());
                //遍历参数并且追加
                for (Object obj : objects) {
                    sb.append(obj.toString());
                }
                System.out.println("调用Redis缓存Key : " + sb.toString());
                return sb.toString();
            }
        };
    }


    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {


        //        设置CacheManager的值序列化方式为JdkSerializationRedisSerializer,但其实RedisCacheConfiguration默认就是使用StringRedisSerializer序列化key，JdkSerializationRedisSerializer序列化value,所以以下注释代码为默认实现
        ClassLoader loader = this.getClass().getClassLoader();
        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer();
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer);
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);

//        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();

        //user信息缓存配置
        RedisCacheConfiguration gradeCacheConfiguration = defaultCacheConfig.entryTtl(Duration.ofHours(5)).prefixKeysWith("gradeUser");
        RedisCacheConfiguration newsCacheConfiguration = defaultCacheConfig.entryTtl(Duration.ofDays(1)).prefixKeysWith("newsUser");
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        redisCacheConfigurationMap.put("gradeUser", gradeCacheConfiguration);
        redisCacheConfigurationMap.put("newsUser", newsCacheConfiguration);
        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);



        //设置默认超过期时间是30秒
            defaultCacheConfig.entryTtl(Duration.ofMinutes(30));
        //初始化RedisCacheManager
        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, defaultCacheConfig, redisCacheConfigurationMap);
//        RedisCacheManager cacheManager = RedisCacheManager.builder(connectionFactory)
//                .withInitialCacheConfigurations(redisCacheConfigurationMap)
//                .cacheDefaults(defaultCacheConfig).build();
        return cacheManager;
    }

}