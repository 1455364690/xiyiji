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

    public static void main(String[] args) {

    }

    public BaseResult<T> success(T data) {
        BaseResult<T> result = new BaseResult<>();
        result.setCode(null);
        result.setMessage(null);
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public BaseResult<T> fail(String message, String errorCode) {
        BaseResult<T> result = new BaseResult<>();
        result.setCode(errorCode);
        result.setMessage(message);
        result.setSuccess(false);
        result.setData(null);
        return result;
    }
}
