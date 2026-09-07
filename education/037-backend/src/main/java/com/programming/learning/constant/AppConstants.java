package com.programming.learning.constant;

/**
 * 应用常量
 */
public class AppConstants {

    /**
     * JWT常量
     */
    public static class Jwt {
        public static final String USER_ID = "userId";
        public static final String ROLE = "role";
        public static final String OPEN_ID = "openId";
    }

    /**
     * Redis Key常量
     */
    public static class RedisKey {
        public static final String USER_TOKEN = "user:token:";
        public static final String CHECK_IN = "user:checkin:";
        public static final String RANK_SCORE = "rank:score";
        public static final String RANK_CHECKIN = "rank:checkin";
        public static final String HOT_COURSE = "hot:course";
        public static final String HOT_QUESTION = "hot:question";
        public static final String HOT_ARTICLE = "hot:article";
    }

    /**
     * 目标类型常量
     */
    public static class TargetType {
        public static final String COURSE = "COURSE";
        public static final String QUESTION = "QUESTION";
        public static final String ANSWER = "ANSWER";
        public static final String ARTICLE = "ARTICLE";
        public static final String CODE = "CODE";
    }

    /**
     * 积分来源类型常量
     */
    public static class ScoreSource {
        public static final String CHECK_IN = "CHECK_IN";
        public static final String PUBLISH_TUTORIAL = "PUBLISH_TUTORIAL";
        public static final String PUBLISH_QUESTION = "PUBLISH_QUESTION";
        public static final String ANSWER_QUESTION = "ANSWER_QUESTION";
        public static final String ANSWER_ACCEPTED = "ANSWER_ACCEPTED";
        public static final String POST_LIKED = "POST_LIKED";
        public static final String COMPLETE_COURSE = "COMPLETE_COURSE";
    }

    /**
     * 通知类型常量
     */
    public static class NotificationType {
        public static final String SYSTEM = "SYSTEM";
        public static final String LIKE = "LIKE";
        public static final String COMMENT = "COMMENT";
        public static final String ANSWER = "ANSWER";
        public static final String ACCEPT = "ACCEPT";
        public static final String FOLLOW = "FOLLOW";
    }

    /**
     * 等级常量
     */
    public static class Level {
        public static final int LV1 = 1;
        public static final int LV2 = 2;
        public static final int LV3 = 3;
        public static final int LV4 = 4;
        public static final int LV5 = 5;

        public static final String LV1_NAME = "新手村民";
        public static final String LV2_NAME = "代码学徒";
        public static final String LV3_NAME = "编程骑士";
        public static final String LV4_NAME = "技术大师";
        public static final String LV5_NAME = "代码传说";
    }
}
