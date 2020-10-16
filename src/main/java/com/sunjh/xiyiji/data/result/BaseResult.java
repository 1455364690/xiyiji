package com.sunjh.xiyiji.data.result;

import lombok.Data;

/**
 * @author sunjh
 * @version 1.0
 * @date 2020/10/16 10:48 上午
 */
@Data
public class BaseResult<T> {
    private String message;
    private String code;
    private boolean success;
    private T data;
}
