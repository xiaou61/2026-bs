DROP DATABASE IF EXISTS devops_release_106;
CREATE DATABASE devops_release_106 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE devops_release_106;

CREATE TABLE sys_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) COMMENT '账号',
  password VARCHAR(80) COMMENT '密码',
  nickname VARCHAR(50) COMMENT '姓名',
  role VARCHAR(30) COMMENT '角色',
  team_name VARCHAR(80) COMMENT '团队',
  phone VARCHAR(30) COMMENT '电话',
  email VARCHAR(100) COMMENT '邮箱',
  status TINYINT COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE devops_environment (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  env_name VARCHAR(80) COMMENT '环境名称',
  env_code VARCHAR(40) COMMENT '环境编码',
  cluster_name VARCHAR(80) COMMENT '集群名称',
  namespace_name VARCHAR(80) COMMENT '命名空间',
  owner_name VARCHAR(50) COMMENT '负责人',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE application_service (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  service_name VARCHAR(100) COMMENT '服务名称',
  service_code VARCHAR(60) COMMENT '服务编码',
  repo_url VARCHAR(255) COMMENT '仓库地址',
  language VARCHAR(40) COMMENT '技术语言',
  owner_name VARCHAR(50) COMMENT '负责人',
  deploy_type VARCHAR(40) COMMENT '部署类型',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE deploy_pipeline (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  service_id BIGINT COMMENT '服务ID',
  pipeline_name VARCHAR(100) COMMENT '流水线名称',
  branch_name VARCHAR(80) COMMENT '默认分支',
  build_command VARCHAR(180) COMMENT '构建命令',
  image_repo VARCHAR(180) COMMENT '镜像仓库',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE release_plan (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  plan_no VARCHAR(60) COMMENT '计划编号',
  plan_title VARCHAR(120) COMMENT '计划标题',
  service_id BIGINT COMMENT '服务ID',
  env_id BIGINT COMMENT '环境ID',
  release_window VARCHAR(80) COMMENT '发布窗口',
  risk_level VARCHAR(30) COMMENT '风险等级',
  status VARCHAR(30) COMMENT '状态',
  description VARCHAR(255) COMMENT '说明',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE release_order (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_no VARCHAR(60) COMMENT '发布单号',
  plan_id BIGINT COMMENT '计划ID',
  service_id BIGINT COMMENT '服务ID',
  version_no VARCHAR(60) COMMENT '版本号',
  applicant VARCHAR(50) COMMENT '申请人',
  release_scope VARCHAR(120) COMMENT '发布范围',
  status VARCHAR(30) COMMENT '状态',
  scheduled_at VARCHAR(40) COMMENT '计划时间',
  summary VARCHAR(255) COMMENT '摘要',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE approval_flow (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  flow_name VARCHAR(100) COMMENT '流程名称',
  business_type VARCHAR(60) COMMENT '业务类型',
  node_name VARCHAR(80) COMMENT '节点名称',
  approver_role VARCHAR(40) COMMENT '审批角色',
  sort_no INT COMMENT '排序',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE approval_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_id BIGINT COMMENT '发布单ID',
  node_name VARCHAR(80) COMMENT '节点名称',
  applicant VARCHAR(50) COMMENT '申请人',
  approver VARCHAR(50) COMMENT '审批人',
  decision VARCHAR(30) COMMENT '审批结果',
  opinion VARCHAR(255) COMMENT '审批意见',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE version_artifact (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  service_id BIGINT COMMENT '服务ID',
  version_no VARCHAR(60) COMMENT '版本号',
  artifact_name VARCHAR(120) COMMENT '制品名称',
  image_tag VARCHAR(120) COMMENT '镜像标签',
  checksum VARCHAR(120) COMMENT '校验值',
  build_time VARCHAR(40) COMMENT '构建时间',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE deploy_task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_id BIGINT COMMENT '发布单ID',
  service_id BIGINT COMMENT '服务ID',
  env_id BIGINT COMMENT '环境ID',
  task_name VARCHAR(120) COMMENT '任务名称',
  executor VARCHAR(50) COMMENT '执行人',
  status VARCHAR(30) COMMENT '状态',
  started_at VARCHAR(40) COMMENT '开始时间',
  finished_at VARCHAR(40) COMMENT '结束时间',
  log_summary VARCHAR(255) COMMENT '日志摘要',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE rollback_plan (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  plan_id BIGINT COMMENT '计划ID',
  service_id BIGINT COMMENT '服务ID',
  rollback_version VARCHAR(60) COMMENT '回滚版本',
  strategy_name VARCHAR(80) COMMENT '策略名称',
  trigger_condition VARCHAR(255) COMMENT '触发条件',
  owner_name VARCHAR(50) COMMENT '负责人',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE rollback_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_id BIGINT COMMENT '发布单ID',
  plan_id BIGINT COMMENT '预案ID',
  rollback_no VARCHAR(60) COMMENT '回滚编号',
  operator_name VARCHAR(50) COMMENT '操作人',
  reason VARCHAR(255) COMMENT '回滚原因',
  status VARCHAR(30) COMMENT '状态',
  started_at VARCHAR(40) COMMENT '开始时间',
  finished_at VARCHAR(40) COMMENT '结束时间',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE change_checklist (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_id BIGINT COMMENT '发布单ID',
  item_name VARCHAR(120) COMMENT '检查项',
  check_owner VARCHAR(50) COMMENT '检查人',
  required_flag TINYINT COMMENT '必检',
  check_result VARCHAR(40) COMMENT '检查结果',
  evidence_url VARCHAR(255) COMMENT '证据地址',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE operation_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) COMMENT '操作人',
  action VARCHAR(80) COMMENT '操作动作',
  target_type VARCHAR(60) COMMENT '对象类型',
  target_id BIGINT COMMENT '对象ID',
  detail VARCHAR(255) COMMENT '详情',
  ip_address VARCHAR(40) COMMENT 'IP地址',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO sys_user (username, password, nickname, role, team_name, phone, email, status, created_time, updated_time) VALUES
('admin', '123456', '系统管理员', 'ADMIN', '平台管理组', '13800000601', 'admin@devops.com', 1, NOW(), NOW()),
('release', '123456', '发布经理', 'RELEASE_MANAGER', '发布管理组', '13800000602', 'release@devops.com', 1, NOW(), NOW()),
('ops', '123456', '运维工程师', 'OPS', '运维组', '13800000603', 'ops@devops.com', 1, NOW(), NOW()),
('auditor', '123456', '审计员', 'AUDITOR', '质量审计组', '13800000604', 'auditor@devops.com', 1, NOW(), NOW());

INSERT INTO devops_environment (env_name, env_code, cluster_name, namespace_name, owner_name, status, created_time, updated_time) VALUES
('生产环境', 'prod', 'k8s-prod', 'business', '赵云', 'ACTIVE', NOW(), NOW()),
('预发环境', 'stage', 'k8s-stage', 'business-stage', '钱涛', 'ACTIVE', NOW(), NOW());

INSERT INTO application_service (service_name, service_code, repo_url, language, owner_name, deploy_type, status, created_time, updated_time) VALUES
('订单服务', 'order-service', 'https://git.example.com/order.git', 'Java', '李雷', 'Kubernetes', 'ACTIVE', NOW(), NOW()),
('会员服务', 'member-service', 'https://git.example.com/member.git', 'Java', '韩梅', 'Kubernetes', 'ACTIVE', NOW(), NOW());

INSERT INTO deploy_pipeline (service_id, pipeline_name, branch_name, build_command, image_repo, status, created_time, updated_time) VALUES
(1, '订单服务主流水线', 'main', 'mvn clean package', 'registry.example.com/order', 'ACTIVE', NOW(), NOW()),
(2, '会员服务主流水线', 'main', 'mvn clean package', 'registry.example.com/member', 'ACTIVE', NOW(), NOW());

INSERT INTO release_plan (plan_no, plan_title, service_id, env_id, release_window, risk_level, status, description, created_time, updated_time) VALUES
('PLAN-106-001', '五一版本发布计划', 1, 1, '2026-05-01 22:00-23:00', 'HIGH', 'DRAFT', '订单服务优惠券能力上线', NOW(), NOW()),
('PLAN-106-002', '会员中心灰度发布', 2, 2, '2026-05-03 20:00-21:00', 'MEDIUM', 'PUBLISHED', '会员等级权益调整', NOW(), NOW());

INSERT INTO release_order (order_no, plan_id, service_id, version_no, applicant, release_scope, status, scheduled_at, summary, created_time, updated_time) VALUES
('REL-106-001', 1, 1, 'v2.4.0', 'release', '订单服务全量发布', 'SUBMITTED', '2026-05-01 22:00', '待审批后执行', NOW(), NOW()),
('REL-106-002', 2, 2, 'v1.8.1', 'developer', '会员服务预发发布', 'APPROVED', '2026-05-03 20:00', '预发环境灰度', NOW(), NOW());

INSERT INTO approval_flow (flow_name, business_type, node_name, approver_role, sort_no, status, created_time, updated_time) VALUES
('高风险生产发布审批', 'RELEASE', '技术负责人', 'RELEASE_MANAGER', 1, 'ACTIVE', NOW(), NOW()),
('高风险生产发布审批', 'RELEASE', '审计确认', 'AUDITOR', 2, 'ACTIVE', NOW(), NOW());

INSERT INTO approval_record (order_id, node_name, applicant, approver, decision, opinion, status, created_time, updated_time) VALUES
(1, '技术负责人', 'release', 'ops', 'PENDING', '等待技术负责人确认', 'PENDING', NOW(), NOW()),
(2, '审计确认', 'developer', 'auditor', 'APPROVED', '变更材料完整', 'APPROVED', NOW(), NOW());

INSERT INTO version_artifact (service_id, version_no, artifact_name, image_tag, checksum, build_time, status, created_time, updated_time) VALUES
(1, 'v2.4.0', 'order-service-2.4.0.jar', 'order:v2.4.0', 'sha256-order', '2026-04-30 18:00', 'ACTIVE', NOW(), NOW()),
(2, 'v1.8.1', 'member-service-1.8.1.jar', 'member:v1.8.1', 'sha256-member', '2026-05-02 16:00', 'ACTIVE', NOW(), NOW());

INSERT INTO deploy_task (order_id, service_id, env_id, task_name, executor, status, started_at, finished_at, log_summary, created_time, updated_time) VALUES
(1, 1, 1, '订单服务生产部署', 'ops', 'PENDING', '', '', '等待审批完成', NOW(), NOW()),
(2, 2, 2, '会员服务预发部署', 'ops', 'FINISHED', '2026-05-03 20:00', '2026-05-03 20:12', '部署成功', NOW(), NOW());

INSERT INTO rollback_plan (plan_id, service_id, rollback_version, strategy_name, trigger_condition, owner_name, status, created_time, updated_time) VALUES
(1, 1, 'v2.3.9', '蓝绿回滚', '错误率超过5%或核心链路不可用', 'ops', 'ACTIVE', NOW(), NOW()),
(2, 2, 'v1.8.0', '镜像回滚', '会员登录失败率超过3%', 'ops', 'ACTIVE', NOW(), NOW());

INSERT INTO rollback_record (order_id, plan_id, rollback_no, operator_name, reason, status, started_at, finished_at, created_time, updated_time) VALUES
(1, 1, 'RB-106-001', 'ops', '演练记录', 'FINISHED', '2026-04-29 20:00', '2026-04-29 20:08', NOW(), NOW()),
(2, 2, 'RB-106-002', 'ops', '预发验证回滚', 'DRAFT', '', '', NOW(), NOW());

INSERT INTO change_checklist (order_id, item_name, check_owner, required_flag, check_result, evidence_url, status, created_time, updated_time) VALUES
(1, '数据库变更确认', 'ops', 1, 'PASSED', 'http://evidence/db-change', 'ACTIVE', NOW(), NOW()),
(1, '监控告警配置确认', 'auditor', 1, 'PENDING', 'http://evidence/monitor', 'ACTIVE', NOW(), NOW());

INSERT INTO operation_log (username, action, target_type, target_id, detail, ip_address, created_time, updated_time) VALUES
('admin', '登录系统', 'sys_user', 1, '管理员登录平台', '127.0.0.1', NOW(), NOW()),
('release', '提交发布单', 'release_order', 1, '提交订单服务发布单', '127.0.0.1', NOW(), NOW());
