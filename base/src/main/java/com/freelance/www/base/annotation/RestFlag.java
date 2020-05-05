package com.freelance.www.base.annotation;

import org.apache.commons.lang3.ArrayUtils;

public enum  RestFlag {
    NO_TOKEN;

    private RestFlag() {
    }

    public static boolean contains(RestFlag[] flags, RestFlag flag) {
        if (ArrayUtils.isEmpty(flags)) {
            return false;
        } else {
            RestFlag[] var2 = flags;
            int var3 = flags.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                RestFlag f = var2[var4];
                if (flag == f) {
                    return true;
                }
            }
            return false;
        }
    }
}
