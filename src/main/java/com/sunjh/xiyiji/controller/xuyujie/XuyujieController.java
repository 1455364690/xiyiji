package com.sunjh.xiyiji.controller.xuyujie;

import com.sunjh.xiyiji.data.result.BasePageResult;
import com.sunjh.xiyiji.data.result.BaseResult;
import com.sunjh.xiyiji.data.xuyujie.BaseVoiceEntity;
import com.sunjh.xiyiji.data.xuyujie.DownloadSelectedDataRequest;
import com.sunjh.xiyiji.data.xuyujie.XuyujieQueryCondition;
import com.sunjh.xiyiji.data.xuyujie.convertor.XuyujieUploadVOConvertor;
import com.sunjh.xiyiji.data.xuyujie.vo.NormTimeVO;
import com.sunjh.xiyiji.data.xuyujie.vo.XuyujieUploadVO;
import com.sunjh.xiyiji.logic.xuyujie.XuyujieLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
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

    private static final String DOWNLOAD_URL_PREFIX = "http://110.42.167.234:6789/download";

    @PostMapping("api/xuyujie/upload_txt_file")
    public BaseResult<String> upload(@RequestParam("filename") MultipartFile file) {
        String newFileName = xuyujieLogic.saveFile(file);
        return new BaseResult<String>().success(newFileName);
    }

    @PostMapping("api/xuyujie/upload_txt_file/cal_avg")
    public BaseResult<Boolean> uploadAndCalAvg(@RequestParam("filename") MultipartFile file) {
        String newFileName = xuyujieLogic.saveFile(file);
        Boolean saveResult = xuyujieLogic.saveNormTimes(newFileName);
        return new BaseResult<Boolean>().success(saveResult);
    }

    @GetMapping("api/xuyujie/cal_avg")
    public BaseResult<List<NormTimeVO>> calAvg() {
        List<NormTimeVO> normTimeVOList = xuyujieLogic.calNormTimeAvg();
        return new BaseResult<List<NormTimeVO>>().success(normTimeVOList);
    }

    @GetMapping("api/xuyujie/cal_avg_step2")
    public BaseResult<List<NormTimeVO>> calAvgStep2() {
        List<NormTimeVO> normTimeVOList = xuyujieLogic.calNormTimeAvgStep2();
        return new BaseResult<List<NormTimeVO>>().success(normTimeVOList);
    }

    @PostMapping("api/xuyujie/cal_avg/download")
    public BaseResult<String> downloadAvgData(@RequestBody List<NormTimeVO> normTimeVOList) {
        String downloadUrl = xuyujieLogic.downloadAvgData(normTimeVOList);
        return new BaseResult<String>().success(DOWNLOAD_URL_PREFIX + downloadUrl);
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
        int total = xuyujieLogic.countByUserName(condition);
        return new BasePageResult<XuyujieUploadVO>().success(xuyujieQueryList, condition.getPageNum(), condition.getPageSize(), total);
    }

    @PostMapping("api/xuyuejie/download_all_data_by_condition")
    public BaseResult<String> downloadAllByCondition(@RequestBody XuyujieQueryCondition condition) {

        String downloadUrl = xuyujieLogic.downloadAllByCondition(condition);
        return new BaseResult<String>().success(DOWNLOAD_URL_PREFIX + downloadUrl);
    }

    @PostMapping("api/xuyuejie/download_selected_data")
    public BaseResult<String> downloadPartData(@RequestBody DownloadSelectedDataRequest request) {
        String downloadUrl = xuyujieLogic.downloadSelectedData(request.getDataList(), request.getDataType());
        return new BaseResult<String>().success(DOWNLOAD_URL_PREFIX + downloadUrl);
    }

    @PostMapping("api/xuyuejie/download_all_four_data_by_condition")
    public BaseResult<String> downloadAllFourDataByCondition(@RequestBody XuyujieQueryCondition condition) {
        String downloadUrl = xuyujieLogic.downloadAllFourDataByCondition(condition);
        return new BaseResult<String>().success(DOWNLOAD_URL_PREFIX + downloadUrl);
    }

    public static void main2(String[] args) throws Exception {
        File file = new File("/Users/sunjh/IdeaProjects/xiyiji/test.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line = null;
        List<List<String>> map = new LinkedList<>();
        while (null != (line = bufferedReader.readLine())) {
            map.add(Arrays.stream(line.split("\t")).collect(Collectors.toList()));
        }
        for (int i = 0; i < map.size(); i += 3) {
            List<String> list1 = map.get(i);
            List<String> list2 = map.get(i + 1);
            List<String> list3 = map.get(i + 2);
            System.out.print(list1.get(0) + "-" + list1.get(2) + "  ");
            for (int j = 3; j < list1.size(); j++) {
                System.out.print(String.format("%.3f", (Double.parseDouble(list1.get(j)) + Double.parseDouble(list2.get(j)) + Double.parseDouble(list3.get(j))) / 3) + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws Exception {
        File file = new File("/Users/sunjh/IdeaProjects/xiyiji/test2.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line = null;
        List<List<String>> map = new LinkedList<>();
        while (null != (line = bufferedReader.readLine())) {
            map.add(Arrays.stream(line.split("\t")).collect(Collectors.toList()));
        }
        Map<Integer, Boolean> hashmap = new HashMap<>();
        for (int i = 0; i < map.size(); i++) {
            hashmap.put(i, false);
        }
        for (int j = 0; j < map.size(); j+=3) {
            if (hashmap.get(j)) {
                continue;
            }
            int tmp = 0;
            for (tmp = 0; tmp < 3; tmp++) {
                if (j + tmp >= map.size()) {
                    break;
                }
                hashmap.put(j + tmp, true);
                List<String> list = map.get(j + tmp);
                System.out.print("[");
                for (int i = 3; i < list.size() - 1; i++) {
                    System.out.print("" + list.get(i) + ",");
                }
                System.out.println("" + list.get(list.size() - 1) + "],");
            }
            for (tmp = 12; tmp < 15; tmp++) {
                if (j + tmp >= map.size()) {
                    break;
                }
                hashmap.put(j + tmp, true);
                List<String> list = map.get(j + tmp);
                System.out.print("[");
                for (int i = 3; i < list.size() - 1; i++) {
                    System.out.print("" + list.get(i) + ",");
                }
                System.out.println("" + list.get(list.size() - 1) + "],");
            }
        }

        for (int i=1;i<61;i++){
            System.out.print(i+",");
        }
    }
}
