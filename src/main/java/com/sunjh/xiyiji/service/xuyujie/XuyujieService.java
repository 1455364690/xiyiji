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

    int countByCondition(XuyujieQueryCondition condition);

    List<XuyujieUploadVO> getAllByUserNameAndType(String userName, String type);

    String createExcel(String filePath, String fileName, String type, List<String> tabList, List<XuyujieUploadVO> dataList);


}
