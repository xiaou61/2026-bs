# 毕设项目开发工作流程

## 📋 完整流程概览

```
1. 读取规则 → 2. 编写PRD → 3. 制定Plan → 4. 后端实现 → 5. 前端实现 → 6. 更新README
```

---

## 🔄 详细步骤

### Step 1: 读取项目规则
**目的**: 了解项目约束和技术栈要求

**操作**:
```
读取 rule.md 文件，记住以下要点：
- 不写注释
- 不写README（除非明确要求）
- 不进行编译验证
- Windows环境
- 前端不使用npx命令
- 技术栈：MyBatis/MyBatis-Plus + Redis + Vue + SpringBoot
```

---

### Step 2: 编写PRD文档
**目的**: 明确需求和技术设计

**文件位置**: `项目目录/PRD.md`

**内容结构**:
```markdown
# 项目标题

## 项目概述
- 项目简介
- 核心功能
- 技术栈

## 功能需求
### 1. 模块一
- 功能点1
- 功能点2

### 2. 模块二
...

## 技术设计
### 数据库设计
表1: xxx
- 字段列表
- 索引

### API接口设计
#### 1.1 接口名
- 请求方式: POST/GET
- 路径: /api/xxx
- 参数: {}
- 返回: {}

### 项目结构
后端:
```
backend/
├── src/main/java/com/xxx/
│   ├── entity/
│   ├── mapper/
│   ├── service/
│   └── controller/
```

前端:
```
frontend/
├── src/
│   ├── views/
│   ├── api/
│   └── router/
```

## 用户角色
- 角色1: 权限描述
- 角色2: 权限描述

## 默认账号
- admin/123456
- user/123456
```

---

### Step 3: 制定实施计划
**目的**: 规划实现步骤

**操作**:
```
使用 create_plan 工具创建计划
```

**计划内容**:
```markdown
# 实现计划

## 问题陈述
简述项目背景和要解决的问题

## 当前状态
已有的项目结构和文件

## 实施方案

### 第一阶段: 后端开发
1. 基础架构
   - pom.xml配置
   - application.yml配置
   - 启动类
   - 通用模块(Result/Exception/JWT)

2. 数据库
   - 创建init.sql
   - 12张表结构

3. 实体类
   - 所有Entity类

4. Mapper接口
   - 所有Mapper

5. Service层
   - 业务逻辑实现

6. Controller层
   - API接口

### 第二阶段: 前端开发
1. 项目结构
   - package.json
   - vite.config.js
   - 路由配置
   - API封装
   - Pinia状态管理

2. 页面开发
   - 登录页
   - 布局页
   - 功能页面
```

**等待用户确认后再执行**

---

### Step 4: 后端实现
**目的**: 完成Spring Boot后端

#### 4.1 基础架构
**创建顺序**:
1. `pom.xml` - Maven依赖
2. `application.yml` - 配置文件
3. `XxxApplication.java` - 启动类
4. `common/Result.java` - 统一返回
5. `common/BusinessException.java` - 业务异常
6. `common/GlobalExceptionHandler.java` - 全局异常处理
7. `config/JwtInterceptor.java` - JWT拦截器
8. `config/WebMvcConfig.java` - Web配置
9. `config/MybatisPlusConfig.java` - MP配置
10. `config/RedisConfig.java` - Redis配置
11. `utils/JwtUtils.java` - JWT工具

#### 4.2 数据库
**文件**: `sql/init.sql`

**内容**:
- DROP DATABASE IF EXISTS
- CREATE DATABASE
- USE DATABASE
- 所有表的CREATE TABLE
- 插入测试数据

#### 4.3 实体类
**路径**: `entity/`

**注意**:
- 使用MyBatis-Plus注解: @TableName, @TableId, @TableField
- 包含所有字段
- 无注释

#### 4.4 Mapper接口
**路径**: `mapper/`

**格式**:
```java
public interface XxxMapper extends BaseMapper<Xxx> {
}
```

#### 4.5 Service层
**路径**: `service/`

**包含**:
- 业务逻辑方法
- 分页查询
- CRUD操作

#### 4.6 Controller层
**路径**: `controller/`

**注意**:
- RESTful风格
- 统一返回Result
- 参数校验

**标记完成**: `mark_todo_as_done`

---

### Step 5: 前端实现
**目的**: 完成Vue3前端

#### 5.1 项目结构
**创建顺序**:
1. `package.json` - 依赖配置
2. `vite.config.js` - Vite配置(含proxy)
3. `index.html` - HTML入口
4. `src/main.js` - Vue入口
5. `src/App.vue` - 根组件
6. `src/router/index.js` - 路由配置
7. `src/api/request.js` - Axios封装
8. `src/api/index.js` - API接口
9. `src/store/user.js` - Pinia状态

**技术栈**:
- Vue 3.4.0
- Element Plus 2.4.4
- Vue Router 4.2.5
- Pinia 2.1.7
- Axios 1.6.2
- ECharts 5.4.3 (如需要)

#### 5.2 页面开发
**必需页面**:
1. `views/Login.vue` - 登录
2. `views/Layout.vue` - 布局(侧边栏+顶栏)
3. `views/Dashboard.vue` - 首页仪表盘

**业务页面**:
- 按照功能模块创建页面
- 统一风格: 搜索栏 + 表格 + 分页 + 弹窗

**页面模板**:
```vue
<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.xxx" placeholder="..." />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="xxx" label="xxx" />
        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button link @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" />
    </el-card>
    <el-dialog v-model="dialogVisible">
      <el-form :model="form" ref="formRef">
        ...
      </el-form>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getXxxList, addXxx, updateXxx, deleteXxx } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10 })
const form = reactive({})

const loadData = async () => {
  loading.value = true
  try {
    const res = await getXxxList(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, {})
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateXxx(form)
  } else {
    await addXxx(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteXxx(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
</style>
```

**标记完成**: `mark_todo_as_done`

---

### Step 6: 更新README
**目的**: 将项目添加到项目合集

#### 6.1 更新readme.md
**位置**: 插入到最后一个项目和"## 快速启动"之间

**格式**:
```markdown
### 0XX - 项目名称 🔥最新

#### 🏷️ 项目名称
完整项目标题

#### 💻 技术栈
**后端**
- Spring Boot x.x.x
- MyBatis-Plus x.x.x
- MySQL 8.0
- Redis
- JWT

**前端**
- Vue 3.x.x
- Element Plus x.x.x
- Vite
- Pinia
- Axios

#### 🎯 功能模块
1. **模块1** - 功能描述
2. **模块2** - 功能描述
...

#### ✨ 特色亮点
- 亮点1
- 亮点2
...

#### 🎬 演示流程
1. 步骤1
2. 步骤2
...

#### 📊 项目规模
- **数据库表**：X张表
- **后端接口**：X+个API
- **前端页面**：X个页面
- **代码量**：约X+行
- **功能模块**：X个核心模块

#### 🎯 技术亮点
- 技术点1
- 技术点2
...

#### 🎓 适合场景
- 场景1
- 场景2
...

---
```

#### 6.2 更新readme_simple.md
**修改内容**:
1. 更新项目总数: `共XX个` → `共XX+1个`
2. 在末尾添加项目简介

**格式**:
```markdown
### 0XX - 项目名称 🔥最新
项目简介（技术栈 + 核心功能）

---
```

#### 6.3 使用edit_files工具
```
同时修改两个文件：
1. 项目总数 +1
2. 插入新项目详情
```

---

## 📝 关键注意事项

### 代码规范
1. ❌ 不写任何注释
2. ❌ 不写README（项目内）
3. ❌ 不验证编译
4. ✅ 遵循rule.md规则

### 文件创建顺序
1. 后端: 基础架构 → SQL → Entity → Mapper → Service → Controller
2. 前端: 配置文件 → 路由/API/Store → 页面组件

### TODO管理
- 创建: `create_todo_list` (3个以上步骤)
- 完成: `mark_todo_as_done`
- 更新: `add_todos` / `remove_todos`

### Plan管理
- 创建: `create_plan` (需要规划时)
- 等待用户确认后才执行
- 执行中根据实际情况更新: `edit_plans`

### 批量操作
- 使用工具批量创建文件，提高效率
- 同类文件可以在一个tool call中创建多个

---

## 🎯 质量检查清单

### 后端
- [ ] pom.xml依赖完整
- [ ] application.yml配置正确
- [ ] 所有Entity有@TableName
- [ ] 所有Mapper继承BaseMapper
- [ ] Service实现业务逻辑
- [ ] Controller返回Result
- [ ] SQL脚本包含测试数据

### 前端
- [ ] package.json依赖版本正确
- [ ] vite.config.js配置proxy
- [ ] 路由配置完整(含权限守卫)
- [ ] API封装(拦截器/错误处理)
- [ ] 所有页面功能完整
- [ ] 登录/布局/首页必有

### README
- [ ] 项目总数已更新
- [ ] readme.md详情完整
- [ ] readme_simple.md简介清晰
- [ ] 格式与其他项目一致

---

## 🚀 快速启动模板

**用户提供**:
```
标题: XXX项目
后端目录: 已创建
开始编写PRD → Plan → 实现
```

**响应流程**:
```
1. 读取rule.md
2. 创建PRD.md
3. 创建Plan (等待确认)
4. 创建TODO list
5. 实现后端 (标记TODO)
6. 实现前端 (标记TODO)
7. 更新README
8. 总结完成
```

---

## 📂 标准项目结构

```
XXX-backend/
├── sql/
│   └── init.sql
├── src/main/java/com/xxx/
│   ├── XxxApplication.java
│   ├── common/
│   │   ├── Result.java
│   │   ├── BusinessException.java
│   │   └── GlobalExceptionHandler.java
│   ├── config/
│   │   ├── JwtInterceptor.java
│   │   ├── WebMvcConfig.java
│   │   ├── MybatisPlusConfig.java
│   │   └── RedisConfig.java
│   ├── utils/
│   │   └── JwtUtils.java
│   ├── entity/
│   ├── mapper/
│   ├── service/
│   ├── controller/
│   └── dto/
└── src/main/resources/
    └── application.yml

XXX-frontend/
├── package.json
├── vite.config.js
├── index.html
└── src/
    ├── main.js
    ├── App.vue
    ├── router/
    │   └── index.js
    ├── api/
    │   ├── request.js
    │   └── index.js
    ├── store/
    │   └── user.js
    └── views/
        ├── Login.vue
        ├── Layout.vue
        ├── Dashboard.vue
        └── xxx/
            └── index.vue
```

---

## 🔧 常用代码片段

### Result统一返回
```java
public class Result<T> {
    private Integer code;
    private String message;
    private T data;
    
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }
    
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }
}
```

### JWT工具类
```java
public class JwtUtils {
    private static final String SECRET = "your-secret-key";
    private static final long EXPIRATION = 24 * 60 * 60 * 1000L;
    
    public static String generateToken(String userId) {
        return Jwts.builder()
            .setSubject(userId)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact();
    }
    
    public static String getUserIdFromToken(String token) {
        return Jwts.parser()
            .setSigningKey(SECRET)
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }
}
```

### Axios拦截器
```javascript
request.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = token
  }
  return config
})

request.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code !== 200) {
      ElMessage.error(res.message || '请求失败')
      if (res.code === 401) {
        localStorage.removeItem('token')
        router.push('/login')
      }
      return Promise.reject(res)
    }
    return res
  },
  error => {
    ElMessage.error(error.message || '网络错误')
    return Promise.reject(error)
  }
)
```

---

## 📖 总结

**一句话**: 读规则 → 写PRD → 订计划 → 后端开发 → 前端开发 → 更新README

**核心原则**:
1. 严格遵守rule.md
2. 先规划再实现
3. 按顺序完成
4. 标记TODO进度
5. 保持代码一致性
