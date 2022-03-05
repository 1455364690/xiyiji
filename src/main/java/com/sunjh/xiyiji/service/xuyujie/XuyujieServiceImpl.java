package com.sunjh.xiyiji.service.xuyujie;

import com.alibaba.fastjson.JSON;
import com.mysql.jdbc.log.LogUtils;
import com.sunjh.xiyiji.controller.xuyujie.XuyujieController;
import com.sunjh.xiyiji.dao.xuyujiedao.*;
import com.sunjh.xiyiji.data.xuyujie.XuyujieQueryCondition;
import com.sunjh.xiyiji.data.xuyujie.convertor.XuyujieUploadVOConvertor;
import com.sunjh.xiyiji.data.xuyujie.enums.XuyujieFileTypeEnum;
import com.sunjh.xiyiji.data.xuyujie.vo.NormTimeVO;
import com.sunjh.xiyiji.data.xuyujie.vo.XuyujieUploadVO;
import com.sunjh.xiyiji.data.xuyujiemodel.*;
import com.sunjh.xiyiji.util.XiyijiLoggerUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.Operation;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.stream.Collectors;

/**
 * @author sunjh
 * @version 1.0
 * @date 2021/10/30 10:54 下午
 */
@Service
public class XuyujieServiceImpl implements XuyujieService {

    private static Logger logger = LoggerFactory.getLogger(XuyujieServiceImpl.class);
    @Autowired
    private DurationDAO durationDAO;

    @Autowired
    private F0AccelerationDAO f0AccelerationDAO;

    @Autowired
    private MeanF0DAO meanF0DAO;

    @Autowired
    private ExcursionSizeDAO excursionSizeDAO;

    @Autowired
    private NormTimeDAO normTimeDAO;

    @Override
    public boolean saveAllDurations(List<Duration> durationList) {
        try {
            durationDAO.saveAll(durationList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Duration> findDurationByNameAndTypeAndUserName(String name, String type, String username) {
        return durationDAO.findByNameAndTypeAndUserName(name, type, username);
    }

    @Override
    public List<ExcursionSize> findExcursionSizeByNameAndTypeAndUserName(String name, String type, String username) {
        return excursionSizeDAO.findByNameAndTypeAndUserName(name, type, username);
    }

    @Override
    public List<MeanF0> findMeanF0ByNameAndTypeAndUserName(String name, String type, String username) {
        return meanF0DAO.findByNameAndTypeAndUserName(name, type, username);
    }

    @Override
    public List<F0Acceleration> findF0AccelerationByNameAndTypeAndUserName(String name, String type, String username) {
        return f0AccelerationDAO.findByNameAndTypeAndUserName(name, type, username);
    }

    @Override
    public Duration saveDuration(Duration duration) {
        duration.setGmtCreate(new Timestamp(System.currentTimeMillis()));
        duration.setGmtModified(new Timestamp(System.currentTimeMillis()));
        return durationDAO.save(duration);
    }

    @Override
    public F0Acceleration saveF0Acceleration(F0Acceleration f0Acceleration) {
        f0Acceleration.setGmtCreate(new Timestamp(System.currentTimeMillis()));
        f0Acceleration.setGmtModified(new Timestamp(System.currentTimeMillis()));
        return f0AccelerationDAO.save(f0Acceleration);
    }

    @Override
    public MeanF0 saveMeanF0(MeanF0 meanF0) {
        meanF0.setGmtCreate(new Timestamp(System.currentTimeMillis()));
        meanF0.setGmtModified(new Timestamp(System.currentTimeMillis()));
        return meanF0DAO.save(meanF0);
    }

    @Override
    public ExcursionSize saveExcursionSize(ExcursionSize excursionSize) {
        excursionSize.setGmtCreate(new Timestamp(System.currentTimeMillis()));
        excursionSize.setGmtModified(new Timestamp(System.currentTimeMillis()));
        return excursionSizeDAO.save(excursionSize);
    }

    @Override
    public List<Duration> getDurationDataListByCondition(XuyujieQueryCondition condition) {
        Pageable pageable = PageRequest.of(condition.getPageNum(), condition.getPageSize());
        if (StringUtils.isEmpty(condition.getUserName())) {
            return durationDAO.findPageable(pageable).getContent();
        }
        return durationDAO.findByUserNamePageable(condition.getUserName(), pageable).getContent();
    }

    @Override
    public List<Duration> getAllDurationByUserName(String userName) {
        if (StringUtils.isEmpty(userName)) {
            return (List<Duration>) durationDAO.findAll();
        }
        return durationDAO.findByUserName(userName);
    }

    @Override
    public List<MeanF0> getAllMeanF0ByUserName(String userName) {
        if (StringUtils.isEmpty(userName)) {
            return (List<MeanF0>) meanF0DAO.findAll();
        }
        return meanF0DAO.findByUserName(userName);
    }

    @Override
    public List<ExcursionSize> getAllExcursionSizeByUserName(String userName) {
        if (StringUtils.isEmpty(userName)) {
            return (List<ExcursionSize>) excursionSizeDAO.findAll();
        }
        return excursionSizeDAO.findByUserName(userName);
    }

    @Override
    public List<F0Acceleration> getAllF0AccelerationyUserName(String userName) {
        if (StringUtils.isEmpty(userName)) {
            try {
                return (List<F0Acceleration>) f0AccelerationDAO.findAll();
            } catch (Exception e) {
                return new LinkedList<>();
            }

        }
        return f0AccelerationDAO.findByUserName(userName);
    }

    @Override
    public List<MeanF0> getMeanF0DataListByCondition(XuyujieQueryCondition condition) {
        Pageable pageable = PageRequest.of(condition.getPageNum(), condition.getPageSize());
        if (StringUtils.isEmpty(condition.getUserName())) {
            return meanF0DAO.findPageable(pageable).getContent();
        }
        return meanF0DAO.findByUserNamePageable(condition.getUserName(), pageable).getContent();
    }

    @Override
    public List<F0Acceleration> getF0AccelerationDataListByCondition(XuyujieQueryCondition condition) {
        Pageable pageable = PageRequest.of(condition.getPageNum(), condition.getPageSize());
        if (StringUtils.isEmpty(condition.getUserName())) {
            return f0AccelerationDAO.findPageable(pageable).getContent();
        }
        return f0AccelerationDAO.findByUserNamePageable(condition.getUserName(), pageable).getContent();
    }

    @Override
    public List<ExcursionSize> getExcursionSizeDataListByCondition(XuyujieQueryCondition condition) {
        Pageable pageable = PageRequest.of(condition.getPageNum(), condition.getPageSize());
        if (StringUtils.isEmpty(condition.getUserName())) {
            return excursionSizeDAO.findPageable(pageable).getContent();
        }
        return excursionSizeDAO.findByUserNamePageable(condition.getUserName(), pageable).getContent();
    }

    @Override
    public int countByCondition(XuyujieQueryCondition condition) {
        if (!StringUtils.isEmpty(condition.getUserName())) {
            switch (condition.getDataFileType()) {
                case "duration":
                    return (int) durationDAO.countByUserName(condition.getUserName());
                case "meanf0":
                    return (int) meanF0DAO.countByUserName(condition.getUserName());
                case "finalvelocity":
                    return (int) f0AccelerationDAO.countByUserName(condition.getUserName());
                case "excursionsize":
                    return (int) excursionSizeDAO.countByUserName(condition.getUserName());
                default:
                    return 0;
            }
        } else {
            switch (condition.getDataFileType()) {
                case "duration":
                    return (int) durationDAO.countAllByUserNameNotNull();
                case "meanf0":
                    return (int) meanF0DAO.countAllByUserNameNotNull();
                case "finalvelocity":
                    return (int) f0AccelerationDAO.countAllByUserNameNotNull();
                case "excursionsize":
                    return (int) excursionSizeDAO.countAllByUserNameNotNull();
                default:
                    return 0;
            }
        }
    }

    @Override
    public List<XuyujieUploadVO> getAllByUserNameAndType(String userName, String type) {
        switch (type) {
            case "duration":
                return getAllDurationByUserName(userName).stream().map(XuyujieUploadVOConvertor::convertDuration2XuyujieUploadVO).collect(Collectors.toList());
            case "meanf0":
                return getAllMeanF0ByUserName(userName).stream().map(XuyujieUploadVOConvertor::convertMeanF02XuyujieUploadVO).collect(Collectors.toList());
            case "excursionsize":
                return getAllExcursionSizeByUserName(userName).stream().map(XuyujieUploadVOConvertor::convertExcursionSize2XuyujieUploadVO).collect(Collectors.toList());
            case "finalvelocity":
                return getAllF0AccelerationyUserName(userName).stream().map(XuyujieUploadVOConvertor::convertF0Acceleration2XuyujieUploadVO).collect(Collectors.toList());
            default:
                return null;
        }
    }

    @Override
    public String createExcel(String filePath, String fileName, String type, List<String> tabList, List<XuyujieUploadVO> dataList) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(type + "表");
        //设置表格列宽度为10个字节
        sheet.setDefaultColumnWidth(30);
        //创建标题的显示样式
        XSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        //创建第一行表头
        XSSFRow headrow = sheet.createRow(0);
        for (int i = 0; i < tabList.size(); i++) {
            //创建一个单元格
            XSSFCell cell = headrow.createCell(i);

            //创建一个内容对象
            XSSFRichTextString text = new XSSFRichTextString(tabList.get(i));

            //将内容对象的文字内容写入到单元格中
            cell.setCellValue(text);
            cell.setCellStyle(headerStyle);
        }

        Set<Integer> intonationSet = new HashSet<>();
        intonationSet.add(1);
        intonationSet.add(3);
        intonationSet.add(5);
        intonationSet.add(7);
        //"数据id", "group", "nation", "tone", "intonation", "句子长度", "数据类型",
        // "数据名称", "姓名", "数据1", "数据2", "数据3", "数据4", "数据5", "数据6"

        int lineNo = 1;
        for (int i = 0; i < dataList.size(); i++) {
            for (int j = 0; j < 6; j++) {
                String data = "";
                if (j == 0) {
                    if (StringUtils.isEmpty(dataList.get(i).getDataOne())) {
                        break;
                    } else {
                        data = dataList.get(i).getDataOne();
                    }
                } else if (j == 1) {
                    if (StringUtils.isEmpty(dataList.get(i).getDataTwo())) {
                        break;
                    } else {
                        data = dataList.get(i).getDataTwo();
                    }
                } else if (j == 2) {
                    if (StringUtils.isEmpty(dataList.get(i).getDataThree())) {
                        break;
                    } else {
                        data = dataList.get(i).getDataThree();
                    }
                } else if (j == 3) {
                    if (StringUtils.isEmpty(dataList.get(i).getDataFour())) {
                        break;
                    } else {
                        data = dataList.get(i).getDataFour();
                    }
                } else if (j == 4) {
                    if (StringUtils.isEmpty(dataList.get(i).getDataFive())) {
                        break;
                    } else {
                        data = dataList.get(i).getDataFive();
                    }
                } else if (j == 5) {
                    if (StringUtils.isEmpty(dataList.get(i).getDataSix())) {
                        break;
                    } else {
                        data = dataList.get(i).getDataSix();
                    }
                }
                //创建一行
                XSSFRow currentRow = sheet.createRow(lineNo);
                lineNo++;
                //赋值
                byte[] zero = new byte[1];
                zero[0] = (byte) 0;
                String s = new String(zero);
                String[] split = dataList.get(i).getType().replace(s, "").substring(1).split("-");
                currentRow.createCell(0).setCellValue(new XSSFRichTextString(dataList.get(i).getId()));
                //group
                currentRow.createCell(1).setCellValue(new XSSFRichTextString(split[1]));
                //nation
                currentRow.createCell(2).setCellValue(new XSSFRichTextString(split[0] + "." + split[1] + "." + split[2]));
                //tone
                currentRow.createCell(3).setCellValue(new XSSFRichTextString(split[2]));
                //intonation
                currentRow.createCell(4).setCellValue(new XSSFRichTextString(intonationSet.contains(Integer.parseInt(split[0])) ? "2" : "1"));
                //句子长度
                currentRow.createCell(5).setCellValue(new XSSFRichTextString("" + countLength(dataList.get(i))));
                //place
                currentRow.createCell(6).setCellValue(new XSSFRichTextString("" + (j + 1)));
                //type
                currentRow.createCell(7).setCellValue(new XSSFRichTextString(dataList.get(i).getType()));
                //name
                currentRow.createCell(8).setCellValue(new XSSFRichTextString(dataList.get(i).getName()));
                //username
                currentRow.createCell(9).setCellValue(new XSSFRichTextString(dataList.get(i).getUserName()));
                //data
                currentRow.createCell(10).setCellValue(new XSSFRichTextString(data));
                //filetype
                currentRow.createCell(11).setCellValue(new XSSFRichTextString(dataList.get(i).getName()));
            }

        }

        OutputStream outputStream = null;
        String downloadUrl = "/" + fileName + ".xls";
        try {
            File dest0 = new File(filePath + downloadUrl);
            if (!dest0.getParentFile().exists()) {
                dest0.getParentFile().mkdirs();
                //检测文件是否存在
            }
            File file = new File(filePath + downloadUrl);
            file.createNewFile();
            outputStream = new FileOutputStream(filePath + downloadUrl);
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return downloadUrl;
    }

    @Override
    public String createExcelAvg(String filePath, String fileName, String type, List<String> tabList, List<NormTimeVO> dataList) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(type + "表");
        //设置表格列宽度为10个字节
        sheet.setDefaultColumnWidth(30);
        //创建标题的显示样式
        XSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        //创建第一行表头
        XSSFRow headrow = sheet.createRow(0);
        for (int i = 0; i < tabList.size(); i++) {
            //创建一个单元格
            XSSFCell cell = headrow.createCell(i);

            //创建一个内容对象
            XSSFRichTextString text = new XSSFRichTextString(tabList.get(i));

            //将内容对象的文字内容写入到单元格中
            cell.setCellValue(text);
            cell.setCellStyle(headerStyle);
        }
        int lineNo = 1;
        for (NormTimeVO normTimeVO : dataList) {
            //创建一行
            XSSFRow currentRow = sheet.createRow(lineNo);
            lineNo++;
            currentRow.createCell(0).setCellValue(normTimeVO.getType());
            for (int j = 0; j < normTimeVO.getDataList().size(); j++) {
                currentRow.createCell(j + 1).setCellValue(normTimeVO.getDataList().get(j));
            }
        }
        OutputStream outputStream = null;
        String downloadUrl = "/" + fileName + ".xls";
        try {
            File dest0 = new File(filePath + downloadUrl);
            if (!dest0.getParentFile().exists()) {
                dest0.getParentFile().mkdirs();
                //检测文件是否存在
            }
            File file = new File(filePath + downloadUrl);
            file.createNewFile();
            outputStream = new FileOutputStream(filePath + downloadUrl);
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return downloadUrl;
    }

    @Override
    public Boolean saveNormTime(NormTime normTime) {
        try {
            normTimeDAO.save(normTime);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<NormTime> getAllNormTime() {
        return (List<NormTime>) normTimeDAO.findAll();
    }

    @Override
    public List<NormTime> getAllNormTimeByType(String type) {
        try {
            List<NormTime> normTimeList = normTimeDAO.findAllByType(type);
            if (null == normTimeList) {
                return new ArrayList<NormTime>();
            }
            return normTimeList;
        } catch (Exception e) {
            return new ArrayList<NormTime>();
        }
    }

    @Override
    public Boolean saveNormTimeList(List<NormTime> nowTime) {
        try {
            normTimeDAO.saveAll(nowTime);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<NormTimeVO> calNormTimeAvg() {
        List<String> types = normTimeDAO.findAllType();
        List<NormTimeVO> normTimeVOList = new LinkedList<>();
        for (String type : types) {
            XiyijiLoggerUtil.info(logger, type);
            NormTimeVO normTimeVO = new NormTimeVO();
            normTimeVO.setType(type);
            List<NormTime> normTimeList = normTimeDAO.findAllByType(type);
            List<List<Double>> dataMap = new LinkedList<>();
            List<String> avgList = new LinkedList<>();
            for (NormTime normTime : normTimeList) {
                List<Double> dataList = JSON.parseArray(normTime.getData(), Double.class);
                dataMap.add(dataList);
            }
            for (int j = 0; j < dataMap.get(0).size(); j++) {
                double total = 0;
                XiyijiLoggerUtil.info(logger, "当前数据点为:" + j);
                int index = 0;
                for (List<Double> doubles : dataMap) {
                    index++;
                    try {
                        total += doubles.get(j);
                    } catch (Exception e) {
                        XiyijiLoggerUtil.info(logger, "当前index为:" + index + ",当前数据点为:" + j + ",type:" + type + "," + e.getMessage());
                    }

                }
                avgList.add(String.format("%.3f", total / dataMap.size()));
            }
            normTimeVO.setDataList(avgList);
            normTimeVOList.add(normTimeVO);
        }
        return normTimeVOList;
    }

    @Override
    public List<NormTimeVO> calNormTimeAvgStep2() {
        List<String> types = normTimeDAO.findAllType();
        return null;
    }

    @Override
    public List<String> getUserIdListByNormF0() {
        return null;
    }

    @Override
    public List<String> getNormTypeList() {
        return null;
    }

    final String DELTA_F0_FLAG = "DELTA_F0_";
    final int TEN = 10;
    final int TWENTY = 10;
    final int THIRTY = 10;
    final int FORTY = 10;
    final int FIFTY = 10;
    final int SIXTY = 10;

    @Override
    public Boolean calDeltaM0AndDuration() {

        // getUserIdListByNormF0获取用户id列表，1-26，31-65，61-70，76-85
        List<String> userNameList = this.getUserIdListByNormF0();
        if (userNameList.isEmpty()) {
            XiyijiLoggerUtil.error(logger, new Exception("获取userId失败"), "获取userId失败");
            return false;
        }
        //获取type,只需要第三声和第二声，所以只需要保留结尾是2和4的
        List<String> normTypeList = normTimeDAO.findAllType().stream().filter(s -> s.endsWith("2") || s.endsWith("4")).collect(Collectors.toList());

        //循环根据uid计算
        // 通过excursionsize保存meanf0数据，通过f0acc报错duration数据
        List<ExcursionSize> meanF0ResultList = new LinkedList<>();
        List<F0Acceleration> durationResultList = new LinkedList<>();
        for (String userName : userNameList) {
            for (String normType : normTypeList) {
                //获取normTime
                NormTime normTime = normTimeDAO.findByTypeAndUniqueName(normType, userName);
                if (null == normTime) {
                    XiyijiLoggerUtil.error(logger, new Exception("获取normF0失败"), "获取normF0失败,type:" + normType + ",uid:" + userName);
                    return false;
                }
                //获取duration
                List<Duration> durationList = durationDAO.findByNameAndTypeAndUserName(XuyujieFileTypeEnum.DURATION.value, normType, userName);
                if (null == durationList || durationList.isEmpty()) {
                    XiyijiLoggerUtil.error(logger, new Exception("获取duration失败"), "获取normF0失败,type:" + normType + ",uid:" + userName);
                    return false;
                }
                Map<String, Object> m0ListAndDurationList = calM0ListAndDurationList(normTime, durationList.get(0));
                if (null == m0ListAndDurationList || m0ListAndDurationList.size() != 2) {
                    XiyijiLoggerUtil.error(logger, new Exception("计算失败"), "计算失败,type:" + normType + ",uid:" + userName);
                    return false;
                }
                durationResultList.add((F0Acceleration) (m0ListAndDurationList.get(XuyujieFileTypeEnum.DURATION.value)));
                meanF0ResultList.add((ExcursionSize) (m0ListAndDurationList.get(XuyujieFileTypeEnum.MEAN_F0.value)));
            }

        }
        f0AccelerationDAO.saveAll(durationResultList);
        excursionSizeDAO.saveAll(meanF0ResultList);
        return true;
    }

    private Map<String, Object> calM0ListAndDurationList(NormTime normTime, Duration duration) {
        Map<String, Object> result = new LinkedHashMap<>();
        List<Double> normDataList = JSON.parseArray(normTime.getData(), Double.class);
        List<Double> newMeanF0DataList = new LinkedList<>();
        List<Double> newDurationDataList = new LinkedList<>();
        for (int normDataIndex = 0; normDataIndex < normDataList.size(); normDataIndex += 10) {
            Double min = normDataList.get(normDataIndex);
            int index = 0;
            for (int i = 1; i < 10; i++) {
                if (normDataIndex + i >= normDataList.size()) {
                    break;
                }
                if (normDataList.get(normDataIndex + i) > min) {
                    min = normDataList.get(normDataIndex + i);
                    index = normDataIndex + i;
                    break;
                } else {
                    min = normDataList.get(normDataIndex + i);
                }
            }
            Double durationData = getDurationDataByIndex(duration, index);
            if (null == durationData) {
                XiyijiLoggerUtil.error(logger, new Exception("计算失败"), "计算失败,normTime:" + normTime + ",duration:" + duration);
                return null;
            }
            newDurationDataList.add(durationData * index / 9d);
            newMeanF0DataList.add(min - normDataList.get(normDataIndex));
        }
        result.put(XuyujieFileTypeEnum.DURATION.value, cloneF0AccelerationByDurationData(duration, newDurationDataList));
        result.put(XuyujieFileTypeEnum.MEAN_F0.value, cloneF0AccelerationByDurationData(duration, newMeanF0DataList));
        return result;
    }

    /**
     * @param duration duration
     * @param index    0/1/2/3/4/5
     * @return double
     */
    private Double getDurationDataByIndex(Duration duration, int index) {
        if (index < 0 || index >= 6) {
            XiyijiLoggerUtil.info(logger, "计算失败,duration:" + duration + ",index:" + index);
            return null;
        }
        try {
            switch (index) {
                case 0:
                    return Double.parseDouble(duration.getDataOne());
                case 1:
                    return Double.parseDouble(duration.getDataTwo());
                case 2:
                    return Double.parseDouble(duration.getDataThree());
                case 3:
                    return Double.parseDouble(duration.getDataFour());
                case 4:
                    return Double.parseDouble(duration.getDataFive());
                case 5:
                    return Double.parseDouble(duration.getDataSix());
                default:
                    return null;
            }
        } catch (Exception e) {
            XiyijiLoggerUtil.error(logger, new Exception("计算失败"), "计算失败,duration:" + duration + ",index:" + index);
            return null;
        }
    }

    /**
     * 通过duration和data构造f0对象，存放的是duration数据
     *
     * @param duration
     * @param durationDataList
     * @return
     */
    private F0Acceleration cloneF0AccelerationByDurationData(Duration duration, List<Double> durationDataList) {
        F0Acceleration f0Acceleration = new F0Acceleration();
        f0Acceleration.setUserName(duration.getUserName());
        f0Acceleration.setName(XuyujieFileTypeEnum.DURATION.value);

        f0Acceleration.setDataOne(durationDataList.size() > 0 ? "" + durationDataList.get(0) : null);
        f0Acceleration.setDataTwo(durationDataList.size() > 1 ? "" + durationDataList.get(1) : null);
        f0Acceleration.setDataThree(durationDataList.size() > 2 ? "" + durationDataList.get(2) : null);
        f0Acceleration.setDataFour(durationDataList.size() > 3 ? "" + durationDataList.get(3) : null);
        f0Acceleration.setDataFive(durationDataList.size() > 4 ? "" + durationDataList.get(4) : null);
        f0Acceleration.setDataSix(durationDataList.size() > 5 ? "" + durationDataList.get(5) : null);
        return f0Acceleration;
    }

    /**
     * 通过duration和data构造excursionSize，存放的是meanf0数据
     *
     * @param duration
     * @param meanF0DataList
     * @return
     */
    private ExcursionSize cloneExcursionSizeByDurationAndData(Duration duration, List<Double> meanF0DataList) {
        ExcursionSize excursionSize = new ExcursionSize();
        excursionSize.setUserName(duration.getUserName());
        excursionSize.setName(XuyujieFileTypeEnum.MEAN_F0.value);
        excursionSize.setType(duration.getType());

        excursionSize.setDataOne(meanF0DataList.size() > 0 ? "" + meanF0DataList.get(0) : null);
        excursionSize.setDataTwo(meanF0DataList.size() > 1 ? "" + meanF0DataList.get(1) : null);
        excursionSize.setDataThree(meanF0DataList.size() > 2 ? "" + meanF0DataList.get(2) : null);
        excursionSize.setDataFour(meanF0DataList.size() > 3 ? "" + meanF0DataList.get(3) : null);
        excursionSize.setDataFive(meanF0DataList.size() > 4 ? "" + meanF0DataList.get(4) : null);
        excursionSize.setDataSix(meanF0DataList.size() > 5 ? "" + meanF0DataList.get(5) : null);
        return excursionSize;
    }

    public int countLength(XuyujieUploadVO vo) {
        if (!StringUtils.isEmpty(vo.getDataSix())) {
            return 6;
        }
        if (!StringUtils.isEmpty(vo.getDataFive())) {
            return 5;
        }
        if (!StringUtils.isEmpty(vo.getDataFour())) {
            return 4;
        }
        if (!StringUtils.isEmpty(vo.getDataThree())) {
            return 3;
        }
        if (!StringUtils.isEmpty(vo.getDataTwo())) {
            return 2;
        }
        if (!StringUtils.isEmpty(vo.getDataOne())) {
            return 1;
        }
        return 0;
    }

    public static void main(String[] args) {
        Set<Integer> intonationSet = new HashSet<>();
        intonationSet.add(1);
        intonationSet.add(3);
        intonationSet.add(5);
        intonationSet.add(7);
        System.out.println(intonationSet.contains(Integer.parseInt("2")));
    }
}
