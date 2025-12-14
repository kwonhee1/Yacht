package HooYah.Redis;

import java.util.Optional;

public interface RedisService {

    void add(Long id, Long second);

    boolean isExist(Long id, SelectDB selectDB, Long second);

    @FunctionalInterface
    interface SelectDB {
        Optional select(Long id);
    }

}
