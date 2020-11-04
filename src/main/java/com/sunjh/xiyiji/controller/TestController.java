package com.sunjh.xiyiji.controller;

import com.sunjh.xiyiji.data.result.BaseResult;
import com.sunjh.xiyiji.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunjh
 * @version 1.0
 * @date 2020/10/16 10:41 上午
 */
@RestController
public class TestController extends BaseController {

    @Autowired
    private TestService testService;

    @GetMapping("/test/get")
    public BaseResult<String> testGet() {
        BaseResult<String> result = new BaseResult<>();
        result.setCode("200");
        result.setMessage("success");
        result.setSuccess(true);
        result.setData(testService.sayHello());
        return result;
    }

    @PostMapping("/test/post")
    public BaseResult<String> testPost() {
        return null;
    }
}
