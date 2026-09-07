# 101 前端 - 多模态招聘材料解析与岗位匹配系统

## 项目说明

`101-frontend` 是 101 号项目的 Vue 3 管理端，按角色提供管理员、HR、候选人和面试官四类入口。

## 技术栈

- Vue 3
- Vite 5
- Element Plus
- Pinia
- Axios
- Vue Router
- ECharts

## 默认端口

- 前端开发端口：`3101`
- 默认代理后端：`http://localhost:8101`

## 安装依赖

在 `101-frontend` 目录执行：

```bash
npm install
```

## 启动方式

开发模式：

```bash
npm run dev
```

生产构建验证：

```bash
npm run build
```

## 页面结构

- 登录：`Login.vue`
- 布局：`Layout.vue`
- 管理员：`Dashboard.vue`、`User.vue`、`OperationLog.vue`
- HR：`Candidate.vue`、`Resume.vue`、`Certificate.vue`、`Job.vue`、`Requirement.vue`、`ParsingTask.vue`、`ParsingResult.vue`、`MatchTask.vue`、`MatchResult.vue`、`InterviewPlan.vue`
- 候选人：`Candidate.vue`、`Resume.vue`、`Certificate.vue`
- 面试官：`InterviewPlan.vue`、`InterviewFeedback.vue`

## 角色首页

- 管理员：`/dashboard`
- HR：`/candidate`
- 候选人：`/resume`
- 面试官：`/interview`

## 使用说明

1. 先启动 `101-backend`
2. 再启动当前前端项目
3. 使用以下演示账号登录：
   - `admin / 123456`
   - `hr / 123456`
   - `candidate / 123456`
   - `interviewer / 123456`
