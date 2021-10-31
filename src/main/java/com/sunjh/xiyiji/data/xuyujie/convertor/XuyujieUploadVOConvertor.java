package com.sunjh.xiyiji.data.xuyujie.convertor;

import com.sunjh.xiyiji.dao.xuyujiedao.DurationDAO;
import com.sunjh.xiyiji.data.xuyujie.BaseVoiceEntity;
import com.sunjh.xiyiji.data.xuyujie.vo.XuyujieUploadVO;
import com.sunjh.xiyiji.data.xuyujiemodel.Duration;
import com.sunjh.xiyiji.data.xuyujiemodel.ExcursionSize;
import com.sunjh.xiyiji.data.xuyujiemodel.F0Acceleration;
import com.sunjh.xiyiji.data.xuyujiemodel.MeanF0;

import java.util.List;

/**
 * @author sunjh
 * @version 1.0
 * @date 2021/10/30 8:39 下午
 */
public class XuyujieUploadVOConvertor {
    public static XuyujieUploadVO convertEntity2VO(BaseVoiceEntity durationEntity) {
        if (null == durationEntity) {
            return null;
        }
        XuyujieUploadVO xuyujieUploadVO = new XuyujieUploadVO();
        xuyujieUploadVO.setId(durationEntity.getId());
        xuyujieUploadVO.setFileName(durationEntity.getFileName());
        xuyujieUploadVO.setName(durationEntity.getName());
        xuyujieUploadVO.setType(durationEntity.getType());
        List<String> data = durationEntity.getData();
        xuyujieUploadVO.setDataOne(data.size() > 0 ? data.get(0) : null);
        xuyujieUploadVO.setDataTwo(data.size() > 1 ? data.get(1) : null);
        xuyujieUploadVO.setDataThree(data.size() > 2 ? data.get(2) : null);
        xuyujieUploadVO.setDataFour(data.size() > 3 ? data.get(3) : null);
        xuyujieUploadVO.setDataFive(data.size() > 4 ? data.get(4) : null);
        xuyujieUploadVO.setDataSix(data.size() > 5 ? data.get(5) : null);
        return xuyujieUploadVO;
    }

    public static Duration convertVO2Duration(XuyujieUploadVO xuyujieUploadVO) {
        if (null == xuyujieUploadVO) {
            return null;
        }
        Duration duration = new Duration();
        duration.setName(xuyujieUploadVO.getName());
        duration.setType(xuyujieUploadVO.getType());
        duration.setUserName("test");
        duration.setDataOne(xuyujieUploadVO.getDataOne());
        duration.setDataTwo(xuyujieUploadVO.getDataTwo());
        duration.setDataThree(xuyujieUploadVO.getDataThree());
        duration.setDataFour(xuyujieUploadVO.getDataFour());
        duration.setDataFive(xuyujieUploadVO.getDataFive());
        duration.setDataSix(xuyujieUploadVO.getDataSix());
        return duration;
    }

    public static MeanF0 convertVO2MeanF0(XuyujieUploadVO xuyujieUploadVO) {
        if (null == xuyujieUploadVO) {
            return null;
        }
        MeanF0 meanF0 = new MeanF0();
        meanF0.setName(xuyujieUploadVO.getName());
        meanF0.setType(xuyujieUploadVO.getType());
        meanF0.setUserName(xuyujieUploadVO.getUserName());
        meanF0.setDataOne(xuyujieUploadVO.getDataOne());
        meanF0.setDataTwo(xuyujieUploadVO.getDataTwo());
        meanF0.setDataThree(xuyujieUploadVO.getDataThree());
        meanF0.setDataFour(xuyujieUploadVO.getDataFour());
        meanF0.setDataFive(xuyujieUploadVO.getDataFive());
        meanF0.setDataSix(xuyujieUploadVO.getDataSix());
        return meanF0;
    }

    public static F0Acceleration convertVO2F0Acceleration(XuyujieUploadVO xuyujieUploadVO) {
        if (null == xuyujieUploadVO) {
            return null;
        }
        F0Acceleration f0Acceleration = new F0Acceleration();
        f0Acceleration.setName(xuyujieUploadVO.getName());
        f0Acceleration.setType(xuyujieUploadVO.getType());
        f0Acceleration.setUserName("test");
        f0Acceleration.setDataOne(xuyujieUploadVO.getDataOne());
        f0Acceleration.setDataTwo(xuyujieUploadVO.getDataTwo());
        f0Acceleration.setDataThree(xuyujieUploadVO.getDataThree());
        f0Acceleration.setDataFour(xuyujieUploadVO.getDataFour());
        f0Acceleration.setDataFive(xuyujieUploadVO.getDataFive());
        f0Acceleration.setDataSix(xuyujieUploadVO.getDataSix());
        return f0Acceleration;
    }

    public static ExcursionSize convertVO2ExcursionSize(XuyujieUploadVO xuyujieUploadVO) {
        if (null == xuyujieUploadVO) {
            return null;
        }
        ExcursionSize excursionSize = new ExcursionSize();
        excursionSize.setName(xuyujieUploadVO.getName());
        excursionSize.setType(xuyujieUploadVO.getType());
        excursionSize.setUserName("test");
        excursionSize.setDataOne(xuyujieUploadVO.getDataOne());
        excursionSize.setDataTwo(xuyujieUploadVO.getDataTwo());
        excursionSize.setDataThree(xuyujieUploadVO.getDataThree());
        excursionSize.setDataFour(xuyujieUploadVO.getDataFour());
        excursionSize.setDataFive(xuyujieUploadVO.getDataFive());
        excursionSize.setDataSix(xuyujieUploadVO.getDataSix());
        return excursionSize;
    }

    public static XuyujieUploadVO convertDuration2XuyujieUploadVO(Duration duration) {
        if (null == duration) {
            return null;
        }
        XuyujieUploadVO xuyujieUploadVO = new XuyujieUploadVO();
        xuyujieUploadVO.setId("" + duration.getId());
        xuyujieUploadVO.setFileName("");
        xuyujieUploadVO.setName(duration.getName());
        xuyujieUploadVO.setType(duration.getType());
        xuyujieUploadVO.setDataOne(duration.getDataOne());
        xuyujieUploadVO.setDataTwo(duration.getDataTwo());
        xuyujieUploadVO.setDataThree(duration.getDataThree());
        xuyujieUploadVO.setDataFour(duration.getDataFour());
        xuyujieUploadVO.setDataFive(duration.getDataFive());
        xuyujieUploadVO.setDataSix(duration.getDataSix());
        xuyujieUploadVO.setUserName(duration.getUserName());
        return xuyujieUploadVO;
    }

    public static XuyujieUploadVO convertMeanF02XuyujieUploadVO(MeanF0 duration) {
        if (null == duration) {
            return null;
        }
        XuyujieUploadVO xuyujieUploadVO = new XuyujieUploadVO();
        xuyujieUploadVO.setId("" + duration.getId());
        xuyujieUploadVO.setFileName("");
        xuyujieUploadVO.setName(duration.getName());
        xuyujieUploadVO.setType(duration.getType());
        xuyujieUploadVO.setDataOne(duration.getDataOne());
        xuyujieUploadVO.setDataTwo(duration.getDataTwo());
        xuyujieUploadVO.setDataThree(duration.getDataThree());
        xuyujieUploadVO.setDataFour(duration.getDataFour());
        xuyujieUploadVO.setDataFive(duration.getDataFive());
        xuyujieUploadVO.setDataSix(duration.getDataSix());
        xuyujieUploadVO.setUserName(duration.getUserName());
        return xuyujieUploadVO;
    }

    public static XuyujieUploadVO convertExcursionSize2XuyujieUploadVO(ExcursionSize duration) {
        if (null == duration) {
            return null;
        }
        XuyujieUploadVO xuyujieUploadVO = new XuyujieUploadVO();
        xuyujieUploadVO.setId("" + duration.getId());
        xuyujieUploadVO.setFileName("");
        xuyujieUploadVO.setName(duration.getName());
        xuyujieUploadVO.setType(duration.getType());
        xuyujieUploadVO.setDataOne(duration.getDataOne());
        xuyujieUploadVO.setDataTwo(duration.getDataTwo());
        xuyujieUploadVO.setDataThree(duration.getDataThree());
        xuyujieUploadVO.setDataFour(duration.getDataFour());
        xuyujieUploadVO.setDataFive(duration.getDataFive());
        xuyujieUploadVO.setDataSix(duration.getDataSix());
        xuyujieUploadVO.setUserName(duration.getUserName());
        return xuyujieUploadVO;
    }

    public static XuyujieUploadVO convertF0Acceleration2XuyujieUploadVO(F0Acceleration duration) {
        if (null == duration) {
            return null;
        }
        XuyujieUploadVO xuyujieUploadVO = new XuyujieUploadVO();
        xuyujieUploadVO.setId("" + duration.getId());
        xuyujieUploadVO.setFileName("");
        xuyujieUploadVO.setName(duration.getName());
        xuyujieUploadVO.setType(duration.getType());
        xuyujieUploadVO.setDataOne(duration.getDataOne());
        xuyujieUploadVO.setDataTwo(duration.getDataTwo());
        xuyujieUploadVO.setDataThree(duration.getDataThree());
        xuyujieUploadVO.setDataFour(duration.getDataFour());
        xuyujieUploadVO.setDataFive(duration.getDataFive());
        xuyujieUploadVO.setDataSix(duration.getDataSix());
        xuyujieUploadVO.setUserName(duration.getUserName());
        return xuyujieUploadVO;
    }
}
