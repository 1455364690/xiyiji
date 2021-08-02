package com.sunjh.xiyiji.controller;

import com.sunjh.xiyiji.logic.HotSearchLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunjh
 * @version 1.0
 * @date 2021/8/1 8:14 下午
 */
@RestController
public class HotSearchController {
    @Autowired
    private HotSearchLogic hotSearchLogic;

    @GetMapping("hello")
    public String hello() {
        return hotSearchLogic.test();
    }
}
