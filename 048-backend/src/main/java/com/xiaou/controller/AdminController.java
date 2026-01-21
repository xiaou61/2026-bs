package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.common.Result;
import com.xiaou.dto.RelicDTO;
import com.xiaou.entity.*;
import com.xiaou.mapper.*;
import com.xiaou.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final RelicService relicService;
    private final ExhibitionService exhibitionService;
    private final ResearchService researchService;
    private final ReservationService reservationService;
    private final GuideBookingService guideBookingService;
    private final RelicMapper relicMapper;
    private final ExhibitionMapper exhibitionMapper;
    private final ReservationMapper reservationMapper;
    private final UserMapper userMapper;
    private final RelicCategoryMapper categoryMapper;
    private final DynastyMapper dynastyMapper;
    private final ExhibitionHallMapper hallMapper;
    private final NoticeMapper noticeMapper;

    @GetMapping("/statistics")
    public Result<?> statistics() {
        Map<String, Object> data = new HashMap<>();
        data.put("relicCount", relicMapper.selectCount(null));
        data.put("exhibitionCount", exhibitionMapper.selectCount(null));
        data.put("reservationCount", reservationMapper.selectCount(null));
        data.put("userCount", userMapper.selectCount(new LambdaQueryWrapper<User>().eq(User::getRole, 0)));
        return Result.success(data);
    }

    // 用户管理
    @GetMapping("/users")
    public Result<?> users(@RequestParam(defaultValue = "1") int current,
                           @RequestParam(defaultValue = "10") int size,
                           String keyword, Integer role) {
        return Result.success(userService.page(current, size, keyword, role));
    }

    @PutMapping("/user/{id}/status")
    public Result<?> updateUserStatus(@PathVariable Long id, @RequestParam Integer status) {
        userService.updateStatus(id, status);
        return Result.success();
    }

    // 文物管理
    @GetMapping("/relics")
    public Result<?> relics(@RequestParam(defaultValue = "1") int current,
                            @RequestParam(defaultValue = "10") int size,
                            String keyword, Long categoryId, Long dynastyId, Integer level) {
        return Result.success(relicService.page(current, size, keyword, categoryId, dynastyId, level));
    }

    @PostMapping("/relic")
    public Result<?> saveRelic(@RequestBody RelicDTO dto) {
        relicService.save(dto);
        return Result.success();
    }

    @PutMapping("/relic")
    public Result<?> updateRelic(@RequestBody RelicDTO dto) {
        relicService.update(dto);
        return Result.success();
    }

    @PutMapping("/relic/{id}/status")
    public Result<?> updateRelicStatus(@PathVariable Long id, @RequestParam Integer status) {
        relicService.updateStatus(id, status);
        return Result.success();
    }

    // 分类管理
    @GetMapping("/categories")
    public Result<?> categories() {
        return Result.success(categoryMapper.selectList(new LambdaQueryWrapper<RelicCategory>().orderByAsc(RelicCategory::getSort)));
    }

    @PostMapping("/category")
    public Result<?> saveCategory(@RequestBody RelicCategory category) {
        categoryMapper.insert(category);
        return Result.success();
    }

    @PutMapping("/category")
    public Result<?> updateCategory(@RequestBody RelicCategory category) {
        categoryMapper.updateById(category);
        return Result.success();
    }

    @DeleteMapping("/category/{id}")
    public Result<?> deleteCategory(@PathVariable Long id) {
        categoryMapper.deleteById(id);
        return Result.success();
    }

    // 朝代管理
    @GetMapping("/dynasties")
    public Result<?> dynasties() {
        return Result.success(dynastyMapper.selectList(new LambdaQueryWrapper<Dynasty>().orderByAsc(Dynasty::getSort)));
    }

    @PostMapping("/dynasty")
    public Result<?> saveDynasty(@RequestBody Dynasty dynasty) {
        dynastyMapper.insert(dynasty);
        return Result.success();
    }

    @PutMapping("/dynasty")
    public Result<?> updateDynasty(@RequestBody Dynasty dynasty) {
        dynastyMapper.updateById(dynasty);
        return Result.success();
    }

    // 展厅管理
    @GetMapping("/halls")
    public Result<?> halls() {
        return Result.success(hallMapper.selectList(new LambdaQueryWrapper<ExhibitionHall>().orderByAsc(ExhibitionHall::getFloor)));
    }

    @PostMapping("/hall")
    public Result<?> saveHall(@RequestBody ExhibitionHall hall) {
        hallMapper.insert(hall);
        return Result.success();
    }

    @PutMapping("/hall")
    public Result<?> updateHall(@RequestBody ExhibitionHall hall) {
        hallMapper.updateById(hall);
        return Result.success();
    }

    // 展览管理
    @GetMapping("/exhibitions")
    public Result<?> exhibitions(@RequestParam(defaultValue = "1") int current,
                                 @RequestParam(defaultValue = "10") int size,
                                 String keyword, Integer status) {
        return Result.success(exhibitionService.page(current, size, keyword, status));
    }

    @PostMapping("/exhibition")
    public Result<?> saveExhibition(@RequestBody Exhibition exhibition) {
        exhibitionService.save(exhibition);
        return Result.success();
    }

    @PutMapping("/exhibition")
    public Result<?> updateExhibition(@RequestBody Exhibition exhibition) {
        exhibitionService.update(exhibition);
        return Result.success();
    }

    @DeleteMapping("/exhibition/{id}")
    public Result<?> deleteExhibition(@PathVariable Long id) {
        exhibitionService.delete(id);
        return Result.success();
    }

    // 研究成果审核
    @GetMapping("/researches")
    public Result<?> researches(@RequestParam(defaultValue = "1") int current,
                                @RequestParam(defaultValue = "10") int size,
                                String keyword, Integer status) {
        return Result.success(researchService.page(current, size, keyword, status));
    }

    @PutMapping("/research/{id}/audit")
    public Result<?> auditResearch(@PathVariable Long id, @RequestParam Integer status) {
        researchService.audit(id, status);
        return Result.success();
    }

    // 预约管理
    @GetMapping("/reservations")
    public Result<?> reservations(@RequestParam(defaultValue = "1") int current,
                                  @RequestParam(defaultValue = "10") int size,
                                  Integer status) {
        return Result.success(reservationService.pageAll(current, size, status));
    }

    @PostMapping("/reservation/{id}/confirm")
    public Result<?> confirmReservation(@PathVariable Long id) {
        reservationService.confirm(id);
        return Result.success();
    }

    @PostMapping("/reservation/{id}/complete")
    public Result<?> completeReservation(@PathVariable Long id) {
        reservationService.complete(id);
        return Result.success();
    }

    // 讲解预约管理
    @GetMapping("/guide-bookings")
    public Result<?> guideBookings(@RequestParam(defaultValue = "1") int current,
                                   @RequestParam(defaultValue = "10") int size,
                                   Integer status) {
        return Result.success(guideBookingService.pageAll(current, size, status));
    }

    // 公告管理
    @GetMapping("/notices")
    public Result<?> notices(@RequestParam(defaultValue = "1") int current,
                             @RequestParam(defaultValue = "10") int size) {
        return Result.success(noticeMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(current, size),
                new LambdaQueryWrapper<Notice>().orderByDesc(Notice::getCreateTime)));
    }

    @PostMapping("/notice")
    public Result<?> saveNotice(@RequestBody Notice notice) {
        noticeMapper.insert(notice);
        return Result.success();
    }

    @PutMapping("/notice")
    public Result<?> updateNotice(@RequestBody Notice notice) {
        noticeMapper.updateById(notice);
        return Result.success();
    }

    @DeleteMapping("/notice/{id}")
    public Result<?> deleteNotice(@PathVariable Long id) {
        noticeMapper.deleteById(id);
        return Result.success();
    }
}
