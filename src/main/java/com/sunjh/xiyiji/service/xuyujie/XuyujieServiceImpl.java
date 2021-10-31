package com.sunjh.xiyiji.service.xuyujie;

import com.sunjh.xiyiji.dao.xuyujiedao.DurationDAO;
import com.sunjh.xiyiji.dao.xuyujiedao.ExcursionSizeDAO;
import com.sunjh.xiyiji.dao.xuyujiedao.F0AccelerationDAO;
import com.sunjh.xiyiji.dao.xuyujiedao.MeanF0DAO;
import com.sunjh.xiyiji.data.xuyujie.XuyujieQueryCondition;
import com.sunjh.xiyiji.data.xuyujie.convertor.XuyujieUploadVOConvertor;
import com.sunjh.xiyiji.data.xuyujie.vo.XuyujieUploadVO;
import com.sunjh.xiyiji.data.xuyujiemodel.Duration;
import com.sunjh.xiyiji.data.xuyujiemodel.ExcursionSize;
import com.sunjh.xiyiji.data.xuyujiemodel.F0Acceleration;
import com.sunjh.xiyiji.data.xuyujiemodel.MeanF0;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.Operation;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.locks.Condition;
import java.util.stream.Collectors;

/**
 * @author sunjh
 * @version 1.0
 * @date 2021/10/30 10:54 下午
 */
@Service
public class XuyujieServiceImpl implements XuyujieService {
    @Autowired
    private DurationDAO durationDAO;

    @Autowired
    private F0AccelerationDAO f0AccelerationDAO;

    @Autowired
    private MeanF0DAO meanF0DAO;

    @Autowired
    private ExcursionSizeDAO excursionSizeDAO;

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
    public List<Duration> findDurationByNameAndTypeAndUserName(String name, String type, String username) {
        return durationDAO.findByNameAndTypeAndUserName(name, type, username);
    }

    @Override
    public List<ExcursionSize> findExcursionSizeByNameAndTypeAndUserName(String name, String type, String username) {
        return excursionSizeDAO.findByNameAndTypeAndUserName(name, type, username);
    }

    @Override
    public List<MeanF0> findMeanF0ByNameAndTypeAndUserName(String name, String type, String username) {
        return meanF0DAO.findByNameAndTypeAndUserName(name, type, username);
    }

    @Override
    public List<F0Acceleration> findF0AccelerationByNameAndTypeAndUserName(String name, String type, String username) {
        return f0AccelerationDAO.findByNameAndTypeAndUserName(name, type, username);
    }

    @Override
    public Duration saveDuration(Duration duration) {
        duration.setGmtCreate(new Timestamp(System.currentTimeMillis()));
        duration.setGmtModified(new Timestamp(System.currentTimeMillis()));
        return durationDAO.save(duration);
    }

    @Override
    public F0Acceleration saveF0Acceleration(F0Acceleration f0Acceleration) {
        f0Acceleration.setGmtCreate(new Timestamp(System.currentTimeMillis()));
        f0Acceleration.setGmtModified(new Timestamp(System.currentTimeMillis()));
        return f0AccelerationDAO.save(f0Acceleration);
    }

    @Override
    public MeanF0 saveMeanF0(MeanF0 meanF0) {
        meanF0.setGmtCreate(new Timestamp(System.currentTimeMillis()));
        meanF0.setGmtModified(new Timestamp(System.currentTimeMillis()));
        return meanF0DAO.save(meanF0);
    }

    @Override
    public ExcursionSize saveExcursionSize(ExcursionSize excursionSize) {
        excursionSize.setGmtCreate(new Timestamp(System.currentTimeMillis()));
        excursionSize.setGmtModified(new Timestamp(System.currentTimeMillis()));
        return excursionSizeDAO.save(excursionSize);
    }

    @Override
    public List<Duration> getDurationDataListByCondition(XuyujieQueryCondition condition) {
        Pageable pageable = PageRequest.of(condition.getPageNum(), condition.getPageSize());
        if (StringUtils.isEmpty(condition.getUserName())) {
            return durationDAO.findPageable(pageable).getContent();
        }
        return durationDAO.findByUserNamePageable(condition.getUserName(), pageable).getContent();
    }

    @Override
    public List<MeanF0> getMeanF0DataListByCondition(XuyujieQueryCondition condition) {
        Pageable pageable = PageRequest.of(condition.getPageNum(), condition.getPageSize());
        if (StringUtils.isEmpty(condition.getUserName())) {
            return meanF0DAO.findPageable(pageable).getContent();
        }
        return meanF0DAO.findByUserNamePageable(condition.getUserName(), pageable).getContent();
    }

    @Override
    public List<F0Acceleration> getF0AccelerationDataListByCondition(XuyujieQueryCondition condition) {
        Pageable pageable = PageRequest.of(condition.getPageNum(), condition.getPageSize());
        if (StringUtils.isEmpty(condition.getUserName())) {
            return f0AccelerationDAO.findPageable(pageable).getContent();
        }
        return f0AccelerationDAO.findByUserNamePageable(condition.getUserName(), pageable).getContent();
    }

    @Override
    public List<ExcursionSize> getExcursionSizeDataListByCondition(XuyujieQueryCondition condition) {
        Pageable pageable = PageRequest.of(condition.getPageNum(), condition.getPageSize());
        if (StringUtils.isEmpty(condition.getUserName())) {
            return excursionSizeDAO.findPageable(pageable).getContent();
        }
        return excursionSizeDAO.findByUserNamePageable(condition.getUserName(), pageable).getContent();
    }

    @Override
    public int countByCondition(XuyujieQueryCondition condition) {
        if (!StringUtils.isEmpty(condition.getUserName())) {
            switch (condition.getDataFileType()) {
                case "duration":
                    return (int) durationDAO.countByUserName(condition.getUserName());
                case "meanf0":
                    return (int) meanF0DAO.countByUserName(condition.getUserName());
                case "f0acceleration":
                    return (int) f0AccelerationDAO.countByUserName(condition.getUserName());
                case "excursionsize":
                    return (int) excursionSizeDAO.countByUserName(condition.getUserName());
                default:
                    return 0;
            }
        } else {
            switch (condition.getDataFileType()) {
                case "duration":
                    return (int) durationDAO.countAllByUserNameNotNull();
                case "meanf0":
                    return (int) meanF0DAO.countAllByUserNameNotNull();
                case "f0acceleration":
                    return (int) f0AccelerationDAO.countAllByUserNameNotNull();
                case "excursionsize":
                    return (int) excursionSizeDAO.countAllByUserNameNotNull();
                default:
                    return 0;
            }
        }
    }
}
