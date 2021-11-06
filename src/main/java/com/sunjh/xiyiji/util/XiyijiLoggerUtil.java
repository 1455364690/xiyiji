package com.sunjh.xiyiji.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author sunjh
 * @version 1.0
 * @date 2021/10/30 7:40 下午
 */
public class XiyijiLoggerUtil {
    public static void info(Logger logger, String... obj) {
        Arrays.stream(obj).forEach(System.out::println);
    }

    public static void warn(Logger logger, String... obj) {
        Arrays.stream(obj).forEach(System.out::println);
    }

    public static void error(Logger logger, String... obj) {
        Arrays.stream(obj).forEach(System.out::println);
    }
}
