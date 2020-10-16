package com.sunjh.xiyiji.data.result;

import java.util.List;

/**
 * @author sunjh
 * @version 1.0
 * @date 2020/10/16 10:51 上午
 */
public class BasePageResult<T> {
    private String message;
    private String code;
    private boolean success;
    private List<T> data;
    private int total;
    private int pageSize;
    private int pageNum;
    private int number;
}
