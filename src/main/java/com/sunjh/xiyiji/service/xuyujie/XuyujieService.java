package com.sunjh.xiyiji.service.xuyujie;

import com.sunjh.xiyiji.data.xuyujie.XuyujieQueryCondition;
import com.sunjh.xiyiji.data.xuyujie.vo.XuyujieUploadVO;
import com.sunjh.xiyiji.data.xuyujiemodel.*;

import java.awt.print.Pageable;
import java.util.List;

/**
 * @author sunjh
 * @version 1.0
 * @date 2021/10/30 10:54 下午
 */
public interface XuyujieService {

    boolean saveAllVoiceData(List<VoiceData> voiceDataList);

    VoiceData saveVoiceData(VoiceData voiceData);

//    List<VoiceData> findVoiceDataListByFileTypeAndOwner(String fileType, String owner);
//
//    List<VoiceData> findVoiceDataListByFileTypeAndUserName(String fileType, String userName);
//
//    List<VoiceData> findVoiceDataListByFileTypeAndDefaultOwner(String fileType);

    int countVoiceDataListByFileTypeAndOwner(String fileType, String owner);

    int countVoiceDataListByFileTypeAndDefaultOwner(String fileType);

    List<VoiceData> findVoiceDataListByCondition(XuyujieQueryCondition condition);

    boolean saveAllDurations(List<Duration> durationList);

    List<Duration> findDurationByNameAndTypeAndUserName(String name, String type, String username);

    List<ExcursionSize> findExcursionSizeByNameAndTypeAndUserName(String name, String type, String username);

    List<MeanF0> findMeanF0ByNameAndTypeAndUserName(String name, String type, String username);

    List<F0Acceleration> findF0AccelerationByNameAndTypeAndUserName(String name, String type, String username);

    Duration saveDuration(Duration duration);

    F0Acceleration saveF0Acceleration(F0Acceleration f0Acceleration);

    MeanF0 saveMeanF0(MeanF0 meanF0);

    ExcursionSize saveExcursionSize(ExcursionSize excursionSize);

    List<Duration> getDurationDataListByCondition(XuyujieQueryCondition condition);

    List<Duration> getAllDurationByUserName(String userName);

    List<MeanF0> getAllMeanF0ByUserName(String userName);

    List<ExcursionSize> getAllExcursionSizeByUserName(String userName);

    List<F0Acceleration> getAllF0AccelerationyUserName(String userName);

    List<MeanF0> getMeanF0DataListByCondition(XuyujieQueryCondition condition);

    List<F0Acceleration> getF0AccelerationDataListByCondition(XuyujieQueryCondition condition);

    List<ExcursionSize> getExcursionSizeDataListByCondition(XuyujieQueryCondition condition);

    int countByCondition(XuyujieQueryCondition condition);

    List<XuyujieUploadVO> getAllByUserNameAndType(String userName, String type);

    String createExcel(String filePath, String fileName, String type, List<String> tabList, List<XuyujieUploadVO> dataList);


}
