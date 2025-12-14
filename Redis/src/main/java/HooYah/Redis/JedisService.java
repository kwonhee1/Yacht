package HooYah.Redis;

import java.util.Optional;

public class JedisService implements RedisService {

    private final JedisTemplate jedisTemplate;
    private final String category;

    public JedisService(String category, ConnectionPool connectionPool) {
        this.jedisTemplate = new JedisTemplate(connectionPool);
        this.category = category;
    }

    @Override
    public void add(Long id, Long second) {
        if(second == null)
            second = 3600L;  // default 1 시간

        String key = toKey(category, id);

        jedisTemplate.add(key, RedisValue.EXIST, second);
    }

    @Override
    public boolean isExist(Long id, SelectDB selectDB, Long second) {
        RedisValue redisValue = jedisTemplate.get(toKey(category, id), RedisValue.class, second);

        if(redisValue != null)
            return redisValue.isExist();

        Optional dbData = selectDB.select(id);

        if(dbData.isPresent())
            add(id, second);

        return dbData.isPresent();
    }

    private String toKey(String category, Long id) {
        return category + ":" + id;
    }
}
