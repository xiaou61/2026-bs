package com.legalcase.controller;

import com.legalcase.common.Result;
import com.legalcase.service.AuthService;
import com.legalcase.mapper.AppointmentRecordMapper;
import com.legalcase.mapper.CaseStageMapper;
import com.legalcase.mapper.ConsultationRecordMapper;
import com.legalcase.mapper.DocumentTemplateMapper;
import com.legalcase.mapper.EvidenceMaterialMapper;
import com.legalcase.mapper.FeeRecordMapper;
import com.legalcase.mapper.LegalCaseMapper;
import com.legalcase.mapper.LegalDocumentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    @Autowired
    private LegalCaseMapper caseMapper;
    @Autowired
    private CaseStageMapper stageMapper;
    @Autowired
    private ConsultationRecordMapper consultationMapper;
    @Autowired
    private DocumentTemplateMapper templateMapper;
    @Autowired
    private LegalDocumentMapper documentMapper;
    @Autowired
    private AppointmentRecordMapper appointmentMapper;
    @Autowired
    private EvidenceMaterialMapper evidenceMapper;
    @Autowired
    private FeeRecordMapper feeMapper;

    @Autowired
    private AuthService authService;

    @GetMapping("/dashboard")
    public Result<Map<String, Object>> dashboard(@RequestAttribute String role) {
        authService.assertAdmin(role);
        Map<String, Object> data = new HashMap<>();
        data.put("caseCount", caseMapper.countAll());
        data.put("stageCount", stageMapper.countAll());
        data.put("consultationCount", consultationMapper.countAll());
        data.put("templateCount", templateMapper.countAll());
        data.put("documentCount", documentMapper.countAll());
        data.put("appointmentCount", appointmentMapper.countAll());
        data.put("evidenceCount", evidenceMapper.countAll());
        data.put("feeCount", feeMapper.countAll());
        data.put("todoCount", stageMapper.countAll() + appointmentMapper.countAll());
        return Result.success(data);
    }
}
