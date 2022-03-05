package com.sunjh.xiyiji.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sunjh
 * @version 1.0
 * @date 2021/10/30 7:40 下午
 */
public class XiyijiLoggerUtil {
    public static void info(Logger logger, String obj) {
        logger.info(obj);
    }

    public static void error(Logger logger, Throwable e, String obj) {
        logger.error(obj, e);
    }

}
