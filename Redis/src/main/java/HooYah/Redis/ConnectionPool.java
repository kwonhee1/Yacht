package HooYah.Redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class ConnectionPool {

    private static ConnectionPool instance;

    private JedisPool pool;

    private ConnectionPool(String host, int port, String username, String password, int maxConnection) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxConnection);

        pool = new JedisPool(config, host, port, 2000, username, password);
    }

    public static ConnectionPool generate(String host, int port, String username, String password, int maxConnection) {
        instance = new ConnectionPool(host, port, password, username, maxConnection);
        return getInstance();
    }

    public static ConnectionPool getInstance() {
        if(instance == null)
            throw new RuntimeException("Instance Not Exist, generate before");
        return instance;
    }

    public Jedis getConnection() {
        return pool.getResource();
    }

}
