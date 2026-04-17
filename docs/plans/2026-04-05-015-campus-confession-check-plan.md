# 015 校园表白墙与匿名社交平台巡检计划 Implementation Plan

**Goal:** 完成 `015` 校园表白墙与匿名社交平台的文档完整性、JDK 17 兼容性、后端测试、启动可用性与关键链路巡检，并把结果纳入总台账与单项目检查报告；对巡检过程中发现的可直接修复问题进行闭环修复与复测。

**Architecture:** 按“Spring Boot 后端 + static 静态前端”的一体化结构巡检 `015`。先核对项目总览、PRD、账号文档、README、应用配置和初始化 SQL，再验证鉴权拦截器、核心控制器、服务层和静态页面脚本，最后执行 Maven 测试、应用启动与主链路抽测。

**Tech Stack:** Spring Boot 3.2.0、MyBatis-Plus 3.5.5、MySQL 8、JWT 0.12.3、BCrypt、jQuery、Bootstrap、Maven 3.9、JDK 17

---

### Task 1: 建立 015 巡检文档

**Files:**
- Create: `docs/checks/015-campus-confession-platform.md`
- Create: `docs/plans/2026-04-05-015-campus-confession-check-plan.md`
- Modify: `docs/project-check-tracker.md`

**Step 1:** 在总台账中新增 `015` 项并更新统计值为“检查中”

**Step 2:** 创建 `015` 单项目检查报告，明确该项目为一体化项目

**Step 3:** 写入端口、数据库、默认账号、上传目录和资料范围等前置信息

### Task 2: 核对文档与源码一致性

**Files:**
- Modify: `docs/checks/015-campus-confession-platform.md`
- Read: `015-backend/PRD/PRD.md`
- Read: `015-backend/ACCOUNTS.md`
- Read: `015-backend/src/main/**`

**Step 1:** 对照 PRD、账号文档、README 与源码，确认匿名发帖、评论、点赞、私信、举报、敏感词和后台模块是否齐全

**Step 2:** 核对接口路径、鉴权拦截器、静态页面、页面脚本和接口调用是否匹配

**Step 3:** 将缺失项、资料冲突项和实现不足项写回检查报告

### Task 3: 验证 JDK 17 与构建链路

**Files:**
- Modify: `docs/checks/015-campus-confession-platform.md`
- Read: `015-backend/pom.xml`
- Read: `015-backend/src/main/resources/application.yml`
- Read: `015-backend/src/test/java/**`

**Step 1:** 检查后端编译参数、数据库/JWT/上传配置与 JDK 17 是否匹配

**Step 2:** 检查自动化测试现状和潜在兼容性风险

**Step 3:** 记录文档与真实环境依赖差异

### Task 4: 执行测试、启动与抽测

**Files:**
- Modify: `docs/checks/015-campus-confession-platform.md`
- Modify: `docs/project-check-tracker.md`
- Read/Modify: `015-backend/**`

**Step 1:** 运行后端测试命令，确认在当前环境下可以通过

**Step 2:** 启动应用，抽测用户端登录、广场、发帖、评论、点赞、搜索、私信、举报、个人中心与后台管理链路

**Step 3:** 结合初始化 SQL 与测试账号，确认关键业务链路是否可用

### Task 5: 修复与结果回填

**Files:**
- Modify: `docs/checks/015-campus-confession-platform.md`
- Modify: `docs/project-check-tracker.md`
- Modify: `015-backend/**`

**Step 1:** 对构建失败、启动失败、接口不一致、鉴权缺陷、页面断链等问题做根因分析

**Step 2:** 对可直接闭环的问题进行最小化修复，并完成复测

**Step 3:** 更新最终结论、问题清单、修复记录和整体统计

---

## Execution Outcome

- 已完成 Task 1 ~ Task 5
- 已修复的关键问题：
  - 后台越权与公开接口放行错误
  - MyBatis-Plus 分页插件缺失
  - 敏感词高等级审核失效
  - 静态页登录返回字段和评论/个人中心接口断链
  - `SensitiveWordFilter` JDK 17 编译错误
  - 初始化 SQL 密码哈希与数据库配置口径错误
- 已验证结果：
  - `mvn test -DskipTests=false` 通过
  - 后端 `8080` 成功启动
  - 登录、权限、发帖、评论/回复、点赞、举报、私信、敏感词待审与实名认证审核链路通过抽测
