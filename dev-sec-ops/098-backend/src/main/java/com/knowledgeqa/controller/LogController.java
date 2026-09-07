package com.knowledgeqa.controller;

import com.github.pagehelper.PageInfo;
import com.knowledgeqa.common.Result;
import com.knowledgeqa.service.AuthService;
import com.knowledgeqa.service.CrudService;
import com.knowledgeqa.utils.Filters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/log")
public class LogController {

    @Autowired
    private CrudService crudService;

    @Autowired
    private AuthService authService;

    @GetMapping("/page")
    public Result<PageInfo<Map<String, Object>>> page(@RequestAttribute String role,
                                                      @RequestParam(defaultValue = "1") Integer pageNum,
                                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                                      String keyword,
                                                      String moduleName) {
        authService.assertAdmin(role);
        return Result.success(crudService.page("log", pageNum, pageSize, keyword, Filters.of("moduleName", moduleName)));
    }
}
