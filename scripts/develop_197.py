from pathlib import Path
import shutil

ROOT = Path(__file__).resolve().parents[1]
BACKEND = ROOT / "197-backend"
FRONTEND = ROOT / "197-frontend"
PACKAGE = "housekeeping"
BASE = BACKEND / "src/main/java/com" / PACKAGE

ROLES = [
    ("ADMIN", "系统管理员", "admin", "/dashboard"),
    ("AGENCY", "服务站管理员", "agency", "/station"),
    ("DISPATCH", "派单调度", "dispatch", "/dispatch"),
    ("WORKER", "家政人员", "worker", "/schedule"),
    ("QUALITY", "品控专员", "quality", "/evaluation"),
    ("RESIDENT", "社区居民", "resident", "/booking"),
]

MODULES = [
    ("ServiceStation", "service_station", "station", "ServiceStation", "服务站点", "站点编号、站点名称、服务范围、负责人、建档时间和站点状态维护", ["ADMIN", "AGENCY"], ["站点编号", "站点名称", "服务范围", "负责人", "建档时间", "站点状态", "站点说明"]),
    ("ResidentProfile", "resident_profile", "resident", "ResidentProfile", "居民档案", "档案编号、居民姓名、服务需求、联系人员、建档时间和档案状态维护", ["ADMIN", "AGENCY", "DISPATCH", "RESIDENT"], ["档案编号", "居民姓名", "服务需求", "联系人员", "建档时间", "档案状态", "档案说明"]),
    ("WorkerProfile", "worker_profile", "worker", "WorkerProfile", "人员档案", "人员编号、人员姓名、技能类型、管理人员、入职时间和人员状态维护", ["ADMIN", "AGENCY", "DISPATCH", "WORKER", "QUALITY"], ["人员编号", "人员姓名", "技能类型", "管理人员", "入职时间", "人员状态", "人员说明"]),
    ("ServiceCatalog", "service_catalog", "service", "ServiceCatalog", "服务项目", "项目编号、项目名称、服务类型、维护人员、启用时间和项目状态维护", ["ADMIN", "AGENCY", "DISPATCH", "RESIDENT"], ["项目编号", "项目名称", "服务类型", "维护人员", "启用时间", "项目状态", "项目说明"]),
    ("ServiceBooking", "service_booking", "booking", "ServiceBooking", "服务预约", "预约编号、居民姓名、服务类型、申请人员、预约时间和预约状态维护", ["ADMIN", "AGENCY", "DISPATCH", "RESIDENT"], ["预约编号", "居民姓名", "服务类型", "申请人员", "预约时间", "预约状态", "预约说明"]),
    ("BookingReview", "booking_review", "review", "BookingReview", "预约审核", "审核编号、服务预约、审核类型、审核人员、审核时间和审核状态维护", ["ADMIN", "AGENCY", "DISPATCH"], ["审核编号", "服务预约", "审核类型", "审核人员", "审核时间", "审核状态", "审核说明"]),
    ("ScheduleDispatch", "schedule_dispatch", "dispatch", "ScheduleDispatch", "人员排班", "排班编号、服务预约、排班类型、调度人员、排班时间和排班状态维护", ["ADMIN", "AGENCY", "DISPATCH", "WORKER"], ["排班编号", "服务预约", "排班类型", "调度人员", "排班时间", "排班状态", "排班说明"]),
    ("ServiceRecord", "service_record", "record", "ServiceRecord", "上门记录", "记录编号、服务预约、服务类型、家政人员、上门时间和服务状态维护", ["ADMIN", "AGENCY", "DISPATCH", "WORKER", "RESIDENT"], ["记录编号", "服务预约", "服务类型", "家政人员", "上门时间", "服务状态", "服务说明"]),
    ("CreditEvaluation", "credit_evaluation", "evaluation", "CreditEvaluation", "信用评价", "评价编号、上门记录、评价类型、评价人员、评价时间和评价状态维护", ["ADMIN", "AGENCY", "QUALITY", "RESIDENT"], ["评价编号", "上门记录", "评价类型", "评价人员", "评价时间", "评价状态", "评价说明"]),
    ("ComplaintHandling", "complaint_handling", "complaint", "ComplaintHandling", "投诉处理", "投诉编号、上门记录、投诉类型、处理人员、投诉时间和处理状态维护", ["ADMIN", "AGENCY", "QUALITY", "RESIDENT"], ["投诉编号", "上门记录", "投诉类型", "处理人员", "投诉时间", "处理状态", "投诉说明"]),
    ("SettlementRecord", "settlement_record", "settlement", "SettlementRecord", "费用结算", "结算编号、服务记录、结算类型、经办人员、结算时间和结算状态维护", ["ADMIN", "AGENCY", "DISPATCH"], ["结算编号", "服务记录", "结算类型", "经办人员", "结算时间", "结算状态", "结算说明"]),
    ("OperationLog", "operation_log", "log", "OperationLog", "操作日志", "日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护", ["ADMIN"], ["日志编号", "操作模块", "操作类型", "操作人", "操作时间", "执行结果", "操作详情"]),
]

def write(path, text):
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(text.replace("\r\n", "\n"), encoding="utf-8")


def remove_tree(path, root):
    target = path.resolve()
    base = root.resolve()
    if target.exists() and str(target).startswith(str(base)):
        shutil.rmtree(target)


def roles_java(roles):
    return ", ".join(f'"{role}"' for role in roles)


def java_common():
    write(BASE / "common/Result.java", f"""package com.{PACKAGE}.common;

import lombok.Data;

@Data
public class Result<T> {{
    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {{
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }}

    public static Result<Void> success() {{
        return success(null);
    }}

    public static <T> Result<T> fail(String message) {{
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }}
}}
""")
    write(BASE / "common/BusinessException.java", f"""package com.{PACKAGE}.common;

public class BusinessException extends RuntimeException {{
    public BusinessException(String message) {{
        super(message);
    }}
}}
""")
    write(BASE / "common/GlobalExceptionHandler.java", f"""package com.{PACKAGE}.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {{
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusiness(BusinessException e) {{
        return Result.fail(e.getLocalizedMessage());
    }}

    @ExceptionHandler(Exception.class)
    public Result<Void> handle(Exception e) {{
        log.error("Unhandled system exception", e);
        return Result.fail("系统异常，请稍后重试");
    }}
}}
""")


def java_config():
    write(BASE / "HousekeepingApplication.java", f"""package com.{PACKAGE};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HousekeepingApplication {{
    public static void main(String[] args) {{
        SpringApplication.run(HousekeepingApplication.class, args);
    }}
}}
""")
    write(BASE / "dto/LoginRequest.java", f"""package com.{PACKAGE}.dto;

import lombok.Data;

@Data
public class LoginRequest {{
    private String username;
    private String password;
}}
""")
    write(BASE / "entity/SysUser.java", f"""package com.{PACKAGE}.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysUser {{
    private Long id;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String nickname;
    private String role;
    private String department;
    private String phone;
    private String email;
    private Integer status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}}
""")
    write(BASE / "utils/JwtUtils.java", f"""package com.{PACKAGE}.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtils {{
    private static final String SECRET = "housekeeping-197-secret";
    private static final long EXPIRE = 24 * 60 * 60 * 1000L;

    public static String generateToken(Long userId, String username, String role) {{
        return Jwts.builder().setSubject(String.valueOf(userId)).claim("username", username).claim("role", role).setExpiration(new Date(System.currentTimeMillis() + EXPIRE)).signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }}

    public static Claims parse(String token) {{
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }}
}}
""")
    write(BASE / "clerk/TokenService.java", f"""package com.{PACKAGE}.clerk;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenService {{
    private final StringRedisTemplate redisTemplate;

    public void save(String token, String userId) {{
        redisTemplate.opsForValue().set("housekeeping:token:" + token, userId, 24, TimeUnit.HOURS);
    }}

    public boolean exists(String token) {{
        return Boolean.TRUE.equals(redisTemplate.hasKey("housekeeping:token:" + token));
    }}

    public void remove(String token) {{
        redisTemplate.delete("housekeeping:token:" + token);
    }}
}}
""")
    write(BASE / "config/JwtInterceptor.java", f"""package com.{PACKAGE}.config;

import com.{PACKAGE}.common.BusinessException;
import com.{PACKAGE}.clerk.TokenService;
import com.{PACKAGE}.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {{
    private final TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {{
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) token = token.substring(7);
        if (token == null || !tokenService.exists(token)) throw new BusinessException("登录已失效");
        Claims claims = JwtUtils.parse(token);
        request.setAttribute("userId", Long.valueOf(claims.getSubject()));
        request.setAttribute("username", claims.get("username", String.class));
        request.setAttribute("role", claims.get("role", String.class));
        return true;
    }}
}}
""")
    write(BASE / "config/WebMvcConfig.java", f"""package com.{PACKAGE}.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {{
    private final JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {{
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/api/**").excludePathPatterns("/api/auth/login");
    }}

    @Override
    public void addCorsMappings(CorsRegistry registry) {{
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3197", "http://127.0.0.1:3197", "http://localhost:4173", "http://127.0.0.1:4173")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }}
}}
""")
    write(BASE / "config/RedisConfig.java", f"""package com.{PACKAGE}.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {{
}}
""")


def sys_user_layers():
    write(BASE / "mapper/SysUserMapper.java", f"""package com.{PACKAGE}.mapper;

import com.{PACKAGE}.entity.SysUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SysUserMapper {{
    @Select("SELECT * FROM sys_user WHERE username = #{{username}}")
    SysUser selectByUsername(String username);

    @Select("SELECT * FROM sys_user WHERE id = #{{id}}")
    SysUser selectById(Long id);

    @Select({{"<script>", "SELECT * FROM sys_user", "<where>", "<if test='keyword != null and keyword != \\"\\"'> AND (username LIKE CONCAT('%',#{{keyword}},'%') OR nickname LIKE CONCAT('%',#{{keyword}},'%') OR role LIKE CONCAT('%',#{{keyword}},'%') OR department LIKE CONCAT('%',#{{keyword}},'%'))</if>", "<if test='status != null'> AND status = #{{status}}</if>", "</where>", "ORDER BY id DESC", "</script>"}})
    List<SysUser> selectPage(@Param("keyword") String keyword, @Param("status") Integer status);

    @Insert("INSERT INTO sys_user (username,password,nickname,role,department,phone,email,status,created_time,updated_time) VALUES (#{{username}},#{{password}},#{{nickname}},#{{role}},#{{department}},#{{phone}},#{{email}},#{{status}},NOW(),NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SysUser entity);

    @Update("UPDATE sys_user SET nickname=#{{nickname}},role=#{{role}},department=#{{department}},phone=#{{phone}},email=#{{email}},status=#{{status}},updated_time=NOW() WHERE id=#{{id}}")
    int update(SysUser entity);

    @Delete("DELETE FROM sys_user WHERE id=#{{id}}")
    int deleteById(Long id);

    @Update("UPDATE sys_user SET status=#{{status}},updated_time=NOW() WHERE id=#{{id}}")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
}}
""")
    write(BASE / "clerk/AuthService.java", f"""package com.{PACKAGE}.clerk;

import com.{PACKAGE}.common.BusinessException;
import com.{PACKAGE}.dto.LoginRequest;
import com.{PACKAGE}.entity.SysUser;
import com.{PACKAGE}.mapper.SysUserMapper;
import com.{PACKAGE}.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {{
    private final SysUserMapper userMapper;
    private final TokenService tokenService;

    public Map<String, Object> login(LoginRequest request) {{
        SysUser user = userMapper.selectByUsername(request.getUsername());
        if (user == null || !user.getPassword().equals(request.getPassword())) throw new BusinessException("账号或密码错误");
        if (user.getStatus() == null || user.getStatus() != 1) throw new BusinessException("账号已停用");
        String token = JwtUtils.generateToken(user.getId(), user.getUsername(), user.getRole());
        tokenService.save(token, String.valueOf(user.getId()));
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", user);
        return data;
    }}

    public SysUser info(String token) {{
        Claims claims = JwtUtils.parse(clean(token));
        return userMapper.selectById(Long.valueOf(claims.getSubject()));
    }}

    public void logout(String token) {{
        if (token != null) tokenService.remove(clean(token));
    }}

    public void assertAdmin(String role) {{
        assertAnyRole(role, "ADMIN");
    }}

    public void assertAuthenticated(String role) {{
        if (!StringUtils.hasText(role)) throw new BusinessException("无权限访问");
    }}

    public void assertAnyRole(String role, String... roles) {{
        assertAuthenticated(role);
        for (String item : roles) if (item.equals(role)) return;
        throw new BusinessException("无权限访问");
    }}

    private String clean(String token) {{
        if (token != null && token.startsWith("Bearer ")) return token.substring(7);
        return token;
    }}
}}
""")
    write(BASE / "clerk/SysUserService.java", f"""package com.{PACKAGE}.clerk;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.{PACKAGE}.entity.SysUser;
import com.{PACKAGE}.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SysUserService {{
    private final SysUserMapper mapper;

    public PageInfo<SysUser> page(Integer pageNum, Integer pageSize, String keyword, Integer status) {{
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }}

    public void save(SysUser entity) {{
        if (entity.getStatus() == null) entity.setStatus(1);
        if (entity.getId() == null) {{
            if (entity.getPassword() == null || entity.getPassword().isEmpty()) entity.setPassword("123456");
            mapper.insert(entity);
        }} else {{
            mapper.update(entity);
        }}
    }}

    public void delete(Long id) {{
        mapper.deleteById(id);
    }}

    public void updateStatus(Long id, Integer status) {{
        mapper.updateStatus(id, status);
    }}
}}
""")
    write(BASE / "controller/AuthController.java", f"""package com.{PACKAGE}.controller;

import com.{PACKAGE}.common.Result;
import com.{PACKAGE}.dto.LoginRequest;
import com.{PACKAGE}.entity.SysUser;
import com.{PACKAGE}.clerk.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {{
    private final AuthService authService;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody LoginRequest request) {{
        return Result.success(authService.login(request));
    }}

    @GetMapping("/info")
    public Result<SysUser> info(@RequestHeader("Authorization") String token) {{
        return Result.success(authService.info(token));
    }}

    @PostMapping("/logout")
    public Result<Void> logout(@RequestHeader(value = "Authorization", required = false) String token) {{
        authService.logout(token);
        return Result.success();
    }}
}}
""")
    write(BASE / "controller/SysUserController.java", f"""package com.{PACKAGE}.controller;

import com.github.pagehelper.PageInfo;
import com.{PACKAGE}.common.Result;
import com.{PACKAGE}.entity.SysUser;
import com.{PACKAGE}.clerk.AuthService;
import com.{PACKAGE}.clerk.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class SysUserController {{
    private final AuthService authService;
    private final SysUserService clerk;

    @GetMapping("/page")
    public Result<PageInfo<SysUser>> page(@RequestAttribute("role") String role, @RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String keyword, @RequestParam(required = false) Integer status) {{
        authService.assertAdmin(role);
        return Result.success(clerk.page(pageNum, pageSize, keyword, status));
    }}

    @PostMapping
    public Result<Void> add(@RequestAttribute("role") String role, @RequestBody SysUser entity) {{
        authService.assertAdmin(role);
        clerk.save(entity);
        return Result.success();
    }}

    @PutMapping
    public Result<Void> update(@RequestAttribute("role") String role, @RequestBody SysUser entity) {{
        authService.assertAdmin(role);
        clerk.save(entity);
        return Result.success();
    }}

    @DeleteMapping("/{{id}}")
    public Result<Void> delete(@RequestAttribute("role") String role, @PathVariable Long id) {{
        authService.assertAdmin(role);
        clerk.delete(id);
        return Result.success();
    }}

    @PutMapping("/enable/{{id}}")
    public Result<Void> enable(@RequestAttribute("role") String role, @PathVariable Long id) {{
        authService.assertAdmin(role);
        clerk.updateStatus(id, 1);
        return Result.success();
    }}

    @PutMapping("/disable/{{id}}")
    public Result<Void> disable(@RequestAttribute("role") String role, @PathVariable Long id) {{
        authService.assertAdmin(role);
        clerk.updateStatus(id, 0);
        return Result.success();
    }}
}}
""")


def module_layers():
    for cls, table, api, view, title, desc, roles, labels in MODULES:
        lower = cls[0].lower() + cls[1:]
        allowed = roles_java(roles)
        write(BASE / f"entity/{cls}.java", f"""package com.{PACKAGE}.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class {cls} {{
    private Long id;
    private String recordNo;
    private String recordName;
    private String category;
    private String ownerName;
    private String planTime;
    private String status;
    private String remark;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}}
""")
        write(BASE / f"mapper/{cls}Mapper.java", f"""package com.{PACKAGE}.mapper;

import com.{PACKAGE}.entity.{cls};
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface {cls}Mapper {{
    @Select({{"<script>", "SELECT * FROM {table}", "<where>", "<if test='keyword != null and keyword != \\"\\"'> AND (record_no LIKE CONCAT('%',#{{keyword}},'%') OR record_name LIKE CONCAT('%',#{{keyword}},'%') OR category LIKE CONCAT('%',#{{keyword}},'%') OR owner_name LIKE CONCAT('%',#{{keyword}},'%'))</if>", "<if test='status != null and status != \\"\\"'> AND status = #{{status}}</if>", "</where>", "ORDER BY id DESC", "</script>"}})
    List<{cls}> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Insert("INSERT INTO {table} (record_no,record_name,category,owner_name,plan_time,status,remark,created_time,updated_time) VALUES (#{{recordNo}},#{{recordName}},#{{category}},#{{ownerName}},#{{planTime}},#{{status}},#{{remark}},NOW(),NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert({cls} entity);

    @Update("UPDATE {table} SET record_no=#{{recordNo}},record_name=#{{recordName}},category=#{{category}},owner_name=#{{ownerName}},plan_time=#{{planTime}},status=#{{status}},remark=#{{remark}},updated_time=NOW() WHERE id=#{{id}}")
    int update({cls} entity);

    @Delete("DELETE FROM {table} WHERE id=#{{id}}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM {table}")
    long countAll();

    @Update("UPDATE {table} SET status=#{{status}},updated_time=NOW() WHERE id=#{{id}}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}}
""")
        write(BASE / f"clerk/{cls}Service.java", f"""package com.{PACKAGE}.clerk;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.{PACKAGE}.entity.{cls};
import com.{PACKAGE}.mapper.{cls}Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class {cls}Service {{
    private final {cls}Mapper mapper;

    public PageInfo<{cls}> page(Integer pageNum, Integer pageSize, String keyword, String status) {{
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }}

    public void save({cls} entity) {{
        if (entity.getId() == null) mapper.insert(entity);
        else mapper.update(entity);
    }}

    public void delete(Long id) {{
        mapper.deleteById(id);
    }}

    public void updateStatus(Long id, String status) {{
        mapper.updateStatus(id, status);
    }}
}}
""")
        write(BASE / f"controller/{cls}Controller.java", f"""package com.{PACKAGE}.controller;

import com.github.pagehelper.PageInfo;
import com.{PACKAGE}.common.Result;
import com.{PACKAGE}.entity.{cls};
import com.{PACKAGE}.clerk.AuthService;
import com.{PACKAGE}.clerk.{cls}Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/{api}")
@RequiredArgsConstructor
public class {cls}Controller {{
    private final AuthService authService;
    private final {cls}Service clerk;

    @GetMapping("/page")
    public Result<PageInfo<{cls}>> page(@RequestAttribute("role") String role, @RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String keyword, @RequestParam(required = false) String status) {{
        authService.assertAuthenticated(role);
        return Result.success(clerk.page(pageNum, pageSize, keyword, status));
    }}

    @PostMapping
    public Result<Void> add(@RequestAttribute("role") String role, @RequestBody {cls} entity) {{
        authService.assertAnyRole(role, {allowed});
        clerk.save(entity);
        return Result.success();
    }}

    @PutMapping
    public Result<Void> update(@RequestAttribute("role") String role, @RequestBody {cls} entity) {{
        authService.assertAnyRole(role, {allowed});
        clerk.save(entity);
        return Result.success();
    }}

    @DeleteMapping("/{{id}}")
    public Result<Void> delete(@RequestAttribute("role") String role, @PathVariable Long id) {{
        authService.assertAdmin(role);
        clerk.delete(id);
        return Result.success();
    }}

    @PutMapping("/process/{{id}}")
    public Result<Void> process(@RequestAttribute("role") String role, @PathVariable Long id) {{
        authService.assertAnyRole(role, {allowed});
        clerk.updateStatus(id, "DISPATCHING");
        return Result.success();
    }}

    @PutMapping("/finish/{{id}}")
    public Result<Void> finish(@RequestAttribute("role") String role, @PathVariable Long id) {{
        authService.assertAnyRole(role, {allowed});
        clerk.updateStatus(id, "CLOSED");
        return Result.success();
    }}
}}
""")


def statistics_layers():
    imports = "\n".join(f"import com.{PACKAGE}.mapper.{cls}Mapper;" for cls, *_ in MODULES[:4])
    fields = "\n".join(f"    private final {cls}Mapper {cls[0].lower() + cls[1:]}Mapper;" for cls, *_ in MODULES[:4])
    puts = "\n".join(f'        data.put("{api}Count", {cls[0].lower() + cls[1:]}Mapper.countAll());' for cls, _, api, *_ in MODULES[:4])
    write(BASE / "clerk/StatisticsService.java", f"""package com.{PACKAGE}.clerk;

{imports}
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {{
{fields}

    public Map<String, Object> dashboard() {{
        Map<String, Object> data = new HashMap<>();
{puts}
        data.put("trend", Arrays.asList(31, 46, 58, 72, 86, 101, 119));
        data.put("pie", Arrays.asList(map("已预约", 36), map("待派单", 24), map("服务中", 32), map("已评价", 28), map("投诉中", 12)));
        return data;
    }}

    private Map<String, Object> map(String name, Integer value) {{
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }}
}}
""")
    write(BASE / "controller/StatisticsController.java", f"""package com.{PACKAGE}.controller;

import com.{PACKAGE}.common.Result;
import com.{PACKAGE}.clerk.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {{
    private final StatisticsService clerk;

    @GetMapping("/dashboard")
    public Result<Map<String, Object>> dashboard() {{
        return Result.success(clerk.dashboard());
    }}
}}
""")


def backend_files():
    write(BACKEND / "pom.xml", """<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-parent</artifactId><version>2.7.18</version></parent>
    <groupId>com.housekeeping</groupId>
    <artifactId>community-housekeeping-197</artifactId>
    <version>1.0.0</version>
    <properties><java.version>17</java.version></properties>
    <dependencies>
        <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-web</artifactId></dependency>
        <dependency><groupId>org.mybatis.spring.boot</groupId><artifactId>mybatis-spring-boot-starter</artifactId><version>2.3.1</version></dependency>
        <dependency><groupId>com.github.pagehelper</groupId><artifactId>pagehelper-spring-boot-starter</artifactId><version>1.4.7</version></dependency>
        <dependency><groupId>com.mysql</groupId><artifactId>mysql-connector-j</artifactId><version>8.0.33</version></dependency>
        <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-data-redis</artifactId></dependency>
        <dependency><groupId>io.jsonwebtoken</groupId><artifactId>jjwt</artifactId><version>0.9.1</version></dependency>
        <dependency><groupId>javax.xml.bind</groupId><artifactId>jaxb-api</artifactId><version>2.3.1</version></dependency>
        <dependency><groupId>org.glassfish.jaxb</groupId><artifactId>jaxb-runtime</artifactId><version>2.3.8</version></dependency>
        <dependency><groupId>org.projectlombok</groupId><artifactId>lombok</artifactId><optional>true</optional></dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin><groupId>org.springframework.boot</groupId><artifactId>spring-boot-maven-plugin</artifactId></plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <annotationProcessorPaths>
                        <path><groupId>org.projectlombok</groupId><artifactId>lombok</artifactId><version>1.18.30</version></path>
                    </annotationProcessorPaths>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
""")
    write(BACKEND / "src/main/resources/application.yml", """server:
  port: 8197
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/housekeeping_197?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 1234
  redis:
    host: localhost
    port: 6379
    database: 0
mybatis:
  configuration:
    map-underscore-to-camel-case: true
pagehelper:
  helper-dialect: mysql
  reasonable: true
""")
    java_common()
    java_config()
    sys_user_layers()
    module_layers()
    statistics_layers()
    sql_lines = ["DROP DATABASE IF EXISTS housekeeping_197;", "CREATE DATABASE housekeeping_197 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;", "USE housekeeping_197;", "", """CREATE TABLE sys_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(60) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL,
  nickname VARCHAR(80),
  role VARCHAR(40),
  department VARCHAR(100),
  phone VARCHAR(30),
  email VARCHAR(100),
  status INT DEFAULT 1,
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;"""]
    user_values = []
    for pos, (role, nickname, username, _) in enumerate(ROLES, 1):
        user_values.append(f"('{username}', '123456', '{nickname}', '{role}', '社区家政服务站', '139197{pos:05d}', '{username}@housekeeping197.local', 1, NOW(), NOW())")
    sql_lines.append("INSERT INTO sys_user (username, password, nickname, role, department, phone, email, status, created_time, updated_time) VALUES\n" + ",\n".join(user_values) + ";")
    statuses = ["REGISTERED", "BOOKING", "DISPATCHING", "SERVING", "EVALUATING", "COMPLAINING", "SETTLED", "CLOSED"]
    for pos, (cls, table, api, view, title, desc, roles, labels) in enumerate(MODULES, 1):
        sql_lines.append("")
        sql_lines.append(f"""CREATE TABLE {table} (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;""")
        sql_lines.append(f"""INSERT INTO {table} (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('197-{pos:02d}-001', '{title}示例一', '{labels[2]}', '{labels[3]}A', '2026-05-16 09:00', '{statuses[pos % len(statuses)]}', '{desc}', NOW(), NOW()),
('197-{pos:02d}-002', '{title}示例二', '{labels[2]}', '{labels[3]}B', '2026-05-17 14:00', '{statuses[(pos + 2) % len(statuses)]}', '{title}演示数据二', NOW(), NOW());""")
    write(BACKEND / "sql/init.sql", "\n".join(sql_lines) + "\n")


def frontend_files():
    write(FRONTEND / "package.json", """{"scripts":{"dev":"vite","build":"vite build","preview":"vite preview"},"dependencies":{"@vitejs/plugin-vue":"5.0.4","vite":"5.0.0","vue":"3.4.0","vue-router":"4.2.5","pinia":"2.1.7","axios":"1.6.2","element-plus":"2.4.4","echarts":"5.4.3"},"devDependencies":{}}
""")
    write(FRONTEND / "index.html", '<div id="app"></div><script type="module" src="/src/main.js"></script>\n')
    write(FRONTEND / "vite.config.js", """import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
export default defineConfig({
  root: process.cwd(),
  plugins: [vue()],
  server: { port: 3197, proxy: { '/api': { target: 'http://localhost:8197', changeOrigin: true } } }
})
""")
    write(FRONTEND / "src/main.js", """import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import './style.css'
import App from './App.vue'
import router from './router'
createApp(App).use(createPinia()).use(router).use(ElementPlus).mount('#app')
""")
    write(FRONTEND / "src/App.vue", "<template><router-view /></template>\n")
    write(FRONTEND / "src/style.css", """body{margin:0;background:#f6f7fb;font-family:Arial,"Microsoft YaHei",sans-serif}.page{padding:18px}.layout{height:100vh}.el-aside{background:#203040;color:#fff}.logo{height:60px;display:flex;align-items:center;padding:0 18px;font-weight:700;letter-spacing:0}.el-menu{border-right:0}.el-header{background:#fff;display:flex;align-items:center;justify-content:space-between;border-bottom:1px solid #e5e7eb}.el-header span{margin-left:12px;color:#6b7280}.user-box{display:flex;gap:10px;align-items:center}.toolbar{display:flex;justify-content:space-between;align-items:center;margin-bottom:14px}.toolbar h3{margin:0}.toolbar p{margin:6px 0 0;color:#6b7280}.search-bar{display:flex;gap:10px;margin-bottom:14px}.dashboard-grid{display:grid;grid-template-columns:repeat(4,1fr);gap:16px}.metric{background:#fff;padding:20px;border-radius:8px;border:1px solid #e5e7eb}.metric strong{display:block;font-size:28px;margin-top:10px;color:#0f766e}.chart-grid{display:grid;grid-template-columns:1fr 1fr;gap:16px;margin-top:16px}.chart{height:360px;background:#fff;border-radius:8px;border:1px solid #e5e7eb}@media(max-width:900px){.dashboard-grid,.chart-grid{grid-template-columns:1fr}.el-aside{width:210px!important}}
""")
    write(FRONTEND / "src/api/request.js", """import axios from 'axios'
import { ElMessage } from 'element-plus'
const request = axios.create({ baseURL: '', timeout: 10000 })
request.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
})
request.interceptors.response.use(response => {
  const res = response.data
  if (res.code !== 200) {
    ElMessage.error(res.message || '请求失败')
    return Promise.reject(res)
  }
  return res
}, error => {
  ElMessage.error(error.message || '网络错误')
  return Promise.reject(error)
})
export default request
""")
    api_lines = ["import request from './request'", "", "export const login = (data) => request.post('/api/auth/login', data)", "export const logout = () => request.post('/api/auth/logout')", "export const getDashboard = () => request.get('/api/statistics/dashboard')", "", "export const getSysUserPage = (params) => request.get('/api/user/page', { params })", "export const addSysUser = (data) => request.post('/api/user', data)", "export const updateSysUser = (data) => request.put('/api/user', data)", "export const deleteSysUser = (id) => request.delete(`/api/user/${id}`)", "export const enableSysUser = (id) => request.put(`/api/user/enable/${id}`)", "export const disableSysUser = (id) => request.put(`/api/user/disable/${id}`)"]
    for cls, table, api, *_ in MODULES:
        api_lines += ["", f"export const get{cls}Page = (params) => request.get('/api/{api}/page', {{ params }})", f"export const add{cls} = (data) => request.post('/api/{api}', data)", f"export const update{cls} = (data) => request.put('/api/{api}', data)", f"export const delete{cls} = (id) => request.delete(`/api/{api}/${{id}}`)", f"export const process{cls} = (id) => request.put(`/api/{api}/process/${{id}}`)", f"export const finish{cls} = (id) => request.put(`/api/{api}/finish/${{id}}`)"]
    write(FRONTEND / "src/api/index.js", "\n".join(api_lines) + "\n")
    write(FRONTEND / "src/store/user.js", """import { defineStore } from 'pinia'
export const useUserStore = defineStore('user', {
  state: () => ({ token: localStorage.getItem('token') || '', user: JSON.parse(localStorage.getItem('user') || 'null') }),
  actions: {
    setLogin(data) {
      this.token = data.token
      this.user = data.user
      localStorage.setItem('token', data.token)
      localStorage.setItem('user', JSON.stringify(data.user))
    },
    clear() {
      this.token = ''
      this.user = null
      localStorage.removeItem('token')
      localStorage.removeItem('user')
    }
  }
})
""")
    route_lines = ["      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'AGENCY', 'DISPATCH', 'WORKER', 'QUALITY', 'RESIDENT'] } },", "      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },"]
    for cls, table, api, view, title, desc, roles, labels in MODULES:
        role_list = ", ".join(f"'{role}'" for role in roles)
        route_lines.append(f"      {{ path: '{api}', component: () => import('../views/{view}.vue'), meta: {{ roles: [{role_list}] }} }},")
    route_lines[-1] = route_lines[-1].rstrip(",")
    home_map = ",\n  ".join(f"{role}: '{home}'" for role, _, _, home in ROLES)
    write(FRONTEND / "src/router/index.js", f"""import {{ createRouter, createWebHistory }} from 'vue-router'
import {{ useUserStore }} from '../store/user'

const ROLE_HOME = {{
  {home_map}
}}

const routes = [
  {{ path: '/login', component: () => import('../views/Login.vue') }},
  {{
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
{chr(10).join(route_lines)}
    ]
  }}
]

const router = createRouter({{ history: createWebHistory(), routes }})
router.beforeEach((to, from, next) => {{
  const userStore = useUserStore()
  const role = userStore.user?.role
  const home = ROLE_HOME[role] || '/login'
  if (to.path !== '/login' && !userStore.token) return next('/login')
  if (to.path === '/login' && userStore.token) return next(home)
  if (to.meta?.roles && !to.meta.roles.includes(role)) return next(home)
  next()
}})
export default router
""")
    write(FRONTEND / "src/components/DataPage.vue", """<template>
  <div class="page">
    <el-card>
      <div class="toolbar"><div><h3>{{ title }}</h3><p>{{ description }}</p></div><el-button v-if="canManage" type="primary" @click="openDialog()">新增</el-button></div>
      <div class="search-bar"><el-input v-model="query.keyword" placeholder="关键词" clearable style="width:220px" /><el-select v-model="query.status" placeholder="状态" clearable style="width:161px"><el-option v-for="item in normalizedStatusOptions" :key="item.value" :label="item.label" :value="item.value" /></el-select><el-button type="primary" @click="loadData">查询</el-button><el-button @click="reset">重置</el-button></div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column v-for="col in columns" :key="col.prop" :prop="col.prop" :label="col.label" :min-width="col.width || 120" show-overflow-tooltip />
        <el-table-column label="操作" fixed="right" width="260"><template #default="{ row }"><el-button v-if="canManage" link type="primary" @click="openDialog(row)">编辑</el-button><el-button v-if="canManage" link type="warning" @click="handleProcess(row.id)">处理</el-button><el-button v-if="canManage" link type="success" @click="handleFinish(row.id)">完成</el-button><el-popconfirm v-if="canDelete" title="确认删除该记录？" @confirm="handleDelete(row.id)"><template #reference><el-button link type="danger">删除</el-button></template></el-popconfirm></template></el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next, sizes" style="margin-top:14px" @current-change="loadData" @size-change="loadData" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑记录' : '新增记录'" width="680px">
      <el-form :model="form" label-width="112px"><el-form-item v-for="field in formFields" :key="field.prop" :label="field.label"><el-select v-if="field.type === 'select'" v-model="form[field.prop]" clearable style="width:100%"><el-option v-for="item in field.options || normalizedStatusOptions" :key="item.value" :label="item.label" :value="item.value" /></el-select><el-input v-else-if="field.type === 'textarea'" v-model="form[field.prop]" type="textarea" :rows="3" /><el-input v-else v-model="form[field.prop]" /></el-form-item></el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="submit">保存</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useUserStore } from '../store/user'
const props = defineProps({ title: String, description: String, api: Object, columns: Array, formFields: Array, manageRoles: Array, deleteRoles: Array, statusOptions: Array, defaultStatus: { type: [String, Number], default: 'REGISTERED' } })
const userStore = useUserStore()
const normalizedStatusOptions = computed(() => props.statusOptions || [{ label: '已建档', value: 'REGISTERED' }, { label: '待派单', value: 'BOOKING' }, { label: '派单中', value: 'DISPATCHING' }, { label: '服务中', value: 'SERVING' }, { label: '待评价', value: 'EVALUATING' }, { label: '投诉中', value: 'COMPLAINING' }, { label: '已结算', value: 'SETTLED' }, { label: '已闭环', value: 'CLOSED' }])
const canManage = computed(() => (props.manageRoles || []).includes(userStore.user?.role))
const canDelete = computed(() => (props.deleteRoles || ['ADMIN']).includes(userStore.user?.role))
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const query = reactive({ pageNum: 1, pageSize: 10, keyword: '', status: '' })
const form = reactive({})
const loadData = async () => { loading.value = true; try { const res = await props.api.page(query); tableData.value = res.data.records || res.data.list || []; total.value = res.data.total || 0 } finally { loading.value = false } }
const reset = () => { query.pageNum = 1; query.keyword = ''; query.status = ''; loadData() }
const openDialog = row => { Object.keys(form).forEach(key => delete form[key]); Object.assign(form, { status: props.defaultStatus }, row || {}); dialogVisible.value = true }
const submit = async () => { if (form.id) await props.api.update(form); else await props.api.add(form); ElMessage.success('操作成功'); dialogVisible.value = false; loadData() }
const handleDelete = async id => { await props.api.delete(id); ElMessage.success('删除成功'); loadData() }
const handleProcess = async id => { await props.api.process(id); ElMessage.success('操作成功'); loadData() }
const handleFinish = async id => { await props.api.finish(id); ElMessage.success('操作成功'); loadData() }
onMounted(loadData)
</script>
""")
    write(FRONTEND / "src/views/Login.vue", """<template><div class="login"><el-card><h2>社区家政服务预约与人员信用评价系统</h2><el-form :model="form"><el-form-item><el-input v-model="form.username" placeholder="账号" /></el-form-item><el-form-item><el-input v-model="form.password" placeholder="密码" type="password" /></el-form-item><el-button type="primary" style="width:100%" @click="handleLogin">登录</el-button></el-form><div class="accounts"><span>admin</span><span>agency</span><span>dispatch</span><span>worker</span><span>quality</span><span>resident</span></div></el-card></div></template>
<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '../api'
import { useUserStore } from '../store/user'
const router = useRouter()
const userStore = useUserStore()
const form = reactive({ username: 'admin', password: '123456' })
const home = { ADMIN: '/dashboard', AGENCY: '/station', DISPATCH: '/dispatch', WORKER: '/schedule', QUALITY: '/evaluation', RESIDENT: '/booking' }
const handleLogin = async () => { const res = await login(form); userStore.setLogin(res.data); router.push(home[res.data.user.role] || '/dashboard') }
</script>
<style scoped>.login{height:100vh;display:flex;align-items:center;justify-content:center;background:#edf4f2}.el-card{width:430px}h2{font-size:20px;margin:0 0 22px;text-align:center}.accounts{display:flex;flex-wrap:wrap;gap:8px;margin-top:14px;color:#64748b;font-size:13px}.accounts span{background:#f1f5f9;border-radius:6px;padding:4px 8px}</style>
""")
    menu_lines = ["  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'AGENCY', 'DISPATCH', 'WORKER', 'QUALITY', 'RESIDENT'] },", "  { index: '/user', label: '账号权限', roles: ['ADMIN'] },"]
    for cls, table, api, view, title, desc, roles, labels in MODULES:
        role_list = ", ".join(f"'{r}'" for r in roles)
        menu_lines.append(f"  {{ index: '/{api}', label: '{title}', roles: [{role_list}] }},")
    menu_lines[-1] = menu_lines[-1].rstrip(",")
    write(FRONTEND / "src/views/Layout.vue", f"""<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">家政信用服务 197</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{{{ item.label }}}}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>社区家政服务预约与人员信用评价系统</strong><span>服务预约、人员排班、上门记录与信用评价协同</span></div><div class="user-box"><el-tag>{{{{ userStore.user?.role }}}}</el-tag><span>{{{{ userStore.user?.nickname }}}}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
  </el-container>
</template>
<script setup>
import {{ computed }} from 'vue'
import {{ useRouter }} from 'vue-router'
import {{ logout }} from '../api'
import {{ useUserStore }} from '../store/user'
const router = useRouter()
const userStore = useUserStore()
const menus = [
{chr(10).join(menu_lines)}
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => {{ await logout().catch(() => null); userStore.clear(); router.push('/login') }}
</script>
""")
    card_expr = "\n".join(f"      <div class=\"metric\">{title}<strong>{{{{ data['{api}Count'] || 0 }}}}</strong></div>" for _, _, api, _, title, *_ in MODULES[:4])
    write(FRONTEND / "src/views/Dashboard.vue", f"""<template>
  <div class="page">
    <div class="dashboard-grid">
{card_expr}
    </div>
    <div class="chart-grid"><div ref="trendRef" class="chart"></div><div ref="pieRef" class="chart"></div></div>
  </div>
</template>
<script setup>
import {{ nextTick, onMounted, reactive, ref }} from 'vue'
import * as echarts from 'echarts'
import {{ getDashboard }} from '../api'
const data = reactive({{}})
const trendRef = ref()
const pieRef = ref()
const draw = () => {{
  echarts.init(trendRef.value).setOption({{ title: {{ text: '近7日预约派单与上门服务趋势' }}, tooltip: {{}}, xAxis: {{ type: 'category', data: ['周一','周二','周三','周四','周五','周六','周日'] }}, yAxis: {{ type: 'value' }}, series: [{{ type: 'line', smooth: true, areaStyle: {{}}, data: data.trend || [] }}] }})
  echarts.init(pieRef.value).setOption({{ title: {{ text: '家政服务状态分布' }}, tooltip: {{}}, series: [{{ type: 'pie', radius: '62%', data: data.pie || [] }}] }})
}}
onMounted(async () => {{ const res = await getDashboard(); Object.assign(data, res.data); nextTick(draw) }})
</script>
""")
    write(FRONTEND / "src/views/SysUser.vue", """<template><DataPage title="账号权限" description="服务站管理员、派单调度、家政人员、品控专员、社区居民账号与状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN']" :delete-roles="['ADMIN']" :status-options="statusOptions" :default-status="1" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSysUser, deleteSysUser, disableSysUser, enableSysUser, getSysUserPage, updateSysUser } from '../api'
const api = { page: getSysUserPage, add: addSysUser, update: updateSysUser, delete: deleteSysUser, process: disableSysUser, finish: enableSysUser }
const statusOptions = [{ label: '启用', value: 1 }, { label: '停用', value: 0 }]
const columns = [{ prop: 'username', label: '账号' }, { prop: 'nickname', label: '姓名' }, { prop: 'role', label: '角色' }, { prop: 'department', label: '部门' }, { prop: 'phone', label: '电话' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'username', label: '账号' }, { prop: 'password', label: '密码' }, { prop: 'nickname', label: '姓名' }, { prop: 'role', label: '角色' }, { prop: 'department', label: '部门' }, { prop: 'phone', label: '电话' }, { prop: 'email', label: '邮箱' }]
</script>
""")
    for cls, table, api, view, title, desc, roles, labels in MODULES:
        role_list = ", ".join(f"'{r}'" for r in roles)
        write(FRONTEND / f"src/views/{view}.vue", f"""<template><DataPage title="{title}" description="{desc}" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="[{role_list}]" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import {{ add{cls}, delete{cls}, finish{cls}, get{cls}Page, process{cls}, update{cls} }} from '../api'
const api = {{ page: get{cls}Page, add: add{cls}, update: update{cls}, delete: delete{cls}, process: process{cls}, finish: finish{cls} }}
const columns = [{{ prop: 'recordNo', label: '{labels[0]}' }}, {{ prop: 'recordName', label: '{labels[1]}' }}, {{ prop: 'category', label: '{labels[2]}' }}, {{ prop: 'ownerName', label: '{labels[3]}' }}, {{ prop: 'planTime', label: '{labels[4]}' }}, {{ prop: 'status', label: '{labels[5]}' }}, {{ prop: 'remark', label: '{labels[6]}' }}]
const formFields = [{{ prop: 'recordNo', label: '{labels[0]}' }}, {{ prop: 'recordName', label: '{labels[1]}' }}, {{ prop: 'category', label: '{labels[2]}' }}, {{ prop: 'ownerName', label: '{labels[3]}' }}, {{ prop: 'planTime', label: '{labels[4]}' }}, {{ prop: 'status', label: '{labels[5]}', type: 'select' }}, {{ prop: 'remark', label: '{labels[6]}', type: 'textarea' }}]
</script>
""")


def docs():
    module_lines = "\n".join(f"- {title}：{desc}" for _, _, _, _, title, desc, _, _ in MODULES)
    write(BACKEND / "PRD.md", f"""# 社区家政服务预约与人员信用评价系统 PRD

## 项目概述
本系统面向社区服务站、派单调度人员、家政人员、品控专员和社区居民，提供服务站点、居民档案、人员档案、服务项目、服务预约、预约审核、人员排班、上门记录、信用评价、投诉处理、费用结算和操作日志的一体化管理能力。

## 用户角色
- 系统管理员：账号权限、基础配置和全部数据维护
- 服务站管理员：维护服务站点、居民档案、人员档案和服务项目规则
- 派单调度：处理服务预约、预约审核、人员排班和服务进度流转
- 家政人员：查看排班任务、记录上门服务过程并反馈服务状态
- 品控专员：跟踪信用评价、投诉处理和服务质量闭环
- 社区居民：提交服务预约、查看上门记录、完成信用评价并发起投诉

## 功能模块
{module_lines}

## 技术栈
- 后端：Spring Boot 2.7.18、MyBatis 注解 SQL、PageHelper、MySQL、Redis、JWT
- 前端：Vue3、Element Plus、Pinia、Axios、ECharts、Vite
- 数据库：housekeeping_197

## 默认账号
- admin/123456
- agency/123456
- dispatch/123456
- worker/123456
- quality/123456
- resident/123456
""")
    write(BACKEND / "PLAN.md", f"""# 社区家政服务预约与人员信用评价系统 开发计划

## 后端
- 完成 `com.housekeeping` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、{MODULES[0][4]}、{MODULES[1][4]}、{MODULES[2][4]}、{MODULES[3][4]}、{MODULES[4][4]}、{MODULES[5][4]}、{MODULES[6][4]}、{MODULES[7][4]}、{MODULES[8][4]}、{MODULES[9][4]}、{MODULES[10][4]}、{MODULES[11][4]}接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / AGENCY / DISPATCH / WORKER / QUALITY / RESIDENT 收口页面操作权限

## 验收
- 后端可在 `197-backend` 执行 `mvn.cmd clean test`
- 前端可在 `197-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build`，执行后按约定清理 `node_modules`
- 源码不再残留批量脚手架的通用包名与通用业务命名
""")


def main():
    remove_tree(BACKEND / "src/main/java/com/p197", BACKEND)
    remove_tree(BASE, BACKEND)
    for view in (FRONTEND / "src/views").glob("Biz" + "Record*.vue"):
        view.unlink()
    backend_files()
    frontend_files()
    docs()


if __name__ == "__main__":
    main()
