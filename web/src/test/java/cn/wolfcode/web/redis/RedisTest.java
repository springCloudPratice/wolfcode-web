package cn.wolfcode.web.redis;

import cn.wolfcode.WebApplication;
import cn.wolfcode.web.commons.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/2 9:34
 * @email chenshiyun2011@163.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebApplication.class, RedisTest.class})
public class RedisTest {

    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void testSave() {
        redisUtils.set("1223", "121212");
        System.out.println(redisUtils.get("1223"));
    }


}
