package com.xiaou.utils;

import cn.hutool.crypto.digest.DigestUtil;

public class MD5Util {
    
    public static String md5(String str) {
        return DigestUtil.md5Hex(str);
    }
}

