package com.sunjh.xiyiji.logic.xuyujie;

import com.sunjh.xiyiji.data.xuyujie.BaseVoiceEntity;
import com.sunjh.xiyiji.data.xuyujie.convertor.XuyujieUploadVOConvertor;
import com.sunjh.xiyiji.data.xuyujie.enums.XuyujieFileTypeEnum;
import com.sunjh.xiyiji.data.xuyujie.vo.XuyujieUploadVO;
import com.sunjh.xiyiji.data.xuyujiemodel.Duration;
import com.sunjh.xiyiji.service.xuyujie.XuyujieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
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

    @Value("${xuyujie.filePath}")
    private String templatePath;

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
                baseVoiceEntity.setName(otherLine[0]);
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
        if (xuyujieUploadVOList.get(0).getFileName().contains(XuyujieFileTypeEnum.DURATION.getValue())) {
            List<Duration> durationList = xuyujieUploadVOList.stream().map(XuyujieUploadVOConvertor::convertVO2Duration).collect(Collectors.toList());
            if (durationList.size() == 0) {
                return false;
            }
            for (Duration duration : durationList) {
                List<Duration> list = xuyujieService.findByNameAndTypeAndUserName(duration.getName(), duration.getType(), duration.getUserName());
                if (null == list || list.size() == 0) {
                    xuyujieService.saveDuration(duration);
                }
            }
            return true;
        }
        return false;
    }
}
