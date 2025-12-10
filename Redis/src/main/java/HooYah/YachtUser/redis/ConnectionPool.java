package HooYah.YachtUser.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class ConnectionPool {

    private static ConnectionPool instance;

    private JedisPool pool;

    private ConnectionPool(String host, int port, String password, int maxConnection) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxConnection);

        if(password==null | password.isEmpty())
            pool = new JedisPool(config, host, port, 2000);
        else
            pool = new JedisPool(config, host, port, 2000, password);
    }

    public static ConnectionPool generate(String host, int port, String password, int maxConnection) {
        instance = new ConnectionPool(host, port, password, maxConnection);
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
