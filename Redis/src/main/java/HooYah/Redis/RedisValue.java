package HooYah.Redis;

public enum RedisValue {
    EXIST,
    NOT_EXIST;

    public boolean isExist(){
        return this.equals(EXIST);
    }
}
