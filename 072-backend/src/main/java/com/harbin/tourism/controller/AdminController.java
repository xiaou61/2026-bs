package com.harbin.tourism.controller;

import com.harbin.tourism.common.Result;
import com.harbin.tourism.entity.ScenicSpot;
import com.harbin.tourism.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private SpotService spotService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private NoteService noteService;

    @GetMapping("/statistics")
    public Result<Map<String, Object>> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("userCount", userService.count());
        stats.put("spotCount", spotService.count());
        stats.put("routeCount", routeService.count());
        stats.put("orderCount", ticketService.orderCount());
        stats.put("activityCount", activityService.count());
        stats.put("noteCount", noteService.count());
        stats.put("pendingNoteCount", noteService.pendingCount());
        stats.put("totalIncome", ticketService.totalIncome());
        return Result.success(stats);
    }

    @GetMapping("/spot-ranking")
    public Result<List<ScenicSpot>> spotRanking() {
        return Result.success(spotService.top10());
    }
}
