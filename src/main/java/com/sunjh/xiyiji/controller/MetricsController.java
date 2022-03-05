package com.sunjh.xiyiji.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunjh
 * @version 1.0
 * @date 2020/11/16 1:49 下午
 */
@RestController
public class MetricsController extends BaseController{
    @GetMapping("metrics")
    public String metrics(){
        return "test.txt";
    }

}
