package com.sunjh.xiyiji.logic.impl;

import com.sunjh.xiyiji.logic.SkywalkingLogic;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author sunjh
 * @version 1.0
 * @date 2020/12/15 5:28 下午
 */
@Service
public class SkywalkingLogicImpl implements SkywalkingLogic {
    @Scheduled(cron = "*/5 * * * * ?")
    public void test(){
        System.out.println("ttttttt");
    }
}
