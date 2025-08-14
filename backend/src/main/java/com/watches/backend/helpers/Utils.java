package com.watches.backend.helpers;

import org.antlr.v4.runtime.misc.ObjectEqualityComparator;

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
        if (input == null || input.isBlank()) return "";

        String noSpaces = input.replaceAll("\\s+", "");

        if (noSpaces.length() == 1) {
            return noSpaces.toUpperCase();
        }

        return noSpaces.substring(0, 1).toUpperCase() + noSpaces.substring(1).toLowerCase();
    }
}
