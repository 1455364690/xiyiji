package com.sunjh.xiyiji.controller;

import com.sunjh.xiyiji.data.result.BaseResult;
import com.sunjh.xiyiji.logic.TestLogic;
import com.sunjh.xiyiji.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunjh
 * @version 1.0
 * @date 2020/10/16 10:41 上午
 */
@RestController
public class TestController extends BaseController {

    @Autowired
    private TestLogic testLogic;

    @GetMapping("/test/get")
    public BaseResult<String> testGet(@RequestParam Long userId) {
        BaseResult<String> result = new BaseResult<>();
        result.setCode("200");
        result.setMessage("success");
        result.setSuccess(true);
        result.setData(testLogic.sayHello(userId));
        return result;
    }

    @PostMapping("/test/post")
    public BaseResult<String> testPost() {
        return null;
    }
}
