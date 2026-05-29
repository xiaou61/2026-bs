---
title: 按技术栈筛选
description: 按后端/前端技术栈筛选项目，找到使用指定技术的项目
---

# 按技术栈筛选

> 所有项目均基于 **Spring Boot + Vue 3** 技术栈，但在具体框架版本、ORM 方案、UI 组件库等方面存在差异。

## 技术栈差异要点

| 维度 | 常见选项 | 说明 |
|------|----------|------|
| **Spring Boot 版本** | 2.7.x / 3.x | 2.x 稳定成熟，3.x 更现代但要求 JDK 17+ |
| **ORM 方案** | MyBatis / MyBatis-Plus | MyBatis 手写 SQL 灵活，MyBatis-Plus 代码量少 |
| **认证方案** | JWT + Redis / Session | JWT 前后端分离友好，Session 传统简单 |
| **前端框架** | Vue 3 + Element Plus | 统一使用 Vue 3，UI 库基本为 Element Plus |
| **状态管理** | Pinia / Vuex | Pinia 是 Vue 3 推荐方案 |
| **图表库** | ECharts | 用于首页统计看板和数据可视化 |

## 版本选择建议

- **Spring Boot 2.7.x**：如果你使用 JDK 8 或 JDK 11，只能选择 2.x 版本
- **Spring Boot 3.x**：需要 JDK 17+，但支持更多现代特性
- **MyBatis vs MyBatis-Plus**：如果不想手写 SQL 选 MyBatis-Plus；如果想更灵活控制 SQL 选 MyBatis

---

## 技术栈分布与筛选

下方展示项目技术栈分布统计，支持按技术栈组合筛选项目。

<ClientOnly>
  <TechFilter />
</ClientOnly>
