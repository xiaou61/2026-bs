package com.xiaou.confession.util;

import java.util.Random;

public class AnonymousNicknameGenerator {
    
    private static final String[] ADJECTIVES = {
        "可爱的", "帅气的", "温柔的", "阳光的", "神秘的", "浪漫的", "害羞的", "活泼的",
        "文艺的", "幽默的", "善良的", "勇敢的", "温暖的", "清新的", "优雅的", "开朗的",
        "呆萌的", "高冷的", "甜美的", "傲娇的"
    };
    
    private static final String[] NOUNS = {
        "小猫", "小狗", "小熊", "小兔", "小鸟", "小鹿", "小狐狸", "小松鼠",
        "小仓鼠", "小企鹅", "小海豚", "小熊猫", "小考拉", "小浣熊", "小刺猬", "小羊驼",
        "小奶牛", "小白鲸", "小水獭", "小树懒"
    };
    
    private static final Random random = new Random();
    
    public static String generate() {
        String adjective = ADJECTIVES[random.nextInt(ADJECTIVES.length)];
        String noun = NOUNS[random.nextInt(NOUNS.length)];
        int number = random.nextInt(9999) + 1;
        return adjective + noun + number;
    }
    
    public static String generateAvatar() {
        int avatarNum = random.nextInt(20) + 1;
        return "/images/avatar/default-" + avatarNum + ".png";
    }
}

