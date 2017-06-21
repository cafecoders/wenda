package com.nowcoder.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nowcoder.model.User;
import org.apache.solr.client.solrj.io.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;



/**
 * Created by Administrator on 2017/6/20 0020.
 */
@Service
public class JedisAdapter implements InitializingBean{
  /*  private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);
    private JedisPool pool;

    public static void print(int index, Object obj){
        System.out.println(String.format("%d, %s", index, obj));
    }

    public static void mainx(String[] args){
        Jedis jedis = new Jedis("redis://localhost:6379/9");
        jedis.flushDB();

        jedis.set("10124066", "wansheng");
        jedis.rename("10124066", "115034910152");
        print(1, jedis.get("115034910152"));
        jedis.setex("10124066", 15, "wansheng pan");

        jedis.set("pv", "100");
        jedis.incrBy("pv", 5);
        print(2, jedis.get("pv"));

        String listName = "list";
        for(int i = 0 ; i < 10; i++){
            jedis.lpush(listName, "a" + i);
        }

        print(3, jedis.lrange(listName, 0, 12));
        print(4, jedis.lrange(listName, 0, 3));
        print(5, jedis.llen(listName));
        print(6, jedis.lpop(listName));
        print(7, jedis.lindex(listName, 3));
        print(8, jedis.linsert(listName, BinaryClient.LIST_POSITION.AFTER, "a4", "aa"));
        print(9, jedis.linsert(listName, BinaryClient.LIST_POSITION.BEFORE, "a4", "bb"));
        print(10, jedis.lrange(listName, 0, 15));

        //hash

        String userKey = "userxx";
        jedis.hset(userKey, "name", "jim");
        jedis.hset(userKey, "age", "22");
        jedis.hset(userKey, "phone", "18801911214");

        print(12, jedis.hget(userKey, "name"));
        print(13, jedis.hgetAll(userKey));

        jedis.hdel(userKey, "phone");
        print(14, jedis.hgetAll(userKey));
        print(15, jedis.hexists(userKey, "email"));
        print(16, jedis.hexists(userKey, "age"));
        print(17, jedis.hkeys(userKey));
        print(18, jedis.hvals(userKey));
        jedis.hsetnx(userKey, "school", "sjtu");
        jedis.hsetnx(userKey, "name", "pws");
        print(19, jedis.hgetAll(userKey));

        //set

        String likeKey1 = "like1";
        String likeKey2 = "like2";

        for(int i = 0; i < 10; i++){
            jedis.sadd(likeKey1, String.valueOf(i));
            jedis.sadd(likeKey2, String.valueOf(i*i));
        }

        print(20, jedis.smembers(likeKey1));
        print(21, jedis.smembers((likeKey2)));
        print(22, jedis.sunion(likeKey1, likeKey2));
        print(23, jedis.sdiff(likeKey1, likeKey2));
        print(24, jedis.sinter(likeKey1, likeKey2));
        print(25, jedis.sismember(likeKey2, "16"));
        print(26, jedis.sismember(likeKey1, "16"));

        jedis.srem(likeKey1, "5");
        print(27, jedis.smembers(likeKey1));
        jedis.smove(likeKey1, likeKey2, "11");
        print(28, jedis.smembers(likeKey2));

        print(29, jedis.scard(likeKey1));

        String rankKey = "rankKey";
        jedis.zadd(rankKey, 15, "pan");
        jedis.zadd(rankKey, 60, "wan");
        jedis.zadd(rankKey, 99, "sheng");
        jedis.zadd(rankKey, 59, "wang");
        jedis.zadd(rankKey, 61, "bao");
        jedis.zadd(rankKey, 75, "heng");

        print(30, jedis.zcard(rankKey));
        print(31, jedis.zcount(rankKey, 61, 100));
        print(32, jedis.zscore(rankKey, "wan"));
        jedis.zincrby(rankKey, 2, "wanz");
        print(33, jedis.zscore(rankKey, "wanz"));
        print(35, jedis.zrange(rankKey, 0, 100));
        print(36, jedis.zrange(rankKey, 1, 3));
        print(37, jedis.zrevrange(rankKey, 1, 3));
       *//* for(Tuple tuple : jedis.zrangeByScoreWithScores(rankKey, "60", "100")){
            print(37, tuple.getElement() + ":" + String.valueOf(tuple.getScore()));
        }*//*

        print(38, jedis.zrank(rankKey, "sheng2"));
        print(39, jedis.zrevrank(rankKey, "sheng"));

        String setKey = "zset";
        jedis.zadd(setKey, 1, "a");
        jedis.zadd(setKey, 1, "b");
        jedis.zadd(setKey, 1, "c");
        jedis.zadd(setKey, 1, "d");
        jedis.zadd(setKey, 1, "e");

        print(40, jedis.zlexcount(setKey, "-", "+"));
        print(41, jedis.zlexcount(setKey, "[b", "[e"));
        print(42, jedis.zlexcount(setKey, "(b", "[e"));

        jedis.zrem(setKey, "b");
        print(43, jedis.zrange(setKey, 0, 10));
        jedis.zremrangeByLex(setKey, "(c", "+");
        print(44, jedis.zrange(setKey, 0, 5));

       *//* JedisPool pool = new JedisPool();
        for (int i = 0; i < 100; ++i) {
            Jedis j = pool.getResource();
            print(45, j.get("pv"));
            //j.close();
        }*//*

        User user = new User();
        user.setName("xx");
        user.setPassword("ppp");
        user.setHeadUrl("a.png");
        user.setSalt("salt");
        user.setId(1);
        print(46, JSONObject.toJSONString(user));
        jedis.set("user1", JSONObject.toJSONString(user));

        String value = jedis.get("user1");
        User user2 = JSON.parseObject(value, User.class);
        print(47, user2);



    }

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("jedis://locallhost:6379/10");
    }

    public long sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sadd(key, value);
        } catch (Exception e) {
            logger.error("链接失败�?" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public long srem(String key, String value){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.srem(key, value);
        }catch(Exception e){
            logger.error("链接失败�?" + e.getMessage());
        }finally{
            if(jedis != null){
                jedis.close();
            }
        }


        return 0;
    }

    public long scard(String key){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.scard(key);
        }catch(Exception e){
            logger.error("链接失败�?" + e.getMessage());
        }finally{
            if(jedis != null){
                jedis.close();
            }
        }


        return 0;
    }

    public boolean sismember(String key, String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }*/

    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);
    private JedisPool pool;

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://localhost:6379/10");
    }

    public long sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sadd(key, value);
        } catch (Exception e) {
            logger.error("链接失败！" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public long srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.srem(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public boolean sismember(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }

}

