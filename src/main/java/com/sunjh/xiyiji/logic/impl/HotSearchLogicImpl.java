package com.sunjh.xiyiji.logic.impl;

import com.sunjh.xiyiji.logic.HotSearchLogic;
import com.sunjh.xiyiji.service.HotSearchService;
import com.sunjh.xiyiji.service.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sunjh
 * @version 1.0
 * @date 2021/8/1 9:43 下午
 */
@Service
public class HotSearchLogicImpl implements HotSearchLogic {
    @Autowired
    private HotSearchService hotSearchService;

    @Autowired
    private HttpService httpService;

    @Override
    public String test() {
        return httpService.get("https://jsnjsw.cnki.net/api/DataCenter/GetMapData?zCode=110&getflag=ALL");
        //return hotSearchService.test.txt();
    }
}
