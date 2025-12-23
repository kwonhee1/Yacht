package HooYah.test;

import HooYah.Redis.ConnectionPool;
import HooYah.Redis.JedisService;
import HooYah.Redis.RedisService;

public class RedisConfig {

    private ConnectionPool connectionPool = ConnectionPool.generate("host", 6, "password", "username", 3);

    public RedisService userRedisService() {
        return new JedisService("REDIS_USER_MODULE_NAME", connectionPool);
    }

}
