package HooYah.test;

import HooYah.Redis.RedisService;

public class MainTest {

    public static void main(String[] args) {
        RedisConfig redisConfig = new RedisConfig();
        RedisService redisService = redisConfig.userRedisService();

    }
}
