# 快速启动指南

## 一体化项目说明

本项目已整合前后端为一体化部署，所有前端文件位于 `src/main/resources/static/` 目录下。

## 快速启动步骤

### 1. 数据库准备
```bash
# 登录MySQL
mysql -u root -p

# 执行数据库脚本
source src/main/resources/sql/schema.sql
```

### 2. 配置文件
修改 `src/main/resources/application.yml`：
```yaml
spring:
  datasource:
    username: root
    password: 你的密码
```

### 3. 启动项目
```bash
# 方式1：使用Maven
mvn spring-boot:run

# 方式2：使用IDE
# 直接运行 com.xiaou.SmartRecipeApplication
```

### 4. 访问系统
- 系统首页：http://localhost:8008
- 用户端：http://localhost:8008/pages/user/index.html  
- 管理端：http://localhost:8008/pages/admin/index.html

## 测试账户

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | 123456 | 管理员 |
| user1 | 123456 | 普通用户 |
| user2 | 123456 | 普通用户 |

## 文件说明

### 后端代码（src/main/java）
- `controller/` - REST API接口
- `service/` - 业务逻辑
- `mapper/` - 数据访问
- `entity/` - 实体类

### 前端代码（src/main/resources/static）
- `index.html` - 登录页
- `css/` - 样式文件
- `js/` - JavaScript文件
- `pages/user/` - 用户端页面
- `pages/admin/` - 管理端页面

## 常见问题

### Q1: 启动失败怎么办？
A: 检查MySQL是否运行，数据库配置是否正确。

### Q2: 登录提示用户不存在？
A: 确认已执行数据库脚本，检查user表是否有数据。

### Q3: 页面显示不正常？
A: 清除浏览器缓存，检查控制台错误信息。

### Q4: 如何添加新功能？
A: 
- 后端：在controller/service/mapper中添加代码
- 前端：在static目录下添加HTML/CSS/JS文件

## 项目特点

✅ **一体化部署** - 前后端集成，启动一次即可
✅ **智能推荐** - 多维度算法推荐菜谱
✅ **营养分析** - 自动计算营养成分
✅ **数据可视化** - ECharts图表展示
✅ **权限管理** - JWT认证，角色控制

## 下一步

项目运行成功后，您可以：
1. 使用测试账户登录体验功能
2. 查看菜谱列表和详情
3. 添加食材到库存
4. 体验智能推荐功能
5. 进行做菜打卡

## 技术支持

- 查看 README.md 了解详细说明
- 查看 ACCOUNTS.md 了解测试账户
- 查看代码注释了解实现细节

祝您使用愉快！🎉

