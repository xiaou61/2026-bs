DROP DATABASE IF EXISTS cloud_monitor_107;
CREATE DATABASE cloud_monitor_107 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE cloud_monitor_107;

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

CREATE TABLE cloud_region (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  region_name VARCHAR(80) COMMENT '区域名称',
  region_code VARCHAR(40) COMMENT '区域编码',
  provider_name VARCHAR(40) COMMENT '云厂商',
  location_name VARCHAR(80) COMMENT '地理位置',
  manager_name VARCHAR(50) COMMENT '负责人',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE server_asset (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  server_name VARCHAR(100) COMMENT '主机名称',
  instance_id VARCHAR(80) COMMENT '实例ID',
  region_id BIGINT COMMENT '区域ID',
  public_ip VARCHAR(40) COMMENT '公网IP',
  private_ip VARCHAR(40) COMMENT '私网IP',
  os_name VARCHAR(80) COMMENT '操作系统',
  cpu_cores INT COMMENT 'CPU核数',
  memory_gb INT COMMENT '内存GB',
  owner_name VARCHAR(50) COMMENT '负责人',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE resource_group (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  group_name VARCHAR(80) COMMENT '分组名称',
  group_code VARCHAR(50) COMMENT '分组编码',
  owner_name VARCHAR(50) COMMENT '负责人',
  description VARCHAR(255) COMMENT '说明',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE metric_definition (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  metric_name VARCHAR(100) COMMENT '指标名称',
  metric_code VARCHAR(80) COMMENT '指标编码',
  unit_name VARCHAR(40) COMMENT '单位',
  collect_interval INT COMMENT '采集间隔',
  description VARCHAR(255) COMMENT '说明',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE metric_sample (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  server_id BIGINT COMMENT '主机ID',
  metric_code VARCHAR(80) COMMENT '指标编码',
  metric_value VARCHAR(60) COMMENT '指标值',
  sample_time VARCHAR(40) COMMENT '采样时间',
  health_level VARCHAR(30) COMMENT '健康等级',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE alert_rule (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  rule_name VARCHAR(100) COMMENT '规则名称',
  metric_code VARCHAR(80) COMMENT '指标编码',
  operator_type VARCHAR(20) COMMENT '比较符',
  threshold_value VARCHAR(60) COMMENT '阈值',
  duration_minute INT COMMENT '持续分钟',
  severity VARCHAR(30) COMMENT '级别',
  notify_group VARCHAR(80) COMMENT '通知组',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE alert_event (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  event_no VARCHAR(60) COMMENT '事件编号',
  server_id BIGINT COMMENT '主机ID',
  rule_id BIGINT COMMENT '规则ID',
  metric_code VARCHAR(80) COMMENT '指标编码',
  current_value VARCHAR(60) COMMENT '当前值',
  severity VARCHAR(30) COMMENT '级别',
  owner_name VARCHAR(50) COMMENT '负责人',
  status VARCHAR(30) COMMENT '状态',
  triggered_at VARCHAR(40) COMMENT '触发时间',
  resolved_at VARCHAR(40) COMMENT '恢复时间',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE alert_notify (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  event_id BIGINT COMMENT '事件ID',
  channel_name VARCHAR(40) COMMENT '通知渠道',
  receiver VARCHAR(100) COMMENT '接收人',
  send_status VARCHAR(30) COMMENT '发送状态',
  send_time VARCHAR(40) COMMENT '发送时间',
  message_text VARCHAR(255) COMMENT '消息内容',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE incident_ticket (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  ticket_no VARCHAR(60) COMMENT '工单编号',
  event_id BIGINT COMMENT '事件ID',
  server_id BIGINT COMMENT '主机ID',
  title VARCHAR(120) COMMENT '工单标题',
  assignee VARCHAR(50) COMMENT '处理人',
  priority VARCHAR(30) COMMENT '优先级',
  status VARCHAR(30) COMMENT '状态',
  solution VARCHAR(255) COMMENT '解决方案',
  closed_at VARCHAR(40) COMMENT '关闭时间',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE maintenance_window (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  window_name VARCHAR(100) COMMENT '窗口名称',
  server_id BIGINT COMMENT '主机ID',
  start_time VARCHAR(40) COMMENT '开始时间',
  end_time VARCHAR(40) COMMENT '结束时间',
  owner_name VARCHAR(50) COMMENT '负责人',
  reason VARCHAR(255) COMMENT '维护原因',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE capacity_plan (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  server_id BIGINT COMMENT '主机ID',
  plan_name VARCHAR(100) COMMENT '规划名称',
  cpu_usage VARCHAR(40) COMMENT 'CPU使用率',
  memory_usage VARCHAR(40) COMMENT '内存使用率',
  disk_usage VARCHAR(40) COMMENT '磁盘使用率',
  suggestion VARCHAR(255) COMMENT '优化建议',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE dashboard_widget (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  widget_name VARCHAR(100) COMMENT '组件名称',
  widget_type VARCHAR(40) COMMENT '组件类型',
  metric_code VARCHAR(80) COMMENT '指标编码',
  refresh_second INT COMMENT '刷新秒数',
  sort_no INT COMMENT '排序',
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
('admin', '123456', '系统管理员', 'ADMIN', '平台管理组', '13800000701', 'admin@cloud.com', 1, NOW(), NOW()),
('ops', '123456', '运维工程师', 'OPS', '运维组', '13800000702', 'ops@cloud.com', 1, NOW(), NOW()),
('sre', '123456', 'SRE工程师', 'SRE', '可靠性组', '13800000703', 'sre@cloud.com', 1, NOW(), NOW()),
('manager', '123456', '运维主管', 'MANAGER', '运维管理组', '13800000704', 'manager@cloud.com', 1, NOW(), NOW());

INSERT INTO cloud_region (region_name, region_code, provider_name, location_name, manager_name, status, created_time, updated_time) VALUES
('华东一区', 'cn-east-1', '阿里云', '上海', '赵云', 'ACTIVE', NOW(), NOW()),
('华北一区', 'cn-north-1', '腾讯云', '北京', '钱涛', 'ACTIVE', NOW(), NOW());

INSERT INTO server_asset (server_name, instance_id, region_id, public_ip, private_ip, os_name, cpu_cores, memory_gb, owner_name, status, created_time, updated_time) VALUES
('订单生产01', 'i-order-prod-01', 1, '47.100.10.1', '10.0.1.11', 'CentOS 7', 8, 32, 'ops', 'ONLINE', NOW(), NOW()),
('会员预发01', 'i-member-stage-01', 2, '49.233.12.8', '10.0.2.21', 'Ubuntu 22.04', 4, 16, 'sre', 'ONLINE', NOW(), NOW());

INSERT INTO resource_group (group_name, group_code, owner_name, description, status, created_time, updated_time) VALUES
('核心业务组', 'core-business', 'ops', '订单、支付、会员核心服务器', 'ACTIVE', NOW(), NOW()),
('数据服务组', 'data-service', 'sre', '报表和数据同步服务器', 'ACTIVE', NOW(), NOW());

INSERT INTO metric_definition (metric_name, metric_code, unit_name, collect_interval, description, status, created_time, updated_time) VALUES
('CPU使用率', 'cpu_usage', '%', 60, 'CPU平均使用率', 'ACTIVE', NOW(), NOW()),
('内存使用率', 'memory_usage', '%', 60, '内存平均使用率', 'ACTIVE', NOW(), NOW());

INSERT INTO metric_sample (server_id, metric_code, metric_value, sample_time, health_level, status, created_time, updated_time) VALUES
(1, 'cpu_usage', '67.5', '2026-05-04 10:00', 'NORMAL', 'ACTIVE', NOW(), NOW()),
(1, 'memory_usage', '82.1', '2026-05-04 10:00', 'WARNING', 'ACTIVE', NOW(), NOW());

INSERT INTO alert_rule (rule_name, metric_code, operator_type, threshold_value, duration_minute, severity, notify_group, status, created_time, updated_time) VALUES
('CPU高负载', 'cpu_usage', '>', '85', 5, 'HIGH', '运维值班组', 'ACTIVE', NOW(), NOW()),
('内存高水位', 'memory_usage', '>', '90', 3, 'MEDIUM', 'SRE组', 'ACTIVE', NOW(), NOW());

INSERT INTO alert_event (event_no, server_id, rule_id, metric_code, current_value, severity, owner_name, status, triggered_at, resolved_at, created_time, updated_time) VALUES
('ALERT-107-001', 1, 1, 'cpu_usage', '91.2', 'HIGH', 'ops', 'ACKED', '2026-05-04 10:05', '', NOW(), NOW()),
('ALERT-107-002', 2, 2, 'memory_usage', '92.8', 'MEDIUM', 'sre', 'OPEN', '2026-05-04 10:08', '', NOW(), NOW());

INSERT INTO alert_notify (event_id, channel_name, receiver, send_status, send_time, message_text, status, created_time, updated_time) VALUES
(1, '短信', 'ops', 'SUCCESS', '2026-05-04 10:06', '订单生产01 CPU高负载', 'ACTIVE', NOW(), NOW()),
(2, '企业微信', 'sre', 'SUCCESS', '2026-05-04 10:09', '会员预发01 内存高水位', 'ACTIVE', NOW(), NOW());

INSERT INTO incident_ticket (ticket_no, event_id, server_id, title, assignee, priority, status, solution, closed_at, created_time, updated_time) VALUES
('INC-107-001', 1, 1, '订单生产CPU高负载', 'ops', 'HIGH', 'ASSIGNED', '扩容实例并检查慢查询', '', NOW(), NOW()),
('INC-107-002', 2, 2, '会员预发内存高水位', 'sre', 'MEDIUM', 'OPEN', '', '', NOW(), NOW());

INSERT INTO maintenance_window (window_name, server_id, start_time, end_time, owner_name, reason, status, created_time, updated_time) VALUES
('订单生产维护窗口', 1, '2026-05-05 22:00', '2026-05-05 23:00', 'ops', '内核参数调整', 'DRAFT', NOW(), NOW()),
('会员预发维护窗口', 2, '2026-05-06 20:00', '2026-05-06 21:00', 'sre', '容量演练', 'DRAFT', NOW(), NOW());

INSERT INTO capacity_plan (server_id, plan_name, cpu_usage, memory_usage, disk_usage, suggestion, status, created_time, updated_time) VALUES
(1, '订单生产扩容建议', '78%', '83%', '62%', '建议增加2台4C16G实例', 'DRAFT', NOW(), NOW()),
(2, '会员预发降配建议', '35%', '42%', '39%', '建议降低规格节省成本', 'PUBLISHED', NOW(), NOW());

INSERT INTO dashboard_widget (widget_name, widget_type, metric_code, refresh_second, sort_no, status, created_time, updated_time) VALUES
('CPU趋势', 'LINE', 'cpu_usage', 60, 1, 'ACTIVE', NOW(), NOW()),
('告警级别分布', 'PIE', 'alert_severity', 120, 2, 'ACTIVE', NOW(), NOW());

INSERT INTO operation_log (username, action, target_type, target_id, detail, ip_address, created_time, updated_time) VALUES
('admin', '登录系统', 'sys_user', 1, '管理员登录平台', '127.0.0.1', NOW(), NOW()),
('ops', '确认告警', 'alert_event', 1, '确认CPU高负载告警', '127.0.0.1', NOW(), NOW());
