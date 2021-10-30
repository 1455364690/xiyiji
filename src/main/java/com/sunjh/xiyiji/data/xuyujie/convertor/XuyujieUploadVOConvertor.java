package com.sunjh.xiyiji.data.xuyujie.convertor;

import com.sunjh.xiyiji.data.xuyujie.BaseVoiceEntity;
import com.sunjh.xiyiji.data.xuyujie.vo.XuyujieUploadVO;

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
        xuyujieUploadVO.setDataOne(durationEntity.getData().get(0));
        xuyujieUploadVO.setDataTwo(durationEntity.getData().get(0));
        xuyujieUploadVO.setDataThree(durationEntity.getData().get(0));
        xuyujieUploadVO.setDataFour(durationEntity.getData().get(0));
        xuyujieUploadVO.setDataFive(durationEntity.getData().get(0));
        xuyujieUploadVO.setDataSix(durationEntity.getData().get(0));
        return xuyujieUploadVO;
    }
}
