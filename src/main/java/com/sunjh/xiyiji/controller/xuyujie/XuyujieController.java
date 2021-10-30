package com.sunjh.xiyiji.controller.xuyujie;

import com.sunjh.xiyiji.data.result.BaseResult;
import com.sunjh.xiyiji.data.xuyujie.BaseVoiceEntity;
import com.sunjh.xiyiji.data.xuyujie.convertor.XuyujieUploadVOConvertor;
import com.sunjh.xiyiji.data.xuyujie.vo.XuyujieUploadVO;
import com.sunjh.xiyiji.logic.xuyujie.XuyujieLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sunjh
 * @version 1.0
 * @date 2021/10/30 7:34 下午
 */
@Controller
@ResponseBody
public class XuyujieController {
    private static Logger logger = LoggerFactory.getLogger(XuyujieController.class);

    @Autowired
    private XuyujieLogic xuyujieLogic;

    @PostMapping("api/xuyujie/upload_txt_file")
    public BaseResult<List<XuyujieUploadVO>> upload(@RequestParam("filename") MultipartFile file) {
        System.out.println(file.getOriginalFilename());
        String newFileName = xuyujieLogic.saveFile(file);
        List<BaseVoiceEntity> baseVoiceEntityList = xuyujieLogic.analyseFile(newFileName);
        List<XuyujieUploadVO> xuyujieUploadVOList = null;
        //List<XuyujieUploadVO> xuyujieUploadVOList = baseVoiceEntityList.stream().map(XuyujieUploadVOConvertor::convertEntity2VO).collect(Collectors.toList());
        System.out.println(new BaseResult<List<XuyujieUploadVO>>().success(xuyujieUploadVOList));
        return new BaseResult<List<XuyujieUploadVO>>().success(xuyujieUploadVOList);
    }
}