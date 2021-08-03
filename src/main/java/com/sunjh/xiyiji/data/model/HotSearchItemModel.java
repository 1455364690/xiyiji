package com.sunjh.xiyiji.data.model;

import lombok.Data;

import java.util.List;

/**
 * @author sunjh
 * @version 1.0
 * @date 2021/8/4 12:13 上午
 */
@Data
public class HotSearchItemModel {
    /**
     * 热搜原始句子
     */
    private String originContent;
    /**
     * 从热搜中提取的关键词
     */
    private List<String> keywords;
    /**
     * 热搜指向的链接
     */
    private List<String> urls;
    /**
     * 热搜来源，包括微博，百度等
     */
    private String source;
}
