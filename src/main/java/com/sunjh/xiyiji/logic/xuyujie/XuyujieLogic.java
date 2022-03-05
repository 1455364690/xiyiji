package com.sunjh.xiyiji.logic.xuyujie;

import com.sunjh.xiyiji.data.xuyujie.BaseVoiceEntity;
import com.sunjh.xiyiji.data.xuyujie.XuyujieQueryCondition;
import com.sunjh.xiyiji.data.xuyujie.vo.NormTimeVO;
import com.sunjh.xiyiji.data.xuyujie.vo.XuyujieUploadVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author sunjh
 * @version 1.0
 * @date 2021/10/30 8:33 下午
 */
public interface XuyujieLogic {
    String saveFile(MultipartFile file);

    List<BaseVoiceEntity> analyseFile(String fileName);

    Boolean saveNormTimes(String fileName);

    boolean saveFileContent(List<XuyujieUploadVO> xuyujieUploadVOList);

    List<XuyujieUploadVO> getDataListByCondition(XuyujieQueryCondition condition);

    int countByUserName(XuyujieQueryCondition condition);

    String downloadAllByCondition(XuyujieQueryCondition condition);

    String downloadAllFourDataByCondition(XuyujieQueryCondition condition);

    String downloadSelectedData(List<XuyujieUploadVO> xuyujieUploadVOList, String dataType);

    List<NormTimeVO> calNormTimeAvg();

    List<NormTimeVO> calNormTimeAvgStep2();

    String downloadAvgData(List<NormTimeVO> normTimeVOList);

    Boolean calDeltaM0AndDuration();
}
