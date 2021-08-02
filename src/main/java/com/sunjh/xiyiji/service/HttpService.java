package com.sunjh.xiyiji.service;

import java.util.Map;

/**
 * @author sunjh
 * @version 1.0
 * @date 2021/8/3 12:39 上午
 */
public interface HttpService {
    /**
     * @param url
     * @param parms
     * @return
     */
    String post(String url, Map<String, String> parms);

    /**
     * @param url
     * @return
     */
    String get(String url);

    /**
     * @param url
     * @param params
     * @return
     */
    String get(String url, Map<String, String> params);
}
