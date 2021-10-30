package com.sunjh.xiyiji.logic.xuyujie;

import com.sunjh.xiyiji.data.xuyujie.BaseVoiceEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

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
        System.out.println(fileName);
        return null;
    }
}
