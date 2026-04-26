# 092 蓝天幼儿园管理系统设计

## 目标
基于现有 087 课程管理模板，重构一个适合毕业设计展示的蓝天幼儿园管理系统，突出园区资料、幼儿成长、家园协同和日常运营四条主线。

## 设计结论
- 技术路线使用 Spring Boot 2.7.18 + MyBatis + PageHelper + Redis + Vue3
- 角色固定为管理员、教师、家长
- 业务主体映射为园区、年级、班级、幼儿档案、课程活动、活动安排、每周食谱、入园考勤、健康晨检、家园反馈、公告和统计看板
- 保留模板中成熟的登录、分页、表格、弹窗和权限守卫结构，优先做高复用改造

## 数据结构映射
- `department_info` -> `campus_info`
- `major_info` -> `grade_info`
- `academic_term` -> `school_term`
- `course_info` -> `activity_info`
- `course_schedule` -> `activity_schedule`
- `course_selection` -> `child_profile`
- `course_resource` -> `weekly_recipe`
- `score_record` -> `health_record`
- `course_evaluation` -> `parent_feedback`
- `operation_log` -> `pickup_record`

## 页面设计
- 管理员侧重基础资料、活动、公告和统计
- 教师侧重幼儿档案、活动安排、食谱、考勤、晨检和反馈处理
- 家长侧重孩子信息查看、食谱查看、考勤晨检查看和反馈提交

## 实施策略
- 先重写文档、SQL 和配置，统一项目口径
- 再做包名、类名和接口名替换
- 最后逐页调整前端文案、字段和权限菜单
