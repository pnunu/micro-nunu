package test.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pnunu.user.EdgeServiceApplication;
import pnunu.user.redis.RedisClient;

/**
 * @Author: pnunu
 * @Date: Created in 11:11 2018/8/9
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdgeServiceApplication.class)
public class RedisTest {

    @Autowired
    private RedisClient redisClient;


    @Test
    public void setRedisTest() {
//        redisUtils.set("1", 2, 1);
        System.out.println(redisClient);
    }
}
