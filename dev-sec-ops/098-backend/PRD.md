# 企业知识库智能问答与文档权限管理系统

## 项目概述

企业知识库智能问答与文档权限管理系统面向企业内部知识沉淀、文档检索和问答追溯场景，提供知识空间、文档入库、文本分段、权限组授权、问答会话、命中来源、反馈复核和访问审计能力。系统帮助企业把制度、产品、运营和项目文档转化为可查询、可授权、可追溯的知识资产。

核心功能包括：
- 用户登录与角色权限
- 知识空间管理
- 文档分类管理
- 文档入库与发布
- 文档分段管理
- 权限组与成员管理
- 文档访问授权
- 问答会话管理
- 问答记录与来源追踪
- 问答反馈处理
- 访问审计日志
- 统计看板

技术栈：
- 后端：Spring Boot 2.7.18、MyBatis、PageHelper、MySQL 8.0、Redis、JWT
- 前端：Vue 3、Element Plus、Vue Router、Pinia、Axios、ECharts、Vite

## 功能需求

### 1. 用户与权限模块

- 用户登录
- 当前用户信息查询
- 用户分页查询
- 用户新增、编辑、禁用
- 管理员负责用户、知识空间、权限组和审计
- 编辑员负责文档、分段和发布
- 员工负责问答、反馈和查看已授权知识

### 2. 知识空间模块

- 知识空间分页查询
- 新增、编辑、删除知识空间
- 维护负责人、可见级别和启用状态
- 支持按关键词和状态筛选

### 3. 文档分类模块

- 分类分页查询
- 按空间筛选分类
- 新增、编辑、删除分类
- 维护排序、状态和说明

### 4. 知识文档模块

- 文档分页查询
- 按空间、分类、状态筛选
- 新增文档标题、摘要、来源、标签和正文
- 编辑文档内容
- 发布、归档和删除文档

### 5. 文档分段模块

- 分段分页查询
- 按文档筛选
- 维护分段标题、内容、关键词和向量状态
- 支持模拟生成索引

### 6. 权限组模块

- 权限组分页查询
- 新增、编辑、删除权限组
- 维护组负责人、说明和启用状态

### 7. 权限组成员模块

- 成员分页查询
- 为权限组添加用户
- 维护成员角色
- 删除成员

### 8. 文档授权模块

- 授权分页查询
- 为知识空间或文档绑定权限组
- 维护授权级别：只读、问答、管理
- 启用或停用授权

### 9. 问答会话模块

- 会话分页查询
- 创建会话并选择知识空间
- 维护会话标题、状态和创建人
- 关闭会话

### 10. 问答记录模块

- 问答记录分页查询
- 提交问题并生成模拟答案
- 保存命中来源、置信度和回答状态
- 支持人工标记是否已解决

### 11. 问答反馈模块

- 反馈分页查询
- 用户对问答记录提交反馈
- 反馈处理、回复和关闭

### 12. 审计统计模块

- 访问日志分页查询
- 记录用户、模块、动作、描述和时间
- 首页统计空间数、文档数、问答数、反馈数和平均置信度

## 技术设计

### 数据库设计

表1：sys_user
- id、username、password、nickname、role、department、phone、email、status、create_time、update_time

表2：knowledge_space
- id、name、owner_name、visibility、description、status、create_time、update_time

表3：document_category
- id、space_id、name、code、description、sort、status、create_time、update_time

表4：knowledge_document
- id、space_id、category_id、title、summary、source_type、tags、content、status、creator_id、create_time、update_time

表5：document_chunk
- id、document_id、chunk_title、chunk_content、keywords、vector_status、sort、create_time、update_time

表6：permission_group
- id、name、owner_name、description、status、create_time、update_time

表7：group_member
- id、group_id、user_id、member_role、status、create_time

表8：document_permission
- id、space_id、document_id、group_id、permission_level、status、create_time、update_time

表9：qa_session
- id、session_no、title、space_id、user_id、status、create_time、close_time

表10：qa_record
- id、session_id、question、answer、source_chunk_ids、confidence、resolved、status、create_time、update_time

表11：qa_feedback
- id、record_id、user_id、content、score、status、reply_content、create_time、update_time

表12：access_log
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

#### 2.1 知识空间分页
- 请求方式：GET
- 路径：`/api/space/page`
- 参数：pageNum、pageSize、keyword、status
- 返回：分页数据

#### 3.1 文档分页
- 请求方式：GET
- 路径：`/api/document/page`
- 参数：pageNum、pageSize、keyword、spaceId、categoryId、status
- 返回：分页数据

#### 3.2 发布文档
- 请求方式：PUT
- 路径：`/api/document/publish/{id}`
- 返回：成功

#### 4.1 分段分页
- 请求方式：GET
- 路径：`/api/chunk/page`
- 参数：pageNum、pageSize、documentId、vectorStatus
- 返回：分页数据

#### 4.2 生成索引
- 请求方式：PUT
- 路径：`/api/chunk/index/{id}`
- 返回：成功

#### 5.1 问答会话分页
- 请求方式：GET
- 路径：`/api/session/page`
- 参数：pageNum、pageSize、keyword、status
- 返回：分页数据

#### 5.2 提交问题
- 请求方式：POST
- 路径：`/api/record/ask`
- 参数：sessionId、question
- 返回：问答记录

#### 6.1 问答反馈处理
- 请求方式：PUT
- 路径：`/api/feedback/reply`
- 参数：id、status、replyContent
- 返回：成功

#### 7.1 统计看板
- 请求方式：GET
- 路径：`/api/statistics/dashboard`
- 返回：空间数、文档数、问答数、反馈数和平均置信度

### 项目结构

后端：
```
098-backend/
├── sql/
├── src/main/java/com/knowledgeqa/
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
098-frontend/
├── src/
│   ├── api/
│   ├── router/
│   ├── store/
│   └── views/
```

## 用户角色

- 管理员：用户、空间、权限组、授权、日志和全部业务管理
- 编辑员：文档入库、分段维护、发布归档和知识索引管理
- 员工：发起问答、查看结果、提交反馈和查看统计

## 默认账号

- admin / 123456
- editor / 123456
- staff / 123456
