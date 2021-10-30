package com.sunjh.xiyiji.service.xuyujie;

import com.sunjh.xiyiji.dao.xuyujiedao.DurationDAO;
import com.sunjh.xiyiji.data.xuyujiemodel.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Operation;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.OptionalDouble;

/**
 * @author sunjh
 * @version 1.0
 * @date 2021/10/30 10:54 下午
 */
@Service
public class XuyujieServiceImpl implements XuyujieService {
    @Autowired
    private DurationDAO durationDAO;

    @Override
    public boolean saveAllDurations(List<Duration> durationList) {
        try {
            durationDAO.saveAll(durationList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Duration> findByNameAndTypeAndUserName(String name, String type, String username) {
        return durationDAO.findByNameAndTypeAndUserName(name, type, username);
    }

    @Override
    public Duration saveDuration(Duration duration) {
        duration.setGmtCreate(new Timestamp(System.currentTimeMillis()));
        duration.setGmtModified(new Timestamp(System.currentTimeMillis()));
        return durationDAO.save(duration);
    }
}
