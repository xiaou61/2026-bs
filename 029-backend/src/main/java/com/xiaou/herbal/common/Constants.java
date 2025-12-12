package com.xiaou.herbal.common;

public class Constants {

    public static final String JWT_SECRET = "herbal-food-platform-secret-key-2025";
    public static final long JWT_EXPIRATION = 7 * 24 * 60 * 60 * 1000;
    public static final String TOKEN_PREFIX = "Bearer ";

    public static class UserType {
        public static final Integer NORMAL = 0;
        public static final Integer CREATOR = 1;
        public static final Integer ADMIN = 2;
    }

    public static class UserStatus {
        public static final Integer DISABLED = 0;
        public static final Integer NORMAL = 1;
    }

    public static class RecipeStatus {
        public static final Integer DRAFT = 0;
        public static final Integer REVIEWING = 1;
        public static final Integer PUBLISHED = 2;
        public static final Integer SHELVED = 3;
    }

    public static class TopicStatus {
        public static final Integer DRAFT = 0;
        public static final Integer PUBLISHED = 1;
        public static final Integer CLOSED = 2;
    }

    public static class CommentStatus {
        public static final Integer NORMAL = 0;
        public static final Integer HIDDEN = 1;
    }

    public static class CommentTargetType {
        public static final Integer RECIPE = 0;
        public static final Integer TOPIC = 1;
    }

    public static class CollectionTargetType {
        public static final Integer RECIPE = 0;
        public static final Integer TOPIC = 1;
    }

    public static class CreatorAuthStatus {
        public static final Integer PENDING = 0;
        public static final Integer APPROVED = 1;
        public static final Integer REJECTED = 2;
    }

    public static class IngredientStatus {
        public static final Integer DISABLED = 0;
        public static final Integer NORMAL = 1;
    }

    public static class RedisKeyPrefix {
        public static final String USER_TOKEN = "user:token:";
        public static final String USER_INFO = "user:info:";
        public static final String RECIPE_INFO = "recipe:info:";
        public static final String RECIPE_LIST = "recipe:list:";
        public static final String INGREDIENT_INFO = "ingredient:info:";
        public static final String HOT_RECIPES = "recipes:hot";
    }
}
