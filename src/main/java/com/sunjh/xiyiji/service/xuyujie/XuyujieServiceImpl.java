package com.sunjh.xiyiji.service.xuyujie;

import com.sunjh.xiyiji.dao.xuyujiedao.*;
import com.sunjh.xiyiji.data.xuyujie.XuyujieQueryCondition;
import com.sunjh.xiyiji.data.xuyujie.convertor.XuyujieUploadVOConvertor;
import com.sunjh.xiyiji.data.xuyujie.vo.XuyujieUploadVO;
import com.sunjh.xiyiji.data.xuyujiemodel.*;
import com.sunjh.xiyiji.util.XiyijiLoggerUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(XuyujieServiceImpl.class);
    @Autowired
    private DurationDAO durationDAO;

    @Autowired
    private F0AccelerationDAO f0AccelerationDAO;

    @Autowired
    private MeanF0DAO meanF0DAO;

    @Autowired
    private ExcursionSizeDAO excursionSizeDAO;

    @Autowired
    private VoiceDataDAO voiceDataDAO;

    @Override
    public boolean saveAllVoiceData(List<VoiceData> voiceDataList) {
        try {
            voiceDataDAO.saveAll(voiceDataList);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public VoiceData saveVoiceData(VoiceData voiceData) {
        return voiceDataDAO.save(voiceData);
    }

    @Override
    public int countVoiceDataListByFileTypeAndOwner(String fileType, String owner) {
        try {
            long count;
            if (!StringUtils.isEmpty(owner)) {
                count = voiceDataDAO.countAllByFileTypeAndOwner(fileType, owner);
            } else {
                count = voiceDataDAO.countAllByFileType(fileType);
            }
            return (int) count;
        } catch (Exception e) {
            XiyijiLoggerUtil.error(logger, e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int countVoiceDataListByFileTypeAndDefaultOwner(String fileType) {
        return 0;
    }

    @Override
    public List<VoiceData> findVoiceDataListByCondition(XuyujieQueryCondition condition) {
        Pageable pageable = PageRequest.of(condition.getPageNum(), condition.getPageSize());
        try {
            Page<VoiceData> pages = voiceDataDAO.findByFileTypeAndUserNamePageable(condition.getDataFileType(), condition.getUserName(), pageable);
            if (!pages.isEmpty()) {
                return pages.getContent();
            }
            return new LinkedList<>();
        } catch (Exception e) {
            return new LinkedList<>();
        }
    }

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
