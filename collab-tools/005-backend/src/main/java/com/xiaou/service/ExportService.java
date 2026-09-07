package com.xiaou.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.entity.Answer;
import com.xiaou.entity.AnswerDetail;
import com.xiaou.entity.Question;
import com.xiaou.entity.Survey;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.*;

@Service
public class ExportService {

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AnswerDetailService answerDetailService;

    public byte[] exportSurveyData(Long surveyId) throws Exception {
        Survey survey = surveyService.getById(surveyId);
        List<Question> questions = questionService.getQuestionsBySurveyId(surveyId);
        
        LambdaQueryWrapper<Answer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Answer::getSurveyId, surveyId);
        List<Answer> answers = answerService.list(wrapper);

        Workbook workbook = new XSSFWorkbook();
        
        createSummarySheet(workbook, survey, questions, answers);
        createDetailSheet(workbook, survey, questions, answers);
        createStatisticsSheet(workbook, questions, answers);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        
        return outputStream.toByteArray();
    }

    private void createSummarySheet(Workbook workbook, Survey survey, List<Question> questions, List<Answer> answers) {
        Sheet sheet = workbook.createSheet("问卷概览");
        
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerStyle.setFont(headerFont);

        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);
        Cell cell = row.createCell(0);
        cell.setCellValue("问卷标题");
        cell.setCellStyle(headerStyle);
        row.createCell(1).setCellValue(survey.getTitle());

        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("问卷描述");
        row.createCell(1).setCellValue(survey.getDescription() != null ? survey.getDescription() : "");

        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("总答卷数");
        row.createCell(1).setCellValue(answers.size());

        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("问题数量");
        row.createCell(1).setCellValue(questions.size());

        sheet.setColumnWidth(0, 20 * 256);
        sheet.setColumnWidth(1, 50 * 256);
    }

    private void createDetailSheet(Workbook workbook, Survey survey, List<Question> questions, List<Answer> answers) {
        Sheet sheet = workbook.createSheet("答卷详情");
        
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Row headerRow = sheet.createRow(0);
        Cell cell = headerRow.createCell(0);
        cell.setCellValue("序号");
        cell.setCellStyle(headerStyle);
        
        cell = headerRow.createCell(1);
        cell.setCellValue("提交时间");
        cell.setCellStyle(headerStyle);
        
        cell = headerRow.createCell(2);
        cell.setCellValue("IP地址");
        cell.setCellStyle(headerStyle);

        for (int i = 0; i < questions.size(); i++) {
            cell = headerRow.createCell(i + 3);
            cell.setCellValue(questions.get(i).getTitle());
            cell.setCellStyle(headerStyle);
        }

        int rowNum = 1;
        for (Answer answer : answers) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(rowNum - 1);
            row.createCell(1).setCellValue(answer.getSubmitTime() != null ? answer.getSubmitTime().toString() : "");
            row.createCell(2).setCellValue(answer.getUserIp() != null ? answer.getUserIp() : "");

            for (int i = 0; i < questions.size(); i++) {
                Question question = questions.get(i);
                LambdaQueryWrapper<AnswerDetail> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(AnswerDetail::getAnswerId, answer.getId());
                wrapper.eq(AnswerDetail::getQuestionId, question.getId());
                AnswerDetail detail = answerDetailService.getOne(wrapper);
                
                row.createCell(i + 3).setCellValue(detail != null && detail.getValue() != null ? detail.getValue() : "");
            }
        }

        for (int i = 0; i < questions.size() + 3; i++) {
            sheet.setColumnWidth(i, 20 * 256);
        }
    }

    private void createStatisticsSheet(Workbook workbook, List<Question> questions, List<Answer> answers) {
        Sheet sheet = workbook.createSheet("统计分析");
        
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        int rowNum = 0;
        
        for (Question question : questions) {
            Row titleRow = sheet.createRow(rowNum++);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue(question.getTitle());
            titleCell.setCellStyle(headerStyle);

            if (question.getType().equals("radio") || question.getType().equals("checkbox") || question.getType().equals("select")) {
                Map<String, Integer> optionCount = new HashMap<>();
                
                for (Answer answer : answers) {
                    LambdaQueryWrapper<AnswerDetail> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(AnswerDetail::getAnswerId, answer.getId());
                    wrapper.eq(AnswerDetail::getQuestionId, question.getId());
                    AnswerDetail detail = answerDetailService.getOne(wrapper);
                    
                    if (detail != null && detail.getValue() != null) {
                        if (question.getType().equals("checkbox")) {
                            String[] options = detail.getValue().split(",");
                            for (String option : options) {
                                optionCount.put(option.trim(), optionCount.getOrDefault(option.trim(), 0) + 1);
                            }
                        } else {
                            optionCount.put(detail.getValue(), optionCount.getOrDefault(detail.getValue(), 0) + 1);
                        }
                    }
                }

                Row headerRow = sheet.createRow(rowNum++);
                headerRow.createCell(0).setCellValue("选项");
                headerRow.createCell(1).setCellValue("选择人数");
                headerRow.createCell(2).setCellValue("占比");

                for (Map.Entry<String, Integer> entry : optionCount.entrySet()) {
                    Row dataRow = sheet.createRow(rowNum++);
                    dataRow.createCell(0).setCellValue(entry.getKey());
                    dataRow.createCell(1).setCellValue(entry.getValue());
                    double percentage = answers.size() > 0 ? (entry.getValue() * 100.0 / answers.size()) : 0;
                    dataRow.createCell(2).setCellValue(String.format("%.2f%%", percentage));
                }
            } else {
                Row headerRow = sheet.createRow(rowNum++);
                headerRow.createCell(0).setCellValue("回答内容");

                for (Answer answer : answers) {
                    LambdaQueryWrapper<AnswerDetail> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(AnswerDetail::getAnswerId, answer.getId());
                    wrapper.eq(AnswerDetail::getQuestionId, question.getId());
                    AnswerDetail detail = answerDetailService.getOne(wrapper);
                    
                    if (detail != null && detail.getValue() != null) {
                        Row dataRow = sheet.createRow(rowNum++);
                        dataRow.createCell(0).setCellValue(detail.getValue());
                    }
                }
            }

            rowNum++;
        }

        sheet.setColumnWidth(0, 40 * 256);
        sheet.setColumnWidth(1, 15 * 256);
        sheet.setColumnWidth(2, 15 * 256);
    }
}

