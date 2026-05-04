# 大模型提示词资产管理与效果评测平台

## 项目概述

大模型提示词资产管理与效果评测平台面向企业内部 AI 应用团队，提供 Prompt 资产沉淀、版本迭代、测试集维护、模型配置、自动评测、人工复核和效果对比能力。系统帮助团队把零散提示词变成可管理、可追踪、可复用、可评估的工程化资产。

核心功能包括：
- 用户登录与角色权限
- 团队空间管理
- Prompt 分类管理
- Prompt 资产管理
- Prompt 版本管理
- 测试用例管理
- 模型配置管理
- 评分规则管理
- 评测任务管理
- 评测结果记录
- 用户反馈管理
- 操作日志审计

技术栈：
- 后端：Spring Boot 2.7.18、MyBatis-Plus、MySQL 8.0、Redis、JWT
- 前端：Vue 3、Element Plus、Vue Router、Pinia、Axios、ECharts、Vite

## 功能需求

### 1. 用户与权限模块

- 用户登录
- 当前用户信息查询
- 用户分页查询
- 用户新增、编辑、禁用
- 管理员拥有全部管理权限
- 工程师负责 Prompt 资产、版本、测试集和评测任务
- 评审员负责评测结果复核和反馈处理

### 2. 团队空间模块

- 团队空间分页查询
- 团队空间新增、编辑、删除
- 团队负责人维护
- 团队状态管理

### 3. Prompt 分类模块

- 分类分页查询
- 分类新增、编辑、删除
- 分类排序和启用状态维护

### 4. Prompt 资产模块

- Prompt 资产分页查询
- 按分类、状态、关键词筛选
- 新增 Prompt 资产
- 编辑 Prompt 标题、场景、标签、状态
- 删除 Prompt 资产
- 查看资产关联版本

### 5. Prompt 版本模块

- 版本分页查询
- 新增版本内容
- 维护变量说明、适用模型、变更说明
- 设置基准版本
- 发布版本

### 6. 测试用例模块

- 测试用例分页查询
- 按资产、难度、状态筛选
- 新增测试输入、期望输出、评分要点
- 编辑和删除测试用例

### 7. 模型配置模块

- 模型配置分页查询
- 维护模型名称、供应商、温度、最大 token、状态
- 设置默认模型

### 8. 评分规则模块

- 评分规则分页查询
- 维护维度名称、权重、说明、状态
- 用于评测结果加权得分展示

### 9. 评测任务模块

- 评测任务分页查询
- 创建任务并选择 Prompt 版本、测试用例、模型配置
- 启动评测
- 完成评测并生成结果
- 查看任务平均分、通过率和状态

### 10. 评测结果模块

- 评测结果分页查询
- 查看输入、期望输出、实际输出、得分和结论
- 评审员复核评测结果
- 维护复核意见

### 11. 用户反馈模块

- 用户反馈分页查询
- 针对 Prompt 资产提交反馈
- 反馈处理和关闭
- 按状态筛选

### 12. 审计日志模块

- 操作日志分页查询
- 记录用户、模块、动作、描述和时间
- 支持管理员追踪关键操作

## 技术设计

### 数据库设计

表1：sys_user
- id、username、password、nickname、role、phone、email、status、create_time、update_time

表2：team_space
- id、name、owner_name、description、member_count、status、create_time、update_time

表3：prompt_category
- id、name、code、description、sort、status、create_time、update_time

表4：prompt_asset
- id、title、category_id、team_id、scene、tags、description、status、creator_id、create_time、update_time

表5：prompt_version
- id、asset_id、version_no、content、variables、model_hint、change_log、is_baseline、status、creator_id、create_time

表6：test_case
- id、asset_id、title、input_text、expected_output、score_points、difficulty、status、creator_id、create_time、update_time

表7：model_config
- id、name、provider、model_name、temperature、max_tokens、is_default、status、create_time、update_time

表8：score_rule
- id、name、dimension、weight、description、status、create_time、update_time

表9：evaluation_task
- id、task_no、name、asset_id、version_id、model_id、status、average_score、pass_rate、creator_id、create_time、finish_time

表10：evaluation_result
- id、task_id、case_id、input_text、expected_output、actual_output、score、passed、review_status、review_comment、create_time、update_time

表11：prompt_feedback
- id、asset_id、user_id、content、priority、status、reply_content、create_time、update_time

表12：operation_log
- id、user_id、username、role、module_name、action_type、description、create_time

### API接口设计

#### 1.1 登录
- 请求方式：POST
- 路径：`/api/auth/login`
- 参数：`{ "username": "admin", "password": "123456" }`
- 返回：token 和用户信息

#### 1.2 当前用户
- 请求方式：GET
- 路径：`/api/auth/info`
- 返回：当前登录用户

#### 1.3 退出登录
- 请求方式：POST
- 路径：`/api/auth/logout`
- 返回：成功

#### 2.1 用户分页
- 请求方式：GET
- 路径：`/api/user/page`
- 参数：pageNum、pageSize、keyword、role、status
- 返回：分页数据

#### 3.1 团队空间分页
- 请求方式：GET
- 路径：`/api/team/page`
- 参数：pageNum、pageSize、keyword、status
- 返回：分页数据

#### 4.1 Prompt 资产分页
- 请求方式：GET
- 路径：`/api/asset/page`
- 参数：pageNum、pageSize、keyword、categoryId、status
- 返回：分页数据

#### 5.1 Prompt 版本分页
- 请求方式：GET
- 路径：`/api/version/page`
- 参数：pageNum、pageSize、assetId、status
- 返回：分页数据

#### 6.1 测试用例分页
- 请求方式：GET
- 路径：`/api/case/page`
- 参数：pageNum、pageSize、assetId、difficulty、status
- 返回：分页数据

#### 7.1 评测任务分页
- 请求方式：GET
- 路径：`/api/evaluation/page`
- 参数：pageNum、pageSize、keyword、status
- 返回：分页数据

#### 7.2 启动评测
- 请求方式：PUT
- 路径：`/api/evaluation/run/{id}`
- 返回：生成评测结果

#### 7.3 完成评测
- 请求方式：PUT
- 路径：`/api/evaluation/finish/{id}`
- 返回：成功

#### 8.1 评测结果分页
- 请求方式：GET
- 路径：`/api/result/page`
- 参数：pageNum、pageSize、taskId、reviewStatus
- 返回：分页数据

#### 8.2 复核结果
- 请求方式：PUT
- 路径：`/api/result/review`
- 参数：id、reviewStatus、reviewComment
- 返回：成功

#### 9.1 统计看板
- 请求方式：GET
- 路径：`/api/statistics/dashboard`
- 返回：资产数、版本数、任务数、平均分、通过率等

### 项目结构

后端：
```
097-backend/
├── sql/
├── src/main/java/com/promptops/
│   ├── common/
│   ├── config/
│   ├── controller/
│   ├── entity/
│   ├── mapper/
│   ├── service/
│   └── utils/
└── src/main/resources/
```

前端：
```
097-frontend/
├── src/
│   ├── api/
│   ├── router/
│   ├── store/
│   └── views/
```

## 用户角色

- 管理员：用户、团队、分类、模型、规则、审计日志和全部业务管理
- 工程师：Prompt 资产、版本、测试用例、评测任务管理
- 评审员：评测结果复核、反馈处理、统计查看

## 默认账号

- admin / 123456
- engineer / 123456
- reviewer / 123456
