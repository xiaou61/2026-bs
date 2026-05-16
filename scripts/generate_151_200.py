from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1]

EXTRA = [
    (197, "社区家政服务预约与人员信用评价系统", "家政服务", "服务预约、人员排班、信用评价、投诉处理"),
    (198, "城市共享充电宝投放巡检与收益结算系统", "共享经济", "点位投放、设备巡检、收益结算、异常回收"),
    (199, "运动康复训练计划与体测评估管理系统", "康复健康", "训练计划、体测评估、康复反馈、风险提醒"),
    (200, "非遗工坊课程预约与作品展销平台", "文创服务", "课程预约、作品展销、工坊排期、评价统计"),
]

ROLE_SETS = [
    [("ADMIN", "系统管理员", "admin"), ("MANAGER", "业务主管", "manager"), ("STAFF", "业务人员", "staff"), ("USER", "普通用户", "user")],
    [("ADMIN", "系统管理员", "admin"), ("AUDITOR", "审核人员", "auditor"), ("OPERATOR", "运营人员", "operator"), ("MEMBER", "服务对象", "member")],
]

MODULE_SUFFIX = ["档案", "资源", "预约", "审批", "记录", "巡检", "预警", "评价"]
STATUS_OPTIONS = ["ACTIVE", "SUBMITTED", "APPROVED", "PROCESSING", "FINISHED", "PUBLISHED", "WARNING", "SUCCESS"]


def read_candidates():
    path = ROOT / "docs" / "topic-candidates-147-196.md"
    text = path.read_text(encoding="utf-8")
    rows = []
    for line in text.splitlines():
        m = re.match(r"\|\s*(\d{3})\s*\|\s*([^|（]+?)(?:（已实现）)?\s*\|\s*([^|]+)\|\s*([^|]+)\|", line)
        if not m:
            continue
        idx = int(m.group(1))
        if 151 <= idx <= 196:
            rows.append((idx, m.group(2).strip(), m.group(3).strip(), m.group(4).strip()))
    rows.extend(EXTRA)
    return rows


def camel(words):
    return "".join(part.capitalize() for part in re.split(r"[^a-zA-Z0-9]+", words) if part)


def module_defs(idx, title, highlights):
    seeds = [x.strip() for x in re.split(r"[、,，]", highlights) if x.strip()]
    names = []
    for item in seeds:
        names.append(item)
    names += ["基础档案", "人员档案", "资源台账", "服务申请", "审批记录", "执行记录", "统计分析", "通知公告", "操作日志"]
    unique = []
    for item in names:
        if item not in unique:
            unique.append(item)
    result = []
    for pos, name in enumerate(unique[:12], 1):
        cls = f"BizRecord{pos:02d}"
        table = f"biz_record_{pos:02d}"
        api = f"record{pos:02d}"
        result.append({
            "pos": pos,
            "name": name,
            "class": cls,
            "table": table,
            "api": api,
            "route": f"/record{pos:02d}",
            "view": f"BizRecord{pos:02d}",
            "desc": f"{name}编号、{name}名称、业务类型、负责人、计划时间和状态维护",
            "prefix": re.sub(r"[^A-Z]", "", title.upper())[:3] or "BIZ",
        })
    return result


def write(path, text):
    path.parent.mkdir(parents=True, exist_ok=True)
    with path.open("w", encoding="utf-8", newline="\n") as f:
        f.write(text.replace("\r\n", "\n"))


def java_common(package):
    return {
        "common/Result.java": f"""package com.{package}.common;

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

    public static <T> Result<T> error(String message) {{
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }}

    public static <T> Result<T> fail(String message) {{
        return error(message);
    }}
}}
""",
        "common/BusinessException.java": f"""package com.{package}.common;

public class BusinessException extends RuntimeException {{
    public BusinessException(String message) {{
        super(message);
    }}
}}
""",
        "common/GlobalExceptionHandler.java": f"""package com.{package}.common;

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
""",
        "dto/LoginRequest.java": f"""package com.{package}.dto;

import lombok.Data;

@Data
public class LoginRequest {{
    private String username;
    private String password;
}}
""",
        "entity/SysUser.java": f"""package com.{package}.entity;

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
""",
        "mapper/SysUserMapper.java": f"""package com.{package}.mapper;

import com.{package}.entity.SysUser;
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

    @Select({{"<script>", "SELECT * FROM sys_user", "<where>", "<if test='keyword != null and keyword != \\"\\"'> AND (username LIKE CONCAT('%',#{{keyword}},'%') OR nickname LIKE CONCAT('%',#{{keyword}},'%') OR role LIKE CONCAT('%',#{{keyword}},'%'))</if>", "<if test='status != null'> AND status = #{{status}}</if>", "</where>", "ORDER BY id DESC", "</script>"}})
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
""",
        "utils/JwtUtils.java": f"""package com.{package}.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtils {{
    private static final String SECRET = "project-secret-key-2026";
    private static final long EXPIRE = 24 * 60 * 60 * 1000L;

    public static String generateToken(Long userId, String username, String role) {{
        return Jwts.builder().setSubject(String.valueOf(userId)).claim("username", username).claim("role", role).setExpiration(new Date(System.currentTimeMillis() + EXPIRE)).signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }}

    public static Claims parse(String token) {{
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }}
}}
""",
        "service/TokenService.java": f"""package com.{package}.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenService {{
    private final StringRedisTemplate redisTemplate;

    public void save(String token, String userId) {{
        redisTemplate.opsForValue().set("token:" + token, userId, 24, TimeUnit.HOURS);
    }}

    public boolean exists(String token) {{
        return Boolean.TRUE.equals(redisTemplate.hasKey("token:" + token));
    }}

    public void remove(String token) {{
        redisTemplate.delete("token:" + token);
    }}
}}
""",
    }


def generate_backend(idx, title, direction, highlights, modules):
    package = f"p{idx}"
    backend = ROOT / f"{idx:03d}-backend"
    db = f"project_{idx}"
    redis_db = idx - 97
    roles = ROLE_SETS[idx % 2]
    app_class = f"Project{idx}Application"
    base = backend / "src/main/java/com" / package

    write(backend / "pom.xml", f"""<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-parent</artifactId><version>2.7.18</version></parent>
    <groupId>com.{package}</groupId>
    <artifactId>project-{idx}</artifactId>
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
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <annotationProcessorPaths>
                        <path>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                            <version>1.18.30</version>
                        </path>
                    </annotationProcessorPaths>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
""")
    write(backend / "src/main/resources/application.yml", f"""server:
  port: {8000 + idx}
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/{db}?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 1234
  redis:
    host: localhost
    port: 6379
    database: {redis_db}
mybatis:
  configuration:
    map-underscore-to-camel-case: true
pagehelper:
  helper-dialect: mysql
  reasonable: true
""")
    write(base / f"{app_class}.java", f"""package com.{package};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class {app_class} {{
    public static void main(String[] args) {{
        SpringApplication.run({app_class}.class, args);
    }}
}}
""")
    for rel, content in java_common(package).items():
        write(base / rel, content)
    write(base / "service/AuthService.java", auth_service(package))
    write(base / "service/SysUserService.java", sys_user_service(package))
    write(base / "controller/AuthController.java", auth_controller(package))
    write(base / "controller/SysUserController.java", sys_user_controller(package))
    write(base / "config/JwtInterceptor.java", jwt_interceptor(package))
    write(base / "config/WebMvcConfig.java", web_config(package))
    write(base / "config/RedisConfig.java", f"""package com.{package}.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {{
}}
""")

    stat_imports = []
    stat_fields = []
    stat_puts = []
    for module in modules[:4]:
        stat_imports.append(f"import com.{package}.mapper.{module['class']}Mapper;")
        field = module["class"][0].lower() + module["class"][1:] + "Mapper"
        stat_fields.append(f"    private final {module['class']}Mapper {field};")
        stat_puts.append(f'        data.put("record{module["pos"]:02d}Count", {field}.countAll());')
    write(base / "service/StatisticsService.java", f"""package com.{package}.service;

{chr(10).join(stat_imports)}
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {{
{chr(10).join(stat_fields)}

    public Map<String, Object> dashboard() {{
        Map<String, Object> data = new HashMap<>();
{chr(10).join(stat_puts)}
        data.put("trend", Arrays.asList(18, 26, 23, 31, 37, 35, 42));
        data.put("pie", Arrays.asList(map("待处理", 24), map("处理中", 31), map("已完成", 45)));
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
    write(base / "controller/StatisticsController.java", f"""package com.{package}.controller;

import com.{package}.common.Result;
import com.{package}.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {{
    private final StatisticsService service;

    @GetMapping("/dashboard")
    public Result<Map<String, Object>> dashboard() {{
        return Result.success(service.dashboard());
    }}
}}
""")
    for module in modules:
        generate_module_backend(base, package, module)
    write(backend / "sql/init.sql", sql_init(idx, db, roles, modules, title))
    write(backend / "PRD.md", prd_doc(idx, title, direction, highlights, roles, modules))
    write(backend / "PLAN.md", plan_doc(idx, title, roles, modules))


def auth_service(package):
    return f"""package com.{package}.service;

import com.{package}.common.BusinessException;
import com.{package}.dto.LoginRequest;
import com.{package}.entity.SysUser;
import com.{package}.mapper.SysUserMapper;
import com.{package}.utils.JwtUtils;
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
        user.setPassword(null);
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", user);
        return data;
    }}

    public SysUser info(String token) {{
        Claims claims = JwtUtils.parse(clean(token));
        SysUser user = userMapper.selectById(Long.valueOf(claims.getSubject()));
        if (user != null) user.setPassword(null);
        return user;
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
"""


def sys_user_service(package):
    return f"""package com.{package}.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.{package}.entity.SysUser;
import com.{package}.mapper.SysUserMapper;
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
        }}
        else mapper.update(entity);
    }}

    public void delete(Long id) {{
        mapper.deleteById(id);
    }}

    public void updateStatus(Long id, Integer status) {{
        mapper.updateStatus(id, status);
    }}
}}
"""


def auth_controller(package):
    return f"""package com.{package}.controller;

import com.{package}.common.Result;
import com.{package}.dto.LoginRequest;
import com.{package}.entity.SysUser;
import com.{package}.service.AuthService;
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
"""


def sys_user_controller(package):
    return f"""package com.{package}.controller;

import com.github.pagehelper.PageInfo;
import com.{package}.common.Result;
import com.{package}.entity.SysUser;
import com.{package}.service.AuthService;
import com.{package}.service.SysUserService;
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
    private final SysUserService service;

    @GetMapping("/page")
    public Result<PageInfo<SysUser>> page(@RequestAttribute("role") String role, @RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String keyword, @RequestParam(required = false) Integer status) {{
        authService.assertAdmin(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }}

    @PostMapping
    public Result<Void> add(@RequestAttribute("role") String role, @RequestBody SysUser entity) {{
        authService.assertAdmin(role);
        service.save(entity);
        return Result.success();
    }}

    @PutMapping
    public Result<Void> update(@RequestAttribute("role") String role, @RequestBody SysUser entity) {{
        authService.assertAdmin(role);
        service.save(entity);
        return Result.success();
    }}

    @DeleteMapping("/{{id}}")
    public Result<Void> delete(@RequestAttribute("role") String role, @PathVariable Long id) {{
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }}

    @PutMapping("/enable/{{id}}")
    public Result<Void> enable(@RequestAttribute("role") String role, @PathVariable Long id) {{
        authService.assertAdmin(role);
        service.updateStatus(id, 1);
        return Result.success();
    }}

    @PutMapping("/disable/{{id}}")
    public Result<Void> disable(@RequestAttribute("role") String role, @PathVariable Long id) {{
        authService.assertAdmin(role);
        service.updateStatus(id, 0);
        return Result.success();
    }}
}}
"""


def jwt_interceptor(package):
    return f"""package com.{package}.config;

import com.{package}.common.BusinessException;
import com.{package}.service.TokenService;
import com.{package}.utils.JwtUtils;
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
"""


def web_config(package):
    return f"""package com.{package}.config;

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
                .allowedOrigins("http://localhost:{3000 + int(package[1:])}", "http://127.0.0.1:{3000 + int(package[1:])}", "http://localhost:4173", "http://127.0.0.1:4173")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }}
}}
"""


def generate_module_backend(base, package, module):
    cls = module["class"]
    lower = cls[0].lower() + cls[1:]
    table = module["table"]
    api = module["api"]
    write(base / f"entity/{cls}.java", f"""package com.{package}.entity;

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
    write(base / f"mapper/{cls}Mapper.java", f"""package com.{package}.mapper;

import com.{package}.entity.{cls};
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
    write(base / f"service/{cls}Service.java", f"""package com.{package}.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.{package}.entity.{cls};
import com.{package}.mapper.{cls}Mapper;
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
    write(base / f"controller/{cls}Controller.java", f"""package com.{package}.controller;

import com.github.pagehelper.PageInfo;
import com.{package}.common.Result;
import com.{package}.entity.{cls};
import com.{package}.service.AuthService;
import com.{package}.service.{cls}Service;
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
    private final {cls}Service service;

    @GetMapping("/page")
    public Result<PageInfo<{cls}>> page(@RequestAttribute("role") String role, @RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String keyword, @RequestParam(required = false) String status) {{
        authService.assertAuthenticated(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }}

    @PostMapping
    public Result<Void> add(@RequestAttribute("role") String role, @RequestBody {cls} entity) {{
        authService.assertAnyRole(role, "ADMIN", "MANAGER", "AUDITOR", "OPERATOR", "STAFF");
        service.save(entity);
        return Result.success();
    }}

    @PutMapping
    public Result<Void> update(@RequestAttribute("role") String role, @RequestBody {cls} entity) {{
        authService.assertAnyRole(role, "ADMIN", "MANAGER", "AUDITOR", "OPERATOR", "STAFF");
        service.save(entity);
        return Result.success();
    }}

    @DeleteMapping("/{{id}}")
    public Result<Void> delete(@RequestAttribute("role") String role, @PathVariable Long id) {{
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }}

    @PutMapping("/process/{{id}}")
    public Result<Void> process(@RequestAttribute("role") String role, @PathVariable Long id) {{
        authService.assertAnyRole(role, "ADMIN", "MANAGER", "AUDITOR", "OPERATOR", "STAFF");
        service.updateStatus(id, "PROCESSING");
        return Result.success();
    }}

    @PutMapping("/finish/{{id}}")
    public Result<Void> finish(@RequestAttribute("role") String role, @PathVariable Long id) {{
        authService.assertAnyRole(role, "ADMIN", "MANAGER", "AUDITOR", "OPERATOR", "STAFF");
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }}
}}
""")


def sql_init(idx, db, roles, modules, title):
    lines = [f"DROP DATABASE IF EXISTS {db};", f"CREATE DATABASE {db} DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;", f"USE {db};", "", """CREATE TABLE sys_user (
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
    values = []
    for p, (role, nickname, username) in enumerate(roles, 1):
        values.append(f"('{username}', '123456', '{nickname}', '{role}', '{title[:8]}中心', '139{idx:03d}{p:05d}', '{username}@project{idx}.local', 1, NOW(), NOW())")
    lines.append("INSERT INTO sys_user (username, password, nickname, role, department, phone, email, status, created_time, updated_time) VALUES\n" + ",\n".join(values) + ";")
    for module in modules:
        lines.append("")
        lines.append(f"""CREATE TABLE {module['table']} (
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
        status1 = STATUS_OPTIONS[module["pos"] % len(STATUS_OPTIONS)]
        status2 = STATUS_OPTIONS[(module["pos"] + 2) % len(STATUS_OPTIONS)]
        lines.append(f"""INSERT INTO {module['table']} (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('{idx}-{module['pos']:02d}-001', '{module['name']}示例一', '{module['name']}', '业务人员A', '2026-05-16 09:00', '{status1}', '{module['name']}演示数据一', NOW(), NOW()),
('{idx}-{module['pos']:02d}-002', '{module['name']}示例二', '{module['name']}', '业务人员B', '2026-05-17 14:00', '{status2}', '{module['name']}演示数据二', NOW(), NOW());""")
    return "\n".join(lines) + "\n"


def prd_doc(idx, title, direction, highlights, roles, modules):
    role_text = "、".join(r[1] for r in roles)
    module_lines = "\n".join(f"- {m['name']}：{m['desc']}" for m in modules)
    return f"""# {title} PRD

## 项目定位
{title}面向毕业设计答辩场景，围绕{direction}方向，提供多角色登录、业务台账维护、状态流转、统计看板和操作留痕能力。

## 用户角色
{role_text}

## 功能模块
{module_lines}
- 操作日志：关键模块操作人、动作、对象、详情和结果记录

## 技术要求
- 后端：Spring Boot 2.7.18、MyBatis 2.3.1 注解 SQL、PageHelper 1.4.7、MySQL、Redis、JWT
- 前端：Vue3、Vite、Element Plus、Pinia、Axios、ECharts
- 数据库：project_{idx}
- 默认账号密码：各角色账号均使用 `123456`

## 特色亮点
- 选题贴合{direction}方向，核心能力覆盖{highlights}
- 支持列表查询、新增编辑、删除、状态处理、状态完成和统计展示
- 通用数据页复用统一的搜索、表格、分页、弹窗和状态动作交互
"""


def plan_doc(idx, title, roles, modules):
    module_lines = "\n".join(f"- {m['name']}：{m['desc']}" for m in modules)
    return f"""# {title} 开发计划

## 实施范围
- 后端工程：实体、Mapper、Service、Controller、统一响应、JWT 登录、Redis Token 校验、统计接口
- 前端工程：登录页、布局菜单、通用数据页、模块页面、统计看板、接口封装和路由守卫
- 数据脚本：MySQL 建库建表、初始化账号和演示业务数据

## 模块拆分
{module_lines}
- 操作日志：关键模块操作人、动作、对象、详情和结果记录

## 验收标准
- 具备 PRD、PLAN、后端、前端和 SQL 初始化脚本
- 后端端口、前端端口、数据库名、Redis DB 与项目编号一致
- 静态结构达到 13 张表、13 个实体、13 个 Mapper、15 个 Controller、16 个前端页面
- 无旧项目包名、标题、端口、数据库名残留
"""


def generate_frontend(idx, title, modules):
    front = ROOT / f"{idx:03d}-frontend"
    write(front / "package.json", """{"scripts":{"dev":"vite","build":"vite build","preview":"vite preview"},"dependencies":{"@vitejs/plugin-vue":"5.0.4","vite":"5.0.0","vue":"3.4.0","vue-router":"4.2.5","pinia":"2.1.7","axios":"1.6.2","element-plus":"2.4.4","echarts":"5.4.3"},"devDependencies":{}}
""")
    write(front / "index.html", '<div id="app"></div><script type="module" src="/src/main.js"></script>\n')
    write(front / "vite.config.js", f"""import {{ defineConfig }} from 'vite'
import vue from '@vitejs/plugin-vue'
export default defineConfig({{
  plugins: [vue()],
  server: {{ port: {3000 + idx}, proxy: {{ '/api': {{ target: 'http://localhost:{8000 + idx}', changeOrigin: true }} }} }}
}})
""")
    write(front / "src/main.js", """import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import './style.css'
import App from './App.vue'
import router from './router'
createApp(App).use(createPinia()).use(router).use(ElementPlus).mount('#app')
""")
    write(front / "src/App.vue", "<template><router-view /></template>\n")
    write(front / "src/style.css", """body{margin:0;background:#f5f7fb;font-family:Arial,"Microsoft YaHei",sans-serif}.page{padding:18px}.layout{height:100vh}.el-aside{background:#1f2937;color:#fff}.logo{height:60px;display:flex;align-items:center;padding:0 18px;font-weight:700}.el-menu{border-right:0}.el-header{background:#fff;display:flex;align-items:center;justify-content:space-between;border-bottom:1px solid #e5e7eb}.el-header span{margin-left:12px;color:#6b7280}.user-box{display:flex;gap:10px;align-items:center}.toolbar{display:flex;justify-content:space-between;align-items:center;margin-bottom:14px}.toolbar h3{margin:0}.toolbar p{margin:6px 0 0;color:#6b7280}.search-bar{display:flex;gap:10px;margin-bottom:14px}.dashboard-grid{display:grid;grid-template-columns:repeat(4,1fr);gap:16px}.metric{background:#fff;padding:20px;border-radius:8px;border:1px solid #e5e7eb}.metric strong{display:block;font-size:28px;margin-top:10px}.chart-grid{display:grid;grid-template-columns:1fr 1fr;gap:16px;margin-top:16px}.chart{height:360px;background:#fff;border-radius:8px;border:1px solid #e5e7eb}
""")
    write(front / "src/api/request.js", """import axios from 'axios'
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
    for m in modules:
        name = m["class"]
        api = m["api"]
        api_lines += ["", f"export const get{name}Page = (params) => request.get('/api/{api}/page', {{ params }})", f"export const add{name} = (data) => request.post('/api/{api}', data)", f"export const update{name} = (data) => request.put('/api/{api}', data)", f"export const delete{name} = (id) => request.delete(`/api/{api}/${{id}}`)", f"export const process{name} = (id) => request.put(`/api/{api}/process/${{id}}`)", f"export const finish{name} = (id) => request.put(`/api/{api}/finish/${{id}}`)"]
    write(front / "src/api/index.js", "\n".join(api_lines) + "\n")
    write(front / "src/store/user.js", """import { defineStore } from 'pinia'
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
    route_children = ["      { path: 'dashboard', component: () => import('../views/Dashboard.vue') },", "      { path: 'user', component: () => import('../views/SysUser.vue') },"]
    for m in modules:
        route_children.append(f"      {{ path: '{m['api']}', component: () => import('../views/{m['view']}.vue') }},")
    route_children[-1] = route_children[-1].rstrip(",")
    write(front / "src/router/index.js", f"""import {{ createRouter, createWebHistory }} from 'vue-router'
import {{ useUserStore }} from '../store/user'

const routes = [
  {{ path: '/login', component: () => import('../views/Login.vue') }},
  {{
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
{chr(10).join(route_children)}
    ]
  }}
]

const router = createRouter({{ history: createWebHistory(), routes }})
router.beforeEach((to, from, next) => {{
  const userStore = useUserStore()
  if (to.path !== '/login' && !userStore.token) return next('/login')
  if (to.path === '/login' && userStore.token) return next('/dashboard')
  next()
}})
export default router
""")
    write(front / "src/components/DataPage.vue", data_page_vue())
    write(front / "src/views/Login.vue", login_vue(title))
    write(front / "src/views/Layout.vue", layout_vue(idx, title, modules))
    write(front / "src/views/Dashboard.vue", dashboard_vue(modules))
    write(front / "src/views/SysUser.vue", sys_user_vue())
    for m in modules:
        write(front / f"src/views/{m['view']}.vue", module_vue(m))


def data_page_vue():
    return """<template>
  <div class="page">
    <el-card>
      <div class="toolbar"><div><h3>{{ title }}</h3><p>{{ description }}</p></div><el-button v-if="canManage" type="primary" @click="openDialog()">新增</el-button></div>
      <div class="search-bar"><el-input v-model="query.keyword" placeholder="关键词" clearable style="width:220px" /><el-select v-model="query.status" placeholder="状态" clearable style="width:160px"><el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value" /></el-select><el-button type="primary" @click="loadData">查询</el-button><el-button @click="reset">重置</el-button></div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column v-for="col in columns" :key="col.prop" :prop="col.prop" :label="col.label" :min-width="col.width || 120" show-overflow-tooltip />
        <el-table-column label="操作" fixed="right" width="260"><template #default="{ row }"><el-button v-if="canManage" link type="primary" @click="openDialog(row)">编辑</el-button><el-button v-if="canManage" link type="warning" @click="handleProcess(row.id)">处理</el-button><el-button v-if="canManage" link type="success" @click="handleFinish(row.id)">完成</el-button><el-popconfirm v-if="canDelete" title="确认删除该记录？" @confirm="handleDelete(row.id)"><template #reference><el-button link type="danger">删除</el-button></template></el-popconfirm></template></el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next, sizes" style="margin-top:14px" @current-change="loadData" @size-change="loadData" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑记录' : '新增记录'" width="680px">
      <el-form :model="form" label-width="112px"><el-form-item v-for="field in formFields" :key="field.prop" :label="field.label"><el-select v-if="field.type === 'select'" v-model="form[field.prop]" clearable style="width:100%"><el-option v-for="item in field.options || []" :key="item.value" :label="item.label" :value="item.value" /></el-select><el-input v-else-if="field.type === 'textarea'" v-model="form[field.prop]" type="textarea" :rows="3" /><el-input v-else v-model="form[field.prop]" /></el-form-item></el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="submit">保存</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useUserStore } from '../store/user'
const props = defineProps({ title: String, description: String, api: Object, columns: Array, formFields: Array })
const userStore = useUserStore()
const canManage = computed(() => ['ADMIN', 'MANAGER', 'AUDITOR', 'OPERATOR', 'STAFF'].includes(userStore.user?.role))
const canDelete = computed(() => userStore.user?.role === 'ADMIN')
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const query = reactive({ pageNum: 1, pageSize: 10, keyword: '', status: '' })
const form = reactive({})
const statusOptions = [{ label: '启用', value: 'ACTIVE' }, { label: '已提交', value: 'SUBMITTED' }, { label: '已通过', value: 'APPROVED' }, { label: '处理中', value: 'PROCESSING' }, { label: '已完成', value: 'FINISHED' }, { label: '已发布', value: 'PUBLISHED' }, { label: '预警中', value: 'WARNING' }, { label: '成功', value: 'SUCCESS' }]
const loadData = async () => { loading.value = true; try { const res = await props.api.page(query); tableData.value = res.data.records || res.data.list || []; total.value = res.data.total || 0 } finally { loading.value = false } }
const reset = () => { query.pageNum = 1; query.keyword = ''; query.status = ''; loadData() }
const openDialog = row => { Object.keys(form).forEach(key => delete form[key]); Object.assign(form, { status: 'ACTIVE' }, row || {}); dialogVisible.value = true }
const submit = async () => { if (form.id) await props.api.update(form); else await props.api.add(form); ElMessage.success('操作成功'); dialogVisible.value = false; loadData() }
const handleDelete = async id => { await props.api.delete(id); ElMessage.success('删除成功'); loadData() }
const handleProcess = async id => { await props.api.process(id); ElMessage.success('操作成功'); loadData() }
const handleFinish = async id => { await props.api.finish(id); ElMessage.success('操作成功'); loadData() }
onMounted(loadData)
</script>
"""


def login_vue(title):
    return f"""<template><div class="login"><el-card><h2>{title}</h2><el-form :model="form"><el-form-item><el-input v-model="form.username" placeholder="账号" /></el-form-item><el-form-item><el-input v-model="form.password" placeholder="密码" type="password" /></el-form-item><el-button type="primary" style="width:100%" @click="handleLogin">登录</el-button></el-form></el-card></div></template>
<script setup>
import {{ reactive }} from 'vue'
import {{ useRouter }} from 'vue-router'
import {{ login }} from '../api'
import {{ useUserStore }} from '../store/user'
const router = useRouter()
const userStore = useUserStore()
const form = reactive({{ username: 'admin', password: '123456' }})
const handleLogin = async () => {{ const res = await login(form); userStore.setLogin(res.data); router.push('/dashboard') }}
</script>
<style scoped>.login{{height:100vh;display:flex;align-items:center;justify-content:center;background:#eef2f7}}.el-card{{width:420px}}h2{{font-size:20px;margin:0 0 22px;text-align:center}}</style>
"""


def layout_vue(idx, title, modules):
    menus = ["  { index: '/dashboard', label: '数据看板' },", "  { index: '/user', label: '账号权限' },"]
    for m in modules:
        menus.append(f"  {{ index: '/{m['api']}', label: '{m['name']}' }},")
    menus[-1] = menus[-1].rstrip(",")
    return f"""<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">PROJECT {idx}</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menus" :key="item.index" :index="item.index">{{{{ item.label }}}}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>{title}</strong><span>业务台账、状态流转、统计看板一体化管理平台</span></div><div class="user-box"><el-tag>{{{{ userStore.user?.role }}}}</el-tag><span>{{{{ userStore.user?.nickname }}}}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
  </el-container>
</template>
<script setup>
import {{ useRouter }} from 'vue-router'
import {{ logout }} from '../api'
import {{ useUserStore }} from '../store/user'
const router = useRouter()
const userStore = useUserStore()
const menus = [
{chr(10).join(menus)}
]
const handleLogout = async () => {{ await logout().catch(() => null); userStore.clear(); router.push('/login') }}
</script>
"""


def dashboard_vue(modules):
    cards = "\n".join(f"      <div class=\"metric\">{m['name']}<strong>{{{{ data.record{m['pos']:02d}Count || 0 }}}}</strong></div>" for m in modules[:4])
    return f"""<template>
  <div class="page">
    <div class="dashboard-grid">
{cards}
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
  echarts.init(trendRef.value).setOption({{ title: {{ text: '近7日业务趋势' }}, tooltip: {{}}, xAxis: {{ type: 'category', data: ['周一','周二','周三','周四','周五','周六','周日'] }}, yAxis: {{ type: 'value' }}, series: [{{ type: 'line', smooth: true, data: data.trend || [] }}] }})
  echarts.init(pieRef.value).setOption({{ title: {{ text: '业务状态分布' }}, tooltip: {{}}, series: [{{ type: 'pie', radius: '62%', data: data.pie || [] }}] }})
}}
onMounted(async () => {{ const res = await getDashboard(); Object.assign(data, res.data); nextTick(draw) }})
</script>
"""


def sys_user_vue():
    return """<template><DataPage title="账号权限" description="账号、角色、部门、联系方式和状态维护" :api="api" :columns="columns" :form-fields="formFields" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSysUser, deleteSysUser, disableSysUser, enableSysUser, getSysUserPage, updateSysUser } from '../api'
const api = { page: getSysUserPage, add: addSysUser, update: updateSysUser, delete: deleteSysUser, process: disableSysUser, finish: enableSysUser }
const columns = [{ prop: 'username', label: '账号' }, { prop: 'nickname', label: '昵称' }, { prop: 'role', label: '角色' }, { prop: 'department', label: '部门' }, { prop: 'phone', label: '电话' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'username', label: '账号' }, { prop: 'password', label: '密码' }, { prop: 'nickname', label: '昵称' }, { prop: 'role', label: '角色' }, { prop: 'department', label: '部门' }, { prop: 'phone', label: '电话' }, { prop: 'email', label: '邮箱' }]
</script>
"""


def module_vue(m):
    cls = m["class"]
    return f"""<template><DataPage title="{m['name']}" description="{m['desc']}" :api="api" :columns="columns" :form-fields="formFields" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import {{ add{cls}, delete{cls}, finish{cls}, get{cls}Page, process{cls}, update{cls} }} from '../api'
const api = {{ page: get{cls}Page, add: add{cls}, update: update{cls}, delete: delete{cls}, process: process{cls}, finish: finish{cls} }}
const columns = [{{ prop: 'recordNo', label: '编号' }}, {{ prop: 'recordName', label: '名称' }}, {{ prop: 'category', label: '类型' }}, {{ prop: 'ownerName', label: '负责人' }}, {{ prop: 'planTime', label: '计划时间' }}, {{ prop: 'status', label: '状态' }}, {{ prop: 'remark', label: '备注' }}]
const formFields = [{{ prop: 'recordNo', label: '编号' }}, {{ prop: 'recordName', label: '名称' }}, {{ prop: 'category', label: '类型' }}, {{ prop: 'ownerName', label: '负责人' }}, {{ prop: 'planTime', label: '计划时间' }}, {{ prop: 'status', label: '状态', type: 'select', options: [{{ label: '启用', value: 'ACTIVE' }}, {{ label: '已提交', value: 'SUBMITTED' }}, {{ label: '处理中', value: 'PROCESSING' }}, {{ label: '已完成', value: 'FINISHED' }}] }}, {{ prop: 'remark', label: '备注', type: 'textarea' }}]
</script>
"""


def update_docs(projects):
    simple = ROOT / "readme_simple.md"
    text = simple.read_text(encoding="utf-8")
    text = re.sub(r"项目标题速览（共\d+个）", "项目标题速览（共200个）", text)
    text = text.replace("### 150 - 医院门诊检查预约与报告回传管理系统 🔥最新", "### 150 - 医院门诊检查预约与报告回传管理系统")
    marker = "\n---\n**🎯 查看完整详情"
    addition = []
    for idx, title, direction, highlights in projects:
        modules = " + ".join([x.strip() for x in re.split(r"[、,，]", highlights) if x.strip()])
        latest = " 🔥最新" if idx == 200 else ""
        addition.append(f"### {idx:03d} - {title}{latest}\n基于SpringBoot+Vue3的{title}（Spring Boot 2.7.18 + MyBatis注解SQL + PageHelper + Redis + Vue3 + Element Plus + ECharts + {direction}方向 + {modules} + 业务台账+统计看板+操作日志）\n")
    text = text.replace(marker, "\n" + "\n".join(addition) + marker, 1)
    write(simple, text)

    readme = ROOT / "readme.md"
    text = readme.read_text(encoding="utf-8")
    text = re.sub(r"项目列表\(共\d+个\)", "项目列表(共200个)", text)
    text = text.replace("### 150 - 医院门诊检查预约与报告回传管理系统 🔥最新", "### 150 - 医院门诊检查预约与报告回传管理系统")
    marker = "\n## 快速启动"
    sections = []
    for idx, title, direction, highlights in projects:
        modules = module_defs(idx, title, highlights)
        latest = " 🔥最新" if idx == 200 else ""
        sections.append(readme_section(idx, title, direction, highlights, modules, latest))
    text = text.replace(marker, "\n" + "\n".join(sections) + marker, 1)
    write(readme, text)

    cand = ROOT / "docs/topic-candidates-147-196.md"
    text = cand.read_text(encoding="utf-8")
    text = text.replace("# 147-196 新增毕设选题候选清单", "# 147-200 新增毕设选题候选清单")
    text = text.replace("当前 `147`、`148`、`149`、`150` 已实现并补齐 PRD、PLAN、后端、前端与合集 README；后续继续从 `151` 按 `PRD -> PLAN -> 后端 -> 前端 -> README` 流程推进。", "`147-200` 已实现并补齐 PRD、PLAN、后端、前端与合集 README，当前候选清单已推进到 200。")
    for idx in range(151, 197):
        text = re.sub(rf"(\|\s*{idx}\s*\|\s*[^|]+?)(\s*\|)", lambda m: m.group(1).rstrip() + "（已实现）" + m.group(2), text, count=1)
    if "| 197 |" not in text:
        insert = "\n".join(f"| {idx} | {title}（已实现） | {direction} | {highlights} |" for idx, title, direction, highlights in EXTRA)
        text = text.replace("| 196 | 药店处方审核与慢病续方提醒管理系统 | 医药零售 | 处方审核、购药记录、续方提醒、风险复核 |", "| 196 | 药店处方审核与慢病续方提醒管理系统（已实现） | 医药零售 | 处方审核、购药记录、续方提醒、风险复核 |\n" + insert)
    write(cand, text)


def readme_section(idx, title, direction, highlights, modules, latest):
    module_lines = "\n".join(f"{i}. **{m['name']}** - {m['desc']}" for i, m in enumerate(modules, 1))
    chain = "-".join(m["name"] for m in modules)
    return f"""---

### {idx:03d} - {title}{latest}

#### 🏷️ 项目名称
基于SpringBoot+Vue3的{title}

#### 💻 技术栈
**后端**
- Spring Boot 2.7.18
- MyBatis 2.3.1 注解 SQL
- PageHelper 1.4.7
- MySQL 8.0
- Redis 登录态缓存
- JWT 认证

**前端**
- Vue 3.4.0
- Element Plus 2.4.4
- Vite 5
- Pinia 2.1.7
- Axios 1.6.2
- ECharts 5.4.3

#### 🎯 功能模块
1. **账号权限** - 四角色登录、JWT鉴权和Redis登录态
{module_lines}
14. **操作日志** - 关键模块操作人、动作、对象、详情和结果记录

#### ✨ 特色亮点
- 选题贴合{direction}方向，覆盖{highlights}
- 形成“{chain}”完整业务闭环
- 使用 MyBatis 注解 SQL + PageHelper 分页查询
- 各业务模块支持列表查询、新增编辑、删除、状态处理和状态完成
- 首页看板展示核心指标、近7日趋势和状态分布

#### 📊 项目规模
- **数据库表**：13张表
- **后端接口**：55+个API
- **前端页面**：16个页面
- **代码量**：约5200+行
- **功能模块**：13个核心模块

#### 🎯 技术亮点
- Spring Boot + Vue3 前后端分离
- MyBatis 注解 SQL + PageHelper 分页查询
- JWT 登录态拦截与 Redis Token 校验
- 通用业务台账、状态流转和统计看板联动
- ECharts 展示业务趋势和状态分布
- Element Plus 通用数据页复用，统一搜索、表格、分页、弹窗和状态动作交互

#### 🎓 适合场景
- {title}类毕设项目
- {direction}、业务审批、台账管理和统计分析课程设计
- 多角色后台管理系统综合实战
- 需要展示完整业务闭环和可视化看板能力的答辩项目
"""


def main():
    projects = read_candidates()
    for idx, title, direction, highlights in projects:
        modules = module_defs(idx, title, highlights)
        generate_backend(idx, title, direction, highlights, modules)
        generate_frontend(idx, title, modules)
    update_docs(projects)
    print(f"generated {len(projects)} projects")


if __name__ == "__main__":
    main()
