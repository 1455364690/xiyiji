package com.sunjh.xiyiji.service.xuyujie;

import com.alibaba.fastjson.JSON;
import com.sunjh.xiyiji.dao.xuyujiedao.*;
import com.sunjh.xiyiji.data.xuyujie.XuyujieQueryCondition;
import com.sunjh.xiyiji.data.xuyujie.convertor.XuyujieUploadVOConvertor;
import com.sunjh.xiyiji.data.xuyujie.vo.NormTimeVO;
import com.sunjh.xiyiji.data.xuyujie.vo.XuyujieUploadVO;
import com.sunjh.xiyiji.data.xuyujiemodel.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.Operation;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(type + "表");
        //设置表格列宽度为10个字节
        sheet.setDefaultColumnWidth(30);
        //创建标题的显示样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        //创建第一行表头
        HSSFRow headrow = sheet.createRow(0);
        for (int i = 0; i < tabList.size(); i++) {
            //创建一个单元格
            HSSFCell cell = headrow.createCell(i);

            //创建一个内容对象
            HSSFRichTextString text = new HSSFRichTextString(tabList.get(i));

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
                HSSFRow currentRow = sheet.createRow(lineNo);
                lineNo++;
                //赋值
                String[] split = dataList.get(i).getType().substring(1).split("-");
                currentRow.createCell(0).setCellValue(new HSSFRichTextString(dataList.get(i).getId()));
                //group
                currentRow.createCell(1).setCellValue(new HSSFRichTextString(split[1]));
                //nation
                currentRow.createCell(2).setCellValue(new HSSFRichTextString(split[0] + "." + split[1] + "." + split[2]));
                //tone
                currentRow.createCell(3).setCellValue(new HSSFRichTextString(split[2]));
                //intonation
                currentRow.createCell(4).setCellValue(new HSSFRichTextString(intonationSet.contains(Integer.parseInt(split[0])) ? "2" : "1"));
                //句子长度
                currentRow.createCell(5).setCellValue(new HSSFRichTextString("" + countLength(dataList.get(i))));
                //place
                currentRow.createCell(6).setCellValue(new HSSFRichTextString("" + (j + 1)));
                //type
                currentRow.createCell(7).setCellValue(new HSSFRichTextString(dataList.get(i).getType()));
                //name
                currentRow.createCell(8).setCellValue(new HSSFRichTextString(dataList.get(i).getName()));
                //username
                currentRow.createCell(9).setCellValue(new HSSFRichTextString(dataList.get(i).getUserName()));
                //data
                currentRow.createCell(10).setCellValue(new HSSFRichTextString(data));
                //filetype
                currentRow.createCell(11).setCellValue(new HSSFRichTextString(dataList.get(i).getName()));
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
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(type + "表");
        //设置表格列宽度为10个字节
        sheet.setDefaultColumnWidth(30);
        //创建标题的显示样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        //创建第一行表头
        HSSFRow headrow = sheet.createRow(0);
        for (int i = 0; i < tabList.size(); i++) {
            //创建一个单元格
            HSSFCell cell = headrow.createCell(i);

            //创建一个内容对象
            HSSFRichTextString text = new HSSFRichTextString(tabList.get(i));

            //将内容对象的文字内容写入到单元格中
            cell.setCellValue(text);
            cell.setCellStyle(headerStyle);
        }
        int lineNo = 1;
        for (NormTimeVO normTimeVO : dataList) {
            //创建一行
            HSSFRow currentRow = sheet.createRow(lineNo);
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
        calNormTimeAvg();
        return true;
    }

    @Override
    public List<NormTimeVO> calNormTimeAvg() {
        List<String> types = normTimeDAO.findAllType();
        List<NormTimeVO> normTimeVOList = new LinkedList<>();
        for (String type : types) {
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
                for (List<Double> doubles : dataMap) {
                    total += doubles.get(j);
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
