package team.redrock.tyre.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import team.redrock.tyre.domain.EmptyRoom;

import java.net.UnknownHostException;

/**
 * redis的配置
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        // 创建一个模板类
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        // 将刚才的redis连接工厂设置到模板类中
        template.setConnectionFactory(factory);
        // 设置key的序列化器
        template.setKeySerializer(new StringRedisSerializer());
        // 设置value的序列化器
        //使用Jackson 2，将对象序列化为JSON
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        //json转对象类，不设置默认的会将json转成hashmap
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);

        return template;
    }

    @Bean
    public RedisTemplate<Object, Object> objectRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        //使用json的序列化器
        Jackson2JsonRedisSerializer ser = new Jackson2JsonRedisSerializer<Object>(Object.class);
//        JdkSerializationRedisSerializer ser = new JdkSerializationRedisSerializer();
        template.setDefaultSerializer(ser);                 //相当于key的序列化类型和value的序列化类型
        return template;
    }

    /**
     * 自定义序列化器
     * @param redisConnectionFactory
     * @return tempalte
     * @throws UnknownHostException
     */
    @Bean
    public RedisTemplate<String, EmptyRoom> EmptyredisTemplate(
            RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<String, EmptyRoom> template = new RedisTemplate<String, EmptyRoom>();
        template.setConnectionFactory(redisConnectionFactory);
        //使用json的序列化器
        Jackson2JsonRedisSerializer ser = new Jackson2JsonRedisSerializer<EmptyRoom>(EmptyRoom.class);
//       JdkSerializationRedisSerializer ser = new JdkSerializationRedisSerializer();
        template.setDefaultSerializer(ser);                 //相当于key的序列化类型和value的序列化类型
        return template;
    }
}
