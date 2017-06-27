package com.nowcoder.util;

/**
 * Created by Administrator on 2017/6/21 0021.
 */
public class RedisKeyUtil {
    private static String SPLIT = ":";
    private static String BIZ_LIKE = "LIKE";
    static private String BIZ_DISLIKE = "DISLIKE";
    private static String BIZ_EVENT_QUEUE = "EVENT_QUEUE";

    public static String getLikeKey(int entityType, int entityId){
        return BIZ_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    public static String getDisLikeKey(int entityType, int entityId){
        return BIZ_DISLIKE + SPLIT + entityType + SPLIT + entityId;
    }

    public static String getEventQueueKey(){
        return BIZ_EVENT_QUEUE;
    }

}
