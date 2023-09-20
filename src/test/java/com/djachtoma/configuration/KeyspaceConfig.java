package com.djachtoma.configuration;

import com.djachtoma.configuration.database.ConnectionProperties;
import com.djachtoma.model.facility.Facility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.KeyspaceConfiguration;

import java.util.Collections;

public class KeyspaceConfig extends KeyspaceConfiguration {

    private static final String KEYSPACE_NAME = "facilityTest";

    @Autowired
    private ConnectionProperties properties;

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory);
        return template;
    }

    @Override
    protected Iterable<KeyspaceSettings> initialConfiguration() {
        return Collections.singleton(new KeyspaceSettings(Facility.class, KEYSPACE_NAME));
    }
}
