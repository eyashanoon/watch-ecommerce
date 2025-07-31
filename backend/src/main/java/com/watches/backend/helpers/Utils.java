package com.watches.backend.helpers;

public class Utils {
    public static boolean isNullOrWhiteSpace(String str){
        return str == null || str.trim().isEmpty();
    }

    public static boolean validBooleanValue(Boolean bool){
        return bool != null && bool;
    }

}
