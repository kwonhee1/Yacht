package HooYah.Gateway.gateway;

import io.netty.util.AttributeKey;

public class AttributeConfig {

    public static final AttributeKey<String> Host = AttributeKey.valueOf("Host");
    public static final AttributeKey<Integer> Port = AttributeKey.valueOf("Port");

}
