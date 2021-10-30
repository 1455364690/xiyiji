package com.sunjh.xiyiji.controller.xuyujie;

import com.sunjh.xiyiji.data.result.BasePageResult;
import com.sunjh.xiyiji.data.result.BaseResult;
import com.sunjh.xiyiji.data.xuyujie.BaseVoiceEntity;
import com.sunjh.xiyiji.data.xuyujie.XuyujieQueryCondition;
import com.sunjh.xiyiji.data.xuyujie.convertor.XuyujieUploadVOConvertor;
import com.sunjh.xiyiji.data.xuyujie.vo.XuyujieUploadVO;
import com.sunjh.xiyiji.logic.xuyujie.XuyujieLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    public BaseResult<String> upload(@RequestParam("filename") MultipartFile file) {
        String newFileName = xuyujieLogic.saveFile(file);
        return new BaseResult<String>().success(newFileName);
    }

    @GetMapping("api/xuyujie/get_analyse_table_from_txt_file/{fileName}")
    public BaseResult<List<XuyujieUploadVO>> analyse(@PathVariable String fileName) {
        List<BaseVoiceEntity> baseVoiceEntityList = xuyujieLogic.analyseFile(fileName);
        List<XuyujieUploadVO> xuyujieUploadVOList = baseVoiceEntityList.stream().map(XuyujieUploadVOConvertor::convertEntity2VO).collect(Collectors.toList());
        return new BaseResult<List<XuyujieUploadVO>>().success(xuyujieUploadVOList);
    }

    @PostMapping("api/xuyujie/save_analyse_table_from_txt_file")
    public BaseResult<Boolean> save(@RequestBody List<XuyujieUploadVO> xuyuUploadVOList) {
        System.out.println(xuyuUploadVOList.size());
        if (xuyujieLogic.saveFileContent(xuyuUploadVOList)) {
            return new BaseResult<Boolean>().success(Boolean.TRUE);
        } else {
            return new BaseResult<Boolean>().fail("error", "error");
        }

    }

    @PostMapping("api/xuyujie/query_data_by_condition")
    public BasePageResult<XuyujieUploadVO> getDataListByCondition(@RequestBody XuyujieQueryCondition condition) {
        List<XuyujieUploadVO> xuyujieQueryList = xuyujieLogic.getDataListByCondition(condition);
        int total = xuyujieLogic.countByUserName(condition.getUserName());
        return new BasePageResult<XuyujieUploadVO>().success(xuyujieQueryList, condition.getPageNum(), condition.getPageSize(), total);
    }

}
