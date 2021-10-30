package com.sunjh.xiyiji.service.xuyujie;

import com.sunjh.xiyiji.data.xuyujie.XuyujieQueryCondition;
import com.sunjh.xiyiji.data.xuyujie.vo.XuyujieUploadVO;
import com.sunjh.xiyiji.data.xuyujiemodel.Duration;

import java.awt.print.Pageable;
import java.util.List;

/**
 * @author sunjh
 * @version 1.0
 * @date 2021/10/30 10:54 下午
 */
public interface XuyujieService {
    boolean saveAllDurations(List<Duration> durationList);

    List<Duration> findByNameAndTypeAndUserName(String name, String type, String username);

    Duration saveDuration(Duration duration);

    List<Duration> getDurationDataListByCondition(XuyujieQueryCondition condition);

    int countByUserName(String userName);
}