package com.zyc;

import com.zyc.manager.UserManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class RedissonTest {

    @Autowired
    private UserManager userManager;

    @Test
    public void lock1() {
        try {
            userManager.getUserByTryLock(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void lock2() {
        try {
            userManager.getUserByTryLock(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
