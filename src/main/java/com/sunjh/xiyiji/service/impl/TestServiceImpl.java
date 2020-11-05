package com.sunjh.xiyiji.service.impl;

import com.sunjh.xiyiji.dao.TestDao;
import com.sunjh.xiyiji.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sunjh
 * @version 1.0
 * @date 2020/10/16 10:43 上午
 */
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestDao testDao;

    @Override
    public String sayHello(Long userId) {
        return "hello service! " + testDao.findOneById(userId).getName();
    }
}
