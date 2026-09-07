package com.xiaou.express.common;

public class Constants {
    
    public static final class OrderStatus {
        public static final int PENDING = 0;
        public static final int ACCEPTED = 1;
        public static final int PICKED_UP = 2;
        public static final int DELIVERING = 3;
        public static final int COMPLETED = 4;
        public static final int CANCELLED = 5;
        public static final int REFUNDED = 6;
    }
    
    public static final class UserStatus {
        public static final int NORMAL = 0;
        public static final int DISABLED = 1;
    }
    
    public static final class TransactionType {
        public static final int RECHARGE = 1;
        public static final int CONSUME = 2;
        public static final int INCOME = 3;
        public static final int WITHDRAW = 4;
        public static final int REFUND = 5;
    }
    
    public static final class NotificationType {
        public static final int SYSTEM = 1;
        public static final int ORDER = 2;
        public static final int TRANSACTION = 3;
    }
    
    public static final class ComplaintStatus {
        public static final int PENDING = 0;
        public static final int HANDLED = 1;
        public static final int REJECTED = 2;
    }
    
    public static final int MIN_CREDIT_SCORE = 60;
    
    public static final int MAX_ACTIVE_ORDERS = 3;
}

