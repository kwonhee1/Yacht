package HooYah.YachtUser.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.GetExParams;
import redis.clients.jedis.params.SetParams;

public class JedisTemplate implements RedisTemplate {

    private final ConnectionPool connectionPool;
    private final JsonMapper jsonMapper = new JsonMapper();

    public JedisTemplate(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void add(String key, Object value, Long second) {
        try (Jedis jedis = connectionPool.getConnection()) {
            if(second != null)
                jedis.set(key, toString(value), new SetParams().ex(second));
            else
                jedis.set(key, toString(value));
        }
    }

    @Override
    public <RT> RT get(String key, Class<RT> clazz, Long second) {
        try (Jedis jedis = connectionPool.getConnection()) {
            if(second != null)
                return fromString(jedis.getEx(key, new GetExParams().ex(second)), clazz);
            else
                return fromString(jedis.get(key), clazz);
        }
    }

    @Override
    public void remove(String key) {
        try (Jedis jedis = connectionPool.getConnection()) {
            jedis.del(key);
        }
    }

    private String toString(Object value) {
        try {
            return jsonMapper.writeValueAsString(value);
        } catch ( JsonProcessingException e) {
            throw new RedisException(e.getMessage());
        }
    }

    private <RT> RT fromString(String value, Class<RT> clazz) {
        try {
            return jsonMapper.readValue(value, clazz);
        } catch ( JsonProcessingException e) {
            throw new RedisException(e.getMessage());
        }
    }

}
