package com.github.datalking.util;

/**
 * 字符串工具类
 *
 * @author yaoo on 4/3/18
 */
public interface StringUtils {

    static boolean isEmpty(String str) {
        return (str == null || "".equals(str));
    }

    static boolean isNotEmpty(String str) {
        return (str != null && str.length() > 0);
    }


    static String firstLetterUpperCase(String original) {
        String result = "";
        result = Character.toUpperCase(original.charAt(0)) + original.substring(1);
        return result;
    }


    static String replace(String inString, String oldPattern, String newPattern) {

        if (!hasLength(inString) || !hasLength(oldPattern) || newPattern == null) {
            return inString;
        }
        int index = inString.indexOf(oldPattern);
        if (index == -1) {
            // no occurrence -> can return input as-is
            return inString;
        }

        int capacity = inString.length();
        if (newPattern.length() > oldPattern.length()) {
            capacity += 16;
        }
        StringBuilder sb = new StringBuilder(capacity);

        int pos = 0;  // our position in the old string
        int patLen = oldPattern.length();
        while (index >= 0) {
            sb.append(inString.substring(pos, index));
            sb.append(newPattern);
            pos = index + patLen;
            index = inString.indexOf(oldPattern, pos);
        }

        // append any characters to the right of a match
        sb.append(inString.substring(pos));
        return sb.toString();
    }

    static boolean hasLength(String str) {
        return hasLength((CharSequence) str);
    }

    static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }

}
