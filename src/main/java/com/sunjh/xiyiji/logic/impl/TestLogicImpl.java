package com.sunjh.xiyiji.logic.impl;

import com.sunjh.xiyiji.logic.TestLogic;
import com.sunjh.xiyiji.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sunjh
 * @version 1.0
 * @date 2020/11/4 3:21 下午
 */
@Service
public class TestLogicImpl implements TestLogic {
    @Autowired
    private TestService testService;

    @Override
    public String sayHello() {
        return "hello logic!" + testService.sayHello();
    }
}
