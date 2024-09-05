package com.file;

public class StringUtils {

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static String removeEnd(final String str, final String remove) {
        if (isEmpty(str) || isEmpty(remove)) {
            return str;
        }
        if (str.endsWith(remove)) {
            return str.substring(0, str.length() - remove.length());
        }
        return str;
    }
}
