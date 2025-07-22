package com.watches.backend.helpers;

public class StringUtils {
    public static boolean isNullOrWhiteSpace(String str){
        return str == null || str.trim().isEmpty();
    }
}
