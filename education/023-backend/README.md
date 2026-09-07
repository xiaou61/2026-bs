# AI智能学习助手与个性化教育平台

## 📋 项目介绍

AI智能学习助手与个性化教育平台是一个基于机器学习算法的智能教育系统，通过分析学习者行为数据，提供个性化的学习推荐、智能问答、学习路径规划等功能，帮助学习者提升学习效率和效果。

## 🛠️ 技术栈

### 后端架构
- **核心框架**：Spring Boot 3.2.0
- **数据访问**：MyBatis-Plus 3.5.5
- **数据库**：MySQL 8.0
- **缓存**：Redis 6.0+
- **认证**：JWT 0.12.3
- **模板引擎**：Thymeleaf 3.1.0
- **工具库**：Hutool 5.8.23
- **机器学习**：Apache Commons Math 3.6.1

### 前端技术
- **基础框架**：jQuery 3.7.1
- **UI组件**：Bootstrap 5.3.0
- **图表库**：ECharts 5.4.3
- **图标库**：Font Awesome 6.0

## 🎯 核心功能

### 1. AI智能推荐
- **协同过滤算法**：基于用户行为相似性推荐
- **内容推荐算法**：TF-IDF + 余弦相似度
- **冷启动处理**：新用户个性化推荐
- **实时更新**：学习行为实时反馈优化

### 2. 智能问答系统
- **自然语言处理**：jieba分词 + Word2Vec
- **知识图谱**：语义搜索和推理
- **多轮对话**：上下文理解和维护
- **学习引导**：从问答到系统化学习

### 3. 个性化学习路径
- **图算法**：Dijkstra最短路径规划
- **知识依赖**：前置关系自动分析
- **能力评估**：个人学习能力建模
- **动态调整**：学习进度实时优化

### 4. 学习数据分析
- **行为追踪**：详细学习轨迹记录
- **效果评估**：多维度学习效果分析
- **薄弱诊断**：知识盲点智能识别
- **可视化展示**：ECharts图表展示

## 🗄️ 数据库设计

系统采用9张核心数据表：

1. **user** - 用户信息和学习偏好
2. **course** - 课程信息和元数据
3. **knowledge_point** - 知识点层次结构
4. **learning_record** - 学习行为详细记录
5. **recommendation** - AI推荐记录和效果追踪
6. **knowledge_relation** - 知识图谱关系
7. **qa_record** - 问答交互记录
8. **learning_path** - 个性化学习路径
9. **user_behavior** - 用户行为分析

## 🚀 快速开始

### 1. 环境准备
```bash
- JDK 17+
- MySQL 8.0+
- Redis 6.0+
- Maven 3.6+
```

### 2. 数据库初始化
```sql
-- 执行初始化脚本
mysql -u root -p < sql/init.sql
```

### 3. 配置修改
修改 `src/main/resources/application.yml` 中的数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ai_learning_db
    username: root
    password: your_password
  
  redis:
    host: localhost
    port: 6379
```

### 4. 启动应用
```bash
mvn spring-boot:run
```

### 5. 访问系统
- 应用地址：http://localhost:8080
- 默认账号：admin / 123456

## 📊 AI算法特色

### 协同过滤推荐
```java
// 基于用户相似度的协同过滤
public List<Course> recommendBySimilarity(Long userId, int topN) {
    // 计算用户相似度矩阵
    Map<Long, Double> similarities = calculateUserSimilarity(userId);
    
    // 基于相似用户推荐
    return generateRecommendations(similarities, topN);
}
```

### 学习路径优化
```java
// Dijkstra算法优化学习路径
public List<KnowledgePoint> optimizeLearningPath(
    Long userId, Long targetKnowledgeId) {
    
    Graph knowledgeGraph = buildKnowledgeGraph();
    UserProfile profile = getUserProfile(userId);
    
    // 根据用户能力调整边权重
    adjustWeightsByAbility(knowledgeGraph, profile);
    
    return dijkstraShortestPath(knowledgeGraph, targetKnowledgeId);
}
```

### 知识掌握度预测
```java
// 基于遗忘曲线的掌握度预测
public double predictMasteryLevel(Long userId, Long knowledgeId) {
    List<LearningRecord> records = getLearningRecords(userId, knowledgeId);
    
    return applyForgettingCurve(records, System.currentTimeMillis());
}
```

## 🎨 界面展示

### 学习仪表盘
- 个性化推荐内容展示
- 学习进度可视化
- AI智能学习建议
- 实时数据统计

### 智能问答界面
- ChatGPT风格对话界面
- 知识点关联展示
- 学习路径引导
- 历史对话记录

### 学习路径可视化
- 时间轴展示学习步骤
- 知识点依赖关系图
- 进度跟踪和调整
- 成就里程碑

## 📈 性能指标

- **推荐准确率**：> 70%
- **问答相关性**：> 80%
- **路径完成率**：> 60%
- **系统响应时间**：< 2秒
- **并发支持**：500+ 用户

## 🔒 安全特性

- **JWT认证**：无状态安全认证
- **密码加密**：MD5加密存储
- **权限控制**：拦截器统一鉴权
- **数据脱敏**：敏感信息保护

## 📝 开发规范

### 代码结构
```
src/main/java/com/xiaou/ailearning/
├── controller/     # 控制器层
├── service/        # 业务逻辑层
├── mapper/         # 数据访问层
├── entity/         # 实体类
├── config/         # 配置类
├── utils/          # 工具类
├── interceptor/    # 拦截器
└── exception/      # 异常处理
```

### API接口规范
- **RESTful设计**：标准HTTP方法
- **统一响应格式**：Result<T>封装
- **错误处理**：全局异常处理器
- **参数验证**：Bean Validation

## 🧪 测试账户

| 用户名 | 密码 | 角色 | 说明 |
|--------|------|------|------|
| admin | 123456 | 管理员 | 系统管理员账户 |
| student1 | 123456 | 学生 | 视觉型学习者 |
| student2 | 123456 | 学生 | 听觉型学习者 |
| student3 | 123456 | 学生 | 动觉型学习者 |

## 🤝 贡献指南

1. Fork 本仓库
2. 创建特性分支 `git checkout -b feature/AmazingFeature`
3. 提交更改 `git commit -m 'Add some AmazingFeature'`
4. 推送到分支 `git push origin feature/AmazingFeature`
5. 打开 Pull Request

## 📄 License

本项目仅供学习交流使用，请勿用于商业用途。

## 📞 联系方式

- **项目作者**：毕业设计开发团队
- **技术支持**：QQ 3153566913
- **项目地址**：https://github.com/xiaou-team/ai-learning-assistant

---

**祝您学习进步！🎓**