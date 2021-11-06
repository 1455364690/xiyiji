package com.sunjh.xiyiji.logic.xuyujie;

import com.sunjh.xiyiji.data.xuyujie.BaseVoiceEntity;
import com.sunjh.xiyiji.data.xuyujie.XuyujieQueryCondition;
import com.sunjh.xiyiji.data.xuyujie.convertor.XuyujieUploadVOConvertor;
import com.sunjh.xiyiji.data.xuyujie.enums.XuyujieFileTypeEnum;
import com.sunjh.xiyiji.data.xuyujie.vo.XuyujieUploadVO;
import com.sunjh.xiyiji.data.xuyujiemodel.*;
import com.sunjh.xiyiji.service.xuyujie.XuyujieService;
import com.sunjh.xiyiji.util.XiyijiLoggerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sunjh
 * @version 1.0
 * @date 2021/10/30 8:33 下午
 */
@Service
public class XuyujieLogicImpl implements XuyujieLogic {
    Logger logger = LoggerFactory.getLogger(XuyujieLogicImpl.class);

    @Value("${xuyujie.filePath}")
    private String templatePath;

    @Value("${xuyujie.downloadPath}")
    private String downloadPath;

    private String fileSpliter = ".";

    @Autowired
    private XuyujieService xuyujieService;

    @Override
    public String saveFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        //获取文件名
        String prefixName = originalFilename.substring(0, originalFilename.lastIndexOf("."));

        String newFileName = prefixName + fileSpliter + System.currentTimeMillis() + fileSpliter + suffixName;

        File dest0 = new File(templatePath);
        File dest = new File(dest0, newFileName);
        //文件上传-覆盖
        try {
            // 检测是否存在目录
            if (!dest0.getParentFile().exists()) {
                dest0.getParentFile().mkdirs();
                //检测文件是否存在
            }
            if (!dest.exists()) {
                dest.mkdirs();
            }
            file.transferTo(dest);
            return newFileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<BaseVoiceEntity> analyseFile(String fileName) {
        try {
            File file = new File(templatePath, fileName);
            if (!file.exists()) {
                System.out.println("null");
                return null;
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            List<BaseVoiceEntity> result = new LinkedList<>();
            //第一行数据弃置
            String line = bufferedReader.readLine();
            //之后的行
            while (null != (line = bufferedReader.readLine())) {
                String[] otherLine = line.split("\t");
                BaseVoiceEntity baseVoiceEntity = new BaseVoiceEntity();
                baseVoiceEntity.setName(fileName.split("\\.")[0]);
                baseVoiceEntity.setFileName(fileName);
                baseVoiceEntity.setType(otherLine[0]);
                List<String> dataList = new LinkedList<>(Arrays.asList(otherLine).subList(1, otherLine.length));
                baseVoiceEntity.setData(dataList);
                result.add(baseVoiceEntity);
            }
            bufferedReader.close();
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean saveFileContent(List<XuyujieUploadVO> xuyujieUploadVOList) {
        String fileType = "";
        switch (xuyujieUploadVOList.get(0).getName()) {
            case XuyujieConstants.DURATION:
                fileType = XuyujieConstants.DURATION;
                break;
            case XuyujieConstants.EXCURSION_SIZE:
                fileType = XuyujieConstants.EXCURSION_SIZE;
                break;
            case XuyujieConstants.MEAN_F0:
                fileType = XuyujieConstants.MEAN_F0;
                break;
            case XuyujieConstants.FINAL_VELOCITY:
                fileType = XuyujieConstants.FINAL_VELOCITY;
                break;
            default:
                break;
        }
        List<VoiceData> voiceDataList = xuyujieUploadVOList.stream().map(XuyujieUploadVOConvertor::convertVoiceDataVO2DO).collect(Collectors.toList());
        for (VoiceData voiceData : voiceDataList) {
            voiceData.setFileType(fileType);
        }
        try {
            xuyujieService.saveAllVoiceData(voiceDataList);
            return true;
        } catch (Exception e) {
            XiyijiLoggerUtil.error(logger, e.getMessage());
            return false;
        }
    }

    @Override
    public List<XuyujieUploadVO> getDataListByCondition(XuyujieQueryCondition condition) {
        List<VoiceData> voiceDataList = xuyujieService.findVoiceDataListByCondition(condition);
        if (null == voiceDataList || voiceDataList.isEmpty()) {
            return new LinkedList<>();
        }
        return voiceDataList.stream().map(XuyujieUploadVOConvertor::convertVoiceDataDO2VO).collect(Collectors.toList());
    }

    @Override
    public int countByUserName(XuyujieQueryCondition condition) {
        return xuyujieService.countByCondition(condition);
    }

    @Override
    public String downloadAllByCondition(XuyujieQueryCondition condition) {
        List<XuyujieUploadVO> voList = xuyujieService.getAllByUserNameAndType(condition.getUserName(), condition.getDataFileType());
        String fileName = condition.getDataFileType() + "-" + System.currentTimeMillis();
        System.out.println(voList.size());
        List<String> tabList = Arrays.asList("数据id", "group", "nation", "tone", "intonation", "句子长度", "place", "数据类型", "数据名称", "姓名", "数据", "文件类型");
        return xuyujieService.createExcel(downloadPath, fileName, condition.getDataFileType(), tabList, voList);
    }

    @Override
    public String downloadAllFourDataByCondition(XuyujieQueryCondition condition) {
        List<XuyujieUploadVO> voList = new LinkedList<>();
        List<XuyujieUploadVO> tmpList = xuyujieService.getAllByUserNameAndType(condition.getUserName(), XuyujieFileTypeEnum.DURATION.value);
        if (null != tmpList) {
            voList.addAll(tmpList);
        }
        tmpList = xuyujieService.getAllByUserNameAndType(condition.getUserName(), XuyujieFileTypeEnum.MEAN_F0.value);
        if (null != tmpList) {
            voList.addAll(tmpList);
        }
        tmpList = xuyujieService.getAllByUserNameAndType(condition.getUserName(), XuyujieFileTypeEnum.F0_ACCELERATION.value);
        if (null != tmpList) {
            voList.addAll(tmpList);
        }
        tmpList = xuyujieService.getAllByUserNameAndType(condition.getUserName(), XuyujieFileTypeEnum.EXCURSION_SIZE.value);
        if (null != tmpList) {
            voList.addAll(tmpList);
        }
        String fileName = "alldata-" + System.currentTimeMillis();
        List<String> tabList = Arrays.asList("数据id", "group", "nation", "tone", "intonation", "句子长度", "place", "数据类型", "数据名称", "姓名", "数据", "文件类型");
        return xuyujieService.createExcel(downloadPath, fileName, condition.getDataFileType(), tabList, voList);
    }

    @Override
    public String downloadSelectedData(List<XuyujieUploadVO> xuyujieUploadVOList, String dataType) {
        List<String> tabList = Arrays.asList("数据id", "group", "nation", "tone", "intonation", "句子长度", "place", "数据类型", "数据名称", "姓名", "数据", "文件类型");
        String fileName = dataType + "-" + System.currentTimeMillis();
        return xuyujieService.createExcel(downloadPath, fileName, dataType, tabList, xuyujieUploadVOList);
    }
}
