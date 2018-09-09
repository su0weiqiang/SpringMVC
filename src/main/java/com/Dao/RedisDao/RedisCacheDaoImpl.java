package com.Dao.RedisDao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * redis dao 的实现层
 *
 * 采用 redis hash 的方式存储对象  需要对这个对象添加@Component 或者 @Repository纳入spring 的管理范围
 *
 * */
//@Component
@Repository("redisCacheDao")
public class RedisCacheDaoImpl implements RedisCacheDao {

    //日志
    private final static Logger logger= LoggerFactory.getLogger(RedisCacheDaoImpl.class);

    //注入 RedisTemplate 序列化，可以对对象存储，而不是只能字符串
    @Resource
    protected RedisTemplate<Serializable, Serializable> redisTemplate;


    //定义了一个 hash   使用 hash 保存对象，具体知识在 http://www.runoob.com/redis/redis-data-types.html 中的 type
    static final byte[] my_hash = SerializeUtil.serialize("my_hash");


    /**
     *
     *      redis hashb保存方式    hash表，key，value
     *
     *
     *          hExists(myhash,key)   判断该 key 在 hash 中
      *
     * */



    /**
     * 根据key存储object
     *
     * @param key
     *            键
     * @param value
     *            要存储的对象Object
     * @return boolean 存储成功或失败的标志
     ***/
    @Override
    public boolean saveObject(String key, Object value) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keyByte = SerializeUtil.serialize(key);
                byte[] valueByte = SerializeUtil.serialize(value);
                Boolean flag = false;
                if (connection.hExists(my_hash, keyByte)) {
                    logger.info("数据已存在，先删除旧数据");
                    connection.hDel(my_hash, keyByte);
                }


                flag = connection.hSet(my_hash, keyByte, valueByte);
                return flag;
            }
        });
        return result;

    }

    /**
     * 根据key获取object
     *
     * @param key
     *            键
     * @return Object 与key对应的object
     ***/
    @Override
    public Object getObject(String key) {
        Object result = redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                Object returnObject = null;

                byte[] keyByte = SerializeUtil.serialize(key);
                if (connection.hExists(my_hash, keyByte)) {
                    logger.info("数据存在--开始读取");
                    returnObject = SerializeUtil
                            .unserialize(connection.hGet(my_hash, keyByte));
                } else {
                    logger.info("数据不存在");
                }
                return returnObject;
            }
        });

        return result;
    }

    /**
     * 根据key删除数据
     *
     * @param key
     *            键
     * @return boolean 删除成功或失败的标志
     ***/
    @Override
    public boolean deleteKey(String key) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keyByte = SerializeUtil.serialize(key);
                Boolean flag = false;
                if (connection.hExists(my_hash, keyByte)) {
                    logger.info("存在该键，执行删除");
                    flag = connection.hDel(my_hash, keyByte);
                }
                return flag;
            }
        });
        return result;

    }
}
