package com.nowcoder.util;

/**
 * Created by Administrator on 2017/6/21 0021.
 */
public class RedisKeyUtil {
    private static String SPLIT = ":";
    private static String BIZ_LIKE = "LIKE";
    static private String BIZ_DISLIKE = "DISLIKE";
    private static String BIZ_EVENT_QUEUE = "EVENT_QUEUE";
    private static String BIZ_FOLLOWER = "FOLLOWER";
    private static String BIZ_FOLLOWEE = "FOLLOWEE";

    public static String getLikeKey(int entityType, int entityId){
        return BIZ_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    public static String getDisLikeKey(int entityType, int entityId){
        return BIZ_DISLIKE + SPLIT + entityType + SPLIT + entityId;
    }

    public static String getEventQueueKey(){
        return BIZ_EVENT_QUEUE;
    }

    public static String getFollowerKey(int entityType, int entityId){
        return BIZ_FOLLOWER + SPLIT + entityType + SPLIT + entityId;
    }

    public static String getFolloweeKey(int userId, int entityType){
        return BIZ_FOLLOWEE + SPLIT + userId + SPLIT + entityType;
    }

}
