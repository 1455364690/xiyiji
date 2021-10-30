package com.sunjh.xiyiji.data.result;

import lombok.Data;

import java.util.List;

/**
 * @author sunjh
 * @version 1.0
 * @date 2020/10/16 10:51 上午
 */
@Data
public class BasePageResult<T> {
    private String message;
    private String code;
    private boolean success;
    private List<T> data;
    private int total;
    private int pageSize;
    private int pageNum;
    private int number;

    public BasePageResult<T> success(List<T> data,int pageNum,int pageSize, int total) {
        BasePageResult<T> result = new BasePageResult<>();
        result.setCode(null);
        result.setMessage(null);
        result.setSuccess(true);
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        result.setTotal(total);
        result.setData(data);
        return result;
    }

    public BasePageResult<T> fail(String message, String errorCode) {
        BasePageResult<T> result = new BasePageResult<>();
        result.setCode(errorCode);
        result.setMessage(message);
        result.setSuccess(false);
        result.setData(null);
        return result;
    }
}
