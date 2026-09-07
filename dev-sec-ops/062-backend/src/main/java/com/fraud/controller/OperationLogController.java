package com.fraud.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fraud.common.BusinessException;
import com.fraud.common.Result;
import com.fraud.service.OperationLogService;
import com.fraud.vo.OperationLogVO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/log")
public class OperationLogController {

    @Resource
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String module,
                          @RequestParam(required = false) String action,
                          @RequestParam(required = false) Long operatorId,
                          @RequestParam(required = false) String keyword,
                          HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        Page<OperationLogVO> page = operationLogService.page(pageNum, pageSize, module, action, operatorId, keyword);
        return Result.success(page);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> export(@RequestParam(required = false) String module,
                                         @RequestParam(required = false) String action,
                                         @RequestParam(required = false) Long operatorId,
                                         @RequestParam(required = false) String keyword,
                                         HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        List<OperationLogVO> list = operationLogService.list(5000, module, action, operatorId, keyword);
        StringBuilder sb = new StringBuilder();
        sb.append('\uFEFF');
        sb.append("ID,模块,动作,操作人ID,操作人,角色,业务号,内容,时间\n");
        for (OperationLogVO item : list) {
            sb.append(item.getId() == null ? "" : item.getId()).append(",");
            sb.append(csv(item.getModule())).append(",");
            sb.append(csv(item.getAction())).append(",");
            sb.append(item.getOperatorId() == null ? "" : item.getOperatorId()).append(",");
            sb.append(csv(item.getOperatorName())).append(",");
            sb.append(csv(item.getOperatorRole())).append(",");
            sb.append(csv(item.getBizNo())).append(",");
            sb.append(csv(item.getContent())).append(",");
            sb.append(csv(item.getCreateTime() == null ? "" : item.getCreateTime().toString())).append("\n");
        }
        byte[] bytes = sb.toString().getBytes(StandardCharsets.UTF_8);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv;charset=UTF-8"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=operation_log.csv");
        return ResponseEntity.ok().headers(headers).body(bytes);
    }

    private String csv(String text) {
        if (text == null) {
            return "";
        }
        String value = text.replace("\"", "\"\"");
        return "\"" + value + "\"";
    }

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
