package com.freelance.www.base.util;

import java.util.regex.Matcher;

public class FLStringUtils {
    public static String replcaceCRLF(String myString) {
        String result = null;
        Matcher m = CommonConstant.CRLF.matcher(myString);
        if (m.find())
            result = m.replaceAll("");
        return result;
    }
}
