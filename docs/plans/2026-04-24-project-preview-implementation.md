# Project Preview Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** 为仓库中的真实项目建立预览流水线，并完成 `001` 项目的首个多图预览闭环。

**Architecture:** 使用 Python 负责项目扫描、数据库初始化、服务启动、清单生成和 Markdown 输出；使用浏览器工具完成真实登录、菜单遍历和截图；所有预览结果统一落在 `docs/previews/` 目录下，并由 `readme.md` 提供入口。

**Tech Stack:** Python 3.9、PyMySQL、Maven、npm、Chrome DevTools、Markdown

---

### Task 1: 建立预览目录骨架

**Files:**
- Create: `docs/previews/README.md`
- Create: `docs/previews/groups/.gitkeep`
- Create: `docs/previews/projects/.gitkeep`
- Create: `docs/previews/assets/.gitkeep`
- Create: `docs/previews/runtime/.gitkeep`

**Step 1: 创建预览说明文档**

- 写明目录结构、查看方式、当前推进策略。

**Step 2: 创建空目录锚点**

- 保证后续脚本运行时目录已存在。

**Step 3: 验证目录结构**

Run: `Get-ChildItem .\\docs\\previews -Force`
Expected: 能看到 `groups`、`projects`、`assets`、`runtime`

### Task 2: 实现预览流水线脚本

**Files:**
- Create: `scripts/project_preview/run_preview.py`

**Step 1: 实现项目扫描**

- 扫描根目录真实项目
- 从 `readme.md` 解析项目编号和项目名称

**Step 2: 实现账号提取**

- 从项目 README/ACCOUNTS/SQL 注释中提取默认账号密码

**Step 3: 实现数据库导入**

- 用 PyMySQL 读取并执行初始化 SQL

**Step 4: 实现前后端启动**

- 后端：`mvn spring-boot:run`
- 前端：优先 `npm run dev`
- 输出日志到 `logs/project-preview/`

**Step 5: 实现运行清单输出**

- 为当前项目生成 JSON 清单，包含：
  - 项目名
  - 目录
  - 类型
  - 账号
  - URL
  - 页面建议

**Step 6: 实现 Markdown 渲染**

- 根据截图目录生成项目详览页
- 根据项目页生成分组页
- 更新 `readme.md` 预览入口区块

### Task 3: 处理 `001` 项目运行准备

**Files:**
- Modify: `scripts/project_preview/run_preview.py`
- Create: `docs/previews/runtime/001.json`

**Step 1: 导入 `001` 数据库脚本**

Run: `python .\\scripts\\project_preview\\run_preview.py prepare 001`
Expected: 成功创建并导入 `campus_affairs`

**Step 2: 启动 `001` 后端**

Expected: `http://127.0.0.1:8080` 可访问

**Step 3: 启动 `001` 前端**

Expected: `http://127.0.0.1:3000` 可访问

**Step 4: 输出运行清单**

Expected: 生成 `docs/previews/runtime/001.json`

### Task 4: 使用浏览器工具完成 `001` 多图截图

**Files:**
- Create: `docs/previews/assets/001/*.png`

**Step 1: 管理员角色截图**

- 登录
- 首页
- 请假管理
- 报修管理
- 公告中心
- 活动中心
- 用户管理
- 个人中心

**Step 2: 教师角色截图**

- 登录
- 仪表盘
- 请假审批
- 公告发布
- 活动管理

**Step 3: 学生角色截图**

- 登录
- 请假申请
- 报修提交
- 公告浏览
- 活动报名
- 个人中心

### Task 5: 生成 `001` 项目预览文档并回填 README

**Files:**
- Create: `docs/previews/projects/001.md`
- Create: `docs/previews/groups/001-010.md`
- Modify: `readme.md`
- Modify: `task_plan.md`
- Modify: `findings.md`
- Modify: `progress.md`

**Step 1: 渲染项目页**

Run: `python .\\scripts\\project_preview\\run_preview.py render 001`
Expected: 生成 `docs/previews/projects/001.md`

**Step 2: 渲染分组页**

Expected: 生成 `docs/previews/groups/001-010.md`

**Step 3: 更新 README**

Expected: 新增“项目预览”入口与查看方式

**Step 4: 回填进度记录**

- 写明 `001` 的启动、截图、文档完成情况

### Task 6: 关闭 `001` 运行进程

**Files:**
- Modify: `docs/previews/runtime/001.json`

**Step 1: 停止前后端**

Run: `python .\\scripts\\project_preview\\run_preview.py stop 001`
Expected: `8080` 和 `3000` 不再被当前项目占用

**Step 2: 验证日志和清单完整**

- 检查日志是否存在
- 检查清单是否保留
