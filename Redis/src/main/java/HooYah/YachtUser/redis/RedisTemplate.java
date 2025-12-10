package HooYah.YachtUser.redis;

public interface RedisTemplate {

    void add(String key, Object value, Long second);
    <RT> RT get(String key, Class<RT> clazz, Long second);
    void remove(String key);

}
