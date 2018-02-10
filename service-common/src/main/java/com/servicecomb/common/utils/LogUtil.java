package com.servicecomb.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.util.regex.Pattern;

/**
 * Created by lanpeng on 16/3/19.
 */
public final class LogUtil {
    private static final Pattern pattern              = Pattern.compile("\\{\\}");

    // public static void console(String msg, Object... args) {
    // Matcher matcher = pattern.matcher(msg);
    // StringBuffer sb = new StringBuffer();
    // int i = 0;
    // while (matcher.find()) {
    // matcher.appendReplacement(sb, args[i++].toString());
    // }
    // matcher.appendTail(sb);
    // System.out.println(sb.toString());
    // }

    public static void info(Logger logger, String log, Object... arags) {
        if (logger != null && logger.isInfoEnabled() && StringUtils.isNoneBlank(log)) {
            logger.info(log, arags);
        }
    }

    public static void warn(Logger logger, String log, Object... arags) {
        if (logger != null && logger.isWarnEnabled() && StringUtils.isNoneBlank(log)) {
            logger.warn(log, arags);
        }
    }

    public static void error(Logger logger, String log, Object... arags) {
        if (logger != null && logger.isErrorEnabled() && StringUtils.isNoneBlank(log)) {
            logger.error(log, arags);
        }
    }
}
