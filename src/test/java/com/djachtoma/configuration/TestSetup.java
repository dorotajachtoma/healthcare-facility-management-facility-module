package com.djachtoma.configuration;

import com.djachtoma.Application;
import com.djachtoma.model.facility.Facility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.djachtoma.util.TestObjectUtil.getFacility;


@RunWith(SpringRunner.class)
@Import({RedisContainerSetup.class, KeyspaceConfig.class})
@TestPropertySource(locations = "classpath:/application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class TestSetup {

    @Autowired
    private RedisTemplate<Object, Object> template;

    @BeforeEach
    public void setup() {
        List<Facility> facilities = List.of(
                getFacility(),
                getFacility(),
                getFacility());
        try {
            this.template.executePipelined(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    facilities.forEach(x -> template.opsForHash().entries(x));
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
