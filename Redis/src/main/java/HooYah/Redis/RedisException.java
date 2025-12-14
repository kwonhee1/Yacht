package HooYah.Redis;

public class RedisException extends RuntimeException {
    public RedisException(String message) {
        super("redis error : " + message);
    }
}
