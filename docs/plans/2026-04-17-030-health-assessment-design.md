# 030 健康评估与预警模块设计

## 目标

在不扩张过多范围的前提下，为 `030` 居民智能健康管理平台补齐一个可运行、可测试、可演示的健康评估与预警闭环。

## 设计范围

1. 基于现有健康数据自动生成健康评估结果
2. 对异常血压、血糖、心率、体温和体重生成预警消息
3. 患者可查看最新评估、历史评估、预警列表，并可标记预警已读
4. 患者在新增或删除健康数据后自动刷新评估结果

## 设计取舍

- 采用规则驱动而不是复杂算法模型：
  - 现有工程只有基础实体和仓库，没有真实 AI 或医学模型基础
  - 规则评估更容易在 JDK 17 + 当前代码结构下快速落地并稳定测试
- 不强行计算 BMI：
  - 当前档案缺少身高字段
  - 统一返回“缺少身高数据，暂无法计算 BMI”，避免伪造结果
- 先只面向患者开放：
  - 当前项目没有完整的医生患者管理关系
  - 先把患者自评估闭环做实，再考虑医生端查看扩展

## 后端方案

- 新增 `HealthAssessmentService`
  - 读取患者最近的各类健康数据
  - 归一化数据类型
  - 计算健康评分、风险因素、建议和分项等级
  - 自动写入 `HealthAssessment`
  - 自动去重生成 `HealthAlert`
- 新增 `HealthAssessmentController`
  - `POST /api/health-assessment/generate`
  - `GET /api/health-assessment/latest`
  - `GET /api/health-assessment/history`
  - `GET /api/health-assessment/alerts`
  - `GET /api/health-assessment/alerts/unread-count`
  - `PUT /api/health-assessment/alerts/{id}/read`
- 修改 `HealthDataService`
  - 新增健康数据后自动触发评估
  - 删除健康数据后自动重新评估

## 前端方案

- 新增患者页 `patient/assessment`
  - 最新评估结果
  - 历史评估列表
  - 预警消息列表
  - 手动重新评估按钮
- 在患者首页增加：
  - 最新健康评分
  - 未读预警数
- 在侧边菜单新增“评估预警”入口

## 验证方案

1. 后端 `mvn test`
2. 前端 `npm run build`
3. 启动后端并完成：
   - 患者登录
   - 新增异常健康数据
   - 查询最新评估
   - 查询未读预警数
4. 启动前端并确认登录页可访问

## 预期剩余缺口

1. 仍未接入真正的医学模型或个性化算法
2. 仍未实现邮件、短信、App 推送等通知能力
3. 仍未形成统计报表与医生端查看患者评估能力
