module YachtUser.main {
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires redis.clients.jedis;
    requires org.apache.commons.pool2;

    exports HooYah.Redis;
}