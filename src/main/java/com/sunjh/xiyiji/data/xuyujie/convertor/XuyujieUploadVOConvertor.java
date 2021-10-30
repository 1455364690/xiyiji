package com.sunjh.xiyiji.data.xuyujie.convertor;

import com.sunjh.xiyiji.dao.xuyujiedao.DurationDAO;
import com.sunjh.xiyiji.data.xuyujie.BaseVoiceEntity;
import com.sunjh.xiyiji.data.xuyujie.vo.XuyujieUploadVO;
import com.sunjh.xiyiji.data.xuyujiemodel.Duration;

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

    public static XuyujieUploadVO convertDuration2XuyujieUploadVO(Duration duration){
        if (null == duration){
            return null;
        }
        XuyujieUploadVO xuyujieUploadVO = new XuyujieUploadVO();
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
