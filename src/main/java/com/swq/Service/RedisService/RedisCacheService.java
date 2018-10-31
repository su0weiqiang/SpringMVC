package com.swq.Service.RedisService;

public interface RedisCacheService {
    /**
     * 根据id缓存数据。 参数说明：id和sessionObject不允许为null或空值。 数据库若已存在同名键，则会覆盖掉
     ***/
    public boolean putSessionObject(String id, Object sessionObject);

    /**
     * 根据id删除已缓存的数据。 参数说明：id不允许为null和空值
     ***/
    public boolean clearSessionObject(String id);

    /**
     * 根据id获取已缓存的缓存数据。 参数说明：id不允许为null和空值
     ***/
    public Object getsessionObject(String id);
}
