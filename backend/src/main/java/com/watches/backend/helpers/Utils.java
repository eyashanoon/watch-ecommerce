package com.watches.backend.helpers;

import java.util.Collection;

public class Utils {
    public static boolean isNullOrWhiteSpace(String str){
        return str == null || str.trim().isEmpty();
    }

    public static boolean validBooleanValue(Boolean bool){
        return bool != null;
    }

    public static boolean isNullOrEmpty(Object obj){
        return obj == null
                || obj instanceof Collection && ((Collection<?>)obj).isEmpty();
    }

    public static String normalizeString(String input) {
        if (isNullOrWhiteSpace(input)) return "";

        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}
