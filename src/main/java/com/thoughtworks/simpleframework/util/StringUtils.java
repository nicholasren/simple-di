package com.thoughtworks.simpleframework.util;

public class StringUtils {
    public static String stripLeadSlash(String str) {
        String newStr = str;
        if (newStr.startsWith("/"))
            newStr = newStr.substring(1);
        return newStr;
    }
}
